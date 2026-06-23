package com.example.Project_With_AI.period_tracking.repositorys;

import com.example.Project_With_AI.period_tracking.entitys.DailyLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyLogRepository extends JpaRepository<DailyLog, Long> {
}
