<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>

<%@page import="edu.ncsu.csc.itrust.beans.MessageBean"%>
<%@page import="edu.ncsu.csc.itrust.dao.DAOFactory"%>
<%@page import="edu.ncsu.csc.itrust.action.EditPersonnelAction"%>
<%@page import="edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO"%>
<%@page import="edu.ncsu.csc.itrust.dao.mysql.PatientDAO"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewMyMessagesAction"%>
<%@page import="edu.ncsu.csc.itrust.action.EditPatientAction"%>
<%@page import="edu.ncsu.csc.itrust.action.EditPersonnelAction"%>
<%@ page import="java.util.Arrays" %>
<%@ page import="edu.ncsu.csc.itrust.exception.DBException" %>
<%@ page import="java.sql.SQLException" %>

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
       				$("#mailbox").dataTable( {
       					"aaColumns": [ [2,'dsc'] ],
       					"aoColumns": [ { "sType": "lname" }, null, null, {"bSortable": false} ],
       					"sPaginationType": "full_numbers"
       				});
   				});
			</script>
			<style type="text/css" title="currentStyle">
				@import "/iTrust/DataTables/media/css/demo_table.css";		
			</style>

<%
	String sortBy = request.getParameter("sortBy");
	String orderOf = request.getParameter("orderOf");

	sortBy = sortBy == null ? "" : sortBy;
	orderOf = orderOf == null ? "" : orderOf;
%>

<form id="sort">
	<div style="text-align: center">
			<span>
				<label for="sortBy"> Sort By
					<select id="sortBy" name="sortBy">
						<option></option>
						<option value="Sender/Recipient" <%if (sortBy.equalsIgnoreCase("Sender/Recipient")) {%>selected<%}%>>Sender/Recipient</option>
						<option value="Timestamp" <%if (sortBy.equalsIgnoreCase("Timestamp")) {%>selected<%}%>>Timestamp</option>
					</select>
				</label>
			</span>
			<span>
				<label for="orderOf"> by order of
					<select id="orderOf" name="orderOf">
						<option></option>
						<option value="Ascending" <%if (orderOf.equalsIgnoreCase("Ascending")) {%>selected<%}%>>Ascending</option>
						<option value="Descending" <%if (orderOf.equalsIgnoreCase("Descending")) {%>selected<%}%>>Descending</option>
					</select>
				</label>
			</span>
			<span>
				<input type="submit" value="Sort" name="submit" />
			</span>
	</div>
</form>

<br>
<hr>
<br>

<%

boolean outbox=(Boolean)session.getAttribute("outbox");
boolean isHCP=(Boolean)session.getAttribute("isHCP");

String pageName="messageInbox.jsp";
if(outbox){
	pageName="messageOutbox.jsp";
}
	
PersonnelDAO personnelDAO = new PersonnelDAO(prodDAO);
PatientDAO patientDAO = new PatientDAO(prodDAO);

DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

ViewMyMessagesAction action = new ViewMyMessagesAction(prodDAO, loggedInMID.longValue());

%>

<%
String filter = ",,,,,";
String filterSender, filterSubject, filterHasTheWords, filterHasNotTheWords, filterTimeStart, filterTimeEnd = "";
if (!outbox) {
	boolean viewUserData = (request.getParameter("Test Search") != null);
	boolean writeDatabase = (request.getParameter("Save") != null);

	// out.println("The viewUserData is " + viewUserData + "  |||   " + "\n");
	// out.println("The writeDatabase is " + writeDatabase + "  |||   " + "\n");

	String filterSenderFromUser = request.getParameter("filterSender");
	String filterSubjectFromUser = request.getParameter("filterSubject");
	String filterHasTheWordsFromUser = request.getParameter("filterHasTheWords");
	String filterHasNotTheWordsFromUser = request.getParameter("filterHasNotTheWords");
	String filterTimeStartFromUser = request.getParameter("filterTimeStart");
	String filterTimeEndFromUser = request.getParameter("filterTimeEnd");

	filterSenderFromUser = filterSenderFromUser == null? "" : filterSenderFromUser;
	filterSubjectFromUser = filterSubjectFromUser == null? "" : filterSubjectFromUser;
	filterHasTheWordsFromUser = filterHasTheWordsFromUser == null? "" : filterHasTheWordsFromUser;
	filterHasNotTheWordsFromUser = filterHasNotTheWordsFromUser == null? "" : filterHasNotTheWordsFromUser;
	filterTimeStartFromUser = filterTimeStartFromUser == null? "" : filterTimeStartFromUser;
	filterTimeEndFromUser = filterTimeEndFromUser == null? "" : filterTimeEndFromUser;

	String filterFromUser = filterSenderFromUser + "," + filterSubjectFromUser + "," + filterHasTheWordsFromUser + ","
			+ filterHasNotTheWordsFromUser + "," + filterTimeStartFromUser + "," + filterTimeEndFromUser;

	if (writeDatabase) {
		action.setUserMessageFilter(filterFromUser, loggedInMID.longValue());
		// out.println("Data has been saved to database! The filter is: " + filterFromUser + "  |||   " + "\n");
	}

	String[] filterList = null;

	try {
		filterList = action.getUserMessageFilterAsList(loggedInMID.longValue());
	} catch (DBException e) {
		filterList = new String[6];
		for (String i : filterList) {
			i = "";
		}
	}

	String filterSenderFromDatabase = filterList[0];
	String filterSubjectFromDatabase = filterList[1];
	String filterHasTheWordsFromDatabase = filterList[2];
	String filterHasNotTheWordsFromDatabase = filterList[3];
	String filterTimeStartFromDatabase = filterList[4];
	String filterTimeEndFromDatabase = filterList[5];

	String filterFromDatabase = filterSenderFromDatabase + "," + filterSubjectFromDatabase  + "," +
			filterHasTheWordsFromDatabase + "," + filterHasNotTheWordsFromDatabase + "," + filterTimeStartFromDatabase +
			"," + filterTimeEndFromDatabase;

	// out.println("The array from the database is" + Arrays.toString(filterList)  + "  |||   " + "\n");
	// out.println("The filter string directly from database is" + action.getUserMessageFilter(loggedInMID.longValue()) + "  |||   " + "\n");
	// out.println("Data has been retrieved from database! The filter from database is " + filterFromDatabase + "  |||   " + "\n");

	if (viewUserData) {
		filter = filterFromUser;
		filterSender = filterSenderFromUser;
		filterSubject = filterSubjectFromUser;
		filterHasTheWords = filterHasTheWordsFromUser;
		filterHasNotTheWords = filterHasNotTheWordsFromUser;
		filterTimeStart = filterTimeStartFromUser;
		filterTimeEnd = filterTimeEndFromUser;
	} else {
		filter = filterFromDatabase;
		filterSender = filterSenderFromDatabase;
		filterSubject = filterSubjectFromDatabase;
		filterHasTheWords = filterHasTheWordsFromDatabase;
		filterHasNotTheWords = filterHasNotTheWordsFromDatabase;
		filterTimeStart = filterTimeStartFromDatabase;
		filterTimeEnd = filterTimeEndFromDatabase;
	}
%>

<form id="filter" style="text-align: center; align-items: center">
	Filter by sender: <input name="filterSender" type="text" value="<%= filterSender %>"> or subject: <input name="filterSubject" type="text" value="<%= filterSubject %>">
	<br>
	Include words: <input name="filterHasTheWords" type="text" value="<%= filterHasTheWords %>"> Exclude words: <input name="filterHasNotTheWords" type="text" value="<%= filterHasNotTheWords %>">
	<br>
	From the date: <input name="filterTimeStart" type="text" value="<%= filterTimeStart %>"><input type="button" onclick="displayDatePicker('filterTimeStart')" value="Pick Time"/> Till the date: <input name="filterTimeEnd" type="text" value="<%= filterTimeEnd %>"><input type="button" onclick="displayDatePicker('filterTimeEnd')" value="Pick Time"/>
	<br>
	<input type="submit" value="Cancel" name="Cancel"/><input type="submit" value="Test Search" name="Test Search"/><input type="submit" value="Save" name="Save"/>
</form>

<br>
<hr>
<br>

<%
}

List<MessageBean> unfilteredMessages;

if (sortBy.equals("Sender/Recipient") && orderOf.equals("Ascending")) {
	unfilteredMessages = outbox ? action.getAllMySentMessagesNameAscending() : action.getAllMyMessagesNameAscending();
} else if (sortBy.equals("Sender/Recipient") && orderOf.equals("Descending")) {
	unfilteredMessages = outbox ? action.getAllMySentMessagesNameDescending() : action.getAllMyMessagesNameDescending();
} else if (sortBy.equals("Timestamp") && orderOf.equals("Ascending")) {
	unfilteredMessages = outbox ? action.getAllMySentMessagesTimeAscending() : action.getAllMyMessagesTimeAscending();
} else if (sortBy.equals("Timestamp") && orderOf.equals("Descending")) {
	unfilteredMessages = outbox ? action.getAllMySentMessagesTimeDescending() : action.getAllMyMessagesTimeDescending();
} else {
	unfilteredMessages = outbox ? action.getAllMySentMessages() : action.getAllMyMessages();
}

List<MessageBean> messages;
if (outbox)
	messages = unfilteredMessages;
else {
	try {
		messages = action.filterMessages(unfilteredMessages, filter);
	} catch (Exception e) {
		e.printStackTrace();
		messages = unfilteredMessages;
	}
}

session.setAttribute("messages", messages);
		
if(messages.size() > 0) { %>

<table id="mailbox" class="display fTable">
	<thead>		
		<tr>
			<th><%= outbox?"Receiver":"Sender" %></th>
			<th>Subject</th>
			<th><%= outbox?"Sent":"Received" %></th>
			<th></th>
		</tr>
	</thead>
	<tbody>
	<% 
	int index=-1;
	for(MessageBean message : messages) {
		String style = "";
		if(message.getRead() == 0) {
			style = "style=\"font-weight: bold;\"";
		}

		if(!outbox || message.getOriginalMessageId()==0){
			index ++; 
			String primaryName = action.getName(outbox?message.getTo():message.getFrom());
			List<MessageBean> ccs = action.getCCdMessages(message.getMessageId());
			String ccNames = "";
			int ccCount = 0;
			for(MessageBean cc:ccs){
				ccCount++;
				long ccMID = cc.getTo();
				ccNames += action.getPersonnelName(ccMID) + ", ";
			}
			ccNames = ccNames.length() > 0?ccNames.substring(0, ccNames.length()-2):ccNames;
			String toString = primaryName;
			if(ccCount>0){
				String ccNameParts[] = ccNames.split(",");
				toString = toString + " (CC'd: ";
				for(int i = 0; i < ccNameParts.length-1; i++) {
					toString += ccNameParts[i] + ", ";
				}
				toString += ccNameParts[ccNameParts.length - 1] + ")";
			}			
			%>					
				<tr <%=style%>>
					<td><%= StringEscapeUtils.escapeHtml("" + ( toString)) %></td>
					<td><%= StringEscapeUtils.escapeHtml("" + ( message.getSubject() )) %></td>
					<td><%= StringEscapeUtils.escapeHtml("" + ( dateFormat.format(message.getSentDate()) )) %></td>
					<td><a href="<%= outbox?"viewMessageOutbox.jsp?msg=" + StringEscapeUtils.escapeHtml("" + ( index )):"viewMessageInbox.jsp?msg=" + StringEscapeUtils.escapeHtml("" + ( index )) %>">Read</a></td>
				</tr>			
			<%
		}
		
	}	
	%>
	</tbody>
</table>
<%} else { %>
	<div>
		<i>You have no messages</i>
	</div>
<%	} %>
