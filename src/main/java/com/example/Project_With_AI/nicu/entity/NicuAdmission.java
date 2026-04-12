package com.example.Project_With_AI.nicu.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "nicu_admissions")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NicuAdmission {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "nicu_bed_id", nullable = false)
	private NicuBed nicuBed;

	@Column(nullable = false, length = 120)
	private String childName;

	@Column(length = 120)
	private String guardianName;

	@Column(nullable = false)
	private LocalDate dateOfBirth;

	@Column(length = 255)
	private String diagnosis;

	@Column(length = 500)
	private String notes;

	@Column(nullable = false, updatable = false)
	private LocalDateTime admittedAt;

	private LocalDateTime dischargedAt;

	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@Column(nullable = false)
	private LocalDateTime updatedAt;

	@PrePersist
	void onCreate() {
		LocalDateTime now = LocalDateTime.now();
		if (admittedAt == null) {
			admittedAt = now;
		}
		createdAt = now;
		updatedAt = now;
	}

	@PreUpdate
	void onUpdate() {
		updatedAt = LocalDateTime.now();
	}
}
