<%@page errorPage="/auth/exceptionHandler.jsp" %>

<%@page import="edu.ncsu.csc.itrust.dao.mysql.CauseOfDeathDAO"%>

<%@page import="java.sql.*" %>

<%@page import="java.util.List"%>

<%@include file="/global.jsp" %>

<%
pageTitle = "iTrust - View Cause of Death Trends";
%>

<%@include file="/header.jsp" %>
<%
	String coverage = request.getParameter("SelectCoverage");
	String startYear = request.getParameter("StartingYear");
	String endYear = request.getParameter("EndingYear");

	coverage = coverage == null ? "All" : coverage;
	startYear = startYear == null ? "2019" : startYear;
	endYear = endYear == null ? "2019" : endYear;
%>

<style>
	table#center {
		margin-left:auto;
		margin-right:auto;
	}
</style>

<form>
	<table class="fTable" id="center">
		<tr>
			<th colspan="4">Cause of Death Trends</th>
		</tr>
		<tr class="subHeader">
			<td>Gender</td>
			<td>Starting Year</td>
			<td>Ending Year</td>
			<td>View Result</td>
		</tr>
		<tr>
			<td>
				<label>
					<select name="SelectCoverage">
						<option></option>
						<option value="All" <%if(coverage.equalsIgnoreCase("All")){%>selected<%}%>>All</option>
						<option value="Male" <%if(coverage.equalsIgnoreCase("Male")){%>selected<%}%>>Male</option>
						<option value="Female" <%if(coverage.equalsIgnoreCase("Female")){%>selected<%}%>>Female</option>
					</select>
				</label>
			</td>
			<td>
				<label>
				<input type="number" maxlength=4 name="StartingYear">
				</label>
			</td>
			<td>
				<label>
					<input type="number" maxlength=4 name="EndingYear">
				</label>
			</td>
			<td>
				<input type="submit" value="Submit" name="submit" />
			</td>
		</tr>
	</table>
</form>

<div class="col-sm-12">
	<div class="panel panel-primary panel-notification">
		<div style="padding-bottom: 25px" class="panel-heading"><h3 class="panel-title">Top 2 Cause of Death of Own Patient</h3></div>
		<div class="panel-body">
			<ul>
				<%
					if (CauseOfDeathDAO.inspectYear(startYear, endYear)) {
						Date start = Date.valueOf(startYear + "-01-01");
						Date end = Date.valueOf(endYear + "-01-01");
						String mf = coverage == null || coverage.equals("") ? "All" : coverage;
						long hcpid = loggedInMID;
						CauseOfDeathDAO ctd = new CauseOfDeathDAO(DAOFactory.getProductionInstance());
						List<String> list = ctd.getTwoCommonDeathsForHCPID(hcpid, mf, start, end);
						for (int i = 0; i < list.size(); i++) {
							out.print("<li>" + (i+1) + ". " + list.get(i) + "</li>");
						}
				%>
			</ul>

		</div>

		<div class="panel-heading"><h3 class="panel-title">Top 2 Cause of Death of All Patient</h3></div>
		<div class="panel-body">
			<ul>
				<%
						List<String> list2 = ctd.getTwoMostCommonDeaths(mf, start, end);
						for (int i = 0; i < list2.size(); i++) {
							out.print("<li>" + (i+1) + ". " + list2.get(i) + "</li>");
						}
						loggingAction.logEvent(TransactionType.DEATH_TRENDS_VIEW, loggedInMID, 0, "");
					} else {
						out.print(CauseOfDeathDAO.inspectYearWithMessage(startYear, endYear));
						loggingAction.logEvent(TransactionType.DEATH_TRENDS_VIEW, loggedInMID, 0, "");
					}
				%>
			</ul>
		</div>
	</div>
</div>

<%@include file="/footer.jsp" %>
