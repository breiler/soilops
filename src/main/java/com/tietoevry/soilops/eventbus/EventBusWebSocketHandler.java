package com.tietoevry.soilops.eventbus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tietoevry.soilops.dto.ObservationResponse;
import com.tietoevry.soilops.model.Observation;
import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Log
@Component
public class EventBusWebSocketHandler extends TextWebSocketHandler {

    public static final String CHANNEL_NAME = "/eventbus";
    public static final String CHANNEL_NAME_SOCKJS = "/eventbus/sockjs";
    private static final List<WebSocketSession> sessions = new ArrayList<>();
    private ObjectMapper objectMapper;

    public EventBusWebSocketHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("Established connection: " + session.getId());
        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("Closed connection: " + session.getId() + " " + status);
        sessions.remove(session);
    }

    @Override

    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info("Got message: " + session.getId() + " " + message);
        session.sendMessage(message);
    }

    public void sendObservation(Observation observation) {
        ModelMapper modelMapper = new ModelMapper();
        ObservationResponse observationResponse = modelMapper.map(observation, ObservationResponse.class);

        try {
            String message = objectMapper.writeValueAsString(observationResponse);
            TextMessage textMessage = new TextMessage(message);
            sessions.forEach(sessions -> {
                try {
                    sessions.sendMessage(textMessage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}