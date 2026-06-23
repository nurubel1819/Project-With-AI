package com.example.Project_With_AI.period_tracking.entitys;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "period_start")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PeriodStart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private List<String> patientGoals;

    private Long StartDateLastPeriod;

    private String cycleRegularity;

    private List<String> medicalBackground;

    private List<String> birthControl;
}
