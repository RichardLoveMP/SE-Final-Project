
DELETE FROM users WHERE MID = 51;
DELETE FROM officevisits WHERE PatientID = 51;
DELETE FROM patients WHERE MID = 51;

INSERT INTO users(MID, password, role, sQuestion, sAnswer)
			VALUES (51, '30c952fab122c3f9759f02a6d95c3758b246b4fee239957b2d4fee46e26170c4', 'patient', 'am i dead?', 'yes');
/*password: pw*/
INSERT INTO patients (MID, firstName,lastName, email, phone, Gender, DateOfDeath, CauseOfDeath)
VALUES (51, 'Abe', 'Lincoln', 'honest@abe.com', '919-444-2222', 'Male', '2014-04-23', 84.50);


INSERT INTO officevisits(id,visitDate,HCPID,notes,HospitalID,PatientID)
VALUES (10051,'2014-02-23',9000000000,'Die in two months','1',51);

DELETE FROM users WHERE MID = 52;
DELETE FROM officevisits WHERE PatientID = 52;
DELETE FROM patients WHERE MID = 52;

INSERT INTO users(MID, password, role, sQuestion, sAnswer)
			VALUES (52, '30c952fab122c3f9759f02a6d95c3758b246b4fee239957b2d4fee46e26170c4', 'patient', 'am i dead?', 'yes');
/*password: pw*/
INSERT INTO patients (MID, firstName,lastName, email, phone, Gender, DateOfDeath, CauseOfDeath)
VALUES (52, 'George', 'Washington', 'cherry@tree.com', '919-444-2223', 'Male', '2014-09-14', 84.50);


INSERT INTO officevisits(id,visitDate,HCPID,notes,HospitalID,PatientID)
VALUES (10052,'2014-07-14',9000000000,'Die in two months','1',52);

DELETE FROM users WHERE MID = 53;
DELETE FROM officevisits WHERE PatientID = 53;
DELETE FROM patients WHERE MID = 53;

INSERT INTO users(MID, password, role, sQuestion, sAnswer)
			VALUES (53, '30c952fab122c3f9759f02a6d95c3758b246b4fee239957b2d4fee46e26170c4', 'patient', 'am i dead?', 'yes');
/*password: pw*/
INSERT INTO patients (MID, firstName,lastName, email, phone, Gender, DateOfDeath, CauseOfDeath)
VALUES (53, 'James', 'Polk', 'polka@dot.com', '919-444-2224', 'Male', '2014-10-19', 84.50);


INSERT INTO officevisits(id,visitDate,HCPID,notes,HospitalID,PatientID)
VALUES (10053,'2014-08-19',9000000000,'Die in two months','1',53);

DELETE FROM users WHERE MID = 54;
DELETE FROM officevisits WHERE PatientID = 54;
DELETE FROM patients WHERE MID = 54;

INSERT INTO users(MID, password, role, sQuestion, sAnswer)
        VALUES (54, '30c952fab122c3f9759f02a6d95c3758b246b4fee239957b2d4fee46e26170c4', 'patient', 'am i dead?', 'yes');
/*password: pw*/
INSERT INTO patients (MID, firstName,lastName, email, phone, Gender, DateOfDeath, CauseOfDeath)
VALUES (54, 'Teddy', 'Roosevelt', 'teddy@bear.com', '919-444-2225', 'Male', '2014-10-20', 72.00);


INSERT INTO officevisits(id,visitDate,HCPID,notes,HospitalID,PatientID)
VALUES (10054,'2014-10-20',9000000000,'Die in two months','1',54);

DELETE FROM users WHERE MID = 55;
DELETE FROM officevisits WHERE PatientID = 55;
DELETE FROM patients WHERE MID = 55;

INSERT INTO users(MID, password, role, sQuestion, sAnswer)
			VALUES (55, '30c952fab122c3f9759f02a6d95c3758b246b4fee239957b2d4fee46e26170c4', 'patient', 'am i dead?', 'yes');
/*password: pw*/
INSERT INTO patients (MID, firstName,lastName, email, phone, Gender, DateOfDeath, CauseOfDeath)
VALUES (55, 'Leslie', 'Knope', 'pawnee@ind.com', '919-444-2226', 'Female', '2014-04-01', 35.00);


INSERT INTO officevisits(id,visitDate,HCPID,notes,HospitalID,PatientID)
VALUES (10055,'2014-02-01',9000000003,'Die in two months','1',55);

DELETE FROM users WHERE MID = 56;
DELETE FROM officevisits WHERE PatientID = 56;
DELETE FROM patients WHERE MID = 56;

INSERT INTO users(MID, password, role, sQuestion, sAnswer)
			VALUES (56, '30c952fab122c3f9759f02a6d95c3758b246b4fee239957b2d4fee46e26170c4', 'patient', 'am i dead?', 'yes');
/*password: pw*/
INSERT INTO patients (MID, firstName,lastName, email, phone, Gender, DateOfDeath, CauseOfDeath)
VALUES (56, 'Amelia', 'Earhardt', 'flying@far.com', '919-444-2227', 'Female', '2014-07-04', 72.00);


INSERT INTO officevisits(id,visitDate,HCPID,notes,HospitalID,PatientID)
VALUES (10056,'2014-05-04',9000000003,'Die in two months','1',56);

DELETE FROM users WHERE MID = 57;
DELETE FROM officevisits WHERE PatientID = 57;
DELETE FROM patients WHERE MID = 57;

INSERT INTO users(MID, password, role, sQuestion, sAnswer)
			VALUES (57, '30c952fab122c3f9759f02a6d95c3758b246b4fee239957b2d4fee46e26170c4', 'patient', 'am i dead?', 'yes');
/*password: pw*/
INSERT INTO patients (MID, firstName,lastName, email, phone, Gender, DateOfDeath, CauseOfDeath)
VALUES (57, 'Captain', 'Morgan', 'rum@man.com', '919-444-2228', 'Male', '2013-04-23', 35.00);


INSERT INTO officevisits(id,visitDate,HCPID,notes,HospitalID,PatientID)
VALUES (10057,'2013-02-23',9000000003,'Die in two months','1',57);

DELETE FROM users WHERE MID = 58;
DELETE FROM officevisits WHERE PatientID = 58;
DELETE FROM patients WHERE MID = 58;

INSERT INTO users(MID, password, role, sQuestion, sAnswer)
			VALUES (58, '30c952fab122c3f9759f02a6d95c3758b246b4fee239957b2d4fee46e26170c4', 'patient', 'am i dead?', 'yes');
/*password: pw*/
INSERT INTO patients (MID, firstName,lastName, email, phone, Gender, DateOfDeath, CauseOfDeath)
VALUES (58, 'Rose', 'Titanic', 'ship@sinks.com', '919-444-2229', 'Female', '2013-09-14', 35.00);


INSERT INTO officevisits(id,visitDate,HCPID,notes,HospitalID,PatientID)
VALUES (10058,'2013-07-14',9000000000,'Die in two months','1',58);

DELETE FROM users WHERE MID = 59;
DELETE FROM officevisits WHERE PatientID = 59;
DELETE FROM patients WHERE MID = 59;

INSERT INTO users(MID, password, role, sQuestion, sAnswer)
			VALUES (59, '30c952fab122c3f9759f02a6d95c3758b246b4fee239957b2d4fee46e26170c4', 'patient', 'am i dead?', 'yes');
/*password: pw*/
INSERT INTO patients (MID, firstName,lastName, email, phone, Gender, DateOfDeath, CauseOfDeath)
VALUES (59, 'Jackie', 'Chan', 'kung@fu.com', '919-444-2230', 'Male', '2012-05-05', 35.00);


INSERT INTO officevisits(id,visitDate,HCPID,notes,HospitalID,PatientID)
VALUES (10059,'2012-03-05',9000000003,'Die in two months','1',59);

DELETE FROM users WHERE MID = 60;
DELETE FROM officevisits WHERE PatientID = 60;
DELETE FROM patients WHERE MID = 60;

INSERT INTO users(MID, password, role, sQuestion, sAnswer)
			VALUES (60, '30c952fab122c3f9759f02a6d95c3758b246b4fee239957b2d4fee46e26170c4', 'patient', 'am i dead?', 'yes');
/*password: pw*/
INSERT INTO patients (MID, firstName,lastName, email, phone, Gender, DateOfDeath, CauseOfDeath)
VALUES (60, 'Mother', 'Theresa', 'save@earth.com', '919-444-2231', 'Female', '2014-07-04', 35.00);


INSERT INTO officevisits(id,visitDate,HCPID,notes,HospitalID,PatientID)
VALUES (10060,'2014-05-04',9000000003,'Die in two months','1',60);