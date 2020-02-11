<%@page import="edu.ncsu.csc.itrust.beans.OfficeVisitBean"%>
<%@page import="edu.ncsu.csc.itrust.beans.LabProcedureBean"%>
<%@page import="edu.ncsu.csc.itrust.beans.TransactionBean"%>
<%@page import="edu.ncsu.csc.itrust.dao.mysql.LabProcedureDAO"%>
<%@page import="edu.ncsu.csc.itrust.dao.mysql.PatientDAO"%>
<%@page import="edu.ncsu.csc.itrust.dao.mysql.TransactionDAO"%>
<%@page import="edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewMyRecordsAction"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewMyReportRequestsAction"%>
<%@page import="edu.ncsu.csc.itrust.beans.PatientBean"%>
<%@page import="edu.ncsu.csc.itrust.beans.PersonnelBean"%>
<%@page import="edu.ncsu.csc.itrust.beans.ReportRequestBean"%>
<%@page import="java.util.Hashtable"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.LinkedList"%>

<%@include file="/global.jsp" %>

<%
	pageTitle = "iTrust - Preregistered";
%>

<%@include file="/header.jsp" %>

<%
	if(request.getParameter("rep") != null && request.getParameter("rep").equals("1")){
%>
<span class="iTrustMessage"><%=StringEscapeUtils.escapeHtml("" + ("Adverse Event Successfully Reported"))%></span>
<%
	}
%>


You have successfully pre-registered. An HCP must activate your account in order for you to use the iTrust medical system.
<%@include file="/footer.jsp" %>
