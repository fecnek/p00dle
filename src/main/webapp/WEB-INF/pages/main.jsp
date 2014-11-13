<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<link rel="stylesheet" type="text/css" href="poodle.css">
	<style>
		#welcome {
			position: relative;
			left: 620px;
			font-size: 11px;
		}
	
		.boxcontainer {
			position: absolute;
			left: 200px;
			border: 1px solid #C4C0B9;
			background-color: #eff4ff;
			padding: 50px;
			
		}
	
		.box {
			width : 100px;
			height: 100px;
			border: 1px solid #C4C0B9;
			cursor: pointer;
			background-color: #56cef6;
			padding: 10px;
			color: white;
			font-family: Verdana;
			position: relative;
			float: left;
			margin-right: 20px;
		}
		.box:hover {
			width : 100px;
			height: 100px;
			border: 1px solid #C4C0B9;
			cursor: pointer;
			background-color: #59d5ff;
			padding: 10px;
			font-family: Verdana;
			position: relative;
			float: left;
			margin-right: 20px;
			cursor: pointer;
		}
	</style>
</head>
 
<body>
<img src="img/logo.jpg" border="0" width="200px"/>
<div id="welcome">
Welcome <c:out value="${username}"></c:out>!
</div>
<div class="boxcontainer">
<div class="box" onclick="location.href='/poodle/campaignmanagement.htm'">
	Campaign
</div>
<div class="box"  onclick="location.href='/poodle/usermanagement.htm'" >
	User management
</div>
<div class="box"  onclick="location.href='/poodle/logout.htm'" >
	Logout
</div>
</div>

</body>
</html>