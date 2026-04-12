package com.example.Project_With_AI.nicu.entity;

import com.example.Project_With_AI.hospital.entity.Hospital;
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
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
	name = "nicu_beds",
	uniqueConstraints = {
		@UniqueConstraint(name = "uk_nicu_bed_hospital_bed_code", columnNames = {"hospital_id", "bed_code"}),
		@UniqueConstraint(name = "uk_nicu_bed_qr_code", columnNames = {"qr_code"})
	}
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NicuBed {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "hospital_id", nullable = false)
	private Hospital hospital;

	@Column(name = "bed_code", nullable = false, length = 50)
	private String bedCode;

	@Column(name = "qr_code", nullable = false, length = 255, unique = true)
	private String qrCode;

	@Column(nullable = false)
	private boolean occupied;

	@Column(length = 500)
	private String extraInformation;

	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@Column(nullable = false)
	private LocalDateTime updatedAt;

	@PrePersist
	void onCreate() {
		LocalDateTime now = LocalDateTime.now();
		createdAt = now;
		updatedAt = now;
	}

	@PreUpdate
	void onUpdate() {
		updatedAt = LocalDateTime.now();
	}
}
