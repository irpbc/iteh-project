CREATE DATABASE  IF NOT EXISTS `iteh_project` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `iteh_project`;
-- MySQL dump 10.13  Distrib 5.7.17, for macos10.12 (x86_64)
--
-- Host: localhost    Database: iteh_project
-- ------------------------------------------------------
-- Server version	5.6.35

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `DATABASECHANGELOG`
--

DROP TABLE IF EXISTS `DATABASECHANGELOG`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DATABASECHANGELOG` (
  `ID` varchar(255) NOT NULL,
  `AUTHOR` varchar(255) NOT NULL,
  `FILENAME` varchar(255) NOT NULL,
  `DATEEXECUTED` datetime NOT NULL,
  `ORDEREXECUTED` int(11) NOT NULL,
  `EXECTYPE` varchar(10) NOT NULL,
  `MD5SUM` varchar(35) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `COMMENTS` varchar(255) DEFAULT NULL,
  `TAG` varchar(255) DEFAULT NULL,
  `LIQUIBASE` varchar(20) DEFAULT NULL,
  `CONTEXTS` varchar(255) DEFAULT NULL,
  `LABELS` varchar(255) DEFAULT NULL,
  `DEPLOYMENT_ID` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DATABASECHANGELOG`
--

LOCK TABLES `DATABASECHANGELOG` WRITE;
/*!40000 ALTER TABLE `DATABASECHANGELOG` DISABLE KEYS */;
INSERT INTO `DATABASECHANGELOG` VALUES ('00000000000001','ivan','config/liquibase/changelog/00000000000000_initial_schema.xml','2018-01-13 14:38:28',1,'EXECUTED','7:466b1500ff94be5da480a73bfd670db2','sql; sql; loadData tableName=user; loadData tableName=authority; loadData tableName=user_authority; sql; sql','',NULL,'3.5.3',NULL,NULL,'5850708152'),('20180105115254-1','jhipster','config/liquibase/changelog/20180105115254_added_entity_Course.xml','2018-01-13 14:38:28',2,'EXECUTED','7:732e4e7b99c948aaf46e34fe23f6a65d','createTable tableName=course; createTable tableName=course_lecturers; addPrimaryKey tableName=course_lecturers','',NULL,'3.5.3',NULL,NULL,'5850708152'),('20180105115255-1','jhipster','config/liquibase/changelog/20180105115255_added_entity_SchoolYear.xml','2018-01-13 14:38:28',3,'EXECUTED','7:a4a1286468483c8edbf000afec4848c4','createTable tableName=school_year','',NULL,'3.5.3',NULL,NULL,'5850708152'),('20180105115257-1','jhipster','config/liquibase/changelog/20180105115257_added_entity_CourseEnrollment.xml','2018-01-13 14:38:28',4,'EXECUTED','7:c4aca28e9c34e37e3942210a275ed112','createTable tableName=course_enrollment','',NULL,'3.5.3',NULL,NULL,'5850708152'),('20180105115258-1','jhipster','config/liquibase/changelog/20180105115258_added_entity_SchoolYearEnrollment.xml','2018-01-13 14:38:28',5,'EXECUTED','7:9befa80772e744db77f2f54f6373710f','createTable tableName=school_year_enrollment','',NULL,'3.5.3',NULL,NULL,'5850708152'),('20180105115259-1','jhipster','config/liquibase/changelog/20180105115259_added_entity_Commitment.xml','2018-01-13 14:38:28',6,'EXECUTED','7:d30f84e175d245e739eaaff9af4fa64c','createTable tableName=commitment','',NULL,'3.5.3',NULL,NULL,'5850708152'),('20180105115300-1','jhipster','config/liquibase/changelog/20180105115300_added_entity_StudentCommitment.xml','2018-01-13 14:38:28',7,'EXECUTED','7:d5420889412cfada713747a51c3362b2','createTable tableName=student_commitment','',NULL,'3.5.3',NULL,NULL,'5850708152'),('20180105115302-1','jhipster','config/liquibase/changelog/20180105115302_added_entity_ExamPeriod.xml','2018-01-13 14:38:28',8,'EXECUTED','7:d784166a6d5c538c039af3bd18c42ddf','createTable tableName=exam_period','',NULL,'3.5.3',NULL,NULL,'5850708152'),('20180105115303-1','jhipster','config/liquibase/changelog/20180105115303_added_entity_Exam.xml','2018-01-13 14:38:28',9,'EXECUTED','7:b13569ffbd93ddf67682ec5df638662b','createTable tableName=exam','',NULL,'3.5.3',NULL,NULL,'5850708152'),('20180105115304-1','jhipster','config/liquibase/changelog/20180105115304_added_entity_StudentExam.xml','2018-01-13 14:38:28',10,'EXECUTED','7:1d78fe77e4587449a3489675aab646fe','createTable tableName=student_exam','',NULL,'3.5.3',NULL,NULL,'5850708152'),('20180105115305-1','jhipster','config/liquibase/changelog/20180105115305_added_entity_Semester.xml','2018-01-13 14:38:28',11,'EXECUTED','7:059cdb0ace83c4aa3cb5880711df7952','createTable tableName=semester','',NULL,'3.5.3',NULL,NULL,'5850708152'),('20180105115306-1','jhipster','config/liquibase/changelog/20180105115306_added_entity_OptionalCourseGroup.xml','2018-01-13 14:38:28',12,'EXECUTED','7:70300c4b5e7be6d67e9cb6e04a165917','createTable tableName=optional_course_group','',NULL,'3.5.3',NULL,NULL,'5850708152'),('20180105115254-2','jhipster','config/liquibase/changelog/20180105115254_added_entity_constraints_Course.xml','2018-01-13 14:38:28',13,'EXECUTED','7:9d919a05640646c8fccedb29154afe65','addForeignKeyConstraint baseTableName=course, constraintName=fk_course_semester_id, referencedTableName=semester; addForeignKeyConstraint baseTableName=course, constraintName=fk_course_optional_group_id, referencedTableName=optional_course_group; ...','',NULL,'3.5.3',NULL,NULL,'5850708152'),('20180105115257-2','jhipster','config/liquibase/changelog/20180105115257_added_entity_constraints_CourseEnrollment.xml','2018-01-13 14:38:28',14,'EXECUTED','7:e9f8573ff1b98167de74d211034433d0','addForeignKeyConstraint baseTableName=course_enrollment, constraintName=fk_course_enrollment_year_enrollment_id, referencedTableName=school_year_enrollment; addForeignKeyConstraint baseTableName=course_enrollment, constraintName=fk_course_enrollme...','',NULL,'3.5.3',NULL,NULL,'5850708152'),('20180105115258-2','jhipster','config/liquibase/changelog/20180105115258_added_entity_constraints_SchoolYearEnrollment.xml','2018-01-13 14:38:28',15,'EXECUTED','7:540de4fed4ca76cfd44f5de0a4ee62d2','addForeignKeyConstraint baseTableName=school_year_enrollment, constraintName=fk_school_year_enrollment_student_id, referencedTableName=user; addForeignKeyConstraint baseTableName=school_year_enrollment, constraintName=fk_school_year_enrollment_yea...','',NULL,'3.5.3',NULL,NULL,'5850708152'),('20180105115259-2','jhipster','config/liquibase/changelog/20180105115259_added_entity_constraints_Commitment.xml','2018-01-13 14:38:28',16,'EXECUTED','7:0b7509270203fcd12a88da83edbae034','addForeignKeyConstraint baseTableName=commitment, constraintName=fk_commitment_course_id, referencedTableName=course','',NULL,'3.5.3',NULL,NULL,'5850708152'),('20180105115300-2','jhipster','config/liquibase/changelog/20180105115300_added_entity_constraints_StudentCommitment.xml','2018-01-13 14:38:28',17,'EXECUTED','7:ff6a6e37df669331aad166026f8d019d','addForeignKeyConstraint baseTableName=student_commitment, constraintName=fk_student_commitment_enrollment_id, referencedTableName=course_enrollment; addForeignKeyConstraint baseTableName=student_commitment, constraintName=fk_student_commitment_com...','',NULL,'3.5.3',NULL,NULL,'5850708152'),('20180105115302-2','jhipster','config/liquibase/changelog/20180105115302_added_entity_constraints_ExamPeriod.xml','2018-01-13 14:38:28',18,'EXECUTED','7:e5517bb01acddea2db50166da2608fc0','addForeignKeyConstraint baseTableName=exam_period, constraintName=fk_exam_period_year_id, referencedTableName=school_year','',NULL,'3.5.3',NULL,NULL,'5850708152'),('20180105115303-2','jhipster','config/liquibase/changelog/20180105115303_added_entity_constraints_Exam.xml','2018-01-13 14:38:28',19,'EXECUTED','7:9a53ccb3eb3b51d386731422d6d6ffd1','addForeignKeyConstraint baseTableName=exam, constraintName=fk_exam_period_id, referencedTableName=exam_period; addForeignKeyConstraint baseTableName=exam, constraintName=fk_exam_course_id, referencedTableName=course','',NULL,'3.5.3',NULL,NULL,'5850708152'),('20180105115304-2','jhipster','config/liquibase/changelog/20180105115304_added_entity_constraints_StudentExam.xml','2018-01-13 14:38:28',20,'EXECUTED','7:00a1baa8dd715b18a810ef32ec97ce53','addForeignKeyConstraint baseTableName=student_exam, constraintName=fk_student_exam_student_id, referencedTableName=user; addForeignKeyConstraint baseTableName=student_exam, constraintName=fk_student_exam_exam_id, referencedTableName=exam; addForei...','',NULL,'3.5.3',NULL,NULL,'5850708152'),('20180105115305-2','jhipster','config/liquibase/changelog/20180105115305_added_entity_constraints_Semester.xml','2018-01-13 14:38:28',21,'EXECUTED','7:4c1f618a9a7397c4c21e71dac5f1f8da','addForeignKeyConstraint baseTableName=semester, constraintName=fk_semester_year_id, referencedTableName=school_year','',NULL,'3.5.3',NULL,NULL,'5850708152'),('20180105115306-2','jhipster','config/liquibase/changelog/20180105115306_added_entity_constraints_OptionalCourseGroup.xml','2018-01-13 14:38:28',22,'EXECUTED','7:a210b89b04d2cb3dc97c398982ee9ba6','addForeignKeyConstraint baseTableName=optional_course_group, constraintName=fk_optional_course_group_semester_id, referencedTableName=semester','',NULL,'3.5.3',NULL,NULL,'5850708152'),('20180126170000','ivan','config/liquibase/changelog/20180126170000_SchoolYear_isCurrent.xml','2018-01-26 17:00:34',23,'EXECUTED','7:67d0f557677f6ce7515636ec1c7c9038','addColumn tableName=school_year','',NULL,'3.5.3',NULL,NULL,'6982434098'),('20180127190000','ivan','config/liquibase/changelog/20180127190000_User_code.xml','2018-01-28 13:21:02',24,'EXECUTED','7:244114e01382561e21b66c7136824eda','addColumn tableName=user','',NULL,'3.5.3',NULL,NULL,'7142062406'),('20180202195353-1','jhipster','config/liquibase/changelog/20180202195353_added_entity_FacebookPostProposal.xml','2018-02-03 12:23:52',25,'EXECUTED','7:1b6c5d3c14608bbf29ddd862554dcf29','createTable tableName=facebook_post_proposal; dropDefaultValue columnName=time, tableName=facebook_post_proposal','',NULL,'3.5.3',NULL,NULL,'7657032763'),('20180202195353-2','jhipster','config/liquibase/changelog/20180202195353_added_entity_constraints_FacebookPostProposal.xml','2018-02-03 12:23:52',26,'EXECUTED','7:1229c404d4f3df46f210ce163634023f','addForeignKeyConstraint baseTableName=facebook_post_proposal, constraintName=fk_facebook_post_proposal_student_id, referencedTableName=user','',NULL,'3.5.3',NULL,NULL,'7657032763');
/*!40000 ALTER TABLE `DATABASECHANGELOG` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `DATABASECHANGELOGLOCK`
--

DROP TABLE IF EXISTS `DATABASECHANGELOGLOCK`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DATABASECHANGELOGLOCK` (
  `ID` int(11) NOT NULL,
  `LOCKED` bit(1) NOT NULL,
  `LOCKGRANTED` datetime DEFAULT NULL,
  `LOCKEDBY` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DATABASECHANGELOGLOCK`
--

LOCK TABLES `DATABASECHANGELOGLOCK` WRITE;
/*!40000 ALTER TABLE `DATABASECHANGELOGLOCK` DISABLE KEYS */;
INSERT INTO `DATABASECHANGELOGLOCK` VALUES (1,'\0',NULL,NULL);
/*!40000 ALTER TABLE `DATABASECHANGELOGLOCK` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `authority`
--

DROP TABLE IF EXISTS `authority`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `authority` (
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `authority`
--

LOCK TABLES `authority` WRITE;
/*!40000 ALTER TABLE `authority` DISABLE KEYS */;
INSERT INTO `authority` VALUES ('ROLE_ADMIN'),('ROLE_LECTURER'),('ROLE_SERVICE'),('ROLE_STUDENT'),('ROLE_USER');
/*!40000 ALTER TABLE `authority` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `commitment`
--

DROP TABLE IF EXISTS `commitment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `commitment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `max_points` decimal(10,2) NOT NULL,
  `course_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_commitment_course_id` (`course_id`),
  CONSTRAINT `fk_commitment_course_id` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `commitment`
--

LOCK TABLES `commitment` WRITE;
/*!40000 ALTER TABLE `commitment` DISABLE KEYS */;
/*!40000 ALTER TABLE `commitment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `course`
--

DROP TABLE IF EXISTS `course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `course` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `espb_points` int(11) NOT NULL,
  `year_of_studies` int(11) NOT NULL,
  `optional` bit(1) NOT NULL,
  `semester_id` bigint(20) NOT NULL,
  `optional_group_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_course_semester_id` (`semester_id`),
  KEY `fk_course_optional_group_id` (`optional_group_id`),
  CONSTRAINT `fk_course_optional_group_id` FOREIGN KEY (`optional_group_id`) REFERENCES `optional_course_group` (`id`),
  CONSTRAINT `fk_course_semester_id` FOREIGN KEY (`semester_id`) REFERENCES `semester` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course`
--

LOCK TABLES `course` WRITE;
/*!40000 ALTER TABLE `course` DISABLE KEYS */;
INSERT INTO `course` VALUES (1,'Matematika 1',6,1,'\0',1,NULL),(2,'Ekonomija',6,1,'\0',1,NULL),(3,'Menadžment',6,1,'\0',1,NULL),(4,'Osnove informaciono-komunikacionih tehnologija',5,1,'\0',1,NULL),(5,'Psihologija',4,1,'\0',1,NULL);
/*!40000 ALTER TABLE `course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `course_enrollment`
--

DROP TABLE IF EXISTS `course_enrollment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `course_enrollment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `total_points` decimal(10,2) NOT NULL,
  `grade` int(11) DEFAULT NULL,
  `completed` bit(1) NOT NULL,
  `year_enrollment_id` bigint(20) NOT NULL,
  `course_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_course_enrollment_year_enrollment_id` (`year_enrollment_id`),
  KEY `fk_course_enrollment_course_id` (`course_id`),
  CONSTRAINT `fk_course_enrollment_course_id` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`),
  CONSTRAINT `fk_course_enrollment_year_enrollment_id` FOREIGN KEY (`year_enrollment_id`) REFERENCES `school_year_enrollment` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course_enrollment`
--

LOCK TABLES `course_enrollment` WRITE;
/*!40000 ALTER TABLE `course_enrollment` DISABLE KEYS */;
INSERT INTO `course_enrollment` VALUES (1,0.00,NULL,'\0',1,1),(2,0.00,NULL,'\0',1,2),(3,0.00,NULL,'\0',1,3);
/*!40000 ALTER TABLE `course_enrollment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `course_lecturers`
--

DROP TABLE IF EXISTS `course_lecturers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `course_lecturers` (
  `lecturers_id` bigint(20) NOT NULL,
  `courses_id` bigint(20) NOT NULL,
  PRIMARY KEY (`courses_id`,`lecturers_id`),
  KEY `fk_course_lecturers_lecturers_id` (`lecturers_id`),
  CONSTRAINT `fk_course_lecturers_courses_id` FOREIGN KEY (`courses_id`) REFERENCES `course` (`id`),
  CONSTRAINT `fk_course_lecturers_lecturers_id` FOREIGN KEY (`lecturers_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course_lecturers`
--

LOCK TABLES `course_lecturers` WRITE;
/*!40000 ALTER TABLE `course_lecturers` DISABLE KEYS */;
/*!40000 ALTER TABLE `course_lecturers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exam`
--

DROP TABLE IF EXISTS `exam`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `exam` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `time` datetime NOT NULL,
  `period_id` bigint(20) NOT NULL,
  `course_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_exam_period_id` (`period_id`),
  KEY `fk_exam_course_id` (`course_id`),
  CONSTRAINT `fk_exam_course_id` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`),
  CONSTRAINT `fk_exam_period_id` FOREIGN KEY (`period_id`) REFERENCES `exam_period` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exam`
--

LOCK TABLES `exam` WRITE;
/*!40000 ALTER TABLE `exam` DISABLE KEYS */;
INSERT INTO `exam` VALUES (1,'2018-04-13 12:00:00',3,1),(2,'2018-04-13 14:00:00',3,2),(3,'2018-04-13 10:00:00',3,3),(4,'2018-04-13 08:00:00',3,4),(5,'2018-04-13 16:00:00',3,5);
/*!40000 ALTER TABLE `exam` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exam_period`
--

DROP TABLE IF EXISTS `exam_period`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `exam_period` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `year_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_exam_period_year_id` (`year_id`),
  CONSTRAINT `fk_exam_period_year_id` FOREIGN KEY (`year_id`) REFERENCES `school_year` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exam_period`
--

LOCK TABLES `exam_period` WRITE;
/*!40000 ALTER TABLE `exam_period` DISABLE KEYS */;
INSERT INTO `exam_period` VALUES (1,'Januar 2017/2018','2018-01-01','2018-01-31',4),(2,'Februar 2017/2018','2018-02-01','2018-02-28',4),(3,'April 2017/2018','2018-04-01','2018-04-20',4);
/*!40000 ALTER TABLE `exam_period` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `facebook_post_proposal`
--

DROP TABLE IF EXISTS `facebook_post_proposal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `facebook_post_proposal` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `kind` varchar(255) NOT NULL,
  `data` varchar(255) NOT NULL,
  `time` timestamp NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  `student_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_facebook_post_proposal_student_id` (`student_id`),
  CONSTRAINT `fk_facebook_post_proposal_student_id` FOREIGN KEY (`student_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `facebook_post_proposal`
--

LOCK TABLES `facebook_post_proposal` WRITE;
/*!40000 ALTER TABLE `facebook_post_proposal` DISABLE KEYS */;
INSERT INTO `facebook_post_proposal` VALUES (1,'EXAM_PASSED','{\"course\": \"Matematika 1\", \"evaluatedBy\": \"Rade Lazović\", \"period\": \"April 2017/2018\", \"grade\": 7}','2018-01-01 07:00:00',5);
/*!40000 ALTER TABLE `facebook_post_proposal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `optional_course_group`
--

DROP TABLE IF EXISTS `optional_course_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `optional_course_group` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `semester_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_optional_course_group_semester_id` (`semester_id`),
  CONSTRAINT `fk_optional_course_group_semester_id` FOREIGN KEY (`semester_id`) REFERENCES `semester` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `optional_course_group`
--

LOCK TABLES `optional_course_group` WRITE;
/*!40000 ALTER TABLE `optional_course_group` DISABLE KEYS */;
/*!40000 ALTER TABLE `optional_course_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `persistent_audit_event`
--

DROP TABLE IF EXISTS `persistent_audit_event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `persistent_audit_event` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `type` varchar(255) DEFAULT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_pers_aud_evt_dat` (`user_id`,`date`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `persistent_audit_event`
--

LOCK TABLES `persistent_audit_event` WRITE;
/*!40000 ALTER TABLE `persistent_audit_event` DISABLE KEYS */;
INSERT INTO `persistent_audit_event` VALUES (1,2,'AUTHENTICATION_SUCCESS','2018-01-13 13:39:18'),(2,2,'AUTHENTICATION_SUCCESS','2018-01-13 13:42:02'),(3,2,'AUTHENTICATION_SUCCESS','2018-01-16 18:18:16'),(4,2,'AUTHENTICATION_SUCCESS','2018-01-17 11:44:32'),(5,2,'AUTHENTICATION_SUCCESS','2018-01-17 15:02:54'),(6,2,'AUTHENTICATION_SUCCESS','2018-01-19 10:10:30'),(7,2,'AUTHENTICATION_SUCCESS','2018-01-19 17:37:36'),(8,2,'AUTHENTICATION_SUCCESS','2018-01-19 17:48:56'),(9,2,'AUTHENTICATION_SUCCESS','2018-01-19 18:45:48'),(10,2,'AUTHENTICATION_SUCCESS','2018-01-19 19:54:20'),(11,2,'AUTHENTICATION_SUCCESS','2018-01-20 12:11:56'),(12,2,'AUTHENTICATION_SUCCESS','2018-01-20 14:08:34'),(13,2,'AUTHENTICATION_SUCCESS','2018-01-20 14:52:47'),(14,2,'AUTHENTICATION_SUCCESS','2018-01-21 11:48:21'),(15,2,'AUTHENTICATION_SUCCESS','2018-01-22 11:36:45'),(16,2,'AUTHENTICATION_SUCCESS','2018-01-23 10:03:01'),(17,2,'AUTHENTICATION_SUCCESS','2018-01-23 10:03:13'),(18,2,'AUTHENTICATION_SUCCESS','2018-01-23 12:23:58'),(19,2,'AUTHENTICATION_SUCCESS','2018-01-23 21:36:22'),(20,2,'AUTHENTICATION_SUCCESS','2018-01-24 09:10:17'),(21,2,'AUTHENTICATION_SUCCESS','2018-01-24 14:22:59'),(22,2,'AUTHENTICATION_SUCCESS','2018-01-24 15:06:05'),(23,2,'AUTHENTICATION_SUCCESS','2018-01-25 09:08:12'),(24,2,'AUTHENTICATION_SUCCESS','2018-01-25 09:08:12'),(25,2,'AUTHENTICATION_SUCCESS','2018-01-25 09:13:43'),(26,2,'AUTHENTICATION_SUCCESS','2018-01-25 10:59:31'),(27,2,'AUTHENTICATION_SUCCESS','2018-01-27 11:44:25'),(28,2,'AUTHENTICATION_SUCCESS','2018-01-27 15:20:14'),(29,4,'AUTHENTICATION_SUCCESS','2018-01-27 17:39:44'),(30,2,'AUTHENTICATION_SUCCESS','2018-01-27 17:40:00'),(31,4,'AUTHENTICATION_SUCCESS','2018-01-27 17:42:05'),(32,2,'AUTHENTICATION_SUCCESS','2018-01-28 12:29:06'),(33,2,'AUTHENTICATION_SUCCESS','2018-01-29 09:51:34'),(34,5,'AUTHENTICATION_SUCCESS','2018-01-29 10:42:43'),(35,5,'AUTHENTICATION_SUCCESS','2018-01-31 22:54:56'),(36,2,'AUTHENTICATION_SUCCESS','2018-01-31 23:01:05'),(37,4,'AUTHENTICATION_SUCCESS','2018-01-31 23:01:14'),(38,4,'AUTHENTICATION_SUCCESS','2018-02-01 10:21:17'),(39,20090197,'AUTHENTICATION_FAILURE','2018-02-02 13:25:42'),(40,4,'AUTHENTICATION_SUCCESS','2018-02-02 13:25:51'),(41,5,'AUTHENTICATION_SUCCESS','2018-02-02 13:26:14'),(42,5,'AUTHENTICATION_SUCCESS','2018-02-02 13:52:25'),(43,5,'AUTHENTICATION_SUCCESS','2018-02-02 18:53:15'),(44,4,'AUTHENTICATION_SUCCESS','2018-02-02 19:10:37'),(45,2,'AUTHENTICATION_SUCCESS','2018-02-02 19:26:31'),(46,5,'AUTHENTICATION_SUCCESS','2018-02-03 12:02:43'),(47,4,'AUTHENTICATION_SUCCESS','2018-02-03 12:21:53'),(48,5,'AUTHENTICATION_SUCCESS','2018-02-07 09:52:21'),(49,5,'AUTHENTICATION_SUCCESS','2018-02-07 16:03:02'),(50,5,'AUTHENTICATION_SUCCESS','2018-02-07 17:06:28'),(51,5,'AUTHENTICATION_SUCCESS','2018-02-08 11:03:21');
/*!40000 ALTER TABLE `persistent_audit_event` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `persistent_audit_evt_data`
--

DROP TABLE IF EXISTS `persistent_audit_evt_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `persistent_audit_evt_data` (
  `event_id` bigint(20) NOT NULL,
  `name` varchar(150) NOT NULL DEFAULT '',
  `value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`event_id`,`name`),
  KEY `idx_pers_aud_evt_dat_evtid` (`event_id`),
  CONSTRAINT `persistent_audit_evt_data_ibfk_1` FOREIGN KEY (`event_id`) REFERENCES `persistent_audit_event` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `persistent_audit_evt_data`
--

LOCK TABLES `persistent_audit_evt_data` WRITE;
/*!40000 ALTER TABLE `persistent_audit_evt_data` DISABLE KEYS */;
INSERT INTO `persistent_audit_evt_data` VALUES (39,'message','Bad credentials'),(39,'type','org.springframework.security.authentication.BadCredentialsException');
/*!40000 ALTER TABLE `persistent_audit_evt_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `school_year`
--

DROP TABLE IF EXISTS `school_year`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `school_year` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `is_current` bit(1) DEFAULT b'0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `school_year`
--

LOCK TABLES `school_year` WRITE;
/*!40000 ALTER TABLE `school_year` DISABLE KEYS */;
INSERT INTO `school_year` VALUES (4,'2017/2018','2018-01-09','2018-01-25',''),(5,'2016/2017','2018-01-16','2018-01-26','\0'),(6,'2015/2016','2018-01-13','2018-01-15','\0'),(7,'2014/2015','2018-01-25','2018-01-25','\0'),(8,'2013/2014','2018-01-17','2018-01-18','\0'),(9,'2012/2013','2018-01-23','2018-01-18','\0'),(10,'2011/2012','2018-01-19','2018-01-17','\0'),(11,'2010/2011','2018-01-26','2018-01-25','\0'),(12,'2009/2010','2018-01-23','2018-01-11','\0'),(13,'2008/2009','2018-01-24','2018-01-18','\0'),(14,'2007/2008','2018-01-17','2018-01-10','\0');
/*!40000 ALTER TABLE `school_year` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `school_year_enrollment`
--

DROP TABLE IF EXISTS `school_year_enrollment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `school_year_enrollment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `average_grade` decimal(10,2) DEFAULT NULL,
  `espb_points_declared` int(11) NOT NULL,
  `espb_points_attained` int(11) NOT NULL,
  `student_id` bigint(20) NOT NULL,
  `year_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_school_year_enrollment_student_id` (`student_id`),
  KEY `fk_school_year_enrollment_year_id` (`year_id`),
  CONSTRAINT `fk_school_year_enrollment_student_id` FOREIGN KEY (`student_id`) REFERENCES `user` (`id`),
  CONSTRAINT `fk_school_year_enrollment_year_id` FOREIGN KEY (`year_id`) REFERENCES `school_year` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `school_year_enrollment`
--

LOCK TABLES `school_year_enrollment` WRITE;
/*!40000 ALTER TABLE `school_year_enrollment` DISABLE KEYS */;
INSERT INTO `school_year_enrollment` VALUES (1,6.00,60,0,5,4),(2,6.00,60,0,6,4);
/*!40000 ALTER TABLE `school_year_enrollment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `semester`
--

DROP TABLE IF EXISTS `semester`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `semester` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `year_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_semester_year_id` (`year_id`),
  CONSTRAINT `fk_semester_year_id` FOREIGN KEY (`year_id`) REFERENCES `school_year` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `semester`
--

LOCK TABLES `semester` WRITE;
/*!40000 ALTER TABLE `semester` DISABLE KEYS */;
INSERT INTO `semester` VALUES (1,'Zimski 2017/2018',4),(2,'Letnji 2017/2018',4);
/*!40000 ALTER TABLE `semester` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `social_user_connection`
--

DROP TABLE IF EXISTS `social_user_connection`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `social_user_connection` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `provider_id` varchar(255) NOT NULL,
  `provider_user_id` varchar(255) NOT NULL,
  `rank` bigint(20) NOT NULL,
  `display_name` varchar(255) DEFAULT NULL,
  `profile_url` varchar(255) DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `access_token` varchar(255) NOT NULL,
  `secret` varchar(255) DEFAULT NULL,
  `refresh_token` varchar(255) DEFAULT NULL,
  `expire_time` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_social` (`user_id`,`provider_id`,`provider_user_id`),
  UNIQUE KEY `unique_social_rank` (`user_id`,`provider_id`,`rank`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `social_user_connection`
--

LOCK TABLES `social_user_connection` WRITE;
/*!40000 ALTER TABLE `social_user_connection` DISABLE KEYS */;
INSERT INTO `social_user_connection` VALUES (3,5,'facebook','111194109700070',1,'Ivan Test','https://www.facebook.com/app_scoped_user_id/111194109700070/','https://graph.facebook.com/v2.5/111194109700070/picture','EAACE8mkNF6EBAOhCViViZAS4ZBukeFCwI9EniO9u2FMcbbhoxylKcfNYQxd3tVpboAlZCsV77eVmtIUJZBrRWsGJAHDQZBmvII4155GljmUbaqKCPRVuZAC2cSQQbpQy5adV6CPGwpYMffdLiXC3ZB0BPpwSrZBgdJiBdDurcltFgQLXUkvDaiFh',NULL,NULL,1523275087266);
/*!40000 ALTER TABLE `social_user_connection` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student_commitment`
--

DROP TABLE IF EXISTS `student_commitment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `student_commitment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `points` decimal(10,2) DEFAULT NULL,
  `enrollment_id` bigint(20) NOT NULL,
  `commitment_id` bigint(20) NOT NULL,
  `evaluated_by_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_student_commitment_enrollment_id` (`enrollment_id`),
  KEY `fk_student_commitment_commitment_id` (`commitment_id`),
  KEY `fk_student_commitment_evaluated_by_id` (`evaluated_by_id`),
  CONSTRAINT `fk_student_commitment_commitment_id` FOREIGN KEY (`commitment_id`) REFERENCES `commitment` (`id`),
  CONSTRAINT `fk_student_commitment_enrollment_id` FOREIGN KEY (`enrollment_id`) REFERENCES `course_enrollment` (`id`),
  CONSTRAINT `fk_student_commitment_evaluated_by_id` FOREIGN KEY (`evaluated_by_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student_commitment`
--

LOCK TABLES `student_commitment` WRITE;
/*!40000 ALTER TABLE `student_commitment` DISABLE KEYS */;
/*!40000 ALTER TABLE `student_commitment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student_exam`
--

DROP TABLE IF EXISTS `student_exam`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `student_exam` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `attended` bit(1) DEFAULT NULL,
  `grade` int(11) DEFAULT NULL,
  `student_id` bigint(20) NOT NULL,
  `exam_id` bigint(20) NOT NULL,
  `evaluated_by_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_student_exam_student_id` (`student_id`),
  KEY `fk_student_exam_exam_id` (`exam_id`),
  KEY `fk_student_exam_evaluated_by_id` (`evaluated_by_id`),
  CONSTRAINT `fk_student_exam_evaluated_by_id` FOREIGN KEY (`evaluated_by_id`) REFERENCES `user` (`id`),
  CONSTRAINT `fk_student_exam_exam_id` FOREIGN KEY (`exam_id`) REFERENCES `exam` (`id`),
  CONSTRAINT `fk_student_exam_student_id` FOREIGN KEY (`student_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student_exam`
--

LOCK TABLES `student_exam` WRITE;
/*!40000 ALTER TABLE `student_exam` DISABLE KEYS */;
INSERT INTO `student_exam` VALUES (7,'',7,5,1,7);
/*!40000 ALTER TABLE `student_exam` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `type` varchar(3) NOT NULL,
  `login` varchar(100) NOT NULL,
  `password_hash` varchar(60) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `full_name` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `activated` tinyint(1) NOT NULL DEFAULT '0',
  `lang_key` varchar(6) DEFAULT NULL,
  `activation_key` varchar(20) DEFAULT NULL,
  `reset_key` varchar(20) DEFAULT NULL,
  `reset_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `code` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `login` (`login`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `idx_user_login` (`login`),
  UNIQUE KEY `idx_user_email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'SY','system','$2a$10$mE.qmcV0mFU5NcKh73TZx.z4ueI/.bDWbj0T1BYyqP481kGGarKLG','System','System','System System','system@localhost','',1,'sr',NULL,NULL,'2018-01-13 13:38:28',''),(2,'AD','admin','$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC','Admin','Admin','Admin Admin','admin@localhost','',1,'sr',NULL,NULL,'2018-01-13 13:38:28',''),(4,'SR','djani','$2a$10$0dCNWAg9oATomMh1416DYOSgHYzs97PwApOXkfrplFvK4QZmw1KCm','Radiša','Trajković','Radiša Trajković','ivan295+djani@gmail.com',NULL,1,'sr',NULL,'27418690156010117762','2018-01-27 17:39:36',''),(5,'ST','20090197','$2a$10$Fxu7kxCvspqyPPtUnmfNyOp4lfCRGPxzB8AthkQXl1EdnZuKqFe7q','Ivan','Rašić','Ivan Rašić','ivan_quygaym_test@tfbnw.net',NULL,1,'sr',NULL,'54265629084755011752','2018-02-05 11:59:47','197/09I'),(6,'ST','20090196','$2a$10$5ki0ufo2BmqsnB4u6lchrOMn0O9Sb5ExcEhbsOzXWHzBToMOPIA1y','Duško','Radić','Duško Radić','ivan295+dusko@gmail.com',NULL,1,'sr',NULL,'14956293631700709335','2018-02-01 15:34:41','196/09I'),(7,'LC','rlazovic','$2a$10$1rY8weZa0AeJvLfdflsbtulkyZ1w8UkBirQQ1pRz8xoPi.eKdpScW','Rade','Lazović','Rade Lazović','ivan295+lazovic@gmail.com',NULL,1,'sr',NULL,'17221963677928034860','2018-02-02 19:32:30',NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_authority`
--

DROP TABLE IF EXISTS `user_authority`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_authority` (
  `user_id` bigint(20) NOT NULL,
  `authority_name` varchar(50) NOT NULL,
  PRIMARY KEY (`user_id`,`authority_name`),
  KEY `fk_ua_authority` (`authority_name`),
  CONSTRAINT `user_authority_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `user_authority_ibfk_2` FOREIGN KEY (`authority_name`) REFERENCES `authority` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_authority`
--

LOCK TABLES `user_authority` WRITE;
/*!40000 ALTER TABLE `user_authority` DISABLE KEYS */;
INSERT INTO `user_authority` VALUES (1,'ROLE_ADMIN'),(2,'ROLE_ADMIN'),(1,'ROLE_LECTURER'),(2,'ROLE_LECTURER'),(4,'ROLE_LECTURER'),(7,'ROLE_LECTURER'),(1,'ROLE_SERVICE'),(2,'ROLE_SERVICE'),(4,'ROLE_SERVICE'),(1,'ROLE_STUDENT'),(2,'ROLE_STUDENT'),(4,'ROLE_STUDENT'),(5,'ROLE_STUDENT'),(6,'ROLE_STUDENT'),(1,'ROLE_USER'),(2,'ROLE_USER'),(4,'ROLE_USER'),(5,'ROLE_USER'),(6,'ROLE_USER'),(7,'ROLE_USER');
/*!40000 ALTER TABLE `user_authority` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-02-09 15:50:59
