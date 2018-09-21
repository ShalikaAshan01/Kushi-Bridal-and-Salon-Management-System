BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS `payment_installment` (
	`aid`	INTEGER,
	`remainingPaymentt`	REAL,
	PRIMARY KEY(`aid`)
);
INSERT INTO `payment_installment` VALUES (6,0.0);
INSERT INTO `payment_installment` VALUES (10,12500.0);
CREATE TABLE IF NOT EXISTS `package_service` (
	`pid`	int,
	`sid`	int,
	CONSTRAINT `pk_ps` PRIMARY KEY(`pid`,`sid`),
	CONSTRAINT `fk_pid` FOREIGN KEY(`pid`) REFERENCES `Package`(`pid`),
	CONSTRAINT `fk_sid` FOREIGN KEY(`sid`) REFERENCES `Service`(`sid`)
);
INSERT INTO `package_service` VALUES (1,102);
INSERT INTO `package_service` VALUES (1,105);
INSERT INTO `package_service` VALUES (2,NULL);
INSERT INTO `package_service` VALUES (2,NULL);
INSERT INTO `package_service` VALUES (2,NULL);
CREATE TABLE IF NOT EXISTS `Service` (
	`sid`	integer,
	`sName`	varchar ( 20 ),
	`price`	REAL,
	CONSTRAINT `Ser_pk` PRIMARY KEY(`sid`)
);
INSERT INTO `Service` VALUES (101,'Men''s Haircut',800.0);
INSERT INTO `Service` VALUES (102,'Women''s Haircut',1200.0);
INSERT INTO `Service` VALUES (103,'Child Haircut',300.0);
INSERT INTO `Service` VALUES (104,'Women''s Hair Color',1800.0);
INSERT INTO `Service` VALUES (105,'Women''s Hair Style',3000.0);
INSERT INTO `Service` VALUES (106,'Men''s Hair Style',2500.0);
CREATE TABLE IF NOT EXISTS `Payment` (
	`payid`	INTEGER PRIMARY KEY AUTOINCREMENT,
	`cid`	int,
	`aid`	int,
	`payDate`	TEXT,
	`payTime`	TEXT,
	`total`	double,
	CONSTRAINT `fk_aid` FOREIGN KEY(`aid`) REFERENCES `Appointment`(`aid`),
	CONSTRAINT `fk_cid` FOREIGN KEY(`cid`) REFERENCES `Customer`(`nic`)
);
INSERT INTO `Payment` VALUES (42,2,5,'2018/09/02','16:23:20',25000.0);
INSERT INTO `Payment` VALUES (43,5,6,'2018/09/02','16:24:21',12500.0);
INSERT INTO `Payment` VALUES (44,5,6,'2018/09/02','16:24:43',12500.0);
INSERT INTO `Payment` VALUES (46,5,10,'2018/09/02','16:36:42',12500.0);
INSERT INTO `Payment` VALUES (53,8,9,'2018/09/08','12:36:56',3500.0);
INSERT INTO `Payment` VALUES (59,1,3,'2018/09/12','14:31:10',3500.0);
INSERT INTO `Payment` VALUES (60,2,2,'2018/09/12','14:33:14',6000.0);
INSERT INTO `Payment` VALUES (62,1,1,'2018/09/12','14:37:26',4600.0);
INSERT INTO `Payment` VALUES (63,4,7,'2018/09/12','14:45:40',3500.0);
CREATE TABLE IF NOT EXISTS `Package` (
	`pid`	integer,
	`pName`	varchar ( 20 ),
	`pPrice`	integer,
	CONSTRAINT `Pak_pk` PRIMARY KEY(`pid`)
);
INSERT INTO `Package` VALUES (1,'Women Hair cut and Style',3500);
INSERT INTO `Package` VALUES (2,'Bridal',25000);
CREATE TABLE IF NOT EXISTS `Employee` (
	`eid`	int,
	`fName`	varchar ( 12 ),
	`lName`	varchar ( 12 ),
	`NIC`	char ( 10 ),
	`address`	varchar ( 30 ),
	`phone`	int,
	`specialization`	varchar ( 20 ),
	`DOB`	date,
	`Gender`	varchar ( 10 ),
	CONSTRAINT `Emp_pf` PRIMARY KEY(`eid`)
);
INSERT INTO `Employee` VALUES (300,'Joy','Aveda','721645893V','46 Easton Ave.',721645236,NULL,NULL,NULL);
INSERT INTO `Employee` VALUES (400,'Geraldo','Geraldo','881645893V','12 Fortis Blvd. Apt. 2A',726645236,NULL,NULL,NULL);
INSERT INTO `Employee` VALUES (500,'Koy','Petruzzio','821645893V','70 Wilard St. ',721645436,NULL,NULL,NULL);
INSERT INTO `Employee` VALUES (600,'Landry','Monroe','921645893V','73 Holly Terrace',721145236,NULL,NULL,NULL);
INSERT INTO `Employee` VALUES (700,'Pat','Reese','711645893V','2 Lincoln Place',721645226,NULL,NULL,NULL);
INSERT INTO `Employee` VALUES (800,'Winter','Tanner','731645893V','215 Elm Ave',721645246,NULL,NULL,NULL);
CREATE TABLE IF NOT EXISTS `Customer` (
	`nic`	varchar ( 12 ),
	`fname`	varchar ( 20 ),
	`lname`	varchar ( 20 ),
	`age`	int,
	`address`	varchar ( 20 ),
	`email`	TEXT DEFAULT 'shalikaashan99@gmail.com',
	PRIMARY KEY(`nic`)
);
INSERT INTO `Customer` VALUES ('1','Elia','Fawcett',NULL,'8989 Smith Rd','shalikaashan99@gmail.com');
INSERT INTO `Customer` VALUES ('2','Ishwarya','Roberts',NULL,'65 Hope Rd','shalikaashan99@gmail.com');
INSERT INTO `Customer` VALUES ('3','Frederic','Fawcett',NULL,'8989 Smith Rd','shalikaashan99@gmail.com');
INSERT INTO `Customer` VALUES ('4','Goldie','Montand',NULL,'5235 Ironwood Ln','shalikaashan99@gmail.com');
INSERT INTO `Customer` VALUES ('5','Dheeraj','Alexander',NULL,'666 22nd Ave','shalikaashan99@gmail.com');
INSERT INTO `Customer` VALUES ('6','Josie','Davis',NULL,'4200 Bluejay Ave','shalikaashan99@gmail.com');
INSERT INTO `Customer` VALUES ('7','Faye','Glenn',NULL,'1522 Main St','shalikaashan99@gmail.com');
INSERT INTO `Customer` VALUES ('8','Lauren','Hershey',NULL,'2360 Maxon Rd','shalikaashan99@gmail.com');
CREATE TABLE IF NOT EXISTS `Cus_PhoneNo` (
	`nic`	varchar ( 12 ),
	`phoneNo`	varchar ( 10 ),
	CONSTRAINT `fk_nic` FOREIGN KEY(`nic`) REFERENCES `Customer`(`nic`)
);
INSERT INTO `Cus_PhoneNo` VALUES ('1','711234567');
INSERT INTO `Cus_PhoneNo` VALUES ('1','721234567');
INSERT INTO `Cus_PhoneNo` VALUES ('1','771234567');
INSERT INTO `Cus_PhoneNo` VALUES ('1','781234567');
INSERT INTO `Cus_PhoneNo` VALUES ('2','751234567');
INSERT INTO `Cus_PhoneNo` VALUES ('3','722234567');
INSERT INTO `Cus_PhoneNo` VALUES ('4','777234567');
INSERT INTO `Cus_PhoneNo` VALUES ('5','777734567');
INSERT INTO `Cus_PhoneNo` VALUES ('6','771264567');
INSERT INTO `Cus_PhoneNo` VALUES ('7','778234567');
INSERT INTO `Cus_PhoneNo` VALUES ('8','716234567');
CREATE TABLE IF NOT EXISTS `Appointment_service` (
	`aid`	int,
	`sid`	int,
	CONSTRAINT `fk_aid` FOREIGN KEY(`aid`) REFERENCES `Appointment`(`aid`),
	CONSTRAINT `fk_pid` FOREIGN KEY(`sid`) REFERENCES `Service`(`sid`),
	CONSTRAINT `pk_as` PRIMARY KEY(`aid`,`sid`)
);
INSERT INTO `Appointment_service` VALUES (1,101);
INSERT INTO `Appointment_service` VALUES (1,103);
INSERT INTO `Appointment_service` VALUES (2,106);
CREATE TABLE IF NOT EXISTS `Appointment` (
	`aid`	int,
	`cid`	int,
	`aPackage`	int,
	`aDate`	date,
	`aTime`	time,
	FOREIGN KEY(`cid`) REFERENCES `Customer`(`nic`),
	CONSTRAINT `App_pk` PRIMARY KEY(`aid`),
	FOREIGN KEY(`aPackage`) REFERENCES `Package`(`pid`)
);
INSERT INTO `Appointment` VALUES (1,1,1,'12/08/2018','12:10');
INSERT INTO `Appointment` VALUES (2,2,1,'12/08/2018','12:10');
INSERT INTO `Appointment` VALUES (3,1,1,'13/08/2018','12:10');
INSERT INTO `Appointment` VALUES (4,3,1,'9/08/2018','12:10');
INSERT INTO `Appointment` VALUES (5,2,2,'15/08/2018','12:10');
INSERT INTO `Appointment` VALUES (6,5,2,'12/08/2018','12:10');
INSERT INTO `Appointment` VALUES (7,4,1,'11/08/2018','12:10');
INSERT INTO `Appointment` VALUES (8,7,1,'12/08/2018','12:10');
INSERT INTO `Appointment` VALUES (9,8,1,'12/08/2018','12:10');
INSERT INTO `Appointment` VALUES (10,5,2,'12/08/2018','12:10');
INSERT INTO `Appointment` VALUES (11,4,2,'12/08/2018','12:10');
COMMIT;
