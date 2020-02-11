package edu.ncsu.csc.itrust.unit.action;

import edu.ncsu.csc.itrust.action.SendMessageAction;
import edu.ncsu.csc.itrust.action.SendRemindersAction;
import edu.ncsu.csc.itrust.beans.ApptBean;
import edu.ncsu.csc.itrust.beans.Email;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.ApptDAO;
import edu.ncsu.csc.itrust.dao.mysql.FakeEmailDAO;
import edu.ncsu.csc.itrust.dao.mysql.MessageDAO;
import edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import junit.framework.TestCase;

import java.sql.Timestamp;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SendRemindersActionTest extends TestCase {
    // we use DAO factory to obtain appointments, patient,
    protected ApptDAO apptDAO;
    protected PersonnelDAO personnelDAO;
    private TestDataGenerator gen;

    private SendRemindersAction sendRemindersAction;
    private DAOFactory factory;

    private FakeEmailDAO emailDAO;

    private long patientID;
    private long hcpID;

    private ApptBean appt;



    @Override
    protected void setUp() throws Exception {
        // refer to SendMessageActionTest.java
        super.setUp();
        gen = new TestDataGenerator();
        gen.clearAllTables();
        gen.standardData();
        gen.clearMessages();
        gen.clearAppointments();

        this.patientID = 2L;
        this.hcpID = 9000000000L;
        this.factory = TestDAOFactory.getTestInstance();
        this.apptDAO = factory.getApptDAO();
        this.emailDAO = factory.getFakeEmailDAO();
        // System.out.

        this.sendRemindersAction = new SendRemindersAction(factory);

        // first appointment
        this.appt = new ApptBean();
        appt.setDate(new Timestamp(new Date().getTime() + 1000 * 60 * 60 * 24 * 3));
        appt.setApptType("SendRemindersActionTest 1");
        appt.setHcp(hcpID);
        appt.setPatient(patientID);
        apptDAO.scheduleAppt(appt);

        // second appointment
        this.appt = new ApptBean();
        appt.setDate(new Timestamp(new Date().getTime() + 1000 * 60 * 60 * 24 * 2));
        appt.setApptType("SendRemindersActionTest 2");
        appt.setHcp(hcpID);
        appt.setPatient(patientID);
        apptDAO.scheduleAppt(appt);

        // third appointment (this should not be sent)
        this.appt = new ApptBean();
        appt.setDate(new Timestamp(new Date().getTime() + 1000 * 60 * 60 * 24 * 10));   // can't be too big... overflow may occurs
        appt.setApptType("SendRemindersActionTest 3");
        appt.setHcp(hcpID);
        appt.setPatient(patientID);
        apptDAO.scheduleAppt(appt);
    }

    //@Test
    public void testSendReminders() throws Exception {
        int originalMessages = emailDAO.getAllEmails().size();
        //System.out.println(emailDAO.getAllEmails());
        sendRemindersAction.sendReminders(3);
        int finalMessages = emailDAO.getAllEmails().size();
        int messagesSent =  finalMessages - originalMessages;
        assertEquals(2, messagesSent);

    //    gen = new TestDataGenerator();
      //  gen.clearAllTables();
        //gen.standardData();
    }
}
