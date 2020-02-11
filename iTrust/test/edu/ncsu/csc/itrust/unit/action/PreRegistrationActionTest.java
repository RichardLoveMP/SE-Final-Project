package edu.ncsu.csc.itrust.unit.action;

import java.util.List;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.action.PreRegistrationAction;
import edu.ncsu.csc.itrust.beans.Email;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.FakeEmailDAO;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import edu.ncsu.csc.itrust.validate.ValidationFormat;

public class PreRegistrationActionTest extends TestCase {
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private DAOFactory evil = EvilDAOFactory.getEvilInstance();
	private FakeEmailDAO feDAO = factory.getFakeEmailDAO();
	private PreRegistrationAction action;
	private TestDataGenerator gen;

	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.clearAllTables();
		action = new PreRegistrationAction(factory);
	}

	public void testPreRegistration() throws Exception {
		gen.patient1();
		gen.hcp0();
		PatientBean patient = new PatientBean();
		patient.setFirstName("fname");
		patient.setLastName("lname");
		patient.setEmail("email"); 
		patient.setPhone("phone");
		patient.setStreetAddress1("address");
		patient.setIsRegistered(0);
		patient.setIcName("insureName");
		patient.setIcPhone("insurePhone");
		patient.setIcAddress1("insureAddr");
		assertTrue(
				action.preRegister(patient, "password", "password", "127.0.0.1")
						.contains("Patient Pre Registered"));
	}
	
	public void testAddPatientHealthData() throws Exception {
		gen.patient1();
		gen.hcp0();
		assertTrue(action.addPatientHealthData("12345","12.5", "13.5", "True"));
	}

	public void testValidatePasswordNull() throws Exception {
		gen.patient1();
		PatientBean patient = new PatientBean();
		patient.setFirstName("fname");
		patient.setLastName("lname");
		patient.setEmail("email"); 
		patient.setPhone("phone");
		patient.setStreetAddress1("address");
		patient.setIsRegistered(0);
		patient.setIcName("insureName");
		patient.setIcPhone("insurePhone");
		patient.setIcAddress1("insureAddr");
		try {
			
			action.preRegister(patient, null, null, "127.0.0.1");
			fail("exception should have been thrown");
		} catch (FormValidationException e) {
			assertEquals("Password cannot be empty", e.getErrorList().get(0));
			assertEquals(1, e.getErrorList().size());
		}
	}

	public void testValidateEmailEmpty() throws Exception {
		gen.patient1();
		PatientBean patient = new PatientBean();
		patient.setFirstName("fname");
		patient.setLastName("lname");
		patient.setEmail(null); 
		patient.setPhone("phone");
		patient.setStreetAddress1("address");
		patient.setIsRegistered(0);
		patient.setIcName("insureName");
		patient.setIcPhone("insurePhone");
		patient.setIcAddress1("insureAddr");
		try {
			action.preRegister(patient, "password", "password", "127.0.0.1");
			fail("exception should have been thrown");
		} catch (FormValidationException e) {
			assertEquals("Email cannot be empty", e.getErrorList().get(0));
			assertEquals(1, e.getErrorList().size());
		}
	}

	public void testValidateEmailWrong() throws Exception {
		gen.patient1();
		PatientBean patient = new PatientBean();
		patient.setFirstName("fname");
		patient.setLastName("lname");
		patient.setEmail("email"); 
		patient.setPhone("phone");
		patient.setStreetAddress1("address");
		patient.setIsRegistered(0);
		patient.setIcName("insureName");
		patient.setIcPhone("insurePhone");
		patient.setIcAddress1("insureAddr");
		try {
			action.preRegister(patient, "password", "password", "127.0.0.1");
			action.preRegister(patient, "password", "password", "127.0.0.1");
			fail("exception should have been thrown");
		} catch (FormValidationException e) {
			assertEquals("Email already exists", e.getErrorList().get(0));
		}
	}

	public void testValidatePasswordEmpty() throws Exception {
		gen.patient1();
		PatientBean patient = new PatientBean();
		patient.setFirstName("fname");
		patient.setLastName("lname");
		patient.setEmail("email"); 
		patient.setPhone("phone");
		patient.setStreetAddress1("address");
		patient.setIsRegistered(0);
		patient.setIcName("insureName");
		patient.setIcPhone("insurePhone");
		patient.setIcAddress1("insureAddr");
		try {
			action.preRegister(patient, "", "", "127.0.0.1");
			fail("exception should have been thrown");
		} catch (FormValidationException e) {
			assertEquals("Password cannot be empty", e.getErrorList().get(0));
			assertEquals(1, e.getErrorList().size());
		}
	}

	public void testValidatePasswordWrong() throws Exception {
		gen.patient1();
		PatientBean patient = new PatientBean();
		patient.setFirstName("fname");
		patient.setLastName("lname");
		patient.setEmail("email"); 
		patient.setPhone("phone");
		patient.setStreetAddress1("address");
		patient.setIsRegistered(0);
		patient.setIcName("insureName");
		patient.setIcPhone("insurePhone");
		patient.setIcAddress1("insureAddr");
		try {
			action.preRegister(patient, "eat", "123", "127.0.0.1");
			fail("exception should have been thrown");
		} catch (FormValidationException e) {
			assertEquals("Passwords don't match", e.getErrorList().get(0));
			assertEquals("Password must be in the following format: " + ValidationFormat.PASSWORD.getDescription(),
					e.getErrorList().get(1));
			assertEquals(2, e.getErrorList().size());
		}
	}
}
