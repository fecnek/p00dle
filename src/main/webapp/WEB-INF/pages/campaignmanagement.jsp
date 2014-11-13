<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<link rel="stylesheet" type="text/css" href="/poodle/poodle.css">
	    <script src="/poodle/scripts/jquery-1.11.1.js"></script>
	
    <script src="/poodle/scripts/jquery-ui.js"></script>
     <link rel="stylesheet" href="/poodle/scripts/jquery-ui.css">
      <link rel="stylesheet" href="/poodle/scripts/jquery-ui.structure.css">
  
    <link rel="stylesheet" href="/poodle/scripts/jquery-ui.theme.css">
  
  <script>
  $(function() {
	  $( "button" ).button();


	  
	  $("#periodStart").datepicker();
      $("#periodEnd").datepicker();
    
  });
  </script>
	
	
</head>
 
<body>
<img src="/poodle/img/logo.jpg" border="0" width="200px"/>
<div id="welcome">
Welcome <c:out value="${username}"></c:out>!
<a href="/poodle/logout.htm">Logout</a>
</div>
<div class="listcontainer">
<h3>Campaign management</h3><br>
<button id="addButton" type="button" onclick="location.href='/poodle/campaignmanagement/addCampaign.htm'">
Add campaign</button>
<button id="backButton" type="button" onclick="location.href='/poodle/main.htm'">Back to the menu</button>
<div class="clear"></div>
<br>
<c:if test="${showCampaignForm}">
   <form method="post" action="/poodle/campaignmanagement/addCampaign.htm">
	<c:if test="${errorList != null}">
	<div class="ui-widget">
	<div class="ui-state-error ui-corner-all" style="padding: 0 .7em;">
		<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
		<strong>Alert:</strong> <ul>
		<c:forEach var="error" items="${errorList}">
				<li><c:out value="${error.defaultMessage}"/></li>
		</c:forEach>
		</ul></p>
	</div>
</div>
		
	</c:if>
    <input type="hidden" name="id" value="${campaignForm.id}">
   	Name: <input type="text" name="name" value="${campaignForm.name}"><br>
   	Period start:<input type="text" id="periodStart" name="periodStart" value="${campaignForm.periodStart}"><br>
   	Period end:<input type="text"  id="periodEnd" name="periodEnd" value="${campaignForm.periodEnd}"><br>
   	
   	<button type="submit">Save campaign</button>
   </form>
</c:if>
<div class="list">
	<table border="0" cellpadding="0" cellspacing="1">
		<tr class="header">
			<td width="200px">Name</td>
			<td width="200px">Period from</td>
			<td width="200px">Period to</td>
			
			<td width="300px"></td>
		</tr>
		<c:forEach var="campaign" items="${campaignList}">
   			<tr class="odd">
						<td><c:out value="${campaign.name}"/></td>
						<td><c:out value="${campaign.periodStart}"/></td>
						<td><c:out value="${campaign.periodEnd}"/></td>
						
						<td><button  onclick="location.href='/poodle/campaignmanagement/modifyCampaign/<c:out value="${campaign.id}"/>.htm'" >
							Modify</button>
							<button  onclick="location.href='/poodle/main.htm'" >Delete</button>
							<button onclick="location.href='/poodle/campaignmanagement/surveys/<c:out value="${campaign.id}"/>.htm'" >Survey</button></td>
					</tr>
		</c:forEach>
	</table>
</div>
</div>

</body>
</html>