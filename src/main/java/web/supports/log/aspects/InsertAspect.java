package web.supports.log.aspects;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import web.supports.log.SystemLogBuilder;
import web.supports.log.core.AbstractAspect;
import web.supports.log.core.LogException;
import web.supports.log.core.annotation.InsertLog;
import web.supports.log.core.utils.LogRecordUtils;
import web.supports.log.domain.LogData;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @description:
 * @author: VAIO
 * @date: 2018-03-24 13:12
 */
@Aspect
@Component
public class InsertAspect extends AbstractAspect<InsertLog> {

    @Override
    public void before(JoinPoint jp) {

    }

    @Override
    public void after(JoinPoint jp){
    }

    @Around("insertPointcut()")
    @Override
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Object result = null;
        List<LogData> logDataSet;
        try {
            try {
                // 保存插入前的数据 字段为新值
                logDataSet = obtainLogDataList(pjp, LogRecordUtils.LogDataType.NEW_VALUE);
            } catch (Exception e) {
                throw new LogException(e.getMessage(), e);
            } finally {
                // 执行目标插入操作后返回数据
                result = pjp.proceed();
            }

            // 检查操作是否成功
            if (!checkFail(result)) {
                try {
                    logger.log(getLogRecord(pjp, new SystemLogBuilder(request).build(logDataSet)));
                } catch (Exception e) {
                    throw new LogException(e.getMessage(), e);
                }
            } else {
                logger.log(getLogRecord(pjp, new SystemLogBuilder(request).build("添加失败")));
            }

        } catch (LogException e) {
            logger.getSlf4jLogger().error(e.getMessage() != null ?
                    "日志系统异常: " + e.getMessage() : "日志系统发生未知异常", e);
        } catch (Throwable e) {
            try {
                logger.log(getLogRecord(pjp, new SystemLogBuilder(request).build(e.getMessage())));
            } catch (Exception ignored) {}
            throw e;
        }

        return result;
    }
}
