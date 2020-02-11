package edu.ncsu.csc.itrust.action;

import java.util.ArrayList;
import java.util.List;

import edu.ncsu.csc.itrust.EmailUtil;
import edu.ncsu.csc.itrust.action.base.PatientBaseAction;
import edu.ncsu.csc.itrust.beans.MedicationBean;
import edu.ncsu.csc.itrust.beans.PersonnelBean;
import edu.ncsu.csc.itrust.beans.AllergyBean;
import edu.ncsu.csc.itrust.beans.DiagnosisBean;
import edu.ncsu.csc.itrust.beans.Email;
import edu.ncsu.csc.itrust.beans.FamilyMemberBean;
import edu.ncsu.csc.itrust.beans.HealthRecord;
import edu.ncsu.csc.itrust.beans.OfficeVisitBean;
import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.beans.PrescriptionBean;
import edu.ncsu.csc.itrust.beans.ProcedureBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.AuthDAO;
import edu.ncsu.csc.itrust.dao.mysql.FamilyDAO;
import edu.ncsu.csc.itrust.dao.mysql.HealthRecordsDAO;
import edu.ncsu.csc.itrust.dao.mysql.ICDCodesDAO;
import edu.ncsu.csc.itrust.dao.mysql.NDCodesDAO;
import edu.ncsu.csc.itrust.dao.mysql.OfficeVisitDAO;
import edu.ncsu.csc.itrust.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.dao.mysql.ProceduresDAO;
import edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.NoHealthRecordsException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.risk.ChronicDiseaseMediator;
import edu.ncsu.csc.itrust.risk.RiskChecker;
import edu.ncsu.csc.itrust.validate.AllergyBeanValidator;

/**
 * Edits the patient health record for a given patient Used by editPHR.jsp
 */
public class EditPrePHRAction extends PatientBaseAction {
	private DAOFactory factory;
	private PatientDAO patientDAO;
	private AuthDAO authDAO;
	private FamilyDAO familyDAO;
	private HealthRecordsDAO hrDAO;
	private OfficeVisitDAO ovDAO;
	private ICDCodesDAO icdDAO;
	private ProceduresDAO procDAO;
	private ChronicDiseaseMediator diseaseMediator;
	private PersonnelDAO personnelDAO;
	private PersonnelBean HCPUAP;
	private PatientBean patient;
	private EmailUtil emailutil;
	private NDCodesDAO ndcodesDAO; // NEW
	private EventLoggingAction loggingAction;
	private long loggedInMID;

	/**
	 * Super class validates the patient id
	 * 
	 * @param factory     The DAOFactory to be used in creating DAOs for this
	 *                    action.
	 * @param loggedInMID The MID of the currently logged in user who is authorizing
	 *                    this action.
	 * @param pidString   The MID of the patient whose personal health records are
	 *                    being added.
	 * @throws ITrustException
	 * @throws NoHealthRecordsException
	 */
	public EditPrePHRAction(DAOFactory factory, long loggedInMID, String pidString) throws ITrustException {
		super(factory, pidString);
		this.patientDAO = factory.getPatientDAO();
		this.authDAO = factory.getAuthDAO();
		this.loggingAction = new EventLoggingAction(factory);
		emailutil = new EmailUtil(factory);
		this.factory = factory;
		this.loggedInMID = loggedInMID;
	}

	/**
	 * Register Prepatient
	 * 
	 * @throws ITrustException
	 */
	public void activatePrePatient() throws ITrustException {
		PatientBean p = this.getPatient();
		p.setIsRegistered(1);
		patientDAO.editPatient(p, this.loggedInMID);
		loggingAction.logEvent(TransactionType.ACTIVATE_PREREGISTERED, this.loggedInMID, p.getMID(), "None");
	}

	/**
	 * De-Activate Prepatient. Yeet them out of the database.
	 * 
	 * @throws ITrustException
	 */
	public void deactivatePrePatient() throws ITrustException {
		long mid = this.getPatient().getMID();
		patientDAO.deletePatient(mid);
		authDAO.removeUser(mid);
		loggingAction.logEvent(TransactionType.DEACTIVATE_PREREGISTERED, this.loggedInMID, mid, "None");
	}

	public PatientBean getPatient() throws ITrustException {
		return patientDAO.getPatient(pid);
	}

}
