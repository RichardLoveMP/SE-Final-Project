<%@taglib uri="/WEB-INF/tags.tld" prefix="itrust"%>
<%-- <%@page errorPage="/auth/exceptionHandler.jsp"%> --%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="edu.ncsu.csc.itrust.beans.PatientBean"%>
<%@page import="edu.ncsu.csc.itrust.beans.PreRegisteredPatientBean"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewPreRegisteredPatientsAction"%>

<%@include file="/global.jsp" %>

<%
pageTitle = "iTrust - View All Pre-registered Patients";
%>

<%@include file="/header.jsp" %>

<%
ViewPreRegisteredPatientsAction action = new ViewPreRegisteredPatientsAction(prodDAO, loggedInMID.longValue());
List<PreRegisteredPatientBean> prePatients = action.getPreRegisteredPatients();
/*boolean deactivation = request.getParameter("deactivation") != null
			&& request.getParameter("deactivation").equals("true");

	
	if (deactivation) {

		// deactivate patient action
		%> <span class="iTrustMessage">Patient has been deactivated.</span> <%
	}*/
%>
			<script src="/iTrust/DataTables/media/js/jquery.dataTables.min.js" type="text/javascript"></script>
			<script type="text/javascript">
				jQuery.fn.dataTableExt.oSort['lname-asc']  = function(x,y) {
					var a = x.split(" ");
					var b = y.split(" ");
					return ((a[1] < b[1]) ? -1 : ((a[1] > b[1]) ?  1 : 0));
				};
				
				jQuery.fn.dataTableExt.oSort['lname-desc']  = function(x,y) {
					var a = x.split(" ");
					var b = y.split(" ");
					return ((a[1] < b[1]) ? 1 : ((a[1] > b[1]) ?  -1 : 0));
				};
			</script>
			<script type="text/javascript">	
   				$(document).ready(function() {
       				$("#patientList").dataTable( {
       					"aaColumns": [ [2,'dsc'] ],
       					"aoColumns": [ { "sType": "lname" }, null, null],
       					"bStateSave": true,
       					"sPaginationType": "full_numbers"
       				});
   				});
			</script>
			<style type="text/css" title="currentStyle">
				@import "/iTrust/DataTables/media/css/demo_table.css";		
			</style>

<br />
	<h2>Pre-registered Patients</h2>
<form action="viewReport.jsp" method="post" name="myform">
<table class="display fTable" id="patientList" align="center">
	
	<thead>


	<tr class="">
		<th>Patient</th>

	</tr>
	</thead>
	<tbody>
	<%
		List<PatientBean> patients = new ArrayList<PatientBean>();
		int index = 0;
		for (PreRegisteredPatientBean bean : prePatients) {
			patients.add(bean.getPatientBean());
	%>
	<tr>
		<td >
			<a href="editPrePHR.jsp?patient=<%= StringEscapeUtils.escapeHtml("" + (index)) %>">
			<%= StringEscapeUtils.escapeHtml("" + (bean.getPatientBean().getFullName())) %>	
			</a>
	</tr>
	<%
			index ++;
		}
		session.setAttribute("patients", patients);
	%>
	</tbody>
</table>
</form>
<br />
<br />

<%@include file="/footer.jsp" %>
