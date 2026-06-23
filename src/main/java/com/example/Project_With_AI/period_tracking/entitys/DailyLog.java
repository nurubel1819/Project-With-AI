package com.example.Project_With_AI.period_tracking.entitys;

import com.example.Project_With_AI.period_tracking.enums.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "daily_log")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DailyLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;          // কোন user এর log

    private Long logDate;    // কোন দিনের log

    // ✅ Section 1: Flow Intensity
    @Enumerated(EnumType.STRING)
    private PeriodFlowIntensity flowIntensity;  // LIGHT, MEDIUM, HEAVY, SPOTTING

    // ✅ Section 2: Discharge Color
    @Enumerated(EnumType.STRING)
    private PeriodDischargeColor dischargeColor;  // RED, BROWN

    // ✅ Section 3: Physical Symptoms (Multiple select)
    @ElementCollection
    @CollectionTable(
            name = "daily_log_physical_symptoms",
            joinColumns = @JoinColumn(name = "daily_log_id")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "symptom")
    private List<PeriodPhysicalSymptom> physicalSymptoms;

    // ✅ Section 4: Mental Health & Energy (Multiple select)
    @ElementCollection
    @CollectionTable(
            name = "daily_log_mental_health",
            joinColumns = @JoinColumn(name = "daily_log_id")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "mental_health")
    private List<PeriodMentalHealth> mentalHealthSymptoms;

    // ✅ Section 5: Biological Signs (Multiple select)
    @ElementCollection
    @CollectionTable(
            name = "daily_log_biological_signs",
            joinColumns = @JoinColumn(name = "daily_log_id")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "biological_sign")
    private List<PeriodBiologicalSign> biologicalSigns;
}
