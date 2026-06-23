package com.example.Project_With_AI.period_tracking.services;

import com.example.Project_With_AI.period_tracking.dtos.DailyLogDTO;
import com.example.Project_With_AI.period_tracking.entitys.DailyLog;
import com.example.Project_With_AI.period_tracking.repositorys.DailyLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DailyLogService {

    private final DailyLogRepository dailyLogRepository;

    public void saveDailyLog(DailyLogDTO request) {

        DailyLog dailyLog = DailyLog.builder()
                .userId(request.getUserId())
                .logDate(request.getLogDate())
                .flowIntensity(request.getFlowIntensity())
                .dischargeColor(request.getDischargeColor())
                .physicalSymptoms(request.getPhysicalSymptoms())
                .mentalHealthSymptoms(request.getMentalHealthSymptoms())
                .biologicalSigns(request.getBiologicalSigns())
                .build();

        dailyLogRepository.save(dailyLog);
    }

    public DailyLogDTO getDailyLogById(Long id) {
        return dailyLogRepository.findById(id)
                .map(dailyLog -> DailyLogDTO.builder()
                        .userId(dailyLog.getUserId())
                        .logDate(dailyLog.getLogDate())
                        .flowIntensity(dailyLog.getFlowIntensity())
                        .dischargeColor(dailyLog.getDischargeColor())
                        .physicalSymptoms(dailyLog.getPhysicalSymptoms())
                        .mentalHealthSymptoms(dailyLog.getMentalHealthSymptoms())
                        .biologicalSigns(dailyLog.getBiologicalSigns())
                        .build()
                )
                .orElseThrow(() ->
                        new RuntimeException("Daily Log Not Found with id: " + id)
                );
    }

}
