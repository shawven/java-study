<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset = utf-8"/>
    <title>WebSocket</title>
</head>
<body>
<a href="${ctx}/home">home</a><br>
<a href="${ctx}/logout">logout</a>
<script src="${ctx}/resources/js/jquery.min.js"></script>
<script src="${ctx}/resources/js/bootstrap.min.js"></script>
<script src="${ctx}/resources/js/socket/sockjs.min.js"></script>
<script src="${ctx}/resources/js/socket/stomp.min.js"></script>
<script type="text/javascript">
    function connect() {
        stompClient = Stomp.over(new SockJS("${ctx}/websocket"));
        stompClient.connect({}, function (frame) {
            console.log(frame);

            stompClient.subscribe('/user/abc/answer', function (res) {
                console.log(res);
            });

            stompClient.send("/app/question", {}, JSON.stringify({
                command: "close",
                formUser: 10000,
                toUser: 1,
                body: {
                    id: 1
                }
            }));
        }, function() {
            console.error("'连接服务器失败，正在重新连接...");
            // setTimeout(1000, connect())
        })
    }
    connect();
</script>
</body>
</html>