package com.example.backend.review;

import com.example.backend.user.User;
import com.example.backend.user.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Controller
@ServerEndpoint(value = "/review/{netId}/{id}")
public class ReviewServer {
    private static Map<Session, SessionState> sessionStateMap = new HashMap<>();
    private static Map<SessionState, Session> stateSessionMap = new HashMap<>();

    private final Logger logger = LoggerFactory.getLogger(ReviewServer.class);

    private ObjectMapper objectMapper = new ObjectMapper();

    private static ReviewRepository rr;
    private static MenuItemRepository mir;
    private static UserRepository ur;

    @Autowired
    public void setMessageRepository(ReviewRepository rr, MenuItemRepository mir, UserRepository urr) {
        ReviewServer.rr = rr;
        ReviewServer.mir = mir;
        ReviewServer.ur = urr;
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("netId") String netId, @PathParam("id") String id) {
        logger.info("(" + session.getId() + ") " + netId + " connected to review server");

        mir.findById(Long.parseLong(id));
        User user = ur.findByNetId(netId);
        MenuItem menuItem = mir.findById(Long.parseLong(id));

        if (user == null || menuItem == null) {
            logger.info("(" + session.getId() + ") " + netId + " failed to connect to review server");
            throw new IllegalArgumentException("User or menu item not found");
        }

        SessionState state = new SessionState(user, menuItem);
        sessionStateMap.put(session, state);
        stateSessionMap.put(state, session);

        ArrayList<Review> reviews = rr.findAllByMenuItem(menuItem);
        reviews.forEach((review) -> {
            try {
                session.getBasicRemote().sendText(objectMapper.writeValueAsString(new ReviewResponse(review)));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @OnMessage
    public void onMessage(Session session, String message) throws JsonProcessingException {
        SessionState state = sessionStateMap.get(session);
        logger.info("(" + session.getId() + ") " + state.getUser().getNetId() + " sent message: " + message);

        MenuItem menuItem = state.getMenuItem();
        Review review = rr.findByUserAndMenuItem(state.getUser(), state.getMenuItem());
        Review payload = objectMapper.readValue(message, Review.class);
        if (review == null) { // Review does not exist
            review = payload;
            review.setUser(state.getUser());
            review.setMenuItem(state.getMenuItem());
            menuItem.incrementNumRatings(); // Only increase num of ratings if review is new
        } else { // Review exists
            review.setComment(payload.getComment());
            review.setRating(payload.getRating());
        }
        rr.save(review);

        ArrayList<Review> allReviews = rr.findAllByMenuItem(menuItem);
        int totalCount = 0;
        for (Review r : allReviews) {
            totalCount += r.getRating();
        }
        float rating = (float) totalCount / menuItem.getNumRatings();
        float roundedRating = Math.round(rating * 2) / (float) 2.0;
        menuItem.setCachedRating(roundedRating);
        mir.save(menuItem);

        // Broadcast review to all clients
        final ReviewResponse reviewResponse = new ReviewResponse(review);
        sessionStateMap.forEach((s, ss) -> {
            try {
                s.getBasicRemote().sendText(objectMapper.writeValueAsString(reviewResponse));
            } catch (Exception e) {
                logger.error("Failed to send message to client", e);
                e.printStackTrace();
            }
        });
    }

    @OnClose
    public void onClose(Session session) {
        SessionState state = sessionStateMap.get(session);

        stateSessionMap.remove(state);
        sessionStateMap.remove(session);

        logger.info("(" + session.getId() + ") " + state.user.getNetId() + " disconnected from review server");
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        logger.error("(" + session.getId() + ") " + throwable.getMessage());
    }

    @Getter
    @Setter
    @AllArgsConstructor
    private class SessionState {
        public User user;
        public MenuItem menuItem;
    }
}
