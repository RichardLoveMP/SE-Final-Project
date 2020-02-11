<%@ page import="edu.ncsu.csc.itrust.action.SendRemindersAction" %>
<%@ page import="java.text.ParseException" %><%--
  Created by IntelliJ IDEA.
  User: ytzouc
  Date: 2019/10/30
  Time: 00:25
  To change this template use File | Settings | File Templates.
--%>


<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@include file="/global.jsp" %>
    <% pageTitle = "iTrust - Send Reminders"; %>
<%@include file="/header.jsp" %>


<div align=center>
    <form id="mainForm" method="post" action="sendReminders.jsp">
        System Reminder: send message for appointments in N days <input type="text" name="days" />
        <input type="submit" name="sendReminders" value="Send Reminders"/>
    </form>
</div>

<%
    String N = request.getParameter("days");

    if (N != null){
        int n;
        try {
            n = Integer.parseInt(N);
            SendRemindersAction sendRemindersAction = new SendRemindersAction(prodDAO);
            sendRemindersAction.sendReminders(n);
            %> Successfully send the reminders. <%
        } catch (ParseException e){
            throw new ParseException("You must types a number!", 0);
        } catch (Exception e){
        %>
        <div align=center>
            <span class="iTrustError"><%=StringEscapeUtils.escapeHtml(e.getMessage()) %></span>
        </div>
        <%
        }
    }
%>



<%@include file="/footer.jsp"%>
