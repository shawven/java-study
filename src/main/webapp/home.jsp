<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<meta http-equiv="Content-Type" content="text/html; charset = utf-8"/>
<title>Welcome</title>
<body>
<h1>Home Page</h1>
<a href="${ctx}/logout">logout</a><br>
<a href="${ctx}/ws">ws</a>
</body>
</html>
