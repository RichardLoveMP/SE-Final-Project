package edu.ncsu.csc.itrust.action;

import edu.ncsu.csc.itrust.beans.ApptBean;
import edu.ncsu.csc.itrust.beans.MessageBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.ApptDAO;
import edu.ncsu.csc.itrust.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO;
import edu.ncsu.csc.itrust.enums.TransactionType;

import edu.ncsu.csc.itrust.action.EventLoggingAction;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SendRemindersAction {
    // we use DAO factory to obtain appointments, patient,
    protected ApptDAO apptDAO;
    protected PersonnelDAO personnelDAO;
    private DateFormat dateFormat;

    private EventLoggingAction action;

    private SendMessageAction sendMessageAction;

    /**
     * Default constructor.
     * */
    public SendRemindersAction(DAOFactory daoFactory) {
        this.apptDAO = daoFactory.getApptDAO();
        this.personnelDAO = daoFactory.getPersonnelDAO();
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        this.sendMessageAction = new SendMessageAction(daoFactory, 9000000009L);

        action = new EventLoggingAction(daoFactory);
    }

    /**
     * Send reminder to patients.
     * The message subject shall be "Reminder: upcoming appointment in N day(s)" where N is the
     * number of days between the upcoming appointment date and the current date.
     * The text of the message shall be "You have an appointment on <TIME>, <DATE> with Dr. <DOCTOR>"
     * where <TIME> is the appointment start time, <DATE> is the appointment date, and <DOCTOR> is
     * the name of the LHCP in the appointment.  The event is logged.
     * @param N the number of days between the upcoming appointment date and the current date.
     * */
    public void sendReminders(int N) throws Exception {


        Date date;
        //String today = dateFormat.format(date);
        MessageBean messageBean = new MessageBean();
        //

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, N);
        date = calendar.getTime();
        String endDate = dateFormat.format(date);

        try{
            List<ApptBean> apptBeanList = apptDAO.getApptsInCertainDays(endDate);
            for (ApptBean apptBean : apptBeanList) {
                Date currentTime = new Date();

                long NdaysInTitle = apptBean.getDate().getTime() - currentTime.getTime();
                // 1000 * 60 * 60 *24: how many ms are there in a day
                NdaysInTitle = 1 + NdaysInTitle / (1000 * 60 * 60 * 24);

                // 9000000009L: admin
                messageBean.setFrom(9000000009L);
                messageBean.setRead(0);
                messageBean.setTo(apptBean.getPatient());
                messageBean.setSubject("Reminder: upcoming appointment in " + NdaysInTitle + " days");
                messageBean.setBody("You have an appointment on " + apptBean.getDate() + " with Dr. " +
                        personnelDAO.getPersonnel(apptBean.getHcp()).getFullName());

                sendMessageAction.sendMessage(messageBean);

             //   action.logEvent(TransactionType.PATIENT_REMINDERS_VIEW, 9000000009L, 0, "");

            }
        } catch (Exception exception){
            exception.printStackTrace();
            throw exception;
        }
    }

}
