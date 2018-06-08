<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
	<title>ログイン画面</title>
</head>
<body>
	<h1>ログイン画面</h1>
 	<form:form modelAttribute="logonForm" action="${pageContext.request.contextPath}/Logon">
		ID:<form:input path="id" />		パスワード:<form:input path="password" />
		<br /><br />
		<input type="submit" value="ログイン" />
		<br />
	</form:form>
</body>
</html>
