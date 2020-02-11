package edu.ncsu.csc.itrust.unit.bean;

import java.text.SimpleDateFormat;
import java.util.Date;
import junit.framework.TestCase;
import edu.ncsu.csc.itrust.DateUtil;
import edu.ncsu.csc.itrust.beans.PreRegisteredPatientBean;
import edu.ncsu.csc.itrust.beans.HealthRecord;
import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.enums.BloodType;

public class PreRegisteredPatientBeanTest extends TestCase {
	private Date today;

	@Override
	protected void setUp() throws Exception {
		today = new Date();
	}

	public void testBean() {
		PreRegisteredPatientBean pre = new PreRegisteredPatientBean();
		PatientBean p1 = new PatientBean();
		p1.setBloodType(BloodType.ABNeg);
		p1.setDateOfBirthStr("bad date");
		p1.setCity("Raleigh");
		p1.setState("NC");
		p1.setZip("27613-1234");
		p1.setIcCity("Raleigh");
		p1.setIcState("NC");
		p1.setIcZip("27613-1234");
		p1.setSecurityQuestion("Question");
		p1.setSecurityAnswer("Answer");
		p1.setPassword("password");
		p1.setConfirmPassword("confirm");
		p1.setIsRegistered(0);
		pre.setPatientBean(p1);
		PatientBean p = pre.getPatientBean();
		assertEquals(BloodType.ABNeg, p.getBloodType());
		assertNull(p.getDateOfBirth());
		assertEquals(-1, p.getAge());
		assertEquals("Raleigh, NC 27613-1234", p.getIcAddress3());
		assertEquals("Raleigh, NC 27613-1234", p.getStreetAddress3());
		assertEquals("Question", p.getSecurityQuestion());
		assertEquals("Answer", p.getSecurityAnswer());
		assertEquals("password", p.getPassword());
		assertEquals("confirm", p.getConfirmPassword());
		assertEquals(0, p.getIsRegistered());
	}

	public void testBean2(){
		PreRegisteredPatientBean pre = new PreRegisteredPatientBean();
		HealthRecord h1 = new HealthRecord();
		h1.setSmoker(1);
		h1.setHeight(12);
		h1.setWeight(23);
		pre.setHealthRecord(h1);
	    HealthRecord h = pre.getHealthRecord();
		assertEquals(h.getHeight(), 12.0);
		assertEquals(h.isSmoker(), true);
		assertEquals(h.getWeight(), 23.0);
	}
	
	
}
