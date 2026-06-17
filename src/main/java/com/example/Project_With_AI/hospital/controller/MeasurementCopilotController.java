package com.example.Project_With_AI.hospital.controller;

import com.example.Project_With_AI.hospital.dto.MeasurementTypeDto;
import com.example.Project_With_AI.hospital.enums.MeasurementType;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/copilot")
@RequiredArgsConstructor
@Tag(name = "Copilot", description = "Copilot management APIs")
public class MeasurementCopilotController {

    @GetMapping("/measurements")
    public ResponseEntity<List<MeasurementTypeDto>> getMeasurementType(Long userId){
        // return ResponseEntity.ok(List.of("Blood Pressure", "Heart Rate", "Respiratory Rate"));
        List<String> measurements = Arrays.stream(MeasurementType.values())
                .map(Enum::name)
                .toList();

        List<String> neededMeasurements = new ArrayList<>();
        neededMeasurements.add(MeasurementType.BLOOD_PRESSURE.name());
        neededMeasurements.add(MeasurementType.BLOOD_GLUCOSE.name());

        List<MeasurementTypeDto> measurementTypeDtos = new ArrayList<>();


        return ResponseEntity.ok(measurementTypeDtos);
    }

}
