package web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import web.dto.Message;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Map;

/**
 * @author FS
 * @date 2018-09-30 10:34
 */
@Controller
public class WebSocketController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @GetMapping("ws")
    public String ws() {
        return "websocket";
    }

    @MessageMapping("/question")
    @SendToUser("/answer")
    public String handleQuestion(Message message, @Headers Map<String, Object> headers, Principal principal) throws Exception {
        System.out.println(message);
        simpMessagingTemplate.convertAndSendToUser("jimi", "/answer", message);
        return "hello";
    }
}
