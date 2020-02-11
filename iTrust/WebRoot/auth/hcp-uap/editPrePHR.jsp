<%@taglib uri="/WEB-INF/tags.tld" prefix="itrust" %>
<%@page errorPage="/auth/exceptionHandler.jsp" %>

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.List"%>
<%@page import="java.text.DateFormat"%>

<%@page import="edu.ncsu.csc.itrust.action.EditPHRAction"%>
<%@page import="edu.ncsu.csc.itrust.action.EditPrePHRAction"%>
<%@page import="edu.ncsu.csc.itrust.dao.DAOFactory"%>
<%@page import="edu.ncsu.csc.itrust.beans.PatientBean"%>
<%@page import="edu.ncsu.csc.itrust.beans.AllergyBean"%>
<%@page import="edu.ncsu.csc.itrust.beans.FamilyMemberBean"%>
<%@page import="edu.ncsu.csc.itrust.beans.HealthRecord"%>
<%@page import="edu.ncsu.csc.itrust.beans.MedicationBean"%>
<%@page import="edu.ncsu.csc.itrust.beans.OfficeVisitBean"%>
<%@page import="edu.ncsu.csc.itrust.beans.ProcedureBean"%>
<%@page import="edu.ncsu.csc.itrust.risk.RiskChecker"%>
<%@page import="edu.ncsu.csc.itrust.beans.PersonnelBean"%>
<%@page import="edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO"%>
<%@page import="edu.ncsu.csc.itrust.dao.mysql.PatientDAO"%>
<%@page import="edu.ncsu.csc.itrust.enums.TransactionType"%>

<%@include file="/global.jsp" %>

<%
pageTitle = "iTrust - Edit Personal Health Record";
%>

<%@include file="/header.jsp" %>

<%
PatientDAO patientDAO = new PatientDAO(prodDAO);
PersonnelDAO personnelDAO = new PersonnelDAO(prodDAO);
PersonnelBean personnelb = personnelDAO.getPersonnel(loggedInMID.longValue());
DateFormat df = DateFormat.getDateInstance();

String switchString = "";
if (request.getParameter("switch") != null) {
	switchString = request.getParameter("switch");
}

String relativeString = "";
if (request.getParameter("relative") != null) {
	relativeString = request.getParameter("relative");
}

String patientString = "";
if (request.getParameter("patient") != null) {
	patientString = request.getParameter("patient");
}

String pidString;
long pid = 0;

if (switchString.equals("true")) pidString = "";
else if (!relativeString.equals("")) {

	int relativeIndex = Integer.parseInt(relativeString);
	List<PatientBean> relatives = (List<PatientBean>) session.getAttribute("relatives");
	pid = relatives.get(relativeIndex).getMID();
	pidString = "" + pid;
	session.removeAttribute("relatives");
	session.setAttribute("pid", pidString);
}
else if (!patientString.equals("")) {

	int patientIndex = Integer.parseInt(patientString);
	List<PatientBean> patients = (List<PatientBean>) session.getAttribute("patients");
	pid = patients.get(patientIndex).getMID();
	pidString = "" + pid;
	session.removeAttribute("patients");
	session.setAttribute("pid", pidString);
}
else {
	if (session.getAttribute("pid") == null) {
		pid = 0;
		pidString = "";
	} else {
		pid = (long) Long.parseLong((String) session.getAttribute("pid"));
		pidString = ""+pid;
	}
}

if (pidString == null || 1 > pidString.length()) {
	response.sendRedirect("../getPatientID.jsp?forward=hcp-uap/editPHR.jsp");
	
   	return;
}
loggingAction.logEvent(TransactionType.PATIENT_HEALTH_INFORMATION_VIEW, loggedInMID.longValue(), pid, "");

//else {
//	session.removeAttribute("pid");
//}


EditPHRAction action = new EditPHRAction(prodDAO,loggedInMID.longValue(), pidString);
pid = action.getPid();
String confirm = "";
if(request.getParameter("addA") != null)
{
	try{
		confirm = action.updateAllergies(pid,request.getParameter("description"));
		loggingAction.logEvent(TransactionType.PATIENT_HEALTH_INFORMATION_EDIT, loggedInMID.longValue(), pid, "");
	} catch(Exception e)
	{
		confirm = e.getMessage();
	}
}

PatientBean patient = action.getPatient();
List<HealthRecord> records = action.getAllHealthRecords();
HealthRecord mostRecent = records.size() > 0 ? records.get(0) : null;
List<OfficeVisitBean> officeVisits = action.getAllOfficeVisits();
List<FamilyMemberBean> family = action.getFamily(); 

boolean activation = request.getParameter("activation") != null
			&& request.getParameter("activation").equals("true");
boolean deactivation = request.getParameter("deactivation") != null
			&& request.getParameter("deactivation").equals("true");
	
	
	if (activation) {
		EditPrePHRAction prePHRAction = new EditPrePHRAction(prodDAO,loggedInMID.longValue(), pidString);
		// activate patient action
		prePHRAction.activatePrePatient();
		session.removeAttribute("patients");
		session.removeAttribute("pid");
		%> <span class="iTrustMessage">Patient has been activated.</span> <%
	}
	if (deactivation) {
		EditPrePHRAction prePHRAction = new EditPrePHRAction(prodDAO,loggedInMID.longValue(), pidString);
		prePHRAction.deactivatePrePatient();
		session.removeAttribute("patients");
		session.removeAttribute("pid");
		response.sendRedirect("viewPreRegisteredPatients.jsp");
		%> <span class="iTrustMessage">Patient has been deactivated.</span> <%
	}
%>


<%@page import="edu.ncsu.csc.itrust.exception.NoHealthRecordsException"%><script type="text/javascript">
function showRisks(){
	document.getElementById("risks").style.display="inline";
	document.getElementById("riskButton").style.display="none";
}
</script>

<% if (!"".equals(confirm)) {%>
<span class="iTrustError"><%= StringEscapeUtils.escapeHtml("" + (confirm)) %></span><br />
<% } %>

<br />
<div align=center>
	<div style="margin-right: 10px;">
		<table class="fTable" align="center">
			<tr>
				<th colspan="2">Patient Information</th>
			</tr>
			<tr>
				<td class="subHeaderVertical">Name:</td>
				<td ><%= StringEscapeUtils.escapeHtml("" + (patient.getFullName())) %></td>
			</tr>
			<tr>
				<td  class="subHeaderVertical">Address:</td>
				<td > <%= StringEscapeUtils.escapeHtml("" + (patient.getStreetAddress1())) %><br />
				     <%="".equals(patient.getStreetAddress2()) ? "" : patient.getStreetAddress2() + "<br />"%>
				     <%= StringEscapeUtils.escapeHtml("" + (patient.getStreetAddress3())) %><br />									  
				</td>
			</tr>
			<tr>
				<td class="subHeaderVertical">Phone:</td>
				<td ><%= StringEscapeUtils.escapeHtml("" + (patient.getPhone())) %></td>
			</tr>
			<tr>
				<td class="subHeaderVertical" >Email:</td>
				<td ><%= StringEscapeUtils.escapeHtml("" + (patient.getEmail())) %></td>
			</tr>
			<tr>
				<th colspan="2">Insurance Information</th>
			</tr>
			<tr>
				<td class="subHeaderVertical" >Provider Name:</td>
				<td ><%= StringEscapeUtils.escapeHtml("" + (patient.getIcName())) %></td>
			</tr>
			<tr>
				<td  class="subHeaderVertical">Address:</td>
				<td > <%= StringEscapeUtils.escapeHtml("" + (patient.getIcAddress1())) %><br />
					<%="".equals(patient.getIcAddress2()) ? "" : patient.getIcAddress2() + "<br />"%>
					<%= StringEscapeUtils.escapeHtml("" + (patient.getIcAddress3())) %><br />							
				</td>
			</tr>
			<tr>
				<td class="subHeaderVertical">Phone:</td>
				<td ><%= StringEscapeUtils.escapeHtml("" + (patient.getIcPhone())) %></td>
			</tr>
		</table>
		<br />
		<a href="editPatient.jsp" style="text-decoration: none;">
			<input type=button name="editbutton" value="Edit" onClick="location='editPatient.jsp';">
		</a>
	</div>
	<div style="margin-right: 10px;">
		<table class="fTable" align="center">
			<tr>
				<th colspan="2">Basic Health Records</th>
			</tr>
			<% if (null == mostRecent) { %>
			<tr><td colspan=2>No basic health records are on file for this patient</td></tr>
			<% } else {%>
			<tr>
				<td class="subHeaderVertical">Height:</td>
				<td ><%= StringEscapeUtils.escapeHtml("" + (mostRecent.getHeight())) %>in.</td>
			</tr>
			<tr>
				<td class="subHeaderVertical">Weight:</td>
				<td ><%= StringEscapeUtils.escapeHtml("" + (mostRecent.getWeight())) %>lbs.</td>
			</tr>
			<tr>
				<td class="subHeaderVertical">Smoker?:</td>
				<td ><%= StringEscapeUtils.escapeHtml("" + (mostRecent.getSmokingStatus()) + " - " + (mostRecent.getSmokingStatusDesc())) %></td>
			</tr>
			
			<% } //closing for "there is a most recent record for this patient" 
			%>
		</table>
		<br />
		<form id="activationForm" action="editPrePHR.jsp" method="post"><input type="hidden"
			 name="activation" value="true"> <br />
			<input type="submit" name="activationbutton" value="Activate Patient">
		</form>
		<form id="deactivationForm" action="editPrePHR.jsp" method="post"><input type="hidden"
			 name="deactivation" value="true"> <br />
			<input type="submit" name="deactivationbutton" value="Deactivate Patient">
		</form>
	</div>

<br /><br /><br />
<%@include file="/footer.jsp" %>
