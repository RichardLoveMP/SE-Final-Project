<%@page import="edu.ncsu.csc.itrust.action.PreRegistrationAction"%>
<%@page import="edu.ncsu.csc.itrust.exception.FormValidationException"%>
<%@page import="edu.ncsu.csc.itrust.exception.ITrustException"%>
<%@page import="edu.ncsu.csc.itrust.beans.PatientBean"%>
<%@page import="edu.ncsu.csc.itrust.beans.PreRegisteredPatientBean"%>

<%@include file="/global.jsp"%>

<%
	pageTitle = "iTrust - Pre-Register Patient";
%>

<%@include file="/header.jsp"%>
<h1>Pre Registration</h1>
<form action="/iTrust/util/preRegistration.jsp" method="post">
<table>
	<%
		PreRegistrationAction action = new PreRegistrationAction(prodDAO);
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String confirmPassword = request.getParameter("confirmPassword");
		String height = request.getParameter("height");
		String weight = request.getParameter("weight");
		String address = request.getParameter("address");
		String phone = request.getParameter("phone");
		String insureName = request.getParameter("insureName");
		String insureAddr = request.getParameter("insureAddr");
		String insurePhone = request.getParameter("insurePhone");
		String smokerstr = request.getParameter("smoker");
		
		PreRegisteredPatientBean prePatient = new PreRegisteredPatientBean();
		PatientBean patient = new PatientBean();
		patient.setFirstName(firstName);
		patient.setLastName(lastName);
		patient.setEmail(email); 
		patient.setPhone(phone);
		patient.setStreetAddress1(address);
		patient.setIsRegistered(0);
		patient.setIcName(insureName);
		patient.setIcPhone(insurePhone);
		patient.setIcAddress1(insureAddr);
		prePatient.setPatientBean(patient);
		
		if(firstName != null && lastName != null && email != null && password != null && confirmPassword != null){
			String confirm = "";
			try {
				confirm = action.preRegister(prePatient.getPatientBean(), password, confirmPassword, request.getRemoteAddr());
				String midstr = confirm.substring(23);
				action.addPatientHealthData(midstr, height, weight, smokerstr);
			} catch (FormValidationException e) {
				e.printHTML(pageContext.getOut());
			}
			if(confirm.contains("Patient Pre Registered")){
				String midstr = confirm.substring(23);
				long mid = Long.parseLong(midstr);
				
				EventLoggingAction event = new EventLoggingAction(prodDAO);
				event.logEvent(TransactionType.CREATE_PREREGISTERED, mid, 0L, "User preregistered as a patient");
			%>
				<p name="confirmation">You have pre-registered successfully. Your MID is <%=mid%>.</p>
			<%
			}
			else{
		%>	<tr>
				<td>
					<a name="tryAgain" href="preRegistration.jsp">
						<h3>Please try again</h3>
					</a>
				</td>
			</tr>
		<%	
			}
		
		}
		else{
%>
	<tr>
		<td>First Name*</td>
		<td><input type="text" name="firstName"></td>
	</tr>
	<tr>
		<td>Last Name*</td>
		<td><input type="text" name="lastName"></td>
	</tr>
	<tr>
		<td>Email*</td>
		<td><input type="text" name="email"></td>
	</tr>
	<tr>
		<td>Password*</td>
		<td><input type="password" name="password"></td>
	</tr>
	<tr>
		<td>Confirm*</td>
		<td><input type="password" name="confirmPassword"></td>
	</tr>

	<tr>
		<td>Address</td>
		<td><input type="text" name="address"></td>
	</tr>

	<tr>
		<td>Phone</td>
		<td><input type="text" name="phone"></td>
	</tr>

	<tr>
		<td>Provider Name</td>
		<td><input type="text" name="insureName"></td>
	</tr>

	<tr>
		<td>Provider Address</td>
		<td><input type="text" name="insureAddr"></td>
	</tr>

	<tr>
		<td>Provider Phone</td>
		<td><input type="text" name="insurePhone"></td>
	</tr>

	<tr>
		<td>Height</td>
		<td><input type="text" name="height"></td>
	</tr>

	<tr>
		<td>Weight</td>
		<td><input type="text" name="weight"></td>
	</tr>

	<tr>
		<td>Smoker</td>
		<td><input type="radio" name="command" value="0" />NO</td>
		<td><input type="radio" name="command" value="1" />YES</td>
	</tr>


	<tr>
		<td colspan=2 align=center><input type="submit" value="Submit"></td>
	</tr>



	<%
		}
%>
</table>
</form>
<%@include file="/footer.jsp" %>