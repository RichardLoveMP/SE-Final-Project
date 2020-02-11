package edu.ncsu.csc.itrust.action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import edu.ncsu.csc.itrust.DBUtil;
import java.util.Arrays;
import edu.ncsu.csc.itrust.EmailUtil;
import edu.ncsu.csc.itrust.beans.Email;
import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.beans.HealthRecord;
import edu.ncsu.csc.itrust.beans.PersonnelBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.AuthDAO;
import edu.ncsu.csc.itrust.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.dao.mysql.HealthRecordsDAO;
import edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO;
import edu.ncsu.csc.itrust.enums.Role;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ErrorList;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.validate.ValidationFormat;

/**
 * Manages resetting the password Used by resetPassword.jsp
 */
public class PreRegistrationAction {

	private AuthDAO authDAO;
	private PatientDAO patientDAO;
	private DAOFactory factory;
	private HealthRecordsDAO healthDAO;

	/**
	 * Set up defaults
	 * 
	 * @param factory The DAOFactory used to create the DAOs used in this action.
	 */
	public PreRegistrationAction(DAOFactory factory) {
		this.authDAO = factory.getAuthDAO();
		this.patientDAO = factory.getPatientDAO();
		this.healthDAO = factory.getHealthRecordsDAO();
		this.factory = factory;
	}

	/**
	 * Resets the password for the given mid
	 * 
	 * @param patient         PatientBean
	 * @param password        their password
	 * @param confirmPassword their password again
	 * @param ipAddr          the ip address the request is coming from
	 * @return status message
	 * @throws FormValidationException
	 * @throws ITrustException
	 */
	public String preRegister(PatientBean patient, String password, String confirmPassword, String ipAddr) throws FormValidationException, ITrustException {
		Role role;
		role = Role.PATIENT;

		try {
			validateName(patient.getFirstName(), patient.getLastName());
			validatePassword(password, confirmPassword);
			validateEmail(patient.getEmail());
			long mid = patientDAO.addEmptyPatient();
			patient.setMID(mid);
			patientDAO.updateNewPatient(patient);
			authDAO.addUser(mid, role, password);
			return "Patient Pre Registered " + mid;

		} catch (DBException e) {
			return "Error";
		}
	}

	/**
	 * Resets the password for the given mid
	 * 
	 * @param mid       of the user to have their password reset
	 * @param height        what role the user has in iTrust
	 * @param weight           answers to their security question
	 * @param smoker        their password
	 * @return status message
	 * @throws FormValidationException
	 * @throws ITrustException
	 */
	public boolean addPatientHealthData(String mid, String height, String weight, String smokerstr) throws FormValidationException, 
	 ITrustException, DBException{
		HealthRecord health = new HealthRecord();
		health.setHeight(0.0);
		health.setWeight(0.0);
		long mid_l = Long.parseLong(mid);
		health.setPatientID(mid_l);
		if(smokerstr != null && smokerstr.equals("True")){
			health.setSmoker(1);
		}

		if(height != null && !height.equals("")){
			health.setHeight(Float.parseFloat(height));
		}

		if(weight != null && !weight.equals("")){
			health.setWeight(Float.parseFloat(weight));
		}

		boolean ret =  healthDAO.add(health);
		System.out.println(ret);
		return ret;
	}
	 
	/**
	 * Checks to make sure the name is valid
	 * 
	 * @param first
	 * @param last
	 * @throws FormValidationException
	 */
	private void validateName(String first, String last) throws FormValidationException {
		ErrorList errorList = new ErrorList();
		if (first == null || "".equals(first) || last == null || "".equals(last)) {
			errorList.addIfNotNull("Make sure both First Name and Last Name are filled in");
		}
		if (errorList.hasErrors())
			throw new FormValidationException(errorList);
	}

	/**
	 * Checks to make sure the email is not already used
	 * 
	 * @param email the email
	 * @throws FormValidationException
	 */
	private void validateEmail(String email) throws FormValidationException {
		ErrorList errorList = new ErrorList();
		if (email == null || "".equals(email)) {
			errorList.addIfNotNull("Email cannot be empty");
		} else {
			// PreparedStatement query = "select count(*) from patients where email=?";
			int count = 0;
			try {
				count = patientDAO.getEmailCount(email);
			} catch (DBException e) {
				// out.println(e.getMessage());
				// ;
			}
			if (count > 0) {
				errorList.addIfNotNull("Email already exists");
			}
		}
		if (errorList.hasErrors())
			throw new FormValidationException(errorList);
	}

	/**
	 * Checks to make sure the password is correctly entered twice.
	 * 
	 * @param password        the password
	 * @param confirmPassword the password again for confirmation
	 * @throws FormValidationException
	 */

	private void validatePassword(String password, String confirmPassword) throws FormValidationException {
		ErrorList errorList = new ErrorList();
		if (password == null || "".equals(password)) {
			errorList.addIfNotNull("Password cannot be empty");
		} else {
			if (!password.equals(confirmPassword))
				errorList.addIfNotNull("Passwords don't match");
			if (!ValidationFormat.PASSWORD.getRegex().matcher(password).matches()) {
				errorList.addIfNotNull(
						"Password must be in the following format: " + ValidationFormat.PASSWORD.getDescription());
			}
		}
		if (errorList.hasErrors())
			throw new FormValidationException(errorList);
	}
}
