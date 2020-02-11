package edu.ncsu.csc.itrust.action;

import edu.ncsu.csc.itrust.beans.TransactionBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.AuthDAO;
import edu.ncsu.csc.itrust.dao.mysql.TransactionDAO;
import edu.ncsu.csc.itrust.enums.Role;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.exception.ITrustException;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;


public class ViewTransactionLogsAction {
    // this class is used in UC39.
    // use dao
    private TransactionDAO transactionDAO;
    private AuthDAO authDAO;
    public ViewTransactionLogsAction(DAOFactory daoFactory){
        transactionDAO = daoFactory.getTransactionDAO();
        authDAO = daoFactory.getAuthDAO();
    }

    //    private TransactionType typeOfLog;
    //    private String roleForLoggedInUser;
    //    private String roleForSecondaryUser;
    //    private Timestamp timeLogged;
    //    private String extraInfo;
    //    private String description;
    public List<TransactionBean> getTransactionLogsAfterFiltering(String roleForLoggedInUser, String roleForSecondaryUser,
                                                                  String dateBeginning, String dateEnding,
                                                                  String transactionType) throws ITrustException, ParseException {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy");
        String loggedInRoleFilter = "%";
        String secondaryRoleFilter = "%";
        Date startDateFilter = dateFormatter.parse("01/01/1753");
        Date endDateFilter = new Date();
        String transactionCodeFilter = "%";

        if (!roleForLoggedInUser.equals("all")) {
            loggedInRoleFilter = roleForLoggedInUser;
        }

        if (!roleForSecondaryUser.equals("all")) {
            secondaryRoleFilter = roleForSecondaryUser;
        }

        if (dateBeginning != null && !dateBeginning.equals("")) {
            startDateFilter = dateFormatter.parse(dateBeginning);
        }

        if (dateEnding != null && !dateEnding.equals("")) {
            endDateFilter = dateFormatter.parse(dateEnding);
        }

        if (!transactionType.equals("all")) {
            transactionCodeFilter = transactionType;
        }
        List<TransactionBean> transactionBeanList;

        transactionBeanList = transactionDAO.getAllTransactions();//transactionDAO.getAllTransactions();
  //      List<Integer> transactionBeanToRemove = new List<Integer>;
        for (int i=0; i<transactionBeanList.size(); i++) {

            TransactionBean transactionBean = transactionBeanList.get(i);
            boolean deleteOrNot = this.decideShouldBePrinted(roleForLoggedInUser, roleForSecondaryUser,
                                                            dateBeginning, dateEnding,
                                                            transactionType, transactionBean);
            // set log-in-user-role and secondary-role
            transactionBean.setRoleForLoggedInUser(this.authDAO.getUserRole(transactionBean.getLoggedInMID()).getUserRolesString());
            try{
                transactionBean.setRoleForSecondaryUser(this.authDAO.getUserRole(transactionBean.getSecondaryMID()).getUserRolesString());
            }catch (Exception ignored){

            }

            // determine if we are to remove this object
            if (deleteOrNot == true){
                transactionBeanList.remove(i);
                i--;
            }
        }

        return transactionBeanList;
    }

    /**
     * helper function. Decide if the the input transaction log should be printed, and set the
     * fields of roleForSecondaryUser and roleForSecondaryUser for that TransactionBean.
     * */
    private boolean decideShouldBePrinted(String roleForLoggedInUser, String roleForSecondaryUser,
                                         String dateBeginning, String dateEnding,
                                         String transactionType, TransactionBean transactionBean)
            throws ITrustException, ParseException {

        // Time constraints test
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Timestamp logTime = transactionBean.getTimeLogged();
        Timestamp beginTime;
        Timestamp endTime;
        if (dateBeginning == null || dateBeginning.equals("")){
            Date date = simpleDateFormat.parse("11/11/1111");
            beginTime = new Timestamp(date.getTime());
        } else {
            Date date = simpleDateFormat.parse(dateBeginning);
            beginTime = new Timestamp(date.getTime());
        }
        if (dateEnding == null || dateEnding.equals("")){
            Date date = simpleDateFormat.parse("11/11/2222");
            endTime = new Timestamp(date.getTime());
        } else {
            Date date = simpleDateFormat.parse(dateEnding);
            endTime = new Timestamp(date.getTime());
        }
        if (!(logTime.after(beginTime) == true)){
            return true;
        }
        if (!(logTime.before(endTime) == true)){
            return true;
        }

        // logged-in-role test
        if (!(roleForLoggedInUser.equals("all"))){
            // if not "all-ok", then we have to test if the role matches
            long mid = transactionBean.getLoggedInMID();
            String temp = authDAO.getUserRole(mid).getUserRolesString();
            if (!(roleForLoggedInUser.equals(temp))){
                return true;
            }

        }

        // secondary-role test
        if (!(roleForSecondaryUser.equals("all"))){
            // if not "all-ok", then we have to test if the role matches
            // here we have this try-catch because we want to handle that cases
            // where the data in logs are corrupted.
            try {
                long mid = transactionBean.getSecondaryMID();
                String temp = authDAO.getUserRole(mid).getUserRolesString();
                if (!(roleForSecondaryUser.equals(temp))) {
                    return true;
                }
            } catch (Exception e){
                return true;
            }

        }

        // transaction type test
        if (!(transactionType.equals("all"))){
            TransactionType temp = transactionBean.getTransactionType();
           // TransactionType temp2 = new TransactionType(transactionType)
            if (!(temp.getCode() == Integer.parseInt(transactionType))){
                return true;
            }

        }


        return false;

    }
    
    
   /**
     * helper function for UC39 S3 Chart 1.
     * returns the number of transaction logs of a certain type of logged in user.
     * */
    private int totalNumberForCertainLoggedInUser(String roleForLoggedInUser) throws ITrustException, ParseException {

        List<TransactionBean> transactionBeanList;
        transactionBeanList = getTransactionLogsAfterFiltering(roleForLoggedInUser, "all",
                null, null,
                "all");
        return transactionBeanList.size();
    }

    /**
     * helper function for UC39 S3 Chart 2.
     * returns the number of transaction logs of a certain type of secondary user.
     * */
    private int totalNumberForCertainSecondaryUser(String roleForSecondaryUser) throws ITrustException, ParseException {

        List<TransactionBean> transactionBeanList;
        transactionBeanList = getTransactionLogsAfterFiltering("all", roleForSecondaryUser,
                null, null,
                "all");
        return transactionBeanList.size();
    }


    /**
     * helper function for UC39 S3 Chart 3.
     * returns the number of transaction logs in a certain time period.
     * */
    private int totalNumberForCertainTimePeriod(String dateBeginning, String dateEnding) throws ITrustException, ParseException {

        List<TransactionBean> transactionBeanList;
        transactionBeanList = getTransactionLogsAfterFiltering("all", "all",
                dateBeginning, dateEnding,
                "all");
        return transactionBeanList.size();
    }

    /**
     * helper function for UC39 S3 Chart 4.
     * returns the number of transaction logs of a certain transaction type.
     * */
    private int totalNumberForCertainTransactionType(String transactionType) throws ITrustException, ParseException {
        List<TransactionBean> transactionBeanList;
        transactionBeanList = getTransactionLogsAfterFiltering("all", "all",
                null, null,
                transactionType);
        return transactionBeanList.size();
    }

    //////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////


    /**
     * UC39 S3 Chart 1 hash map function.
     * */
    public HashMap<String, Integer> hashMapForLoggedInUserType() throws ITrustException, ParseException {
        HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
        for (int i = 0; i < Role.values().length; i++) {
            String roleUser = Role.values()[i].getUserRolesString();
            int temp = this.totalNumberForCertainLoggedInUser(roleUser);
            hashMap.put(roleUser, temp);
        }
        return hashMap;
    }


    /**
     * UC39 S3 Chart 2 hash map function.
     * */
    public HashMap<String, Integer> hashMapForSecondaryUserType() throws ITrustException, ParseException {
        HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
        for (int i = 0; i < Role.values().length; i++) {
            String roleUser = Role.values()[i].getUserRolesString();
            int temp = this.totalNumberForCertainSecondaryUser(roleUser);
            hashMap.put(roleUser, temp);
        }
        return hashMap;
    }



    /**
     * UC39 S3 Chart 3 hash map function.
     * */
    public HashMap<String, Integer> hashMapForAYear(String year) throws ITrustException, ParseException {
        HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
        // TODO:
        //  the number of transaction logs in 12 months in a certain year
        Integer temp;

        temp = this.totalNumberForCertainTimePeriod("01/01/"+year, "01/31/"+year);
        hashMap.put("Jan", temp);

        // Feb is a little special.... have to decide if it has 28 days or 29 days
        if (temp % 4 == 0 && temp % 400 != 0){
            temp = this.totalNumberForCertainTimePeriod("02/01/"+year, "02/29/"+year);
        } else {
            temp = this.totalNumberForCertainTimePeriod("02/01/"+year, "02/28/"+year);
        }
        hashMap.put("Feb", temp);

        temp = this.totalNumberForCertainTimePeriod("03/01/"+year, "03/31/"+year);
        hashMap.put("Mar", temp);

        temp = this.totalNumberForCertainTimePeriod("04/01/"+year, "04/30/"+year);
        hashMap.put("Apr", temp);

        temp = this.totalNumberForCertainTimePeriod("05/01/"+year, "05/31/"+year);
        hashMap.put("May", temp);

        temp = this.totalNumberForCertainTimePeriod("06/01/"+year, "06/30/"+year);
        hashMap.put("Jun", temp);

        temp = this.totalNumberForCertainTimePeriod("07/01/"+year, "07/31/"+year);
        hashMap.put("Jul", temp);

        temp = this.totalNumberForCertainTimePeriod("08/01/"+year, "08/31/"+year);
        hashMap.put("Aug", temp);

        temp = this.totalNumberForCertainTimePeriod("09/01/"+year, "09/30/"+year);
        hashMap.put("Sep", temp);

        temp = this.totalNumberForCertainTimePeriod("10/01/"+year, "10/31/"+year);
        hashMap.put("Oct", temp);

        temp = this.totalNumberForCertainTimePeriod("11/01/"+year, "11/30/"+year);
        hashMap.put("Nov", temp);

        temp = this.totalNumberForCertainTimePeriod("12/01/"+year, "12/31/"+year);
        hashMap.put("Dec", temp);

        return hashMap;

    }


    /**
     * UC39 S3 Chart 4 hash map function.
     * */
    public HashMap<String, Integer> hashMapForTransactionType() throws ITrustException, ParseException {
        HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
        for (int i=0; i<TransactionType.values().length; i++) {
            String transactionType = Integer.toString(TransactionType.values()[i].getCode());
            int temp = this.totalNumberForCertainTransactionType(transactionType);
            if(temp == 0){
                continue;
            }
            String description = TransactionType.values()[i].getDescription();
            hashMap.put(description, temp);
        }
        return hashMap;
    }

}
