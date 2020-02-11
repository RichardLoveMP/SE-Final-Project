DELETE FROM personalhealthinformation WHERE PatientID = 411;
	
INSERT INTO personalhealthinformation 
	   (PatientID,OfficeVisitID,Height,Weight,Smoker,BloodPressureN,BloodPressureD,CholesterolHDL,CholesterolLDL,CholesterolTri,HCPID, AsOfDate, OfficeVisitDate)
VALUES ( 411, 9001, 62.2,   206.5,   0,      165,          220,           40,             255,         230,          9000000000, CONCAT(YEAR(NOW())-2, '-08-12 08:34:58') , CONCAT(YEAR(NOW())-2, '-08-12'))
on duplicate key update OfficeVisitID = OfficeVisitID;