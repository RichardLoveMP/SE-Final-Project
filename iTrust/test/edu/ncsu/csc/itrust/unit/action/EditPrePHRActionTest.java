package edu.ncsu.csc.itrust.unit.action;

import java.util.List;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.action.EditPrePHRAction;
import edu.ncsu.csc.itrust.action.ViewMyRecordsAction;
import edu.ncsu.csc.itrust.beans.AllergyBean;
import edu.ncsu.csc.itrust.beans.PreRegisteredPatientBean;
import edu.ncsu.csc.itrust.beans.FamilyMemberBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class EditPrePHRActionTest extends TestCase {
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen;
	private EditPrePHRAction action;
	private PatientDAO patientDAO = new PatientDAO(factory);

	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.standardData();
	}

	public void testActivatePrePatient() throws Exception, ITrustException{
		action = new EditPrePHRAction(factory, 9000000000L, "411");
		action.activatePrePatient();
		assertTrue(action.getPatient().getIsRegistered() == 1);
		assertTrue(patientDAO.checkIfPatientIsActive(411));
	}

	public void testDeactivatePrePatient() throws Exception, ITrustException{
		action = new EditPrePHRAction(factory, 9000000000L, "411");
		action.deactivatePrePatient();
		assertTrue(action.getPatient() == null);
	}

}
