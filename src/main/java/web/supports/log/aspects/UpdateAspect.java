package web.supports.log.aspects;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import web.supports.log.SystemLogBuilder;
import web.supports.log.core.AbstractAspect;
import web.supports.log.core.LogException;
import web.supports.log.core.annotation.UpdateLog;
import web.supports.log.core.utils.LogRecordUtils;
import web.supports.log.domain.DataItem;
import web.supports.log.domain.LogData;
import web.supports.utils.ReflectHelper;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @description:
 * @author: VAIO
 * @date: 2018-03-24 13:13
 */
@Aspect
@Component
public class UpdateAspect extends AbstractAspect<UpdateLog> {

    @Override
    public void before(JoinPoint jp) {

    }

    @Override
    public void after(JoinPoint jp) {

    }

    @Around("updatePointcut()")
    @Override
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Object result = null;

        List<LogData> logDataSet = null;
        try {
            try {
                // 保存修改前的数据 字段为旧值
                logDataSet = obtainLogDataList(pjp);
            } catch (Exception e) {
                throw new LogException(e.getMessage(), e);
            } finally {
                // 执行目标更新操作，并更新后返回数据， 如果不返还则通过修改前的对象主键去查找最新的数据
                result = pjp.proceed();
            }

            // 检查操作是否成功
            if (!checkFail(result)) {
                try {
                    // 保存修改后的数据 字段为新值
                    modifyLogDataList(logDataSet, result);
                    logger.log(getLogRecord(pjp, new SystemLogBuilder(request).build(logDataSet)));
                } catch (Exception e) {
                    throw new LogException(e.getMessage(), e);
                }
            } else {
                logger.log(getLogRecord(pjp, new SystemLogBuilder(request).build("更新失败")));
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

    /**
     * 修饰日志数据
     *
     * @param logDataSet
     * @param result
     */
    public void modifyLogDataList(List<LogData> logDataSet, Object result) {
        if (CollectionUtils.isNotEmpty(logDataSet)) {
            for (LogData logData : logDataSet) {
                List<DataItem> oldDataItems = logData.getChanges();
                List<DataItem> newDataItems = obtainNewDataItems(logData, result);
                List<DataItem> dataItems = combineDateItems(oldDataItems, newDataItems);
                logData.setChanges(dataItems);
            }
        }
    }

    /**
     * 获取更新后的数据对象
     *
     * @param logData
     * @param result
     * @return
     */
    private List<DataItem> obtainNewDataItems(LogData logData, Object result) {
        Object validObject = LogRecordUtils.convertProceedResult(result);

        //目标方法返回的结果不可用，从数据持久化中间件获取
        if (validObject == null) {
            try {
                Class<?> objClass = Class.forName(logData.getObjectName());
                Object obj = objClass.newInstance();

                for (DataItem change : logData.getChanges()) {
                    ReflectHelper.setProperty(obj, change.getField(), Long.valueOf(change.getOldValue()));
                }

                return LogRecordUtils.objectToDataItems(logRepository.select(obj),
                        LogRecordUtils.LogDataType.NEW_VALUE);
            } catch (Exception e) {
                throw new LogException(e.getMessage(), e);
            }
        }

        return LogRecordUtils.objectToDataItems(validObject, LogRecordUtils.LogDataType.NEW_VALUE);
    }

    /**
     * 合并日志数据项的新旧字段
     *
     * @param oldDataItems
     * @param newDataItems
     * @return
     */
    private List<DataItem> combineDateItems(List<DataItem> oldDataItems, List<DataItem> newDataItems) {
        if (CollectionUtils.isNotEmpty(newDataItems)) {
            for (DataItem oldDataItem : oldDataItems) {
                for (DataItem newDataItem : newDataItems) {
                    if (StringUtils.equals(oldDataItem.getField(), newDataItem.getField())) {
                        oldDataItem.setNewValue(newDataItem.getNewValue());
                    }
                }
            }
        }

        return oldDataItems;
    }
}
