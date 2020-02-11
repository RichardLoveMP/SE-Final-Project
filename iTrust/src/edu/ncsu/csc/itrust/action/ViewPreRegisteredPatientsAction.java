package edu.ncsu.csc.itrust.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.beans.PreRegisteredPatientBean;
import edu.ncsu.csc.itrust.beans.HealthRecord;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.OfficeVisitDAO;
import edu.ncsu.csc.itrust.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO;
import edu.ncsu.csc.itrust.dao.mysql.HealthRecordsDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;

/**
 * 
 * Action class for ViewPatientOfficeVisitHistory.jsp
 *
 */
public class ViewPreRegisteredPatientsAction {
	private long loggedInMID;
	private PatientDAO patientDAO;
	private PersonnelDAO personnelDAO;
	private OfficeVisitDAO officevisitDAO;
	private HealthRecordsDAO healthRecordDAO;
	private ArrayList<PreRegisteredPatientBean> prePatients;

	/**
	 * Set up defaults
	 * @param factory The DAOFactory used to create the DAOs used in this action.
	 * @param loggedInMID The MID of the person viewing the office visits.
	 */
	public ViewPreRegisteredPatientsAction(DAOFactory factory, long loggedInMID) {
		this.loggedInMID = loggedInMID;
		this.personnelDAO = factory.getPersonnelDAO();
		officevisitDAO = factory.getOfficeVisitDAO();
		this.patientDAO = factory.getPatientDAO();
		this.healthRecordDAO = factory.getHealthRecordsDAO();
		
		prePatients = new ArrayList<PreRegisteredPatientBean>();
		
	}
	
	/**
	 * Adds all the office visits for the logged in HCP to a list.
	 * 
	 * @throws ITrustException
	 */
	private void processPreRegisteredPatients() throws ITrustException {
		try {
			List<PatientBean> plist = patientDAO.getAllPrePatients();
			for(PatientBean pb : plist) {
				// Create a new pre-patient bean
				if(pb.getDateOfDeactivationStr() == "" || pb.getDateOfDeactivationStr() == "null"){
					PreRegisteredPatientBean prePatientBean = new PreRegisteredPatientBean();
					
					prePatientBean.setPatientBean(pb);
					long mid = pb.getMID();
					List<HealthRecord> hlist = healthRecordDAO.getAllHealthRecords(mid);
					prePatientBean.setHealthRecord(hlist.get(0));
					prePatients.add(prePatientBean);
				}
			}
		}
		catch (DBException dbe) {
			throw new ITrustException(dbe.getMessage());
		}
	}
	
/**
 * Get the list of pre-patients a HCP can register
 * 
 * @return the list of pre-patients a HCP can register
 * @throws DBException
 */
	public List<PreRegisteredPatientBean> getPreRegisteredPatients() throws DBException {
		
		try {
			processPreRegisteredPatients();
		}
		catch (ITrustException ie) {
			//TODO
		}

		return prePatients;
	}
}