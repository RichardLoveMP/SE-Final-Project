package edu.ncsu.csc.itrust.dao.mysql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;

/**
 * A class that contains essential methods to access the cause of death data in the database
 */
public class CauseOfDeathDAO {

    /**
     * The main factory for the user to be searched
     */
    private DAOFactory factory;

    /**
     * Helper function to checkout whether the years user input is correct
     * @param start the start year that the user input
     * @param end the end year that the user input
     * @return true if the inputs are valid. False otherwise
     */
    public static boolean inspectYear(String start, String end) {
        int startYearInt, endYearInt;
        try {
            startYearInt = Integer.parseInt(start);
            endYearInt = Integer.parseInt(end);
        } catch (NumberFormatException e) {
            return false;
        }
        return startYearInt >= 1970 && endYearInt >= 1970 && startYearInt <= endYearInt;
    }

    /**
     * Helper function to tell why the inputs are wrong.
     * This function should be used ONLY IF the function inspectYear() returns false
     * @param start the start year that the user input
     * @param end the end year that the user input
     * @return a message with information telling why the input is incorrect
     */
    public static String inspectYearWithMessage(String start, String end) {
        if (inspectYear(start, end)) {
            return "Your input is actually correct";
        }
        if (start == null || end == null) {
            return "You do not have any valid input yet!";
        }
        int startYearInt, endYearInt;
        try {
            startYearInt = Integer.parseInt(start);
            endYearInt = Integer.parseInt(end);
        } catch (NumberFormatException e) {
            return "Your input is not a valid number!";
        }
        if (startYearInt < 1970) {
            return "Start year should not be lower than 1970!";
        }
        if (endYearInt < 1970) {
            return "End year should not be lower than 1970!";
        }
        if (startYearInt > endYearInt) {
            return "Your start year is bigger than end year!";
        }
        return "Your year input is not correct!";
    }

    /**
     * Constructor for the class to checkout cause of death trends
     * @param factory the user factory
     */
    public CauseOfDeathDAO(DAOFactory factory) {
        this.factory = factory;
    }

    /**
     * Finds whether or not the patient given is dead
     * @param patientMID MID of the desired patient
     * @return True if the patient is dead, false otherwise
     * @throws ITrustException
     * @throws DBException
     */
    public boolean patientIsDead(long patientMID) throws ITrustException, DBException {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = factory.getConnection();
            ps = conn.prepareStatement("SELECT MID FROM patients WHERE DateOfDeath IS NOT NULL AND MID=?");
            ps.setLong(1, patientMID);
            ResultSet rs;
            rs = ps.executeQuery();
            if(rs.next()) {
                rs.close();
                ps.close();
                return true;
            }
            else{
                rs.close();
                ps.close();
                return false;
            }


        } catch (SQLException e) {

            throw new DBException(e);
        } finally {
            DBUtil.closeConnection(conn, ps);
        }
    }

    /**
     * Gets the two most common causes of deaths for a certain HCP
     * @param hcpid ID of the HCP
     * @param gender Gender of the desired patients (Male, Female, All)
     * @param startDate Date to begin the search
     * @param endDate Date to end the search
     * @return List of patient MID's that are dead according the specified parameters
     * @throws ITrustException
     * @throws DBException
     */
    @SuppressWarnings("finally")
    public List<String> getTwoCommonDeathsForHCPID(long hcpid, String gender, Date startDate, Date endDate) throws ITrustException, DBException {
        Connection conn = null;
        PreparedStatement ps = null;
        List<Long> results = new ArrayList<Long>();
        List<String> ret = new ArrayList<String>();
        try {
            conn = factory.getConnection();
            ps = conn.prepareStatement("SELECT DISTINCT PatientID FROM officevisits WHERE HCPID = ?");
            ps.setLong(1, hcpid);
            ResultSet rs;
            rs = ps.executeQuery();
            while (rs.next()) {
                long result = rs.getLong("PatientID");
                if(this.patientIsDead(result))
                {
                    results.add(result);
                }
                ret.add("" + result);
            }
            rs.close();
            ps.close();

        } catch (SQLException e) {

            throw new DBException(e);
        } finally {
            DBUtil.closeConnection(conn, ps);
            return this.getTwoMostCommonDeathsMIDs(results, gender, startDate, endDate);
        }
    }

    /**
     * Finds the name associated with an ICDCode
     * @param icdCode Code for the disease
     * @return Name of the disease for the ICDCode
     * @throws ITrustException
     * @throws DBException
     */
    public String getCauseOfDeathName(String icdCode) throws ITrustException, DBException {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = factory.getConnection();
            ps = conn.prepareStatement("SELECT Description FROM icdcodes WHERE Code = ?");
            ps.setString(1, icdCode);
            ResultSet rs;
            rs = ps.executeQuery();
            if(rs.next()) {
                String result = rs.getString("Description");
                rs.close();
                ps.close();
                return result;
            }
            else{
                rs.close();
                ps.close();
                return null;
            }


        } catch (SQLException e) {

            throw new DBException(e);
        } finally {
            DBUtil.closeConnection(conn, ps);
        }
    }

    /**
     * Finds the two most common causes of deaths among all HCP's
     * @param gender Gender of the patients desired to be in the results (Male, Female, All)
     * @param startDate Date to start the search
     * @param endDate Date to end the search
     * @return List of the top two names of diseases according to the specified parameters
     * @throws ITrustException
     * @throws DBException
     */
    @SuppressWarnings("finally")
    public List<String> getTwoMostCommonDeaths(String gender, Date startDate, Date endDate) throws ITrustException, DBException {
        Connection conn = null;
        PreparedStatement ps = null;
        List<String> results = new ArrayList<String>();


        try {
            conn = factory.getConnection();
            if(gender.equalsIgnoreCase("All")){
                ps = conn.prepareStatement("SELECT DISTINCT CauseOfDeath, COUNT(CauseOfDeath) FROM patients WHERE YEAR(?) <= YEAR(DateOfDeath) AND YEAR(DateOfDeath) <= YEAR(?) GROUP BY CauseOfDeath ORDER BY COUNT(CauseOfDeath) DESC");
                ps.setDate(1, startDate);
                ps.setDate(2, endDate);
            }
            else{
                ps = conn.prepareStatement("SELECT DISTINCT CauseOfDeath, COUNT(CauseOfDeath) FROM patients WHERE Gender = ? AND YEAR(?) <= YEAR(DateOfDeath) AND YEAR(DateOfDeath) <= YEAR(?) GROUP BY CauseOfDeath ORDER BY COUNT(CauseOfDeath) DESC");
                ps.setString(1, gender);
                ps.setDate(2, startDate);
                ps.setDate(3, endDate);
            }
            ResultSet rs;
            rs = ps.executeQuery();
            int count = 0;
            while(rs.next() && count < 2) {

                String result = rs.getString("CauseOfDeath");
                if(!result.isEmpty())
                {
                    String name = this.getCauseOfDeathName(result);
                    results.add("Name: " + name + ", Code: " + result + ", Number of Deaths: " + rs.getString("COUNT(CauseOfDeath)"));
                    count++;
                }
            }

            rs.close();
            ps.close();


        } catch (SQLException e) {

            throw new DBException(e);
        } finally {
            DBUtil.closeConnection(conn, ps);
            return results;
        }
    }

    /**
     * Finds the two most common deaths among specific patients
     * @param mids List of MID's of the patients to search
     * @param gender Gender of the patients to be included in results (Male, Female, All)
     * @param startDate Date to start the search
     * @param endDate Date to end the search
     * @return List of the top two names of the most common deaths for the specified parameters
     * @throws ITrustException
     * @throws DBException
     */
    @SuppressWarnings("finally")
    public List<String> getTwoMostCommonDeathsMIDs(List<Long> mids, String gender, Date startDate, Date endDate) throws ITrustException, DBException {
        Connection conn = null;
        PreparedStatement ps = null;
        List<String> results = new ArrayList<String>();


        try {
            conn = factory.getConnection();
            String statement = "SELECT DISTINCT CauseOfDeath, COUNT(CauseOfDeath) FROM patients WHERE (";
            for(int i = 0; i < mids.size(); i++)
            {
                if(i+1 == mids.size())
                    statement += "MID = " + mids.get(i) + ")";
                else
                    statement += "MID = " + mids.get(i) + " OR ";
            }

            if(gender.equalsIgnoreCase("All")){

                statement += " AND YEAR(?) <= YEAR(DateOfDeath) AND YEAR(DateOfDeath) <= YEAR(?) GROUP BY CauseOfDeath ORDER BY COUNT(CauseOfDeath) DESC";
                ps = conn.prepareStatement(statement);

                ps.setDate(1, startDate);
                ps.setDate(2, endDate);
            }
            else{
                statement += " AND Gender = ? AND YEAR(?) <= YEAR(DateOfDeath) AND YEAR(DateOfDeath) <= YEAR(?) GROUP BY CauseOfDeath ORDER BY COUNT(CauseOfDeath) DESC";

                ps = conn.prepareStatement(statement);
                ps.setString(1, gender);
                ps.setDate(2, startDate);
                ps.setDate(3, endDate);
            }
            ResultSet rs;
            rs = ps.executeQuery();
            int count = 0;
            while(rs.next() && count < 2) {

                String result = rs.getString("CauseOfDeath");
                if(!result.isEmpty())
                {
                    String name = this.getCauseOfDeathName(result);
                    results.add("Name: " + name + ", Code: " + result + ", Number of Deaths: " + rs.getString("COUNT(CauseOfDeath)"));
                    count++;
                }
            }

            rs.close();
            ps.close();


        } catch (SQLException e) {

            throw new DBException(e);
        } finally {
            DBUtil.closeConnection(conn, ps);
            return results;
        }
    }
}
