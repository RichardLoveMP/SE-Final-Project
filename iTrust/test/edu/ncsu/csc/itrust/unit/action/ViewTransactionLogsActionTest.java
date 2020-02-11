package edu.ncsu.csc.itrust.unit.action;

import static org.junit.Assert.*;

import java.util.List;
import java.util.HashMap;

import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.beans.OphthalmologyOVRecordBean;
import edu.ncsu.csc.itrust.beans.TransactionBean;

import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;



import edu.ncsu.csc.itrust.action.AddOphthalmologyOVAction;
import edu.ncsu.csc.itrust.action.ViewOphthalmologyOVAction;
import edu.ncsu.csc.itrust.action.EditOphthalmologyOVAction;

import edu.ncsu.csc.itrust.action.ViewTransactionLogsAction;
import edu.ncsu.csc.itrust.dao.DAOFactory;

import edu.ncsu.csc.itrust.enums.TransactionType;
import junit.framework.TestCase;

public class ViewTransactionLogsActionTest extends TestCase{
    private DAOFactory factory = TestDAOFactory.getTestInstance();
    private TestDataGenerator gener = new TestDataGenerator();
    private ViewTransactionLogsAction Logaction;
    public void setUp() throws Exception {
	gener = new TestDataGenerator();
	gener.clearAllTables();
        gener.standardData();
        Logaction = new ViewTransactionLogsAction(factory);
    }
    
    public void testgetTransactionLogsAfterFiltering() throws Exception {
	//test 1: try to get a specific transcation data but out of bound
        List<TransactionBean> testtransactions1 = Logaction.getTransactionLogsAfterFiltering("hcp", "patient", "07/01/2009", "08/23/2009", "1900");
        assertEquals(0, testtransactions1.size());

    }


    //revise for the previous test

    public void testgetTransactionLogsAfterFiltering2() throws Exception {

	// test 2: try to get all of the transaction data
        List<TransactionBean> testtransactions2 = Logaction.getTransactionLogsAfterFiltering("all", "all", null, null, "all");
        assertEquals(47, testtransactions2.size());

    }

    public void testgetTransactionLogsAfterFiltering3() throws Exception {
    	// test 3: try to get the valid good data
	    List<TransactionBean> testtransaction3 = Logaction.getTransactionLogsAfterFiltering("all","all","06/01/2007","07/01/2007","1900");
	    assertEquals(18, testtransaction3.size());
    }

    public void testgetTransactionLogsAfterFiltering4() throws Exception {
        // test 4: try to get a invaild reverse data
	    List<TransactionBean> testtransaction4 = Logaction.getTransactionLogsAfterFiltering("all","all","07/01/2007","06/01/2007","1900");
    	assertEquals(0,testtransaction4.size());

    }


    public void testgetTransactionLogsAfterFiltering5() throws Exception {
        	//test 5: test by different transaction code
	    List<TransactionBean> testtransaction5 = Logaction.getTransactionLogsAfterFiltering("all","all","03/04/2008","11/14/2008","1600");
	    assertEquals(4,testtransaction5.size());

    }



    public void testgetTransactionLogsContent() throws Exception {
	// test 1: try to get all of the transaction data and refer to a middle part data
        List<TransactionBean> testtransactions1 = Logaction.getTransactionLogsAfterFiltering("all", "all", null, null, "all");
        assertEquals("hcp", testtransactions1.get(12).getRoleForLoggedInUser());
 
    }
    
    

    public void testgetTransactionLogsContent2() throws Exception {
        // test 1: try to get all of the transaction data and refer to a middle part data
            List<TransactionBean> testtransactions1 = Logaction.getTransactionLogsAfterFiltering("all", "all", null, null, "all");
        
        // test 2: try to get the addinfo
        assertEquals("Identified risk factors of chronic diseases", testtransactions1.get(14).getAddedInfo());
     
    }

    public void testgetTransactionLogsContent3() throws Exception {

         //test 3: get logged in MID by different transaction code
            List<TransactionBean> testtransaction3 = Logaction.getTransactionLogsAfterFiltering("all","all","03/04/2008","11/14/2008","1600");
           
    
        assertEquals(9000000003l,testtransaction3.get(2).getLoggedInMID());
    
    }



    public void testgetTransactionLogsContent4() throws Exception {

        //test 3: get logged in MID by different transaction code
           List<TransactionBean> testtransaction3 = Logaction.getTransactionLogsAfterFiltering("all","all","03/04/2008","11/14/2008","1600");
   
       // test 4:test the time logged
       
       assertEquals(1213553700000l,testtransaction3.get(1).getTimeLogged().getTime());
   }

   // for uc39_s3 test case
   public void testhashMapForLoggedInUserType1() throws Exception {
       // test: to get the hashmap for the logged in user type
        HashMap<String,Integer> userhash = Logaction.hashMapForLoggedInUserType();
        // test the hash size of the hashmap
        assertEquals(8, userhash.size());
   }
    // for uc39_s3 test case
    public void testhashMapForLoggedInUserType2() throws Exception {
        // test: to get the hashmap for the logged in user type
         HashMap<String,Integer> userhash = Logaction.hashMapForLoggedInUserType();
         
        int UserSize = userhash.keySet().size();
        assertEquals(8,UserSize);
    }

    public void testhashMapForLoggedInUserType3() throws Exception {
        // test: to get the hashmap for the logged in user type
        HashMap<String,Integer> userhash = Logaction.hashMapForLoggedInUserType();
         
        if(userhash.containsKey("hcp")){
            int valueget = (int) userhash.get("hcp");
            assertEquals(37 , valueget);
        }
    }


    public void testhashMapForSecondaryUserType() throws Exception {
        // test: to get the hashmap for the logged in user type
        HashMap<String,Integer> userhash = Logaction.hashMapForSecondaryUserType();

        if(userhash.containsKey("lt")){
            int valueget = (int) userhash.get("lt");
            assertEquals(0 , valueget);
        }
    }
    
    public void testhashMapForSecondaryUserType2() throws Exception {
        // test: to get the hashmap for the logged in user type
        HashMap<String,Integer> userhash = Logaction.hashMapForSecondaryUserType();
         
        if(userhash.containsKey("patient")){
            int valueget = (int) userhash.get("patient");
            assertEquals(44 , valueget);
        }
    }

    public void testhashMapForAYear() throws Exception{
        HashMap<String,Integer> yearhash = Logaction.hashMapForAYear("2008");

        int valueget = (int) yearhash.get("Mar");
        assertEquals(1 , valueget);
    }

    
    public void testhashMapForTransactionType() throws Exception{
        HashMap<String,Integer> yearhash = Logaction.hashMapForTransactionType();

        assertEquals(8, yearhash.size()); 
    }



    public void testhashMapForTransactionType2() throws Exception{
      /*  HashMap<String,Integer> yearhash = Logaction.hashMapForTransactionType();
       	if(yearhash.containsKey("Failed login")){
	    int valueget = (int) yearhash.get("Failed login");
	    assertEquals(10,valueget);
	}*/
//	HashMap<String,Integer> yearhash = Logaction.hashMapForTransactionType();
	String description = TransactionType.values()[1].getDescription();
        assertEquals("User views homepage", description);;
    }
}

