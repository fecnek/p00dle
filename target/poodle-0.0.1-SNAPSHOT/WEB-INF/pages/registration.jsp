<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Poodle registration </title>
<link rel="stylesheet" type="text/css" href="/poodle/poodle.css">
	    <script src="/poodle/scripts/jquery-1.11.1.js"></script>
	
    <script src="/poodle/scripts/jquery-ui.js"></script>
     <link rel="stylesheet" href="/poodle/scripts/jquery-ui.css">
      <link rel="stylesheet" href="/poodle/scripts/jquery-ui.structure.css">
  
    <link rel="stylesheet" href="/poodle/scripts/jquery-ui.theme.css">
  
  <script>
  $(function() {
	  $( "button" ).button();
    
  });
  </script></head>
<body onload='document.registrationForm.j_username.focus();'>
<img src="img/logo.jpg" border="0"/>
<h3>Registration</h3>
<a href="/poodle/">Back to the main page</a>
 
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
		</ul>
	</c:if>
<c:if test="${successed}">
<div class="ui-widget">
	<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
		<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
		<strong>Congratulation!</strong> Your registration succesed!</p>
	</div>
</div>
</c:if>

<form name='registrationForm' action="/poodle/registration.htm"
method='POST'>
 
<table>
<tr>
<td>User:</td>
<td><input type='text' name='userName' value="<c:out value='${registrationForm.userName}'/>">
</td>
</tr>
<tr>
<td>E-mail:</td>
<td><input type='text' name='email' value="<c:out value='${registrationForm.email}'/>">
</td>
</tr>
<tr>
<td>Password:</td>
<td><input type='password' name='password' value=''>
</td>
</tr>
<tr>
<td>Retype password:</td>
<td><input type='password' name='retypedPassword' />
</td>
</tr>
<tr>
<td><button type="submit">Registration</button>
</td>
<td><button type="reset">Reset</button>
</td>
</tr>
</table>
 
</form>
</body>
</html>