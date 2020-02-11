package edu.ncsu.csc.itrust.beans;

/**
 * A bean for storing data about a patient's visit.
 * 
 * A bean's purpose is to store data. Period. Little or no functionality is to be added to a bean 
 * (with the exception of minor formatting such as concatenating phone numbers together). 
 * A bean must only have Getters and Setters (Eclipse Hint: Use Source > Generate Getters and Setters.
 * to create these easily)
 */
public class PreRegisteredPatientBean {
	//(Name, Address, Email, Phone, Insurance Information, Height, Weight, Smoke
	private PatientBean patient = null;
	private HealthRecord health = null;

	public PreRegisteredPatientBean() {
		
	}

	public PatientBean getPatientBean() {
		return patient;
	}

	public void setPatientBean(PatientBean patient) {
		this.patient = patient;
	}

	public HealthRecord getHealthRecord(){
		return health;
	}
	
	public void setHealthRecord(HealthRecord health){
		this.health = health;
	}

}
