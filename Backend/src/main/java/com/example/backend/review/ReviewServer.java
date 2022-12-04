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
import java.util.HashMap;
import java.util.Map;

@Controller
@ServerEndpoint(value = "/review/{netId}/{id}")
public class ReviewServer {
    private static Map<Session, SessionState> sessionUserMap = new HashMap<>();
    private static Map<SessionState, Session> usernameSessionMap = new HashMap<>();

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
        SessionState state = new SessionState(ur.findByNetId(netId), mir.findById(Long.parseLong(id)));
        sessionUserMap.put(session, state);
        usernameSessionMap.put(state, session);
    }

    @OnMessage
    public void onMessage(Session session, String message) throws JsonProcessingException {
        SessionState state = sessionUserMap.get(session);
        logger.info("(" + session.getId() + ") " + state.getUser().getNetId() + " sent message: " + message);

        Review review = objectMapper.readValue(message, Review.class);
        review.setUser(state.getUser());
        rr.save(review);
    }

    @OnClose
    public void onClose(Session session) {
        SessionState state = sessionUserMap.get(session);

        usernameSessionMap.remove(state);
        sessionUserMap.remove(session);

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
