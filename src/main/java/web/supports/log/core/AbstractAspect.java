package web.supports.log.core;


import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import web.supports.log.core.annotation.Log;
import web.supports.log.core.annotation.UpdateLog;
import web.supports.log.core.emun.LogType;
import web.supports.log.core.utils.LogAnnotationUtils;
import web.supports.log.core.utils.LogRecordUtils;
import web.supports.log.domain.LogData;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 *
 * @description:
 * @author: VAIO
 * @date: 2018-03-24 11:16
 */
public abstract class AbstractAspect<T extends Annotation> {

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected Logger<T> logger;

    @Resource(name = "databaseRepository")
    protected LogRepository logRepository;

    private Class<T> aClass;

    @Pointcut("execution(* com.fs.system..*.*(..))" +
            " && @annotation(com.fs.supports.log.core.annotation.Log)")
    public void commonPointcut(){}

    @Pointcut("execution(* com.fs.system..*.*(..))" +
            " && @target(com.fs.supports.log.core.annotation.Log)" +
            " && @annotation(com.fs.supports.log.core.annotation.InsertLog)")
    public void insertPointcut(){}

    @Pointcut("execution(* com.fs.system..*.*(..))" +
            " && @target(com.fs.supports.log.core.annotation.Log)" +
            " && @annotation(com.fs.supports.log.core.annotation.UpdateLog)")
    public void updatePointcut(){}

    @Pointcut("execution(* com.fs.system..*.*(..))" +
            " && @target(com.fs.supports.log.core.annotation.Log)" +
            " && @annotation(com.fs.supports.log.core.annotation.DeleteLog)")
    public void deletePointcut(){}

    public abstract void before(JoinPoint jp);

    public abstract void after(JoinPoint jp) ;

    public abstract Object around(ProceedingJoinPoint jp) throws Throwable;

    /**
     * 获取日志记录
     *
     * @param jp
     * @param builder
     * @param <R>
     * @return
     */
    protected <R> R getLogRecord(JoinPoint jp, LogRecordBuilder<R> builder) {
        return builder.createLogRecord(jp, getActualAnnotationType());
    }

    /**
     * 获取当前泛型类的Class对象
     *
     * @return  泛型类的实际Class对象
     */
    public Class<T> getActualAnnotationType() {
        if (this.aClass == null) {
            Type type = this.getClass().getGenericSuperclass();
            //noinspection unchecked
            this.aClass = (Class<T>) ((ParameterizedType) type).getActualTypeArguments()[0];
        }
        return this.aClass;
    }

    /**
     * 获取详细的日志数据项 设置数据为旧字段
     *
     * @param jp
     * @return
     */
    public List<LogData> obtainLogDataList(JoinPoint jp) {
        return obtainLogDataList(jp, LogRecordUtils.LogDataType.OLD_VALUE);
    }

    /**
     * 获取详细的日志数据项
     *
     * @param jp
     * @param logDataType  数据新旧类型
     * @return
     */
    public List<LogData> obtainLogDataList(JoinPoint jp, LogRecordUtils.LogDataType logDataType) {
        Object[] args = jp.getArgs();

        if (ArrayUtils.isEmpty(args)) {
            return null;
        }

        List<LogData> dataSet = new ArrayList<>();
        for (Object arg : args) {
            if (arg == null) {
                continue;
            }

            LogData logData = new LogData();
            if (!LogRecordUtils.isBaseDataType(arg.getClass())) {
                logData.setIdName(LogRecordUtils.getObjectId(arg));
                logData.setObjectName(arg.getClass().getName());

                // 只有更新操作才要获取原数据
                if (isUpdateLog(jp)) {
                    logData.setChanges(LogRecordUtils.objectToDataItems(logRepository.select(arg), logDataType));
                } else {
                    logData.setChanges(LogRecordUtils.objectToDataItems(arg, logDataType, true));
                }
            } else {
                logData.setIdName(String.valueOf(arg));
                logData.setObjectName(arg.getClass().getSimpleName());
            }

            dataSet.add(logData);
        }

        return dataSet;
    }

    /**
     * 检查是否失败
     *
     * @param result
     * @return
     */
    protected Boolean checkFail(Object result) {
        return LogRecordUtils.checkFail(result);
    }

    /**
     * 是否是更新日志记录
     *
     * @param jp
     * @return
     */
    private Boolean isUpdateLog(JoinPoint jp) {
        if (getActualAnnotationType().isAssignableFrom(UpdateLog.class)) {
            return true;
        }
        Log log = LogAnnotationUtils.getMethodLogAnnotation(jp, Log.class);
        return log != null && log.type() == LogType.UPDATE;
    }

}
