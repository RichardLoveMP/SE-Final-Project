package edu.ncsu.csc.itrust.beans;

/**
 * A bean for storing normal values.
 * 
 * A bean's purpose is to store data. Period. Little or no functionality is to be added to a bean 
 * (with the exception of minor formatting such as concatenating phone numbers together). 
 * A bean must only have Getters and Setters (Eclipse Hint: Use Source > Generate Getters and Setters 
 * to create these easily)
 */
public class NormalBean {
	
	private double z;
	private double[] data = new double[10];
	
	/**
	 * Constructs a NormalBean object with null fields
	 */
	public NormalBean() {
	}
	
	/**
	 * Gets the z value
	 * @return the z value
	 */
	public double getZ() {
		return z;
	}
	
	/**
	 * Sets the z value
	 * @param z double value representing a z value in the normal distribution table
	 */
	public void setZ(double z) {
		this.z = z;
	}
	
	public double getData(int index) {
		if (index >= 1 && index <=9) {
			return data[index];
		}
		return data[0];
		
	}

	public void setData(int index, double val) {
		data[index] = val;
	}

	public double get_00() {
		return data[0];
	}
	
	public void set_00(double _00) {
		data[0] = _00;
	}
	
	/**
	 * Gets the .01 value 
	 * @return the .01 value
	 */
	public double get_01() {
		return data[1];
	}
	
	/**
	 * Sets the .01 value
	 * @param _01 the .01 value as a double
	 */
	public void set_01(double _01) {
		data[1] = _01;
	}
	
	/**
	 * Gets the .02 value 
	 * @return the .02 value
	 */
	public double get_02() {
		return data[2];
	}
	
	/**
	 * Sets the .02 value
	 * @param _02 the .02 value as a double
	 */
	public void set_02(double _02) {
		data[2] = _02;
	}
	
	/**
	 * Gets the .03 value 
	 * @return the .03 value
	 */
	public double get_03() {
		return data[3];
	}
	
	/**
	 * Sets the .03 value
	 * @param _03 the .03 value as a double
	 */
	public void set_03(double _03) {
		data[3] = _03;
	}
	
	/**
	 * Gets the .04 value 
	 * @return the .04 value
	 */
	public double get_04() {
		return data[4];
	}
	
	/**
	 * Sets the .04 value
	 * @param _04 the .04 value as a double
	 */
	public void set_04(double _04) {
		data[4] = _04;
	}
	
	/**
	 * Gets the .05 value 
	 * @return the .05 value
	 */
	public double get_05() {
		return data[5];
	}
	
	/**
	 * Sets the .05 value
	 * @param _05 the .05 value as a double
	 */
	public void set_05(double _05) {
		data[5] = _05;
	}
	
	/**
	 * Gets the .06 value 
	 * @return the .06 value
	 */
	public double get_06() {
		return data[6];
	}
	
	/**
	 * Sets the .06 value
	 * @param _06 the .06 value as a double
	 */
	public void set_06(double _06) {
		data[6] = _06;
	}
	
	/**
	 * Gets the .07 value 
	 * @return the .07 value
	 */
	public double get_07() {
		return data[7];
	}
	
	/**
	 * Sets the .07 value
	 * @param _07 the .07 value as a double
	 */
	public void set_07(double _07) {
		data[7] = _07;
	}
	
	/**
	 * Gets the .08 value 
	 * @return the .08 value
	 */
	public double get_08() {
		return data[8];
	}
	
	/**
	 * Sets the .08 value
	 * @param _08 the .08 value as a double
	 */
	public void set_08(double _08) {
		data[8] = _08;
	}
	
	/**
	 * Gets the .09 value 
	 * @return the .09 value
	 */
	public double get_09() {
		return data[9];
	}
	
	/**
	 * Sets the .09 value
	 * @param _09 the .09 value as a double
	 */
	public void set_09(double _09) {
		data[9] = _09;
	}
}
