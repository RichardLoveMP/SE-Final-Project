<%@include file="/footer.jsp" %>
<%@page import="edu.ncsu.csc.itrust.enums.TransactionType"%>
<%@page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@ page import="edu.ncsu.csc.itrust.enums.Role" %>
<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@include file="/global.jsp" %>
<% pageTitle = "iTrust - Send Reminders"; %>
<%@include file="/header.jsp" %>

<%@page errorPage="/auth/exceptionHandler.jsp"%>
<div>
    <h1>
        Transaction Logs
    </h1>

    <form method="get" action="/iTrust/auth/admin/viewTransactionLogsSheet.jsp">
        <div class="form-group">
            <label for="roleForLoggedInUser">Select role for the logged in user:</label>
            <select name="roleForLoggedInUser" id="roleForLoggedInUser">
                <option value="all" id="defaultLoggedInUser"> All </option>
                <%
                    for (int i=0; i < Role.values().length; i++){
                        String roleUser = Role.values()[i].getUserRolesString();

                %>
                <option id="specificRole" value="<%= roleUser %>"><%= roleUser %></option>
                <%
                    }
                %>
            </select>
        </div>

        <div class="form-group">
            <label for="roleForSecondaryUser">Select role for the secondary user:</label>
            <select  name="roleForSecondaryUser" id="roleForSecondaryUser">
                <option value="all" id="defaultSecondaryUser"> All </option>
                <%
                    for (int i=0; i < Role.values().length; i++){
                        String roleUser = Role.values()[i].getUserRolesString();

                %>
                <option id="specificRole" value="<%= roleUser %>"><%= roleUser %></option>
                <%
                    }
                %>
            </select>
        </div>
        <% /* for the usage of the func displayDatePicker(), refer to editOfficeVisit.jsp*/ %>
        <div class="form-group">
            <label for="fromDate">From: (blank for no restriction)</label>
            <input id="fromDate" name="dateBeginning" type="text" >
            <input type="button" value="Select Date" onclick="displayDatePicker('dateBeginning');" />
        </div>

        <div class="form-group">
            <label for="toDate">To: (blank for no restriction)</label>
            <input id="toDate" name="dateEnding" type="text">
            <input type="button" value="Select Date" onclick="displayDatePicker('dateEnding');" />
        </div>

        <div class="form-group">
            <label for="transactionType">Transaction type</label>
            <select id="transactionType" name="transactionLogType">
                <option value="all" selected>All transaction types</option>
                <%
                    for (TransactionType transactionType : TransactionType.values()) {
                        String description = StringEscapeUtils.escapeHtml(transactionType.getDescription());
                %>
                <option value="<%= transactionType.getCode() %>"><%= description %></option>
                <%
                    }
                %>
            </select>
        </div>

        <input type="submit" value="View" name="view">


    </form>
</div>
