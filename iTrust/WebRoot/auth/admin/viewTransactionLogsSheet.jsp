<%@page import="edu.ncsu.csc.itrust.action.ViewTransactionLogsAction"%>
<%@page import="edu.ncsu.csc.itrust.beans.TransactionBean" %>
<%@page import="java.util.List" %>
<%@page import="java.util.HashMap" %>
<%@page import="java.lang.String" %>

<%@include file="/global.jsp" %>

<%
    pageTitle = "iTrust - Transaction Logs Sheet";
%>
<%@include file="/header.jsp" %>
<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%
    ViewTransactionLogsAction viewTransactionLogsAction = new ViewTransactionLogsAction(prodDAO);

    String dateBeginning = request.getParameter("dateBeginning");
    String dateEnding = request.getParameter("dateEnding");

    String roleForLoggedInUser = request.getParameter("roleForLoggedInUser");
    String roleForSecondaryUser = request.getParameter("roleForSecondaryUser");

    String transactionType = request.getParameter("transactionLogType");
    List<TransactionBean> transactions = viewTransactionLogsAction.getTransactionLogsAfterFiltering(roleForLoggedInUser,
                                                                                roleForSecondaryUser, dateBeginning,
                                                                                dateEnding, transactionType);
    HashMap<String, Integer> map = viewTransactionLogsAction.hashMapForLoggedInUserType();
    HashMap<String, Integer> map2 = viewTransactionLogsAction.hashMapForSecondaryUserType();
    HashMap<String, Integer> map3 = viewTransactionLogsAction.hashMapForAYear(dateEnding.substring(dateEnding.length()-4));
    HashMap<String, Integer> map4 = viewTransactionLogsAction.hashMapForTransactionType();

%>

<head>
	<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
	<script type="text/javascript">

google.charts.load('current', {packages: ['corechart', 'bar']});
google.charts.setOnLoadCallback(drawDualX);
function drawDualX() {
      var temparray = [['Roles', 'Transaction Num']];
      <%
          for (String key : map.keySet()) {
      %>
      temparray.push(['<%= key %>', <%= map.get(key) %>]);
      <%
          }
      %>
      var data = google.visualization.arrayToDataTable(temparray);

      var materialOptions = {
        chart: {
          title: 'Logged In Role vs. Number of Transactions',
        },
        hAxis: {
          title: 'Total Transaction'
        },
        vAxis: {
          title: 'Logged In Role'
        },
        bars: 'horizontal',
      };
      var materialChart = new google.charts.Bar(document.getElementById('chart_div'));
      materialChart.draw(data, materialOptions);

    var temparray2 = [['Roles', 'Transaction Num']];
    <%
        for (String key : map2.keySet()) {
    %>
    temparray2.push(['<%= key %>', <%= map2.get(key) %>]);
    <%
        }
    %>
     var data2 = google.visualization.arrayToDataTable(temparray2);

      var materialOptions2 = {
        chart: {
          title: 'Secondary Role vs. Number of Transactions',
        },
        hAxis: {
          title: 'Total Transaction'
        },
        vAxis: {
          title: 'Secondary Role'
        },
        bars: 'horizontal',
      };
    var materialChart2 = new google.charts.Bar(document.getElementById('chart_div2'));
    materialChart2.draw(data2, materialOptions2);

    var temparray3 = [['Months', 'Transaction Num']];
       <%
           for (String key : map3.keySet()) {
       %>
       temparray3.push(['<%= key %>', <%= map3.get(key) %>]);
       <%
           }
       %>
      var data3 = google.visualization.arrayToDataTable(temparray3);

      var materialOptions3 = {
        chart: {
          title: 'Months vs. Number of Transactions',
        },
        hAxis: {
          title: 'Total Transaction'
        },
        vAxis: {
          title: 'Month'
        },
        bars: 'horizontal',
      };
      var materialChart3 = new google.charts.Bar(document.getElementById('chart_div3'));
      materialChart3.draw(data3, materialOptions3);



        var temparray4 = [['Types', 'Transaction Num']];
       <%
           for (String key : map4.keySet()) {
       %>
       temparray4.push(['<%= key %>', <%= map4.get(key) %>]);
       <%
           }
       %>
      var data4 = google.visualization.arrayToDataTable(temparray4);

      var materialOptions4 = {
         chart: {
           title: 'Types vs. Number of Transactions',
         },
         hAxis: {
           title: 'Total Transaction'
         },
         vAxis: {
           title: 'Types'
         },
         bars: 'horizontal',
       };
      var materialChart4 = new google.charts.Bar(document.getElementById('chart_div4'));
      materialChart4.draw(data4, materialOptions4);
    }
	</script>
</head>
  <body>
    <div id="chart_div" style="width: 900px; height: 500px;"></div>
    <div id="chart_div2" style="width: 900px; height: 500px;"></div>
    <div id="chart_div3" style="width: 900px; height: 500px;"></div>
    <div id="chart_div4" style="width: 900px; height: 500px;"></div>
  </body>

<div>
    <h1>Filtered Transaction Logs</h1>

    <table class="table">

        <thead>
        <tr>
            <th>Role of Logged in User</th>
            <th>Role of Secondary User</th>
            <th>Transaction Type</th>
            <th>Extra Info</th>
            <th>Log Time</th>
        </tr>
        </thead>
        <tbody>
        <%
            for (int i=0; i<transactions.size(); i++){
                TransactionBean transactionBean = transactions.get(i);

        %>

        <tr>
            <td><%= transactionBean.getRoleForLoggedInUser() %></td>
            <td><%= transactionBean.getRoleForSecondaryUser() %></td>
            <td><%= transactionBean.getTransactionType().getDescription() %></td>
            <td><%= transactionBean.getAddedInfo() %></td>
            <td><%= transactionBean.getTimeLogged() %></td>
        </tr>
        <%
            }
        %>

        </tbody>
    </table>
</div>




<%@include file="/footer.jsp" %>
