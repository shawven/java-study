<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<meta http-equiv="Content-Type" content="text/html; charset = utf-8"/>
<title>Index Jsp</title>
<body>
<form action="${ctx}/login" method="post" >
    <ul>
        <li>
            <input type="text" name="username" id="username"  autofocus placeholder="用户名" value="jimi" >
        </li>
        <li>
            <input type="password"  name="password" id="password"  placeholder="密码"  value="jimispassword" >
        </li>
        <li class="loginsub">
            <input style="cursor:pointer;" type="submit" value="登  录" />
            <div class="clear"></div>
        </li>
    </ul>
</form><br>
<a href="${ctx}/ws">ws</a>
</body>
</html>
