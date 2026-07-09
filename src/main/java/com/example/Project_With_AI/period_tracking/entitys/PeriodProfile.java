package com.example.Project_With_AI.period_tracking.entitys;

import com.example.Project_With_AI.period_tracking.enums.PeriodBirthControl;
import com.example.Project_With_AI.period_tracking.enums.PeriodCycleRegularity;
import com.example.Project_With_AI.period_tracking.enums.PeriodMedicalBackground;
import com.example.Project_With_AI.period_tracking.enums.PeriodTrackingGoal;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Set;

//@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Entity
@Table(name = "period_profile")
@AllArgsConstructor
@NoArgsConstructor
public class PeriodProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    /**
     * Last period information
     */
    private Date periodStartDate;
    private Integer periodDuration;

    /**
     * Multiple Goals
     */
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "period_profile_goals",
            joinColumns = @JoinColumn(name = "profile_id")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "goal")
    private Set<PeriodTrackingGoal> goals;

    /**
     * REGULAR / IRREGULAR / UNPREDICTABLE
     */
    @Enumerated(EnumType.STRING)
    private PeriodCycleRegularity cycleRegularity;

    /**
     * Multiple Medical Backgrounds
     */
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "period_profile_medical_backgrounds",
            joinColumns = @JoinColumn(name = "profile_id")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "medical_background")
    private Set<PeriodMedicalBackground> medicalBackgrounds;

    /**
     * Multiple Birth Control Methods
     */
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "period_profile_birth_controls",
            joinColumns = @JoinColumn(name = "profile_id")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "birth_control")
    private Set<PeriodBirthControl> birthControls;
}
