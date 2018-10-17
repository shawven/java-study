package web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import web.dto.Message;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    public Message handleQuestion(Message message,
                                  Principal principal,
                                  @Headers Map<String, Object> headers,
//                                  HttpSession session,
                                  SimpMessageHeaderAccessor headerAccessor) throws Exception {
        System.out.println(message);
        simpMessagingTemplate.convertAndSendToUser("abc", "/answer", message);
        return message;
    }

    @MessageExceptionHandler
    @SendToUser("/errors")
    public String handleException(Exception ex) {
        ex.printStackTrace();
        return ex.getMessage();
    }
}
