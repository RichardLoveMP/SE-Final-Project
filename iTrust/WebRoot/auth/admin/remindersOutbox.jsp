
<%@page import="edu.ncsu.csc.itrust.enums.Role"%>
<%@page import="edu.ncsu.csc.itrust.enums.TransactionType"%>
<%@page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewMyMessagesAction"%>
<%@page import="edu.ncsu.csc.itrust.beans.MessageBean"%>
<%@page import="java.util.List"%>
<%@ page import="edu.ncsu.csc.itrust.exception.DBException" %>
<%@ page import="java.sql.SQLException" %>


<%@include file="/global.jsp" %>

<%
    pageTitle = "iTrust - Reminders Outbox";
%>

<%@include file="/header.jsp" %>
<div align=center>
    <h2>Reminders Sent</h2>

    <br /><br />
    <%
        ViewMyMessagesAction action = new ViewMyMessagesAction(prodDAO, 9000000009L);
        List<MessageBean> messages = null;
        try {
            messages = action.getAllMySentMessages();
        } catch (DBException | SQLException e) {
            e.printStackTrace();
        }
        //
        session.setAttribute("messages", messages);
    %>

    <%@include file="/auth/admin/mailbox.jsp" %>
</div>
<%@include file="/footer.jsp" %>