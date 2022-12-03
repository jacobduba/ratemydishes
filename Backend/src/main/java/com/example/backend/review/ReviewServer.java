package com.example.backend.review;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.HashMap;
import java.util.Map;

@ServerEndpoint("/review/{netId}/{menuItemId}")
@Component
public class ReviewServer {
    private static Map<Session, SessionState> sessionUserMap = new HashMap<>();
    private static Map<SessionState, Session> usernameSessionMap = new HashMap<>();

    private final Logger logger = LoggerFactory.getLogger(ReviewServer.class);

    @OnOpen
    public void onOpen(Session session, @PathParam("netId") String netId, @PathParam("menuItemId") String menuItemId) {
        logger.info("(" + session.getId() + ") " + netId + " connected to review server");

        SessionState state = new SessionState(netId, menuItemId);
        sessionUserMap.put(session, state);
        usernameSessionMap.put(state, session);
    }

    @OnMessage
    public void onMessage(Session session, String message) {
        SessionState state = sessionUserMap.get(session);
        logger.info("(" + session.getId() + ") " + state.getNetId() + " sent message: " + message);
    }

    @OnClose
    public void onClose(Session session) {
        SessionState state = sessionUserMap.get(session);

        usernameSessionMap.remove(state);
        sessionUserMap.remove(session);

        logger.info("(" + session.getId() + ") " + state.getNetId() + " disconnected from review server");
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        logger.error("(" + session.getId() + ") " + throwable.getMessage());
    }

    @Getter
    @Setter
    @AllArgsConstructor
    private class SessionState {
        public String netId;
        public String menuItemId;
    }
}
