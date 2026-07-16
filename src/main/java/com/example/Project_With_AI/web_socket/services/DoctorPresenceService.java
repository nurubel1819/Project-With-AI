package com.example.Project_With_AI.web_socket.services;


import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class DoctorPresenceService {

    // key = doctorUserId, value = active session count (একই ডাক্তার একাধিক ট্যাব খুলতে পারে)
    private final Map<Long, Integer> onlineDoctors = new ConcurrentHashMap<>();

    public void markOnline(Long doctorUserId) {
        onlineDoctors.merge(doctorUserId, 1, Integer::sum);
    }

    public void markOffline(Long doctorUserId) {
        onlineDoctors.computeIfPresent(doctorUserId, (id, count) -> count > 1 ? count - 1 : null);
    }

    public boolean isOnline(Long doctorUserId) {
        return onlineDoctors.containsKey(doctorUserId);
    }

    public Set<Long> getOnlineDoctorIds() {
        return onlineDoctors.keySet();
    }
}
