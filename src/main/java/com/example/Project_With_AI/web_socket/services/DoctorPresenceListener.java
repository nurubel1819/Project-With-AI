package com.example.Project_With_AI.web_socket.services;

import com.example.Project_With_AI.auth.entity.Role;
import com.example.Project_With_AI.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import java.security.Principal;

@Component
@RequiredArgsConstructor
public class DoctorPresenceListener {

    private final DoctorPresenceService presenceService;
    private final UserRepository userRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @EventListener
    public void handleConnect(SessionConnectedEvent event) {
        Principal principal = event.getUser();
        System.out.println("Connected principal: " + (principal != null ? principal.getName() : "NULL"));

        if (principal == null) return;

        userRepository.findByEmail(principal.getName())
                .filter(u -> u.getRole() == Role.DOCTOR)
                .ifPresent(doctor -> {
                    System.out.println("Doctor marked online: " + doctor.getId());
                    presenceService.markOnline(doctor.getId());
                    broadcastLiveDoctors();
                });
    }

    @EventListener
    public void handleDisconnect(SessionDisconnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        Principal principal = accessor.getUser();
        if (principal == null) return;

        userRepository.findByEmail(principal.getName())
                .filter(u -> u.getRole() == Role.DOCTOR)
                .ifPresent(doctor -> {
                    presenceService.markOffline(doctor.getId());
                    broadcastLiveDoctors();
                });
    }

    private void broadcastLiveDoctors() {
        messagingTemplate.convertAndSend("/topic/doctors/live", presenceService.getOnlineDoctorIds());
    }
}
