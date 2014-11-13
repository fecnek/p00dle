<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
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
<h3>Surveys of <a href="/poodle/campaignmanagement.htm"><c:out value="${campaignName}"/></a></h3><br>
<button id="addButton" type="button" onclick="location.href='/poodle/campaignmanagement/surveys/<c:out value="${campaignId}"/>/addSurvey.htm'">
Add survey</button>
<button id="backButton" type="button" onclick="location.href='/poodle/main.htm'">Back to the menu</button>
<div class="clear"></div>
<br>
<c:if test="${showSurveyForm}">
<form:form method="post" action="/poodle/campaignmanagement/surveys/${campaignId}/addSurvey.htm" modelAttribute="surveyForm">

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
    <input type="hidden" name="id" value="${surveyForm.id}">
   	Name: <input type="text" name="name" value="${surveyForm.name}"><br>
   	
   	<c:if test="${surveyForm.id != null}">
   	<table>
    <tr>
        <th>Question</th>
        <th>Yes answer</th>
        <th>No answer</th>
        <td></th>
    </tr>
    <input type="hidden" id="addQuestion" name="addQuestion" value="">
    <input type="hidden" id="delQuestion" name="delQuestion" value="">
    
    <button type="submit" onclick="$('#addQuestion').val('true')">Add question</button> 
    <c:forEach items="${surveyForm.questions}" var="question" varStatus="status">
        <tr>
            <td><input name="questions[${status.index}].question" value="${question.question}"/></td> 
            <td><input name="questions[${status.index}].answerYes" value="${question.answerYes}"/></td>
            <td><input name="questions[${status.index}].answerNo" value="${question.answerNo}"/></td>
            <td><input name="questions[${status.index}].id" type="hidden" value="${question.id}"/><button type="submit" onclick="$('#delQuestion').val(${status.index})">Remove question</button></td>
        </tr>
    </c:forEach>
    </table>
    </c:if>
   	<br><br><button type="submit">Save Survey</button>
   	<br>
   </form:form>
</c:if>
<div class="list">
	<table border="0" cellpadding="0" cellspacing="1">
		<tr class="header">
			<td width="200px">Name</td>
			
			<td width="300px"></td>
		</tr>
		<c:forEach var="survey" items="${surveyList}">
   			<tr class="odd">
						<td><c:out value="${survey.name}"/></td>

						
						<td><button  onclick="location.href='/poodle/campaignmanagement/surveys/<c:out value="${campaignId}"/>/modifySurvey/<c:out value="${survey.id}"/>.htm'" >
							Modify</button>
							<button  onclick="location.href='/poodle/main.htm'" >Delete</button>
							<button onclick="location.href='/poodle/campaignmanagement/surveys/<c:out value="${survey.id}"/>/sendMail.htm'" >Send e-mail to the users</button></td>
					</tr>
		</c:forEach>
	</table>
</div>
</div>

</body>
</html>