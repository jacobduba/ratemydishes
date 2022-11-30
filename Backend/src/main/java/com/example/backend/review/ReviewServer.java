package com.example.backend.review;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@ServerEndpoint("/review/{netId}/{menuItem}")
@Component
// WIP
public class ReviewServer {
    private static Map<Session, UserState> sessionUserMap = new HashMap<>();
    private static Map<UserState, Session> usernameSessionMap = new HashMap<>();

    private final Logger logger = LoggerFactory.getLogger(ReviewServer.class);

    @OnOpen
    public void onOpen(Session session, @PathParam("netId") String netId, @PathParam("menuItemName") String menuItemName) throws IOException {
        logger.info("(" + session.getId() + ") " + netId + " connected to review server");

        UserState state = new UserState(netId, menuItemName);
        sessionUserMap.put(session, state);
        usernameSessionMap.put(state, session);
    }

    @Getter
    @Setter
    @AllArgsConstructor
    private class UserState {
        public String netId;
        public String menuItemName;
    }
}
