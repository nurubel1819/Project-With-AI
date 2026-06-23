package com.example.Project_With_AI.period_tracking.dtos;

import com.example.Project_With_AI.period_tracking.enums.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DailyLogDTO {
    private Long userId;

    private Long logDate;

    private PeriodFlowIntensity flowIntensity;

    private PeriodDischargeColor dischargeColor;

    private List<PeriodPhysicalSymptom> physicalSymptoms;

    private List<PeriodMentalHealth> mentalHealthSymptoms;

    private List<PeriodBiologicalSign> biologicalSigns;
}
