package web.supports.log.core;

import org.apache.commons.collections4.MapUtils;
import org.aspectj.lang.JoinPoint;
import web.supports.log.core.emun.LogAttribute;
import web.supports.log.core.emun.LogType;
import web.supports.log.core.utils.LogAnnotationUtils;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @description:
 * @author: VAIO
 * @date: 2018-03-24 13:55
 */
public abstract class LogRecordBuilder<T> {


    public abstract LogRecordBuilder setModule(String module);

    public abstract LogRecordBuilder setDesc(String desc);

    public abstract LogRecordBuilder setType(LogType type);


    public abstract T getLogRecord();

    public T createLogRecord(JoinPoint jp, Class<? extends Annotation> aClass) {
        Map<LogAttribute, Object> attributes = LogAnnotationUtils.getAnnotationAttributes(jp, aClass);

        if (MapUtils.isNotEmpty(attributes)) {
            setModule(String.valueOf(attributes.get(LogAttribute.MODULE)));
            setDesc(String.valueOf(attributes.get(LogAttribute.VALUE)));
            setType(LogType.valueOf(String.valueOf(attributes.get(LogAttribute.TYPE))));
        }

        return getLogRecord();
    }
}
