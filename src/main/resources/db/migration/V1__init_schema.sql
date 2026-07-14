
/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
DROP TABLE IF EXISTS `app_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `app_users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `email` varchar(120) NOT NULL,
  `full_name` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` enum('ADMIN','USER') NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK4vj92ux8a2eehds1mdvmks473` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `daily_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `daily_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `discharge_color` enum('BROWN','RED') DEFAULT NULL,
  `flow_intensity` enum('HEAVY','LIGHT','MEDIUM','SPOTTING') DEFAULT NULL,
  `log_date` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `daily_log_biological_signs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `daily_log_biological_signs` (
  `daily_log_id` bigint NOT NULL,
  `biological_sign` enum('BASAL_BODY_TEMPERATURE_LOGGING','CERVICAL_MUCUS_TRACKING') DEFAULT NULL,
  KEY `FK8nk0u7lrpkka0oj4y2hd2dd7v` (`daily_log_id`),
  CONSTRAINT `FK8nk0u7lrpkka0oj4y2hd2dd7v` FOREIGN KEY (`daily_log_id`) REFERENCES `daily_log` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `daily_log_mental_health`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `daily_log_mental_health` (
  `daily_log_id` bigint NOT NULL,
  `mental_health` enum('ANXIETY','BRAIN_FOG','ENERGY_LEVEL_CHANGES','IRRITABILITY','MOOD_SWINGS') DEFAULT NULL,
  KEY `FK4ytevldidmtiusw2tnyclebkb` (`daily_log_id`),
  CONSTRAINT `FK4ytevldidmtiusw2tnyclebkb` FOREIGN KEY (`daily_log_id`) REFERENCES `daily_log` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `daily_log_physical_symptoms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `daily_log_physical_symptoms` (
  `daily_log_id` bigint NOT NULL,
  `symptom` enum('ACNE','APPETITE_CHANGES','BACK_PAIN','BLOATING','BREAST_TENDERNESS','CRAMPS','HEADACHE') DEFAULT NULL,
  KEY `FKqpvfxy58h8da6kulmjc8506i7` (`daily_log_id`),
  CONSTRAINT `FKqpvfxy58h8da6kulmjc8506i7` FOREIGN KEY (`daily_log_id`) REFERENCES `daily_log` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `hospital_reviews`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hospital_reviews` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `message` varchar(1000) DEFAULT NULL,
  `rating` int NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9xaqg1x5f176m5mc016o0yn9q` (`user_id`),
  CONSTRAINT `FK9xaqg1x5f176m5mc016o0yn9q` FOREIGN KEY (`user_id`) REFERENCES `app_users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `hospitals`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hospitals` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `address` varchar(255) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `description` varchar(500) DEFAULT NULL,
  `email` varchar(120) DEFAULT NULL,
  `latitude` double NOT NULL,
  `longitude` double NOT NULL,
  `name` varchar(150) NOT NULL,
  `phone_number` varchar(30) DEFAULT NULL,
  `updated_at` datetime(6) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `nicu_admissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nicu_admissions` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `admitted_at` datetime(6) NOT NULL,
  `child_name` varchar(120) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `date_of_birth` date NOT NULL,
  `diagnosis` varchar(255) DEFAULT NULL,
  `discharged_at` datetime(6) DEFAULT NULL,
  `guardian_name` varchar(120) DEFAULT NULL,
  `notes` varchar(500) DEFAULT NULL,
  `updated_at` datetime(6) NOT NULL,
  `nicu_bed_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKdob3f7dd4dlhygq756370h85g` (`nicu_bed_id`),
  CONSTRAINT `FKdob3f7dd4dlhygq756370h85g` FOREIGN KEY (`nicu_bed_id`) REFERENCES `nicu_beds` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `nicu_beds`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nicu_beds` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `bed_code` varchar(50) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `extra_information` varchar(500) DEFAULT NULL,
  `occupied` bit(1) NOT NULL,
  `qr_code` varchar(255) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `hospital_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_nicu_bed_hospital_bed_code` (`hospital_id`,`bed_code`),
  UNIQUE KEY `uk_nicu_bed_qr_code` (`qr_code`),
  CONSTRAINT `FKiojwqlt8cvx4luavamamtmgwo` FOREIGN KEY (`hospital_id`) REFERENCES `hospitals` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `period_profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `period_profile` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `cycle_regularity` enum('IRREGULAR','REGULAR','UNPREDICTABLE') DEFAULT NULL,
  `period_duration` int DEFAULT NULL,
  `period_start_date` datetime(6) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `period_profile_birth_controls`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `period_profile_birth_controls` (
  `profile_id` bigint NOT NULL,
  `birth_control` enum('INJECTION','IUD','PILL') DEFAULT NULL,
  KEY `FK6t7bbxcjf7dj135vyqwejw56x` (`profile_id`),
  CONSTRAINT `FK6t7bbxcjf7dj135vyqwejw56x` FOREIGN KEY (`profile_id`) REFERENCES `period_profile` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `period_profile_goals`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `period_profile_goals` (
  `profile_id` bigint NOT NULL,
  `goal` enum('GET_PREGNANT','MANAGE_SYMPTOMS_AND_MOODS','SPOT_SIGNS_OF_CONDITIONS','TRACK_MY_PERIOD') DEFAULT NULL,
  KEY `FK31gpvsk15e6kww0q1i2irox9b` (`profile_id`),
  CONSTRAINT `FK31gpvsk15e6kww0q1i2irox9b` FOREIGN KEY (`profile_id`) REFERENCES `period_profile` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `period_profile_medical_backgrounds`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `period_profile_medical_backgrounds` (
  `profile_id` bigint NOT NULL,
  `medical_background` enum('DIABETES','FIBROIDS','PCOS','THYROID_DISORDER') DEFAULT NULL,
  KEY `FK7wn7c9dxlkhv10vsd09ymo8bk` (`profile_id`),
  CONSTRAINT `FK7wn7c9dxlkhv10vsd09ymo8bk` FOREIGN KEY (`profile_id`) REFERENCES `period_profile` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `period_start`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `period_start` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `start_date_last_period` bigint DEFAULT NULL,
  `birth_control` json DEFAULT NULL,
  `cycle_regularity` varchar(255) DEFAULT NULL,
  `medical_background` json DEFAULT NULL,
  `patient_goals` json DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `review_likes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `review_likes` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `review_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_review_user_like` (`review_id`,`user_id`),
  KEY `FKsnq1pya9nvdwxw1hgriml2yib` (`user_id`),
  CONSTRAINT `FK68yiyd05kxkak0gmtow2tq5hw` FOREIGN KEY (`review_id`) REFERENCES `hospital_reviews` (`id`),
  CONSTRAINT `FKsnq1pya9nvdwxw1hgriml2yib` FOREIGN KEY (`user_id`) REFERENCES `app_users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

