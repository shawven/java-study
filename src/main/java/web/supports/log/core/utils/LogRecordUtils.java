package web.supports.log.core.utils;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import web.dto.Response;
import web.supports.log.core.LogException;
import web.supports.log.core.annotation.UpdateLog;
import web.supports.log.domain.DataItem;
import web.supports.utils.ReflectHelper;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @description:
 * @author: VAIO
 * @date: 2018-03-25 21:04
 */
public class LogRecordUtils {

    public enum LogDataType{
        OLD_VALUE, NEW_VALUE
    }

    /**
     * 查找某个实体对象的主键属性名
     *
     * @param obj
     * @return
     */
    public static String getObjectId(Object obj) {
        Class<?> cls = obj.getClass();
        try {
            return ReflectHelper.getPropertyNameByAnnotation(cls, UpdateLog.class);
        } catch (Exception e) {
            Field[] fields = cls.getDeclaredFields();
            for (Field field : fields) {
                String objectWithId = cls.getSimpleName() + "Id";
                if ("id".equals(field.getName()) || objectWithId.equals(field.getName())) {
                    return field.getName();
                }
            }
        }

        return null;
    }

    /**
     * 对象转数据项 不过滤空
     *
     * @param obj
     * @param logDataType
     * @return
     */
    public static List<DataItem> objectToDataItems(Object obj, LogDataType logDataType) {
        return objectToDataItems(obj, logDataType, false);
    }

    /**
     * 对象转数据项
     *
     * @param obj          对象
     * @param logDataType  数据值类型
     * @param filterNull  是否过滤空
     * @return
     */
    public static List<DataItem> objectToDataItems(Object obj, LogDataType logDataType, boolean filterNull) {
        try {
            Map<String, Object> objectMap = ReflectHelper.objectToMap(obj);
            if (MapUtils.isNotEmpty(objectMap)) {
                List<DataItem> dataItems = new ArrayList<>();
                for (Map.Entry<String, Object> entry : objectMap.entrySet()) {
                    if (filterNull) {
                        if (StringUtils.isNotBlank(entry.getKey()) && entry.getValue() != null) {
                            dataItems.add(generateDataItem(entry, logDataType));
                        }
                    } else {
                        dataItems.add(generateDataItem(entry, logDataType));
                    }

                }
                return dataItems;
            }
        } catch (Exception e) {
            throw new LogException("数据转换异常！", e);
        }

        return null;
    }

    /**
     * 生成数据项
     *
     * @param entry
     * @param logDataType
     * @return
     */
    private static DataItem generateDataItem(Map.Entry<String, Object> entry, LogDataType logDataType) {
        DataItem dataItem = new DataItem();
        dataItem.setField(entry.getKey());
        String value = convertToString(entry.getValue());
        if (logDataType == LogDataType.NEW_VALUE) {
            dataItem.setNewValue(value);
        } else {
            dataItem.setOldValue(value);
        }

        return dataItem;
    }


    /**
     * 转换字符串
     *
     * @param object
     * @return
     */
    private static String convertToString(Object object){
        if (object == null) {
            return null;
        }
        if (object.getClass().isAssignableFrom(Date.class)) {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((Date)object);
        }
        return String.valueOf(object);
    }

    /**
     * 转化目标方法的处理结果
     *
     * @param result
     * @return
     */
    public static Object convertProceedResult(Object result) {
        // 捕获的是controller层的返回的ResponseEntity
        try {
            if (result instanceof ResponseEntity) {
                Object body = ((ResponseEntity) result).getBody();
                // 是ResponseUtils返回的结果
                if (body instanceof Response) {
                    Response response = (Response) body;
                    return response.getData();
                } else {
                    return body;
                }

            // 只返回普通对象，对于service层的返回bool和影响的记录条数转成数据项是无意义的
            } else if(!isBaseDataType(result.getClass()) ) {
                return result;
            }
        } catch (Exception ignored) {}
        return null;
    }


    /**
     * 通过返回结果是否失败，失败不写入日志系统
     * 从目标方法返回的结果只能确定失败的情况，其他情况默认成功
     *
     * @param result
     * @return
     */
    public static  Boolean checkFail(Object result) {
        // 捕获的是controller层的返回ResponseEntity
        try {
            if (result instanceof ResponseEntity) {
                Object body = ((ResponseEntity) result).getBody();
                // 是ResponseUtils返回的结果
                if (body instanceof Response) {
                    Response response = (Response) body;
                    return !response.getSuccess();
                }
            } else if(!isBaseDataType(result.getClass()) ) {
                //  service层的返回影响的记录
                if (result.getClass().isAssignableFrom(Integer.class)) {
                    return Integer.valueOf(String.valueOf(result)) == 0;
                }
                // service层的返回bool
                if (result.getClass().isAssignableFrom(Boolean.class)) {
                    return !Boolean.valueOf(String.valueOf(result));
                }
            }

        } catch (Exception ignored) { }
        return true;
    }

    /**
     * 判断一个类是否为基本数据类型或包装类，或日期。
     *
     * @param clazz 要判断的类。
     * @return true 表示为基本数据类型。
     */
    public static boolean isBaseDataType(Class clazz) {
        return clazz.equals(String.class) || clazz.equals(Integer.class) || clazz.equals(Byte.class)
                || clazz.equals(Long.class) || clazz.equals(Double.class) || clazz.equals(Float.class)
                || clazz.equals(Character.class) || clazz.equals(Short.class) || clazz.equals(BigDecimal.class)
                || clazz.equals(BigInteger.class) || clazz.equals(Boolean.class) || clazz.equals(Date.class)
                || clazz.isPrimitive();
    }
}
