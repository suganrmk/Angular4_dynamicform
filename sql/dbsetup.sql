DROP DATABASE IF EXISTS `perficient`;

CREATE DATABASE perficient;

USE perficient;

GRANT ALL PRIVILEGES ON perficient.* TO perfuser@localhost IDENTIFIED BY 'perfpwd' WITH GRANT OPTION;

--
-- Definition of table `designations`
--

DROP TABLE IF EXISTS `designations`;
CREATE TABLE `designations` (
  `pk` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `designation` varchar(100) NOT NULL,
  `sbu` varchar(45) NOT NULL,
  `active` BOOLEAN default false NOT NULL,
  `dt_created` datetime NOT NULL,
  `created_by` int(10) unsigned NOT NULL,
  `dt_modified` datetime NOT NULL,
  `modified_by` int(10) unsigned NOT NULL,
  PRIMARY KEY (`pk`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

--
-- Definition of table `employee`
--

DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee` (
  `pk` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `employee_id` varchar(45) NOT NULL,
  `firstname` varchar(45) NOT NULL,
  `lastname` varchar(45) NOT NULL,
  `middlename` varchar(45) DEFAULT NULL,
  `dob` date DEFAULT NULL,
  `contact_no` varchar(250) DEFAULT NULL,
  `emergency_contact_no` varchar(25) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `nationality` varchar(45) DEFAULT NULL,
  `current_address` varchar(500) NOT NULL,
  `permanent_address` varchar(250) DEFAULT NULL,
  `pincode` int(6) unsigned DEFAULT NULL,
  `blood_group` varchar(10) DEFAULT NULL,
  `pan_card_no` varchar(20) DEFAULT NULL,
  `img_src` varchar(45) DEFAULT NULL,
  `designation` int(10) unsigned NOT NULL,
  `designation_effective_date` date DEFAULT NULL,
  `joindate` date NOT NULL,
  `supervisor` int(10) unsigned NOT NULL,
  `department` varchar(45) DEFAULT NULL,
  `billable` int(1) unsigned DEFAULT NULL,
  `skills` varchar(255) DEFAULT NULL,
  `last_working_date` date DEFAULT NULL,
  `gender` varchar(10) NOT NULL,
  `city` varchar(45) DEFAULT NULL,
  `location` varchar(45) DEFAULT NULL,
  `flag` BOOLEAN default false NOT NULL,
  `active` BOOLEAN default false NOT NULL,
  `adlogin` BOOLEAN default false NOT NULL,
  `dt_created` datetime DEFAULT NULL,
  `dt_modified` datetime DEFAULT NULL,
  `created_by` int(10) unsigned NOT NULL,
  `modified_by` int(10) unsigned NOT NULL,
  PRIMARY KEY (`pk`),
  KEY `FK_employee_1` (`designation`),
  KEY `FK_employee_2` (`supervisor`),
  CONSTRAINT `FK_employee_1` FOREIGN KEY (`designation`) REFERENCES `designations` (`pk`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_employee_2` FOREIGN KEY (`supervisor`) REFERENCES `employee` (`pk`)
) ENGINE=InnoDB AUTO_INCREMENT=137 DEFAULT CHARSET=latin1;


--
-- Definition of table `projects`
--

DROP TABLE IF EXISTS `projects`;
CREATE TABLE `projects` (
  `pk` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `project_name` varchar(45) NOT NULL,
  `st_date` datetime NOT NULL,
  `end_date` datetime ,
  `active` BOOLEAN default false NOT NULL,
  `dt_created` datetime NOT NULL,
  `created_by` int(10) unsigned NOT NULL,
  `dt_modified` datetime NOT NULL,
  `modified_by` int(10) unsigned NOT NULL,
  PRIMARY KEY (`pk`),
  KEY `FK_created_by_projects` (`created_by`),
  KEY `FK_modified_by_projects` (`modified_by`),
  CONSTRAINT `FK_created_by_projects` FOREIGN KEY (`created_by`) REFERENCES `employee` (`pk`),
  CONSTRAINT `FK_modified_by_projects` FOREIGN KEY (`modified_by`) REFERENCES `employee` (`pk`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Definition of table `roles`
--

DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles` (
  `pk` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `rolename` varchar(45) NOT NULL,
  `active` BOOLEAN default false NOT NULL,
  `dt_created` datetime NOT NULL,
  `created_by` int(10) unsigned NOT NULL,
  `dt_modified` datetime NOT NULL,
  `modified_by` int(10) unsigned NOT NULL,
  PRIMARY KEY (`pk`),
  KEY `FK_created_by_roles` (`created_by`),
  KEY `FK_modified_by_roles` (`modified_by`),
  CONSTRAINT `FK_created_by_roles` FOREIGN KEY (`created_by`) REFERENCES `employee` (`pk`),
  CONSTRAINT `FK_modified_by_roles` FOREIGN KEY (`modified_by`) REFERENCES `employee` (`pk`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Definition of table `holidays`
--

DROP TABLE IF EXISTS `holidays`;
CREATE TABLE `holidays` (
  `pk` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `year` int(4) NOT NULL,
  `holiday_name` varchar(25) NOT NULL,
  `holiday_date` date NOT NULL,
  `active` BOOLEAN default false NOT NULL,
  `dt_created` datetime NOT NULL,
  `created_by` int(10) unsigned NOT NULL,
  `dt_modified` datetime NOT NULL,
  `modified_by` int(10) unsigned NOT NULL,
  PRIMARY KEY (`pk`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Definition of table `roles_components`
--

CREATE TABLE `roles_components` (
  `pk` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `roles_pk`  int(10) unsigned NOT NULL,
  `admMn` BOOLEAN default false NOT NULL,
  `mgmtMn` BOOLEAN default false NOT NULL,
  `jtPg` BOOLEAN default false NOT NULL,
  `jtSvBtn` BOOLEAN default false NOT NULL,
  `jtUpBtn` BOOLEAN default false NOT NULL,
  `jtDlBtn` BOOLEAN default false NOT NULL,
  `rlPg` BOOLEAN default false NOT NULL,
  `rlSvBtn` BOOLEAN default false NOT NULL,
  `rlUpBtn` BOOLEAN default false NOT NULL,
  `rlDlBtn` BOOLEAN default false NOT NULL,
  `empPg` BOOLEAN default false NOT NULL,
  `empSvBtn` BOOLEAN default false NOT NULL,
  `empUpBtn` BOOLEAN default false NOT NULL,
  `empDlBtn` BOOLEAN default false NOT NULL,
  `erPg` BOOLEAN default false NOT NULL,
  `erApBtn` BOOLEAN default false NOT NULL,
  `hdPg` BOOLEAN default false NOT NULL,
  `hdSvBtn` BOOLEAN default false NOT NULL,
  `hdUpBtn` BOOLEAN default false NOT NULL,
  `hdDlBtn` BOOLEAN default false NOT NULL,
  `mlPg` BOOLEAN default false NOT NULL,
  `prPg` BOOLEAN default false NOT NULL,
  `prSvBtn` BOOLEAN default false NOT NULL,
  `prUpBtn` BOOLEAN default false NOT NULL,
  `prDlBtn` BOOLEAN default false NOT NULL,
  `pmPg` BOOLEAN default false NOT NULL,
  `pmSvBtn` BOOLEAN default false NOT NULL,
  `pmUpBtn` BOOLEAN default false NOT NULL,
  `pmDlBtn` BOOLEAN default false NOT NULL,
  `repMn` BOOLEAN default false NOT NULL,
  `jtRepPg` BOOLEAN default false NOT NULL,
  `ptoRepPg` BOOLEAN default false NOT NULL,
  `wfhRepPg` BOOLEAN default false NOT NULL,
  `ebsRepPg` BOOLEAN default false NOT NULL,
  `tktRepPg` BOOLEAN default false NOT NULL,
  `sdPg` BOOLEAN default false NOT NULL,
  `sdUpBtn` BOOLEAN default false NOT NULL,
  `finMn` BOOLEAN default false NOT NULL,
  `itruwin` BOOLEAN default false NOT NULL,
  `itrrept` BOOLEAN default false NOT NULL,
  `rstpwdpg` BOOLEAN default false NOT NULL,
  `ptoUpBtn` BOOLEAN default false NOT NULL,
  `ptoDlBtn` BOOLEAN default false NOT NULL,
  `wfhUpBtn` BOOLEAN default false NOT NULL,
  `wfhDlBtn` BOOLEAN default false NOT NULL,
  `taaMn` BOOLEAN default false NOT NULL,
  `active` BOOLEAN default false NOT NULL,
  `dt_created` datetime NOT NULL,
  `created_by` int(10) unsigned NOT NULL,
  `dt_modified` datetime NOT NULL,
  `modified_by` int(10) unsigned NOT NULL,
  PRIMARY KEY (`pk`),
  KEY `FK_created_by_role_components` (`created_by`),
  KEY `FK_modified_by_role_components` (`modified_by`),
  KEY `FK_roles_pk_roles_components` (`roles_pk`),
  CONSTRAINT `roles_pk_roles_components` FOREIGN KEY (`roles_pk`) REFERENCES `roles` (`pk`),
  CONSTRAINT `FK_created_by_role_components` FOREIGN KEY (`created_by`) REFERENCES `employee` (`pk`),
  CONSTRAINT `FK_modified_by_role_components` FOREIGN KEY (`modified_by`) REFERENCES `employee` (`pk`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

--
-- Definition of table `employee_designation_history`
--

DROP TABLE IF EXISTS `employee_designation_history`;
CREATE TABLE `employee_designation_history` (
  `pk` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `employee_pk` int(10) unsigned NOT NULL,
  `designation_pk` int(10) unsigned NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date ,
  `active` BOOLEAN default false NOT NULL,
  `dt_created` datetime NOT NULL,
  `dt_modified` datetime NOT NULL,
  `created_by` int(10) unsigned NOT NULL,
  `modified_by` int(10) unsigned NOT NULL,
  PRIMARY KEY (`pk`),
  KEY `FK_employee_designation_history_1` (`employee_pk`),
  KEY `FK_employee_designation_history_2` (`designation_pk`),
  KEY `FK_created_by_edh` (`created_by`),
  KEY `FK_modified_by_edh` (`modified_by`),
  CONSTRAINT `FK_created_by_edh` FOREIGN KEY (`created_by`) REFERENCES `employee` (`pk`),
  CONSTRAINT `FK_modified_by_edh` FOREIGN KEY (`modified_by`) REFERENCES `employee` (`pk`),
  CONSTRAINT `FK_employee_designation_history_1` FOREIGN KEY (`employee_pk`) REFERENCES `employee` (`pk`),
  CONSTRAINT `FK_employee_designation_history_2` FOREIGN KEY (`designation_pk`) REFERENCES `designations` (`pk`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Definition of table `ebs_employee_leaves_details`
--

DROP TABLE IF EXISTS `ebs_employee_leaves`;
CREATE TABLE `ebs_employee_leaves` (
  `pk` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `employee_pk` int(10) unsigned NOT NULL,
  `applied_by_pk` int(10) unsigned NOT NULL,
  `request_type` varchar(45) NOT NULL,
  `request_title` varchar(100) NOT NULL,
  `comments` varchar(100) NOT NULL,
  `leave_dt` date NOT NULL,
  `dt_from` datetime NOT NULL,
  `dt_from_half` varchar(10),
  `dt_end` datetime NOT NULL,
  `dt_end_half` varchar(10),
  `hours` int(5) NOT NULL,
  `active` BOOLEAN default false NOT NULL,
  `dt_created` datetime NOT NULL,
  `dt_modified` datetime NOT NULL,
  `created_by` int(10) unsigned NOT NULL,
  `modified_by` int(10) unsigned NOT NULL,
  PRIMARY KEY (`pk`),
  UNIQUE KEY `unique_index`(`employee_pk`, `request_type`, `leave_dt`),
  KEY `FK_employee_pk_ebs` (`employee_pk`),
  KEY `FK_created_by_ebs` (`created_by`),
  KEY `FK_modified_by_ebs` (`modified_by`),
  CONSTRAINT `FK_created_by_ebs` FOREIGN KEY (`created_by`) REFERENCES `employee` (`pk`),
  CONSTRAINT `FK_modified_by_ebs` FOREIGN KEY (`modified_by`) REFERENCES `employee` (`pk`),
  CONSTRAINT `employee_leaves_ebs` FOREIGN KEY (`employee_pk`) REFERENCES `employee` (`pk`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Definition of table `employee_leaves`
--

DROP TABLE IF EXISTS `employee_leaves`;
CREATE TABLE `employee_leaves` (
  `pk` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `employee_pk` int(10) unsigned NOT NULL,
  `applied_by_pk` int(10) unsigned NOT NULL,
  `request_type` varchar(45) NOT NULL,
  `request_title` varchar(100) NOT NULL,
  `comments` varchar(500) NOT NULL,
  `dt_from` datetime NOT NULL,
  `dt_from_half` varchar(10),
  `dt_end` datetime NOT NULL,
  `dt_end_half` varchar(10),
  `hours` int(5) NOT NULL,
  `mail_sequence` int(2) NOT NULL,
  `active` BOOLEAN default false NOT NULL,
  `dt_created` datetime NOT NULL,
  `dt_modified` datetime NOT NULL,
  `created_by` int(10) unsigned NOT NULL,
  `modified_by` int(10) unsigned NOT NULL,
  PRIMARY KEY (`pk`),
  KEY `FK_employee_pk` (`employee_pk`),
  KEY `FK_created_by_el` (`created_by`),
  KEY `FK_modified_by_el` (`modified_by`),
  CONSTRAINT `FK_created_by_el` FOREIGN KEY (`created_by`) REFERENCES `employee` (`pk`),
  CONSTRAINT `FK_modified_by_el` FOREIGN KEY (`modified_by`) REFERENCES `employee` (`pk`),
  CONSTRAINT `employee_leaves_ibfk_1` FOREIGN KEY (`employee_pk`) REFERENCES `employee` (`pk`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


--
-- Definition of table `employee_leaves_details`
--

DROP TABLE IF EXISTS `employee_leaves_details`;
CREATE TABLE `employee_leaves_details` (
  `pk` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `employee_leaves_pk` int(10) unsigned NOT NULL,
  `leave_dt` date NOT NULL,
  `hours` int(5) NOT NULL,  
  `active` BOOLEAN default false NOT NULL,
  `dt_created` datetime NOT NULL,
  `dt_modified` datetime NOT NULL,
  `created_by` int(10) unsigned NOT NULL,
  `modified_by` int(10) unsigned NOT NULL,
  PRIMARY KEY (`pk`),
  KEY `FK_employee_leaves_pk` (`employee_leaves_pk`),
  KEY `FK_created_by_eld` (`created_by`),
  KEY `FK_modified_by_eld` (`modified_by`),
  CONSTRAINT `FK_created_by_eld` FOREIGN KEY (`created_by`) REFERENCES `employee` (`pk`),
  CONSTRAINT `FK_modified_by_eld` FOREIGN KEY (`modified_by`) REFERENCES `employee` (`pk`),
  CONSTRAINT `FK_employee_leaves_pk` FOREIGN KEY (`employee_leaves_pk`) REFERENCES `employee_leaves` (`pk`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


--
-- Definition of table `employee_leave_balance`
--
DROP TABLE IF EXISTS `employee_leave_balance`;
CREATE TABLE `employee_leave_balance` (
  `pk` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `employee_pk` int(10) unsigned NOT NULL,
  `year` int(4) NOT NULL,
  `op_bal` int(4) NOT NULL,
  `jan` int(4) NOT NULL,
  `feb` int(4) NOT NULL,
  `mar` int(4) NOT NULL,
  `apr` int(4) NOT NULL,
  `may` int(4) NOT NULL,
  `jun` int(4) NOT NULL,
  `jul` int(4) NOT NULL,
  `aug` int(4) NOT NULL,
  `sep` int(4) NOT NULL,
  `oct` int(4) NOT NULL,
  `nov` int(4) NOT NULL,
  `decem` int(4) NOT NULL,
  `active` BOOLEAN default false NOT NULL,
  `dt_created` datetime NOT NULL,
  `dt_modified` datetime NOT NULL,
  `created_by` int(10) unsigned NOT NULL,
  `modified_by` int(10) unsigned NOT NULL,
  PRIMARY KEY (`pk`),
  UNIQUE KEY `unique_index`(`employee_pk`, `year`),
  KEY `FK_employee_pk` (`employee_pk`),
  KEY `FK_created_by_elb` (`created_by`),
  KEY `FK_modified_by_elb` (`modified_by`),
  CONSTRAINT `FK_created_by_elb` FOREIGN KEY (`created_by`) REFERENCES `employee` (`pk`),
  CONSTRAINT `FK_modified_by_elb` FOREIGN KEY (`modified_by`) REFERENCES `employee` (`pk`),
  CONSTRAINT `employee__elb_fk` FOREIGN KEY (`employee_pk`) REFERENCES `employee` (`pk`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


--
-- Definition of table `employee_login`
--

DROP TABLE IF EXISTS `employee_login`;
CREATE TABLE `employee_login` (
  `pk` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `employee_pk` int(10) unsigned NOT NULL,
  `email_id` varchar(45) NOT NULL,
  `pwd` varchar(50) NOT NULL,
  `emp_lock` tinyint(1) NOT NULL,
  `last_login` datetime,
  `active` BOOLEAN default false NOT NULL,
  `dt_created` datetime,
  `dt_modified` datetime,
  `created_by` int(10) unsigned NOT NULL,
  `modified_by` int(10) unsigned NOT NULL,
  PRIMARY KEY (`pk`),
  KEY `FK_employee_login_1` (`employee_pk`),
  KEY `FK_created_by_emplog` (`created_by`),
  KEY `FK_modified_by_emplog` (`modified_by`),
  CONSTRAINT `FK_created_by_emplog` FOREIGN KEY (`created_by`) REFERENCES `employee` (`pk`),
  CONSTRAINT `FK_modified_by_emplog` FOREIGN KEY (`modified_by`) REFERENCES `employee` (`pk`),
  CONSTRAINT `FK_employee_login_1` FOREIGN KEY (`employee_pk`) REFERENCES `employee` (`pk`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Employee office entry
--
DROP TABLE IF EXISTS `employee_office_entry`;
CREATE TABLE `employee_office_entry` (
  `pk` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `employee_pk` int(10) unsigned NOT NULL,
  `in_date` date NOT NULL,
  `location` varchar(50) NOT NULL,
  `active` BOOLEAN default false NOT NULL,
  `dt_created` datetime,
  `dt_modified` datetime,
  `created_by` int(10) unsigned NOT NULL,
  `modified_by` int(10) unsigned NOT NULL,
  PRIMARY KEY (`pk`),
  UNIQUE KEY `unique_index`(`employee_pk`, `in_date`),
  KEY `FK_employee_entry` (`employee_pk`),
  KEY `FK_created_by_empentry` (`created_by`),
  KEY `FK_modified_by_empentry` (`modified_by`),
  CONSTRAINT `FK_created_by_empentry` FOREIGN KEY (`created_by`) REFERENCES `employee` (`pk`),
  CONSTRAINT `FK_modified_by_empentry` FOREIGN KEY (`modified_by`) REFERENCES `employee` (`pk`),
  CONSTRAINT `FK_employee_entry` FOREIGN KEY (`employee_pk`) REFERENCES `employee` (`pk`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


--
-- Definition of table `employee_projects`
--

DROP TABLE IF EXISTS `employee_projects`;
CREATE TABLE `employee_projects` (
  `pk` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `employee_pk` int(10) unsigned NOT NULL,
  `project_pk` int(10) unsigned NOT NULL,
  `dt_started` date NOT NULL,
  `dt_ended` date NOT NULL,
  `active` BOOLEAN default false NOT NULL,
  `dt_created` datetime NOT NULL,
  `dt_modified` datetime NOT NULL,
  `created_by` int(10) unsigned NOT NULL,
  `modified_by` int(10) unsigned NOT NULL,
  PRIMARY KEY (`pk`),
  KEY `employee_pk` (`employee_pk`),
  KEY `project_pk` (`project_pk`),
  KEY `FK_created_by_empproj` (`created_by`),
  KEY `FK_modified_by_empproj` (`modified_by`),
  CONSTRAINT `FK_created_by_empproj` FOREIGN KEY (`created_by`) REFERENCES `employee` (`pk`),
  CONSTRAINT `FK_modified_by_empproj` FOREIGN KEY (`modified_by`) REFERENCES `employee` (`pk`),
  CONSTRAINT `FK_employee_pk` FOREIGN KEY (`employee_pk`) REFERENCES `employee` (`pk`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_project_pk` FOREIGN KEY (`project_pk`) REFERENCES `projects` (`pk`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=FIXED;

--
-- Definition of table `employee_roles`
--

DROP TABLE IF EXISTS `employee_roles`;
CREATE TABLE `employee_roles` (
  `pk` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `employee_pk` int(10) unsigned NOT NULL,
  `role_pk` int(10) unsigned NOT NULL,
  `active` BOOLEAN default false NOT NULL,
  `dt_created` datetime NOT NULL,
  `created_by` int(10) unsigned NOT NULL,
  `dt_modified` datetime NOT NULL,
  `modified_by` int(10) unsigned NOT NULL,
  PRIMARY KEY (`pk`),
  KEY `employee_pk` (`employee_pk`),
  KEY `role_pk` (`role_pk`),
  KEY `FK_created_by_emprol` (`created_by`),
  KEY `FK_modified_by_emprol` (`modified_by`),
  CONSTRAINT `FK_created_by_emprol` FOREIGN KEY (`created_by`) REFERENCES `employee` (`pk`),
  CONSTRAINT `FK_modified_by_emprol` FOREIGN KEY (`modified_by`) REFERENCES `employee` (`pk`),
  CONSTRAINT `employee_roles_ibfk_1` FOREIGN KEY (`employee_pk`) REFERENCES `employee` (`pk`) ON DELETE CASCADE,
  CONSTRAINT `employee_roles_ibfk_2` FOREIGN KEY (`role_pk`) REFERENCES `roles` (`pk`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Definition of table `tickets`
--

DROP TABLE IF EXISTS `tickets`;
CREATE TABLE `tickets` (
  `pk` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `ticket_no` varchar(45) NOT NULL,
  `location` varchar(45) NOT NULL,
  `issueType` varchar(20) NOT NULL,
  `description` varchar(500) NOT NULL,
  `comments` varchar(100) NULL,
  `priority` varchar(20),
  `status` varchar(20) NOT NULL,
  `assigned_to` int(10) unsigned NOT NULL,
  `closed_on` datetime,
  `flag` BOOLEAN default false NOT NULL,
  `active` BOOLEAN default false NOT NULL,
  `dt_created` datetime NOT NULL,
  `created_by` int(10) unsigned NOT NULL,
  `dt_modified` datetime NOT NULL,
  `modified_by` int(10) unsigned NOT NULL,
  PRIMARY KEY (`pk`),
  KEY `FK_assigned_to` (`assigned_to`),
  KEY `FK_created_by_ticket` (`created_by`),
  KEY `FK_modified_by_ticket` (`modified_by`),
  CONSTRAINT `FK_assigned_to` FOREIGN KEY (`assigned_to`) REFERENCES `employee` (`pk`),
  CONSTRAINT `FK_created_by_ticket` FOREIGN KEY (`created_by`) REFERENCES `employee` (`pk`),
  CONSTRAINT `FK_modified_by_ticket` FOREIGN KEY (`modified_by`) REFERENCES `employee` (`pk`)
 ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Definition of table `ticket_attachments`
--

DROP TABLE IF EXISTS `ticket_attachments`;
CREATE TABLE `ticket_attachments` (
  `pk` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `ticket_pk` int(10) unsigned NOT NULL,
  `filename` varchar(75) NOT NULL,
  `active` BOOLEAN default false NOT NULL,
  `dt_created` datetime NOT NULL,
  `created_by` int(10) unsigned NOT NULL,
  `dt_modified` datetime NOT NULL,
  `modified_by` int(10) unsigned NOT NULL,
  PRIMARY KEY (`pk`),
  KEY `FK_ticket_pk` (`ticket_pk`),
  KEY `FK_created_by_ticket_attachments` (`created_by`),
  KEY `FK_modified_by_ticket_attachments` (`modified_by`),
  CONSTRAINT `FK_ticket_pk` FOREIGN KEY (`ticket_pk`) REFERENCES `tickets` (`pk`),
  CONSTRAINT `FK_created_by_ticket_attachments` FOREIGN KEY (`created_by`) REFERENCES `employee` (`pk`),
  CONSTRAINT `FK_modified_by_ticket_attachments` FOREIGN KEY (`modified_by`) REFERENCES `employee` (`pk`)
 ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Definition of table `ticket_attachments`
--

DROP TABLE IF EXISTS `ticket_comments`;
CREATE TABLE `ticket_comments` (
  `pk` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `ticket_pk` int(10) unsigned NOT NULL,
  `comment` varchar(500) NOT NULL,
  `active` BOOLEAN default false NOT NULL,
  `dt_created` datetime NOT NULL,
  `created_by` int(10) unsigned NOT NULL,
  `dt_modified` datetime NOT NULL,
  `modified_by` int(10) unsigned NOT NULL,
  PRIMARY KEY (`pk`),
  KEY `FK_ticket_comments_pk` (`ticket_pk`),
  KEY `FK_created_by_ticket_comments` (`created_by`),
  KEY `FK_modified_by_ticket_comments` (`modified_by`),
  CONSTRAINT `FK_ticket_comments_pk` FOREIGN KEY (`ticket_pk`) REFERENCES `tickets` (`pk`),
  CONSTRAINT `FK_created_by_ticket_comments` FOREIGN KEY (`created_by`) REFERENCES `employee` (`pk`),
  CONSTRAINT `FK_modified_by_ticket_comments` FOREIGN KEY (`modified_by`) REFERENCES `employee` (`pk`)
 ) ENGINE=InnoDB DEFAULT CHARSET=latin1;
 
--
-- Definition of table `notification`
--

DROP TABLE IF EXISTS `notification`;
CREATE TABLE `notification` (
  `pk` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `id_generic` int(10) unsigned NOT NULL,
  `notification_type` varchar(45) NOT NULL,
  `notification_to` int(10) unsigned NOT NULL, -- Shall we maintain Mailids or Employee Ids?
  `notification_status` varchar(45) NOT NULL,
  `comments` varchar(500),
  `flag` BOOLEAN default false NOT NULL,
  `active` BOOLEAN default false NOT NULL,
  `dt_created` datetime NOT NULL,
  `created_by` int(10) unsigned NOT NULL,
  `dt_modified` datetime NOT NULL,
  `modified_by` int(10) unsigned NOT NULL,
  PRIMARY KEY (`pk`),
  KEY `FK_notification_to_notify` (`notification_to`),
  KEY `FK_created_by_notify` (`created_by`),
  KEY `FK_modified_by_notify` (`modified_by`),
  CONSTRAINT `FK_notification_to_notify` FOREIGN KEY (`notification_to`) REFERENCES `employee` (`pk`),
  CONSTRAINT `FK_created_by_notify` FOREIGN KEY (`created_by`) REFERENCES `employee` (`pk`),
  CONSTRAINT `FK_modified_by_notify` FOREIGN KEY (`modified_by`) REFERENCES `employee` (`pk`)
 ) ENGINE=InnoDB DEFAULT CHARSET=latin1;
 
 --
-- Definition of table `email track`
--

DROP TABLE IF EXISTS `email_track`;
CREATE TABLE `email_track` (
  `pk` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `id_generic` int(10) unsigned NOT NULL,
  `mail_type` varchar(45) ,
  `mail_to` varchar(500) NOT NULL, 
  `mail_from` varchar(50) NOT NULL, 
  `mail_cc` varchar(256) ,
  `comments` varchar(2000),
  `subject` varchar(256),
  `attachment` varchar(150),
  `media_type` varchar(45),
  `priority` varchar(45),
  `from_date` datetime,
  `to_date` datetime,
  `mail_sequence` int(2) NOT NULL,
  `uid` varchar(40),
  `mail_status` varchar(45) NOT NULL,
  `request_type` varchar(15),
  `flag` BOOLEAN default false NOT NULL,
  `active` BOOLEAN default false NOT NULL,
  `dt_created` datetime NOT NULL,
  `created_by` int(10) unsigned NOT NULL,
  `dt_modified` datetime NOT NULL,
  `modified_by` int(10) unsigned NOT NULL,
  PRIMARY KEY (`pk`),
  KEY `FK_created_by_mail` (`created_by`),
  KEY `FK_modified_by_mail` (`modified_by`),
  CONSTRAINT `FK_created_by_mail` FOREIGN KEY (`created_by`) REFERENCES `employee` (`pk`),
  CONSTRAINT `FK_modified_by_mail` FOREIGN KEY (`modified_by`) REFERENCES `employee` (`pk`)
 ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `employee_backofficeinfo`;
CREATE TABLE `employee_backofficeinfo` (
  `pk` int(11) NOT NULL AUTO_INCREMENT,
  `employee_pk` int(10) unsigned NOT NULL,
  `offered_grosssalary` decimal(18,2) DEFAULT NULL,
  `offeredloyalty` decimal(18,2) DEFAULT NULL,
  `hired_for` varchar(200) DEFAULT NULL,
  `current_location` varchar(20) DEFAULT NULL,
  `work_location` varchar(20) DEFAULT NULL,
  `active` BOOLEAN default false NOT NULL,
  `dt_created` datetime NOT NULL,
  `dt_modified` datetime NOT NULL,
  `created_by` int(10) unsigned NOT NULL,
  `modified_by` int(10) unsigned NOT NULL,
  PRIMARY KEY (`pk`),
  KEY `fk_employeebackofficeinfo` (`employee_pk`),
  KEY `FK_created_by_eboi` (`created_by`),
  KEY `FK_modified_by_eboi` (`modified_by`),
  CONSTRAINT `fk_employeebackofficeinfo` FOREIGN KEY (`employee_pk`) REFERENCES `employee` (`pk`),
  CONSTRAINT `FK_created_by_eboi` FOREIGN KEY (`created_by`) REFERENCES `employee` (`pk`),
  CONSTRAINT `FK_modified_by_eboi` FOREIGN KEY (`modified_by`) REFERENCES `employee` (`pk`)  
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `employee_education`;
CREATE TABLE `employee_education` (
  `pk` int(11) NOT NULL AUTO_INCREMENT,
  `employee_pk` int(10) unsigned NOT NULL,
  `degree_name` varchar(20) DEFAULT NULL,
  `specialization` varchar(200) DEFAULT NULL,
  `course_type` varchar(20) DEFAULT NULL,
  `qualification` varchar(50) DEFAULT NULL,
  `college` varchar(200) DEFAULT NULL,
  `university` varchar(200) DEFAULT NULL,
  `year_passed` date NOT NULL,
  `percentage` decimal(4,2) DEFAULT NULL,
  `gap_days` int(4),
  `gap_reason` varchar(500) DEFAULT NULL,
  `active` BOOLEAN default false NOT NULL,
  `dt_created` datetime NOT NULL,
  `dt_modified` datetime NOT NULL,
  `created_by` int(10) unsigned NOT NULL,
  `modified_by` int(10) unsigned NOT NULL,
  PRIMARY KEY (`pk`),
  KEY `fk_employee_education` (`employee_pk`),
  KEY `FK_created_by_emp_edu` (`created_by`),
  KEY `FK_modified_by_emp_edu` (`modified_by`),
  CONSTRAINT `fk_employee_education` FOREIGN KEY (`employee_pk`) REFERENCES `employee` (`pk`),
  CONSTRAINT `FK_created_by_emp_edu` FOREIGN KEY (`created_by`) REFERENCES `employee` (`pk`),
  CONSTRAINT `FK_modified_by_emp_edu` FOREIGN KEY (`modified_by`) REFERENCES `employee` (`pk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `employee_workexperience`;
CREATE TABLE `employee_workexperience` (
  `pk` int(11) NOT NULL AUTO_INCREMENT,
  `employee_pk` int(10) unsigned NOT NULL,
  `organization_name` varchar(100) DEFAULT NULL,
  `organization_address` varchar(250) DEFAULT NULL,
  `designation` varchar(100) DEFAULT NULL,
  `startDate` date NOT NULL,
  `endDate` date  NOT NULL,
  `leavingReason` varchar(200) DEFAULT NULL,
  `contactPerson` varchar(200) DEFAULT NULL,
  `contactNumber` varchar(45) DEFAULT NULL,
  `gapDays` int(4) DEFAULT NULL,
  `gapReason` varchar(500) DEFAULT NULL,
  `active` BOOLEAN default false NOT NULL,
  `dt_created` datetime NOT NULL,
  `dt_modified` datetime NOT NULL,
  `created_by` int(10) unsigned NOT NULL,
  `modified_by` int(10) unsigned NOT NULL,
  PRIMARY KEY (`pk`),
  KEY `fk_employee_workexperience` (`employee_pk`),
  KEY `FK_created_by_emp_workexp` (`created_by`),
  KEY `FK_modified_by_emp_workexp` (`modified_by`),
  CONSTRAINT `fk_employee_workexperience` FOREIGN KEY (`employee_pk`) REFERENCES `employee` (`pk`),
  CONSTRAINT `FK_created_by_emp_workexp` FOREIGN KEY (`created_by`) REFERENCES `employee` (`pk`),
  CONSTRAINT `FK_modified_by_emp_workexp` FOREIGN KEY (`modified_by`) REFERENCES `employee` (`pk`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `employee_feedback`;
CREATE TABLE `employee_feedback` (
  `pk` int(11) NOT NULL AUTO_INCREMENT,
  `employee_pk` int(10) unsigned NOT NULL,
  `interviewerId` int(11) DEFAULT NULL,
  `dateOfInterview` datetime NOT NULL,
  `appliedPosition` int(10) unsigned NOT NULL,
  `suggestedRole` int(10) unsigned NOT NULL,
  `feedback` varchar(500) DEFAULT NULL,
  `feedbackStatus` varchar(20) NOT NULL,
  `interviewLevel` varchar(20) NOT NULL,
  `leadershipRating` int(1) DEFAULT NULL,
  `leadershipComments` varchar(250) DEFAULT NULL,
  `decisionRating` int(1) DEFAULT NULL,
  `decisionComments` varchar(250) DEFAULT NULL,
  `active` BOOLEAN default false NOT NULL,
  `dt_created` datetime NOT NULL,
  `dt_modified` datetime NOT NULL,
  `created_by` int(10) unsigned NOT NULL,
  `modified_by` int(10) unsigned NOT NULL,
  PRIMARY KEY (`pk`),
  KEY `fk_employee_feedback` (`employee_pk`),
  KEY `FK_created_by_emp_feed` (`created_by`),
  KEY `FK_modified_by_emp_feed` (`modified_by`),
  CONSTRAINT `fk_employee_feedback` FOREIGN KEY (`employee_pk`) REFERENCES `employee` (`pk`),
  CONSTRAINT `FK_created_by_emp_feed` FOREIGN KEY (`created_by`) REFERENCES `employee` (`pk`),
  CONSTRAINT `FK_modified_by_emp_feed` FOREIGN KEY (`modified_by`) REFERENCES `employee` (`pk`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
 
--
-- Definition of table `login history`
--

DROP TABLE IF EXISTS `login_history`;
CREATE TABLE `login_history` (
  `pk` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `employee_pk` int(10) unsigned NOT NULL,
  `system_ip` varchar(15) DEFAULT NULL,
  `login_time` datetime NOT NULL,
  `logout_time` datetime default NULL,
  PRIMARY KEY (`pk`),
  KEY `FK_employee_pk_lh` (`employee_pk`),
  CONSTRAINT `FK_employee_pk_lh` FOREIGN KEY (`employee_pk`) REFERENCES `employee` (`pk`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Finance Transaction table
drop table IF EXISTS `finance_transaction`;
CREATE TABLE `finance_transaction` (
  `pk` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `employee_pk` int(10) unsigned NOT NULL,
  `financial_year` varchar(45) NOT NULL,
  `financial_status` varchar(45) NOT NULL,
  `dt_created` datetime NOT NULL,
  `dt_modified` datetime NOT NULL,
  `created_by` int(10) unsigned NOT NULL,
  `modified_by` int(10) unsigned NOT NULL,
  PRIMARY KEY (`pk`),
  KEY `FK_employee_finance_txn_1` (`employee_pk`),
  KEY `FK_created_by_eft` (`created_by`),
  KEY `FK_modified_by_eft` (`modified_by`),
  CONSTRAINT `FK_created_by_eft` FOREIGN KEY (`created_by`) REFERENCES `employee` (`pk`),
  CONSTRAINT `FK_employee_finance_txn_1` FOREIGN KEY (`employee_pk`) REFERENCES `employee` (`pk`),
  CONSTRAINT `FK_modified_by_eft` FOREIGN KEY (`modified_by`) REFERENCES `employee` (`pk`)
);

-- Finance ITR table
drop table IF EXISTS `finance_itrwindow`;
CREATE TABLE `finance_itrwindow` (
  `financialyear` varchar(45) NOT NULL,
  `startdate` date NOT NULL,
  `enddate` date NOT NULL,
  PRIMARY KEY (`financialyear`) USING BTREE
);

-- VIEW for Employee table
create or replace view perficient.vw_employee_supervisor as
select e.*, s.employee_id as sup_employee_id, s.firstname as sup_firstname, s.lastname as sup_lastname 
from perficient.employee e 
inner join perficient.employee s on s.pk = e.supervisor;

-- View for Employee Basic details
create or replace view perficient.vw_employee_by_name as
select e.pk, e.firstName, e.lastName, e.employee_id, e.supervisor, e.email, e.designation, e.joindate, e.last_working_date, e.active, e.flag
from perficient.employee e;

DROP PROCEDURE if exists perficient.getEmployeeDesignationByRange;

DELIMITER $$
CREATE PROCEDURE perficient.getEmployeeDesignationByRange(
IN startDate date,  IN endDate date, IN designationInput varchar(50))
BEGIN
Select * from perficient.vw_employee_supervisor emp where pk In (
SELECT t.employee_pk FROM (
    SELECT employee_pk, max(start_date) AS maxtimestamp
    FROM employee_designation_history s
    where (s.start_date >= startDate
		AND s.start_date <= endDate)
		OR ((s.end_date >= startDate
		AND s.end_date <= endDate)
		OR (s.start_date >= startDate
		AND s.start_date <= endDate
		AND s.end_date IS NULL)
		OR (s.start_date <= startDate
		AND (s.end_date IS NULL OR s.end_date >= endDate)))
    GROUP BY employee_pk
) AS tmax inner join employee_designation_history as t on
t.employee_pk = tmax.employee_pk and t.start_date = tmax.maxtimestamp
LEFT OUTER JOIN designations d on t.designation_pk = d.pk
where d.designation=designationInput
AND ( flag = 0 OR	
(flag = 1  AND last_working_date is not null
	AND last_working_date >= startDate ))
GROUP BY t.employee_pk order by t.start_date desc);
END$$
DELIMITER ;

DROP TRIGGER IF EXISTS `updateemployeerole`;
DELIMITER $$
CREATE TRIGGER updateemployeerole AFTER UPDATE ON employee
	FOR EACH ROW
	BEGIN
	IF (OLD.designation <> NEW.designation) then
        update employee_designation_history set active = 1, end_date = NEW.designation_effective_date-1
        where employee_pk = NEW.pk and designation_pk = OLD.designation and active = 0;
                   
		INSERT INTO employee_designation_history
		(`employee_pk`, `designation_pk`, `start_date`, `active`, `dt_created`, `dt_modified`, `created_by`, `modified_by`)
			VALUES
			(NEW.pk, NEW.designation, NEW.designation_effective_date, 0, NEW.dt_modified, NEW.dt_modified, NEW.modified_by, NEW.modified_by);
	END IF;
  END$$
DELIMITER ;

DELIMITER $$
CREATE FUNCTION `func_inc_var_session`() RETURNS int
    NO SQL
    NOT DETERMINISTIC
     begin
      SET @var := IFNULL(@var,0) + 1;
      return @var;
     end$$
DELIMITER ;

create or replace VIEW notification_view as
SELECT func_inc_var_session(), id_generic, notification_type, GROUP_CONCAT(notification_to) as notification_to
FROM notification n where n.active=0 
GROUP BY id_generic, notification_type;

-- PTD Automation Scripts below

drop table IF EXISTS `process`;
create table `process`(
`pk` int (10) unsigned NOT NULL AUTO_INCREMENT, 
`process_name` varchar (150) DEFAULT NULL, 
`process_desc` varchar (150) DEFAULT NULL, 
`process_code` varchar (150) DEFAULT NULL,
`active` tinyint (1),
`dt_created` datetime ,
`dt_modified` datetime ,
`created_by` int (11),
`modified_by` int (11),
PRIMARY KEY (`pk`)
)ENGINE=InnoDB DEFAULT CHARSET=latin1; 


drop table IF EXISTS `sub_process`;
create table `sub_process` (
`pk` int (10) unsigned NOT NULL AUTO_INCREMENT,
`process_pk` int (10) unsigned NOT NULL,
`sub_process_name` varchar (150) DEFAULT NULL,
`process_desc` varchar (150) DEFAULT NULL,
`sub_process_code` varchar (30) DEFAULT NULL,
`active` tinyint (1),
`dt_created` datetime ,
`dt_modified` datetime ,
`created_by` int (11),
`modified_by` int (11),
PRIMARY KEY (`pk`),
KEY `FK_process_pk_sub` (`process_pk`),
CONSTRAINT `FK_process_pk_sub` FOREIGN KEY (`process_pk`) REFERENCES `process` (`pk`)
)ENGINE=InnoDB DEFAULT CHARSET=latin1; 


drop table IF EXISTS `project_audit`;
create table `project_audit`(
`pk` int (10) unsigned NOT NULL AUTO_INCREMENT, 
`project_pk` int (10) unsigned, 
`review_status` varchar (15) DEFAULT NULL, 
`sequence` varchar (150) DEFAULT NULL, 
`date` date DEFAULT NULL,
`active` tinyint (1),
`dt_created` datetime ,
`dt_modified` datetime ,
`created_by` int (11),
`modified_by` int (11),
PRIMARY KEY (`pk`), 
KEY `FK_project_key_pr_audit` (`project_pk`), 
CONSTRAINT `FK_project_key_pr_audit` FOREIGN KEY (`project_pk`) REFERENCES `projects` (`pk`)
)ENGINE=InnoDB DEFAULT CHARSET=latin1; 


drop table IF EXISTS `sp_pr_continuous_cr`;
create table `sp_pr_continuous_cr` (
`pk` int (10) unsigned NOT NULL AUTO_INCREMENT,
`process_pk` int (10) unsigned NOT NULL,
`project_audit_pk` int (10) unsigned NOT NULL,
`applicable` tinyint (1) NOT NULL,
`review_status` varchar (150) DEFAULT NULL,
`frequency` varchar (150) DEFAULT NULL,
`team_peer_review` varchar (150) DEFAULT NULL,
`review_analysis` varchar (150) DEFAULT NULL,
`review_from_gdc` varchar (150) DEFAULT NULL,
`enable_latest_review_template` varchar (150) DEFAULT NULL,
`review_comment_tracking` varchar (150) DEFAULT NULL,
`automated` varchar (15) DEFAULT NULL,
`active` tinyint (1),
`dt_created` datetime NOT NULL,
`dt_modified` datetime NOT NULL,
`created_by` int (11) NOT NULL,
`modified_by` int (11) NOT NULL,
PRIMARY KEY (`pk`),
KEY `FK_process_pr_ccr` (`process_pk`),
KEY `FK_project_audit_pr_ccr` (`project_audit_pk`),
CONSTRAINT `FK_process_key_pr_ccr` FOREIGN KEY (`process_pk`) REFERENCES `process` (`pk`),
CONSTRAINT `FK_project_audit_key_pr_ccr` FOREIGN KEY (`project_audit_pk`) REFERENCES `project_audit` (`pk`)
)ENGINE=InnoDB DEFAULT CHARSET=latin1; 

drop table IF EXISTS `sp_pr_peer_plan`;
create table `sp_pr_peer_plan` (
`pk` int (10) unsigned NOT NULL AUTO_INCREMENT,
`process_pk` int (10) unsigned NOT NULL,
`project_audit_pk` int (10) unsigned NOT NULL,
`review_status` varchar (15) DEFAULT NULL,
`applicable` tinyint (1) NOT NULL,
`review_time_tracking` varchar (150) DEFAULT NULL,
`review_criteria_defined_by` varchar (150) DEFAULT NULL,
`review_plan_tracking_system` varchar (150) DEFAULT NULL,
`active` tinyint (1),
`dt_created` datetime NOT NULL,
`dt_modified` datetime NOT NULL,
`created_by` int (11) NOT NULL,
`modified_by` int (11) NOT NULL,
PRIMARY KEY (`pk`),
KEY `FK_process_key_pr_pp` (`process_pk`),
KEY `FK_project_audit_pr_pp` (`project_audit_pk`),
CONSTRAINT `FK_process_key_pr_pp` FOREIGN KEY (`process_pk`) REFERENCES `process` (`pk`),
CONSTRAINT `FK_project_audit_key_pr_pp` FOREIGN KEY (`project_audit_pk`) REFERENCES `project_audit` (`pk`)
)ENGINE=InnoDB DEFAULT CHARSET=latin1; 


drop table IF EXISTS `sp_ridm_risks_issues_dependency_tracking`;
create table `sp_ridm_risks_issues_dependency_tracking` (
`pk` int (10) unsigned NOT NULL AUTO_INCREMENT,
`process_pk` int (10) unsigned NOT NULL,
`project_audit_pk` int (10) unsigned NOT NULL,
`review_status` varchar (15) DEFAULT NULL,
`applicable` tinyint (1) NOT NULL,
`risk_management_owner` varchar (150) DEFAULT NULL,
`risk_tracking_system` varchar (150) DEFAULT NULL,
`enable_2_0_risk_process_or_tailored` varchar (150) DEFAULT NULL,
`criteria_for_updating_risk_in_PMO_dashboard` varchar (150) DEFAULT NULL,
`risk_tracking_frequency` varchar (150) DEFAULT NULL,
`organization_risk_included` varchar (150) DEFAULT NULL,
`known_risks` varchar (150) DEFAULT NULL,
`comments` varchar (150) DEFAULT NULL,
`active` tinyint (1),
`dt_created` datetime NOT NULL,
`dt_modified` datetime NOT NULL,
`created_by` int (11) NOT NULL,
`modified_by` int (11) NOT NULL,
PRIMARY KEY (`pk`),
KEY `FK_process_key_ridm_ridt` (`process_pk`),
KEY `FK_project_audit_key_ridm_ridt` (`project_audit_pk`),
CONSTRAINT `FK_process_key_ridm_ridt` FOREIGN KEY (`process_pk`) REFERENCES `process` (`pk`),
CONSTRAINT `FK_project_audit_key_ridm_ridt` FOREIGN KEY (`project_audit_pk`) REFERENCES `project_audit` (`pk`)
)ENGINE=InnoDB DEFAULT CHARSET=latin1; 

drop table IF EXISTS `sp_tcdr_detailed_requirement`;
create table `sp_tcdr_detailed_requirement` (
	`pk` int (10) unsigned NOT NULL AUTO_INCREMENT,
	`process_pk` int (10) unsigned NOT NULL,
	`project_audit_pk` int (10) unsigned NOT NULL,
	`review_status` varchar (15) DEFAULT NULL,
	`applicable` tinyint (1) NOT NULL,
	`who_defines_detailed` varchar (150) DEFAULT NULL,
	`requirement_analysis_method` varchar (150) DEFAULT NULL,
	`requirement_elicitation_method` varchar (150) DEFAULT NULL,
	`requirement_definition_or_representation_method` varchar (150) DEFAULT NULL,
	`requirement_clarification` varchar (150) DEFAULT NULL,
	`requirement_owner` varchar (150) DEFAULT NULL,
	`requirement_repository` varchar (150) DEFAULT NULL,
	`requirement_tracking` varchar (150) DEFAULT NULL,
	`requirement_review` varchar (150) DEFAULT NULL,
	`active` BOOLEAN default false NOT NULL,
	`dt_created` datetime NOT NULL,
	`created_by` int(10) unsigned NOT NULL,
	`dt_modified` datetime NOT NULL,
	`modified_by` int(10) unsigned NOT NULL,
	PRIMARY KEY (`pk`),
	KEY `FK_process_key_tcdr_dr` (`process_pk`),
	KEY `FK_project_audit_key_tcdr_dr` (`project_audit_pk`),
	CONSTRAINT `FK_process_key_tcdr_dr` FOREIGN KEY (`process_pk`) REFERENCES `process` (`pk`),
	CONSTRAINT `FK_project_audit_key_tcdr_dr` FOREIGN KEY (`project_audit_pk`) REFERENCES `project_audit` (`pk`)
)ENGINE=InnoDB DEFAULT CHARSET=latin1; 

drop table IF EXISTS `sp_tcdr_high_level_requirement`;
create table `sp_tcdr_high_level_requirement` (
	`pk` int (10) unsigned NOT NULL AUTO_INCREMENT,
	`process_pk` int (10) unsigned NOT NULL,
	`project_audit_pk` int (10) unsigned NOT NULL,
	`review_status` varchar (15) DEFAULT NULL,
	`applicable` tinyint (1) NOT NULL,
	`who_defines_hlr` varchar (150) DEFAULT NULL,
	`requirement_analysis_method` varchar (150) DEFAULT NULL,
	`requirement_elicitation_method` varchar (150) DEFAULT NULL,
	`requirement_definition_or_representation_method` varchar (150) DEFAULT NULL,
	`requirement_clarification` varchar (150) DEFAULT NULL,
	`requirement_owner` varchar (150) DEFAULT NULL,
	`requirement_repository` varchar (150) DEFAULT NULL,
	`requirement_tracking` varchar (150) DEFAULT NULL,
	`requirement_review` varchar (150) DEFAULT NULL,
	`active` BOOLEAN default false NOT NULL,
	`dt_created` datetime NOT NULL,
	`created_by` int(10) unsigned NOT NULL,
	`dt_modified` datetime NOT NULL,
	`modified_by` int(10) unsigned NOT NULL,
	PRIMARY KEY (`pk`),
	KEY `FK_process_key_tcdr_hlr` (`process_pk`),
	KEY `FK_project_audit_key_tcdr_hlr` (`project_audit_pk`),
	CONSTRAINT `FK_process_key_tcdr_hlr` FOREIGN KEY (`process_pk`) REFERENCES `process` (`pk`),
	CONSTRAINT `FK_project_audit_key_tcdr_hlr` FOREIGN KEY (`project_audit_pk`) REFERENCES `project_audit` (`pk`)
)ENGINE=InnoDB DEFAULT CHARSET=latin1; 

drop table IF EXISTS `sp_tcdr_traceability_matrix`;
create table `sp_tcdr_traceability_matrix` (
	`pk` int (10) unsigned NOT NULL AUTO_INCREMENT,
	`process_pk` int (10) unsigned NOT NULL,
	`project_audit_pk` int (10) unsigned NOT NULL,
	`review_status` varchar (15) DEFAULT NULL,
	`applicable` tinyint (1) NOT NULL,
	`requirement_traceability_method` varchar (150) DEFAULT NULL,
	`traceable_items` varchar (150) DEFAULT NULL,
	`bi_directional_traceability` varchar (150) DEFAULT NULL,
	`owner` varchar (150) DEFAULT NULL,
	`active` BOOLEAN default false NOT NULL,
	`dt_created` datetime NOT NULL,
	`created_by` int(10) unsigned NOT NULL,
	`dt_modified` datetime NOT NULL,
	`modified_by` int(10) unsigned NOT NULL,
	PRIMARY KEY (`pk`),
	KEY `FK_process_key_tcdr_tm` (`process_pk`),
	KEY `FK_project_audit_key_tcdr_tm` (`project_audit_pk`),
	CONSTRAINT `FK_process_key_tcdr_tm` FOREIGN KEY (`process_pk`) REFERENCES `process` (`pk`),
	CONSTRAINT `FK_project_audit_key_tcdr_tm` FOREIGN KEY (`project_audit_pk`) REFERENCES `project_audit` (`pk`)
)ENGINE=InnoDB DEFAULT CHARSET=latin1;

drop table IF EXISTS `sp_dm_defect_tracking`;
create table `sp_dm_defect_tracking` (
	`pk` int (10) unsigned NOT NULL AUTO_INCREMENT,
	`process_pk` int (10) unsigned NOT NULL,
	`project_audit_pk` int (10) unsigned NOT NULL,
	`review_status` varchar (15) DEFAULT NULL,
	`applicable` tinyint (1) NOT NULL,
	`defect_management_by_GDC` varchar (150) DEFAULT NULL,
	`defect_tracking_tool` varchar (150) DEFAULT NULL,
	`active` BOOLEAN default false NOT NULL,
	`dt_created` datetime NOT NULL,
	`created_by` int(10) unsigned NOT NULL,
	`dt_modified` datetime NOT NULL,
	`modified_by` int(10) unsigned NOT NULL,
	PRIMARY KEY (`pk`),
	KEY `FK_process_key_dm_dt` (`process_pk`),
	KEY `FK_project_audit_key_dm_dt` (`project_audit_pk`),
	CONSTRAINT `FK_process_key_dm_dt` FOREIGN KEY (`process_pk`) REFERENCES `process` (`pk`),
	CONSTRAINT `FK_project_audit_key_dm_dt` FOREIGN KEY (`project_audit_pk`) REFERENCES `project_audit` (`pk`)
)ENGINE=InnoDB DEFAULT CHARSET=latin1;

drop table IF EXISTS `sp_dar_decision_analysis_and_resolution_artifacts`;
create table `sp_dar_decision_analysis_and_resolution_artifacts` (
	`pk` int (10) unsigned NOT NULL AUTO_INCREMENT,
	`process_pk` int (10) unsigned NOT NULL,
	`project_audit_pk` int (10) unsigned NOT NULL,
	`review_status` varchar (15) DEFAULT NULL,
	`applicable` tinyint (1) NOT NULL,
	`design_or_technical_solution` varchar (150) DEFAULT NULL,
	`conflict_mangement` varchar (150) DEFAULT NULL,
	`tool_selection` varchar (150) DEFAULT NULL,
	`active` BOOLEAN default false NOT NULL,
	`dt_created` datetime NOT NULL,
	`created_by` int(10) unsigned NOT NULL,
	`dt_modified` datetime NOT NULL,
	`modified_by` int(10) unsigned NOT NULL,
	PRIMARY KEY (`pk`),
	KEY `FK_process_key_dar_dara` (`process_pk`),
	KEY `FK_project_audit_key_dar_dara` (`project_audit_pk`),
	CONSTRAINT `FK_process_key_dar_dara` FOREIGN KEY (`process_pk`) REFERENCES `process` (`pk`),
	CONSTRAINT `FK_project_audit_key_dar_dara` FOREIGN KEY (`project_audit_pk`) REFERENCES `project_audit` (`pk`)
)ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `sp_apm_iteration_demo`;
create table `sp_apm_iteration_demo` (
	`pk` int (10) unsigned NOT NULL AUTO_INCREMENT,
	`process_pk` int (10) unsigned NOT NULL,
	`project_audit_pk` int (10) unsigned NOT NULL,
	`review_status` varchar (150) DEFAULT NULL,
	`applicable` tinyint (1) NOT NULL,
	`demo_frequency` varchar (15) DEFAULT NULL,
	`demo_mode` varchar (15) DEFAULT NULL,
	`demo_participants` varchar (150) DEFAULT NULL,
	`demo_feedback_recording` varchar (150) DEFAULT NULL,
	`active` BOOLEAN default false NOT NULL,
    `dt_created` datetime NOT NULL,
    `created_by` int(10) unsigned NOT NULL,
    `dt_modified` datetime NOT NULL,
    `modified_by` int(10) unsigned NOT NULL,
	PRIMARY KEY (`pk`),
	KEY `FK_process_key_apm_itd` (`process_pk`),
	KEY `FK_project_audit_key_apm_itd` (`project_audit_pk`),
	CONSTRAINT `FK_process_key_apm_itd` FOREIGN KEY (`process_pk`) REFERENCES `process` (`pk`),
	CONSTRAINT `FK_project_audit_key_apm_itd` FOREIGN KEY (`project_audit_pk`) REFERENCES `project_audit` (`pk`)
)ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `sp_ad_architecture_design`;
create table `sp_ad_architecture_design` (
	`pk` int (10) unsigned NOT NULL AUTO_INCREMENT,
	`process_pk` int (10) unsigned NOT NULL,
	`project_audit_pk` int (10) unsigned NOT NULL,
	`review_status` varchar (150) DEFAULT NULL,
	`applicable` tinyint (1) NOT NULL,
	`gdc_involvement` varchar(150) DEFAULT NULL,
	`low_level_design` varchar(150) DEFAULT NULL,
	`design_repository` varchar(150) DEFAULT NULL,
	`design_review_outcome` varchar(150) DEFAULT NULL,
	`design_tool` varchar(150) DEFAULT NULL,
	`alternative_design_evaluation` boolean DEFAULT false NOT NULL,
	`reusable_components` varchar(150) DEFAULT NULL,
	`active` BOOLEAN default false NOT NULL ,
    `dt_created` datetime NOT NULL,
    `created_by` int(10) unsigned NOT NULL,
    `dt_modified` datetime NOT NULL,
    `modified_by` int(10) unsigned NOT NULL,
	PRIMARY KEY (`pk`),
	KEY `FK_process_key_ad_ard` (`process_pk`),
	KEY `FK_project_audit_key_ad_ard` (`project_audit_pk`),
	CONSTRAINT `FK_process_key_ad_ard` FOREIGN KEY (`process_pk`) REFERENCES `process` (`pk`),
	CONSTRAINT `FK_project_audit_key_ad_ard` FOREIGN KEY (`project_audit_pk`) REFERENCES `project_audit` (`pk`)	
)ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `sp_ho_hand_off`;
create table `sp_ho_hand_off` (
	`pk` int (10) unsigned NOT NULL AUTO_INCREMENT,
	`process_pk` int (10) unsigned NOT NULL,
	`project_audit_pk` int (10) unsigned NOT NULL,
	`review_status` varchar (150) DEFAULT NULL,
	`applicable` tinyint (1) NOT NULL,
	`ho_responsibility_gdc` varchar(150) DEFAULT NULL,
	`manual_help_doc` varchar(150) DEFAULT NULL,
	`active` BOOLEAN default false NOT NULL,
    `dt_created` datetime NOT NULL,
    `created_by` int(10) unsigned NOT NULL,
    `dt_modified` datetime NOT NULL,
    `modified_by` int(10) unsigned NOT NULL,
	PRIMARY KEY (`pk`),
	KEY `FK_process_keyho_hno` (`process_pk`),
	KEY `FK_project_audit_key_ho_hno` (`project_audit_pk`),
	CONSTRAINT `FK_process_keyho_hno` FOREIGN KEY (`process_pk`) REFERENCES `process` (`pk`),
	CONSTRAINT `FK_project_audit_key_ho_hno` FOREIGN KEY (`project_audit_pk`) REFERENCES `project_audit` (`pk`)	
)ENGINE=InnoDB DEFAULT CHARSET=latin1; 

DROP TABLE IF EXISTS `sp_ho_knowledge_transfer`;
create table `sp_ho_knowledge_transfer` (
	`pk` int (10) unsigned NOT NULL AUTO_INCREMENT,
	`process_pk` int (10) unsigned NOT NULL,
	`project_audit_pk` int (10) unsigned NOT NULL,
	`review_status` varchar (150) DEFAULT NULL,	
	`applicable` tinyint (1) NOT NULL,
	`knowledge_transfer_mode` varchar(150) DEFAULT NULL,
	`resource_planning_backlog` varchar(150) DEFAULT NULL,
	`active` BOOLEAN default false NOT NULL,
    `dt_created` datetime NOT NULL,
    `created_by` int(10) unsigned NOT NULL,
    `dt_modified` datetime NOT NULL,
    `modified_by` int(10) unsigned NOT NULL,
	PRIMARY KEY (`pk`),
	KEY `FK_process_key_ho_knt` (`process_pk`),
	KEY `FK_project_audit_key_ho_knt` (`project_audit_pk`),
	CONSTRAINT `FK_process_key_ho_knt` FOREIGN KEY (`process_pk`) REFERENCES `process` (`pk`),
	CONSTRAINT `FK_project_audit_key_ho_knt` FOREIGN KEY (`project_audit_pk`) REFERENCES `project_audit` (`pk`) 	
)ENGINE=InnoDB DEFAULT CHARSET=latin1; 

DROP TABLE IF EXISTS `sp_car_artifacts`;
create table `sp_car_artifacts` (
	`pk` int (10) unsigned NOT NULL AUTO_INCREMENT,
	`process_pk` int (10) unsigned NOT NULL,
	`project_audit_pk` int (10) unsigned NOT NULL,
	`review_status` varchar (150) DEFAULT NULL,
	`applicable` tinyint (1) NOT NULL,
	`car_artifacts` varchar(150) DEFAULT NULL,
	`active` BOOLEAN default false NOT NULL,
    `dt_created` datetime NOT NULL,
    `created_by` int(10) unsigned NOT NULL,
    `dt_modified` datetime NOT NULL,
    `modified_by` int(10) unsigned NOT NULL,
	PRIMARY KEY (`pk`),
	KEY `FK_process_key_car_art` (`process_pk`),
	KEY `FK_project_audit_key_car_art` (`project_audit_pk`),
	CONSTRAINT `FK_process_key_car_art` FOREIGN KEY (`process_pk`) REFERENCES `process` (`pk`),
	CONSTRAINT `FK_project_audit_key_car_art` FOREIGN KEY (`project_audit_pk`) REFERENCES `project_audit` (`pk`) 	
)ENGINE=InnoDB DEFAULT CHARSET=latin1; 

DROP TABLE IF EXISTS `sp_apm_projectplanning_tt`;
create table `sp_apm_projectplanning_tt` (
	`pk` int (10) unsigned NOT NULL AUTO_INCREMENT,
	`process_pk` int (10) unsigned NOT NULL,
	`project_audit_pk` int (10) unsigned NOT NULL,
	`review_status` varchar (150) DEFAULT NULL,
	`applicable` tinyint (1) NOT NULL,
	`project_planning` varchar (150) DEFAULT NULL,
	`high_level_project_planning` varchar (150) DEFAULT NULL,
	`sprint_frequency` varchar (150) DEFAULT NULL,
	`project_estimation_method` varchar (150) DEFAULT NULL,
	`sprint_planning` varchar (150) DEFAULT NULL,
	`task_update_freq` varchar (15) DEFAULT NULL,
	`onsite_offsite_overlap_hrs` int (15) DEFAULT NULL,
	`release_frequency` varchar (15) DEFAULT NULL,
	`release_planning` varchar (150) DEFAULT NULL,
	`project_process` varchar (150) DEFAULT NULL,
	`daily_scrum` varchar (150) DEFAULT NULL,
	`sprint_backlog_management` varchar (150) DEFAULT NULL,
	`estimation_tracking_tool` varchar (150) DEFAULT NULL,
	`project_document_repository` varchar (150) DEFAULT NULL,
	`meeting_minutes` varchar (150) DEFAULT NULL,
	`tdc_involvement` varchar (150) DEFAULT NULL,
	`infrastructure_request_management` varchar (150) DEFAULT NULL,
	`sprint_baseline_critera` varchar (150) DEFAULT NULL,
	`support_task_management` varchar (150) DEFAULT NULL,
	`active` BOOLEAN default false NOT NULL,
    `dt_created` datetime NOT NULL,
    `created_by` int(10) unsigned NOT NULL,
    `dt_modified` datetime NOT NULL,
    `modified_by` int(10) unsigned NOT NULL,
	PRIMARY KEY (`pk`),
	KEY `FK_process_key_apm_ptt` (`process_pk`),
	KEY `FK_project_audit_key_apm_ptt` (`project_audit_pk`),
	CONSTRAINT `FK_process_key_apm_ptt` FOREIGN KEY (`process_pk`) REFERENCES `process` (`pk`),
	CONSTRAINT `FK_project_audit_key_apm_ptt` FOREIGN KEY (`project_audit_pk`) REFERENCES `project_audit` (`pk`)
)ENGINE=InnoDB DEFAULT CHARSET=latin1; 

DROP TABLE IF EXISTS `sp_apm_iteration_retrospective`;
create table `sp_apm_iteration_retrospective` (
	`pk` int (10) unsigned NOT NULL AUTO_INCREMENT,
	`process_pk` int (10) unsigned NOT NULL,
	`project_audit_pk` int (10) unsigned NOT NULL,
	`review_status` varchar (150) DEFAULT NULL,
	`applicable` tinyint (1) NOT NULL,
	`retrospective_duration` varchar(50) DEFAULT NULL,
	`retrospective_frequency` varchar (15) DEFAULT NULL,
	`retrospective_action_item_tracking` varchar (150) DEFAULT NULL,
	`retrospective_outcome_documentation` varchar (150) DEFAULT NULL,
	`retrospective_owner` varchar (150) DEFAULT NULL,
	`stakeholder_involvement` varchar (150) DEFAULT NULL,
	`retrospective_focus_on_all_pa` varchar (150) DEFAULT NULL,
	`active` BOOLEAN default false NOT NULL,
    `dt_created` datetime NOT NULL,
    `created_by` int(10) unsigned NOT NULL,
    `dt_modified` datetime NOT NULL,
    `modified_by` int(10) unsigned NOT NULL,
	PRIMARY KEY (`pk`),
	KEY `FK_process_key_apm_itr` (`process_pk`),
	KEY `FK_project_audit_key_apm_itr` (`project_audit_pk`),
	CONSTRAINT `FK_process_key_apm_itr` FOREIGN KEY (`process_pk`) REFERENCES `process` (`pk`),
	CONSTRAINT `FK_project_audit_key_apm_itr` FOREIGN KEY (`project_audit_pk`) REFERENCES `project_audit` (`pk`)
)ENGINE=InnoDB DEFAULT CHARSET=latin1; 


DROP TABLE IF EXISTS `sp_sc_change_request`;
create table `sp_sc_change_request` (
	`pk` int (10) unsigned NOT NULL AUTO_INCREMENT,
	`process_pk` int (10) unsigned NOT NULL,
	`project_audit_pk` int (10) unsigned NOT NULL,
	`applicable` tinyint (1) NOT NULL,
	`review_status` varchar(150) DEFAULT NULL,
	`scope_change_tracking` varchar (150) DEFAULT NULL,
	`owner` varchar(150) DEFAULT NULL,
	`change_approver` varchar (150) DEFAULT NULL,
	`backlog_updates` varchar (150) DEFAULT NULL,
	`changes_allowed_during_middle_of_sprint` varchar(150) DEFAULT NULL,
	`impact_will_include` varchar(150) DEFAULT NULL,
	`scope_change_managed_by_gdc_pm` varchar(150) DEFAULT NULL,
	`change_log_management` varchar(150) DEFAULT NULL,
	`active` BOOLEAN default false NOT NULL,
	`dt_created` datetime NOT NULL,
	`created_by` int(10) unsigned NOT NULL,
	`dt_modified` datetime NOT NULL,
	`modified_by` int(10) unsigned NOT NULL,
	PRIMARY KEY (`pk`),
	KEY `FK_process_key` (`process_pk`),
	KEY `FK_project_audit_key` (`project_audit_pk`),
	CONSTRAINT `FK_sc_change_process_key` FOREIGN KEY (`process_pk`) REFERENCES `process` (`pk`),
	CONSTRAINT `FK_project_audit_key_sc_change` FOREIGN KEY (`project_audit_pk`) REFERENCES `project_audit` (`pk`)
)ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `sp_sc_change_log`;
create table `sp_sc_change_log` (
	`pk` int (10) unsigned NOT NULL AUTO_INCREMENT,
	`process_pk` int (10) unsigned NOT NULL,
	`project_audit_pk` int (10) unsigned NOT NULL,
	`applicable` tinyint (1) NOT NULL,
	`review_status` varchar(150) DEFAULT NULL,
	`change_log_management` varchar (150) DEFAULT NULL,
	`change_log_owner` varchar (150) DEFAULT NULL,
	`change_approver` varchar (150) DEFAULT NULL,
	`active` BOOLEAN default false NOT NULL,
	`dt_created` datetime NOT NULL,
	`created_by` int(10) unsigned NOT NULL,
	`dt_modified` datetime NOT NULL,
	`modified_by` int(10) unsigned NOT NULL,
	PRIMARY KEY (`pk`),
	KEY `FK_process_key` (`process_pk`),
	KEY `FK_project_audit_key` (`project_audit_pk`),
	CONSTRAINT `FK_sc_log_process_key` FOREIGN KEY (`process_pk`) REFERENCES `process` (`pk`),
	CONSTRAINT `FK_project_audit_key_sc_log` FOREIGN KEY (`project_audit_pk`) REFERENCES `project_audit` (`pk`)
)ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `sp_cm_cmplan`;
create table `sp_cm_cmplan` (
	`pk` int (10) unsigned NOT NULL AUTO_INCREMENT,
	`process_pk` int (10) unsigned NOT NULL,
	`project_audit_pk` int (10) unsigned NOT NULL,
	`applicable` tinyint (1) NOT NULL,
	`review_status` varchar(150) DEFAULT NULL,
	`environment_details` varchar (150) DEFAULT NULL,
	`configuration_items` varchar (150) DEFAULT NULL,
	`baseline_plan` varchar (150) DEFAULT NULL,
	`checkin_and_checkout_procedure` varchar (150) DEFAULT NULL,
	`active` BOOLEAN default false NOT NULL,
	`dt_created` datetime NOT NULL,
	`created_by` int(10) unsigned NOT NULL,
	`dt_modified` datetime NOT NULL,
	`modified_by` int(10) unsigned NOT NULL,
	PRIMARY KEY (`pk`),
	KEY `FK_process_key` (`process_pk`),
	KEY `FK_project_audit_key` (`project_audit_pk`),
	CONSTRAINT `FK_cm_plan_process_key` FOREIGN KEY (`process_pk`) REFERENCES `process` (`pk`),
	CONSTRAINT `FK_project_audit_key_cm_plan` FOREIGN KEY (`project_audit_pk`) REFERENCES `project_audit` (`pk`)
)ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `sp_cm_build_and_release_process`;
create table `sp_cm_build_and_release_process` (
	`pk` int (10) unsigned NOT NULL AUTO_INCREMENT,
	`process_pk` int (10) unsigned NOT NULL,
	`project_audit_pk` int (10) unsigned NOT NULL,
	`applicable` tinyint (1) NOT NULL,
	`review_status` varchar(150) DEFAULT NULL,
	`build_and_release_process` varchar (150) DEFAULT NULL,
	`active` BOOLEAN default false NOT NULL,
	`dt_created` datetime NOT NULL,
	`created_by` int(10) unsigned NOT NULL,
	`dt_modified` datetime NOT NULL,
	`modified_by` int(10) unsigned NOT NULL,
	PRIMARY KEY (`pk`),
	KEY `FK_process_key` (`process_pk`),
	KEY `FK_project_audit_key` (`project_audit_pk`),
	CONSTRAINT `FK_cm_brp_process_key` FOREIGN KEY (`process_pk`) REFERENCES `process` (`pk`),
	CONSTRAINT `FK_project_audit_key_cm_brp` FOREIGN KEY (`project_audit_pk`) REFERENCES `project_audit` (`pk`)
)ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `sp_cm_continuous_integration`;
create table `sp_cm_continuous_integration` (
	`pk` int (10) unsigned NOT NULL AUTO_INCREMENT,
	`process_pk` int (10) unsigned NOT NULL,
	`project_audit_pk` int (10) unsigned NOT NULL,
	`applicable` tinyint (1) NOT NULL,
	`review_status` varchar(150) DEFAULT NULL,
	`continuous_integration` varchar (150) DEFAULT NULL,
	`active` BOOLEAN default false NOT NULL,
	`dt_created` datetime NOT NULL,
	`created_by` int(10) unsigned NOT NULL,
	`dt_modified` datetime NOT NULL,
	`modified_by` int(10) unsigned NOT NULL,
	PRIMARY KEY (`pk`),
	KEY `FK_process_key` (`process_pk`),
	KEY `FK_project_audit_key` (`project_audit_pk`),
	CONSTRAINT `FK_cm_ci_process_key` FOREIGN KEY (`process_pk`) REFERENCES `process` (`pk`),
	CONSTRAINT `FK_project_audit_key_cm_ci` FOREIGN KEY (`project_audit_pk`) REFERENCES `project_audit` (`pk`)
)ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `sp_cm_backup_and_recovery_plan`;
create table `sp_cm_backup_and_recovery_plan` (
	`pk` int (10) unsigned NOT NULL AUTO_INCREMENT,
	`process_pk` int (10) unsigned NOT NULL,
	`project_audit_pk` int (10) unsigned NOT NULL,
	`applicable` tinyint (1) NOT NULL,
	`review_status` varchar(150) DEFAULT NULL,
	`backup_and_recovery_plan` varchar (150) DEFAULT NULL,
	`active` BOOLEAN default false NOT NULL,
	`dt_created` datetime NOT NULL,
	`created_by` int(10) unsigned NOT NULL,
	`dt_modified` datetime NOT NULL,
	`modified_by` int(10) unsigned NOT NULL,
	PRIMARY KEY (`pk`),
	KEY `FK_process_key` (`process_pk`),
	KEY `FK_project_audit_key` (`project_audit_pk`),
	CONSTRAINT `FK_cm_backup_process_key` FOREIGN KEY (`process_pk`) REFERENCES `process` (`pk`),
	CONSTRAINT `FK_project_audit_key_cm_backup` FOREIGN KEY (`project_audit_pk`) REFERENCES `project_audit` (`pk`)
)ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `sp_pqpp_metrics_collection`;
create table `sp_pqpp_metrics_collection` (
	`pk` int (10) unsigned NOT NULL AUTO_INCREMENT,
	`process_pk` int (10) unsigned NOT NULL,
	`project_audit_pk` int (10) unsigned NOT NULL,
	`applicable` tinyint (1) NOT NULL,
	`review_status` varchar(150) DEFAULT NULL,
	`quality_metrics_goal_source` varchar (150) DEFAULT NULL,
	`schedule_metrics_goal_source` varchar (150) DEFAULT NULL,
	`productivity_metrics_goal_source` varchar (150) DEFAULT NULL,
	`test_metrics_goal_source` varchar (150) DEFAULT NULL,
	`defect_metrics_goal_source` varchar (150) DEFAULT NULL,
	`active` BOOLEAN default false NOT NULL,
	`dt_created` datetime NOT NULL,
	`created_by` int(10) unsigned NOT NULL,
	`dt_modified` datetime NOT NULL,
	`modified_by` int(10) unsigned NOT NULL,
	PRIMARY KEY (`pk`),
	KEY `FK_process_key` (`process_pk`),
	KEY `FK_project_audit_key` (`project_audit_pk`),
	CONSTRAINT `FK_pqpp_metrics_process_key` FOREIGN KEY (`process_pk`) REFERENCES `process` (`pk`),
	CONSTRAINT `FK_project_audit_key_pqpp_metrics` FOREIGN KEY (`project_audit_pk`) REFERENCES `project_audit` (`pk`)
)ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `sp_pqpp_spc_analysis`;
create table `sp_pqpp_spc_analysis` (
	`pk` int (10) unsigned NOT NULL AUTO_INCREMENT,
	`process_pk` int (10) unsigned NOT NULL,
	`project_audit_pk` int (10) unsigned NOT NULL,
	`applicable` tinyint (1) NOT NULL,
	`review_status` varchar(150) DEFAULT NULL,
	`spc_analysis` varchar (150) DEFAULT NULL,
	`active` BOOLEAN default false NOT NULL,
	`dt_created` datetime NOT NULL,
	`created_by` int(10) unsigned NOT NULL,
	`dt_modified` datetime NOT NULL,
	`modified_by` int(10) unsigned NOT NULL,
	PRIMARY KEY (`pk`),
	KEY `FK_process_key` (`process_pk`),
	KEY `FK_project_audit_key` (`project_audit_pk`),
	CONSTRAINT `FK_pqpp_spc_process_key` FOREIGN KEY (`process_pk`) REFERENCES `process` (`pk`),
	CONSTRAINT `FK_project_audit_key_pqpp_spc` FOREIGN KEY (`project_audit_pk`) REFERENCES `project_audit` (`pk`)
)ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `sp_pqpp_process_performance_model`;
create table `sp_pqpp_process_performance_model` (
	`pk` int (10) unsigned NOT NULL AUTO_INCREMENT,
	`process_pk` int (10) unsigned NOT NULL,
	`project_audit_pk` int (10) unsigned NOT NULL,
	`applicable` tinyint (1) NOT NULL,
	`review_status` varchar(150) DEFAULT NULL,
	`project_improvement_plan` varchar (150) DEFAULT NULL,
	`sqa_audit_cycle` varchar (150) DEFAULT NULL,
	`work_product_audit` varchar (150) DEFAULT NULL,
	`internal_process_audit` varchar (150) DEFAULT NULL,
	`active` BOOLEAN default false NOT NULL,
	`dt_created` datetime NOT NULL,
	`created_by` int(10) unsigned NOT NULL,
	`dt_modified` datetime NOT NULL,
	`modified_by` int(10) unsigned NOT NULL,
	PRIMARY KEY (`pk`),
	KEY `FK_process_key` (`process_pk`),
	KEY `FK_project_audit_key` (`project_audit_pk`),
	CONSTRAINT `FK_pqpp_ppm_process_key` FOREIGN KEY (`process_pk`) REFERENCES `process` (`pk`),
	CONSTRAINT `FK_project_audit_key_pqpp_ppm` FOREIGN KEY (`project_audit_pk`) REFERENCES `project_audit` (`pk`)
)ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;