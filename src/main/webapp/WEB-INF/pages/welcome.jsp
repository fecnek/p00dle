<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Poodle Main Page</title>
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
  </script>
</head>
<body onload='document.loginForm.j_username.focus();'>
<img src="img/logo.jpg" border="0"/>
<h3>Poodle Main Page</h3>
<a href="/poodle/registration.htm">Registration</a><br>
 
 
<c:if test="${error != null }">
<div class="ui-widget">
	<div class="ui-state-error ui-corner-all" style="padding: 0 .7em;">
		<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
		<strong>Alert:</strong> Incorrect login name or password. Please retry using correct login name and password.
		</p>
	</div>
</c:if> 
 
<form name='loginForm' action="<c:url value='j_spring_security_check' />"
method='POST'>
 
<table>
<tr>
<td>User:</td>
<td><input type='text' name='j_username' value=''>
</td>
</tr>
<tr>
<td>Password:</td>
<td><input type='password' name='j_password' />
</td>
</tr>
<tr>
<td><button type="submit">Login</button>
</td>
<td><button type="reset">Reset</button>
</td>
</tr>
</table>
 
</form>
</body>
</html>