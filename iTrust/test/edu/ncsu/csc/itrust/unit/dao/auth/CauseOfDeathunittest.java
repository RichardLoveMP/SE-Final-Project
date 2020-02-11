package edu.ncsu.csc.itrust.unit.dao.auth;


import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.CauseOfDeathDAO;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

/**
 * JUnit test to test the CauseOfDeathDAO class
 */
public class CauseOfDeathunittest {
    /**
     * The user factory
     */
    private DAOFactory factory = TestDAOFactory.getTestInstance();
    /**
     * The instance to generate possible test data
     */
    private TestDataGenerator tdg = new TestDataGenerator();
    /**
     * Instance of Cause Of Death trend
     */
    private CauseOfDeathDAO ctd;

    /**
     * Necessary set up process to clear up database
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        ctd = new CauseOfDeathDAO(factory);
        tdg.clearAllTables();
        tdg.deadpatient1();
        tdg.icd9cmCodes();

    }

    @Test
    public void testPatientIsDead() throws DBException, ITrustException {
        assertTrue(ctd.patientIsDead(51));
    }

    @Test
    public void testPatientNotDead() throws DBException, ITrustException {
        assertTrue(!ctd.patientIsDead(1));
    }

    @Test
    public void testInspectYear() throws DBException, ITrustException {
        assertTrue(!ctd.inspectYear("1969", "1971"));
        assertTrue(!ctd.inspectYear("1968", "1969"));
        assertTrue(!ctd.inspectYear("hello", "kill"));
        assertTrue(!ctd.inspectYear("2007", "2005"));
        assertTrue(ctd.inspectYear("2014", "2019"));
    }
    @Test
    public void testInspectYearWithMessage() throws DBException, ITrustException {
        assertEquals(ctd.inspectYearWithMessage(null, "1971"), "You do not have any valid input yet!");
        assertEquals(ctd.inspectYearWithMessage("1971", null), "You do not have any valid input yet!");
        assertEquals(ctd.inspectYearWithMessage("hello", "1971"), "Your input is not a valid number!");
        assertEquals(ctd.inspectYearWithMessage("1969", "1971"), "Start year should not be lower than 1970!");
        assertEquals(ctd.inspectYearWithMessage("1980", "1969"), "End year should not be lower than 1970!");
        assertEquals(ctd.inspectYearWithMessage("2004", "2000"), "Your start year is bigger than end year!");
    }

    @Test
    public void testNameOfDeath() throws DBException, ITrustException {
        assertTrue(ctd.getCauseOfDeathName("84.50").equalsIgnoreCase("malaria"));
    }

    @Test
    public void testTwoMostCommonDeathAll() throws DBException, ITrustException {
        List<String> list = ctd.getTwoMostCommonDeaths("All", Date.valueOf("2012-01-01"), Date.valueOf("2014-01-01"));
        assertTrue(list.size() == 2);
        assertTrue(list.get(0).contains("35.00"));
        assertTrue(list.get(1).contains("84.50"));
    }

    @Test
    public void testTwoMostCommonDeathMale() throws DBException, ITrustException {
        List<String> list = ctd.getTwoMostCommonDeaths("Male", Date.valueOf("2012-01-01"), Date.valueOf("2014-01-01"));
        assertTrue(list.size() == 2);
        assertTrue(list.get(0).contains("84.50"));
        assertTrue(list.get(1).contains("35.00"));
    }

    @Test
    public void testTwoMostCommonDeathsMIDAll() throws DBException, ITrustException {
        List<Long> mids = new ArrayList<Long>();
        mids.add((long) 51);
        mids.add((long) 52);
        mids.add((long) 53);
        mids.add((long) 54);
        mids.add((long) 55);
        mids.add((long) 56);

        List<String> list = ctd.getTwoMostCommonDeathsMIDs(mids, "All", Date.valueOf("2012-01-01"), Date.valueOf("2014-01-01"));
        assertTrue(list.size() == 2);
        assertTrue(list.get(0).contains("84.50"));
        assertTrue(list.get(1).contains("72.00"));
    }

    @Test
    public void testTwoMostCommonDeathsMIDFemale() throws DBException, ITrustException {
        List<Long> mids = new ArrayList<Long>();
        mids.add((long) 51);
        mids.add((long) 52);
        mids.add((long) 53);
        mids.add((long) 55);
        mids.add((long) 56);
        mids.add((long) 58);

        List<String> list = ctd.getTwoMostCommonDeathsMIDs(mids, "Female", Date.valueOf("2012-01-01"), Date.valueOf("2014-01-01"));
        assertTrue(list.size() == 2);
        assertTrue(list.get(0).contains("35.00"));
        assertTrue(list.get(1).contains("72.00"));
    }

    @Test
    public void testTwoMostCommonDeathsHCPID() throws DBException, ITrustException {
        long hcpid = Long.parseLong("9000000000");
        List<String> list = ctd.getTwoCommonDeathsForHCPID(hcpid, "All", Date.valueOf("2012-01-01"), Date.valueOf("2014-01-01"));
        assertTrue(list.size() == 2);
        assertTrue(list.get(0).contains("84.50"));
        assertTrue(list.get(1).contains("35.00") || list.get(1).contains("72.00"));
    }
}