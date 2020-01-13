<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="shortcut icon" href="resources/images/index.png"
	type="image/png" />
<title>${appTitle} | Login Form</title>
<link rel="stylesheet" href="resources/css/login.css">
<script
	src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>
</head>
<body>
	<div class="header">
		<img src="resources/images/estuate-inc-logo.png" alt="logo" />
		<h1 style="color: #bc0517; font-family: Arial Bold Italic">${appHeader}</h1>
	</div>
	<div class="login-page" style="margin-left: 400px;padding-top: 137px;">
		<div class="form">
			<form class="login-form" action="validateLogin" method="post"
				name="form1" onsubmit="return validate();">
				<table>
					<tr>
						<td align="center">
						<c:if test="${message.length()!=0}">
								<div id="mail" style="color: #024457;font-size: 13px"><b>${message}</b></div>
							</c:if> 
							</td>
					</tr>
					<tr>
						<td style="width: 425px;"><input class="username" type="text" name="emailId"
							placeholder="e-mail ID" id="emailId" autofocus="autofocus" /></td>
					</tr>
					<tr>
						<td style="width: 425px;"><input class="password" type="password" name="password"
							placeholder="Password" id="password" /></td>
					</tr>
				</table>
					<input class="submit" type="submit"
							onclick="return validate();" value="login">


			</form>
		</div>
	</div>
	<%-- <c:if test="${message != null}">
		<c:out value="${message}"></c:out>
	</c:if> --%>


	<script type="text/javascript" src="resources/js/validation.js"></script>
	<script src="resources/js/index.js"></script>
</body>
</html>
