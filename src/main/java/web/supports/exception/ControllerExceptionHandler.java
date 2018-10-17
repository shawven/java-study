package web.supports.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;
import web.supports.utils.ResponseUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author FS
 * @date 2018-10-08 11:20
 */
@ControllerAdvice
public class ControllerExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    private final String  errorMsg = "系统异常，请稍后再试";

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseBody
    public ResponseEntity noHandlerFoundExceptionHandler(HttpServletRequest request, NoHandlerFoundException e){
        String errorMsg = "请求的URl" + request.getRequestURL() + "未找到";
        e.printStackTrace();
        return ResponseUtils.notFound(errorMsg);
    }

    @ExceptionHandler(AuthenticationException.class)
    public String authenticationExceptionHandler(AuthenticationException e){
        e.printStackTrace();
        return "/passport";
    }


    @ExceptionHandler(BindException.class)
    @ResponseBody
    public ResponseEntity bindExceptionHandler(BindException e){
        String errorMsg = e.getMessage() !=  null ? "请求数据校验不合法: " + e.getMessage() : this.errorMsg;
        e.printStackTrace();
        return ResponseUtils.badRequest(errorMsg);
    }


    @ExceptionHandler(BizException.class)
    @ResponseBody
    public ResponseEntity bizExceptionHandler(HttpServletRequest request, Exception e) {
        String exceptionMsg = e.getMessage();
        String errorMsg = e.getMessage() != null ? "发生异常: " + exceptionMsg : this.errorMsg;
        e.printStackTrace();
        return ResponseUtils.error(errorMsg, "request url is" + request.getRequestURI());
    }

    @ExceptionHandler({Exception.class})
    @ResponseBody
    public ResponseEntity exceptionHandler(HttpServletRequest request, Exception e) {
        String errorMsg ;
        if (e instanceof MaxUploadSizeExceededException){
            long maxUploadSize = ((MaxUploadSizeExceededException) e).getMaxUploadSize();
            errorMsg = "上传文件大小超过"+ getSize(maxUploadSize) +"限制";
        } else {
            errorMsg = e.getMessage() != null ? "系统异常: " + e.getMessage() : this.errorMsg;
            log.error(errorMsg);
        }
        return ResponseUtils.error(errorMsg, "url:" + request.getRequestURI());
    }

    private String getSize(long b) {
        if (b == 0) {
            return "0B";
        } else if (b < 1024) {
            return b +"B";
        } else if (b > 1024 && b < 1024 * 1024) {
            long kb = 1024;
            return b / kb + "KB";
        } else {
            long mb = 1024 * 1024;
            return b / mb + "MB";
        }
    }

}
