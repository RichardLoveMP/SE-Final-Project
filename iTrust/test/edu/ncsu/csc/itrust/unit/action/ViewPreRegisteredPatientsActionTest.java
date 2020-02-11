package edu.ncsu.csc.itrust.unit.action;

import java.util.List;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.action.ViewPreRegisteredPatientsAction;
import edu.ncsu.csc.itrust.beans.PreRegisteredPatientBean;
import edu.ncsu.csc.itrust.beans.PersonnelBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

/**
 * ViewPatientOfficeVisitHistoryActionTest
 */
public class ViewPreRegisteredPatientsActionTest extends TestCase{

	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen = new TestDataGenerator();
	private ViewPreRegisteredPatientsAction action;
	
	@Override
	protected void setUp() throws Exception{
		action = new ViewPreRegisteredPatientsAction(factory, 9000000000L);
		gen.clearAllTables();
		gen.standardData();
	}
	
	/**
	 * testGetPatients
	 * @throws Exception
	 */
	public void testGetPreRegisteredPatients() throws Exception {

		List<PreRegisteredPatientBean> list = action.getPreRegisteredPatients();
		assertEquals(1, list.size());
		assertEquals("Pre Patient", list.get(0).getPatientBean().getFullName());
		assertEquals(411, list.get(0).getPatientBean().getMID());
		
	}
}
