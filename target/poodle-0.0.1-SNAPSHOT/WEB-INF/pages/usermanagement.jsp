<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<link rel="stylesheet" type="text/css" href="/poodle/poodle.css">
	<style>
		
	
	
	</style>
</head>
 
<body>
<img src="/poodle/img/logo.jpg" border="0" width="200px"/>
<div id="welcome">
Welcome <c:out value="${username}"></c:out>!
<a href="/poodle/logout.htm">Logout</a>
</div>
<div class="listcontainer">
<h3>User management</h3><br>
<div class="button"  onclick="location.href='/poodle/usermanagement/addUser.htm'" >
	Add user
</div>
<div class="button"  onclick="location.href='/poodle/main.htm'" >
	Back to the menu
</div>
<div class="clear"></div>
<br>
<c:if test="${showUserForm}">
   <form method="post" action="/poodle/usermanagement/addUser.htm">
    <b>New user</b><br>
	<c:if test="${errorList != null}">
		<ul>
		<c:forEach var="error" items="${errorList}">
				<li><c:out value="${error.defaultMessage}"/></li>
		</c:forEach>
		</ul>
	</c:if>
    <input type="hidden" name="id" value="${userForm.id}">
   	Name: <input type="text" name="name" value="${userForm.name}"><br>
   	E-mail:<input type="text" name="email" value="${userForm.email}"><br>
   	<input type="submit" value="Save user"/>
   </form>
</c:if>
<div class="list">
	<table border="0" cellpadding="0" cellspacing="1">
		<tr class="header">
			<td width="200px">Name</td>
			<td width="300px">E-mail address</td>
			<td width="300px"></td>
		</tr>
		<c:forEach var="user" items="${userList}">
   			<tr class="odd">
						<td><c:out value="${user.name}"/></td>
						<td><c:out value="${user.email}"/></td>
						<td><div class="button" onclick="location.href='/poodle/usermanagement/modifyUser/<c:out value="${user.id}"/>.htm'" >
				Modify
			</div>
			<div class="button"   onclick="location.href='/poodle/main.htm'" >
				Delete
			</div></td>
					</tr>
		</c:forEach>
	</table>
</div>
</div>

</body>
</html>