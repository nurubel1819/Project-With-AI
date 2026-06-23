package com.example.Project_With_AI.period_tracking.controller;

import com.example.Project_With_AI.period_tracking.dtos.DailyLogDTO;
import com.example.Project_With_AI.period_tracking.services.DailyLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/period/tracking")
@RequiredArgsConstructor
public class PeriodTrackingController {

    private final DailyLogService dailyLogService;

    @PostMapping
    ResponseEntity<String> addNewPeriodTracking(){

        return ResponseEntity.ok("Period tracking added successfully");
    }

    @PostMapping("/save/daily/log")
    public ResponseEntity<String> saveDailyLog(
            @RequestBody DailyLogDTO request) {

        dailyLogService.saveDailyLog(request);
        return ResponseEntity.ok("Daily Log Saved Successfully!");
    }

    @GetMapping("/get/log/{logId}")
    public ResponseEntity<DailyLogDTO> getDailyLogById(
            @PathVariable Long logId) {

        DailyLogDTO dailyLogDTO = dailyLogService.getDailyLogById(logId);
        return ResponseEntity.ok(dailyLogDTO);
    }
}
