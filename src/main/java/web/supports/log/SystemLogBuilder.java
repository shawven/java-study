package web.supports.log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import web.supports.log.core.LogRecordBuilder;
import web.supports.log.core.emun.LogType;
import web.supports.log.domain.LogData;
import web.supports.log.domain.SystemLog;
import web.supports.utils.IpUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @description:
 * @author: VAIO
 * @date: 2018-03-24 13:55
 */


public class SystemLogBuilder extends LogRecordBuilder<SystemLog> {

    private SystemLog logRecord;

    private HttpServletRequest request;

    public SystemLogBuilder(HttpServletRequest request) {
        this.request = request;
        this.logRecord = getSystemLog();
    }

    public SystemLogBuilder build(List<LogData> logData) {
        String s = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            s = mapper.writeValueAsString(logData);
        } catch (JsonProcessingException ignored) {}

        logRecord.setData(s);

        return this;
    }

    public SystemLogBuilder build(String message) {
        if (message != null) {
            logRecord.setRemark(message.length() < 254 ? message : message.substring(0, 255));
        }

        return this;
    }

    private SystemLog getSystemLog(){
        SystemLog systemLog = (SystemLog) request.getSession().getAttribute("systemLog");
        if (systemLog == null) {
            systemLog = new SystemLog();

            systemLog.setIp(IpUtils.getIpAddress(request));
            systemLog.setAddress(IpUtils.getAddress(systemLog.getIp()));
            request.getSession().setAttribute("systemLog", systemLog);
        }

        String params = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            params = mapper.writeValueAsString(request.getParameterMap());
        } catch (JsonProcessingException ignored) {}
        systemLog.setRequestParams(params);

        systemLog.setRequestUrl(request.getMethod() + ": " + request.getRequestURI());
        systemLog.setOpertime(new Date());

        //去除自增主键
        systemLog.setId(null);

        return systemLog;
    }


    @Override
    public SystemLogBuilder setModule(String module) {
        this.logRecord.setOpermodule(module);
        return this;
    }

    @Override
    public SystemLogBuilder setDesc(String desc) {
        this.logRecord.setOpertext(desc);
        return this;
    }

    @Override
    public SystemLogBuilder setType(LogType type) {
        this.logRecord.setOpertype(type.getType());
        return this;
    }

    @Override
    public SystemLog getLogRecord() {
        return logRecord;
    }


}

