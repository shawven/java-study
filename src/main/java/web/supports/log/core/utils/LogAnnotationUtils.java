package web.supports.log.core.utils;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import web.supports.log.core.annotation.Log;
import web.supports.log.core.emun.LogAttribute;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @description:
 * @author: VAIO
 * @date: 2018-03-25 14:34
 */
public class LogAnnotationUtils {

    /**
     * 获取当前方法注解的属性值
     *
     * @param jp
     * @param aClass
     * @return
     */
    public static Map<LogAttribute, Object> getAnnotationAttributes(JoinPoint jp, Class<? extends Annotation> aClass) {
        Annotation classLogAnnotation = getClassLogAnnotation(jp, Log.class);
        Annotation methodLogAnnotation = getMethodLogAnnotation(jp, aClass);
        // 找不到匹配的注解，当前方法上使用的@Log注解， 通过属性LogType进行调度过来的
        if (methodLogAnnotation == null) {
            methodLogAnnotation = getMethodLogAnnotation(jp, Log.class);
        }

        // 获取目标类的注解的属性值
        Map<String, Object> classAnnotationAttributes =
                classLogAnnotation != null ? getAnnotationAttributes(classLogAnnotation) : null;

        // 获取目标方法的注解的属性值
        Map<String, Object> methodAnnotationAttributes =
                methodLogAnnotation != null ? getAnnotationAttributes(methodLogAnnotation) : null;

        return combineAttributes(methodLogAnnotation, classAnnotationAttributes, methodAnnotationAttributes);
    }

    /**
     * 合并注解属性
     *
     * @param methodLogAnnotation 目标方法的注解
     * @param classAnnotationAttributes 目标类的注解的属性值
     * @param methodAnnotationAttributes 目标方法的注解的属性值
     * @return
     */
    private static Map<LogAttribute, Object> combineAttributes(Annotation methodLogAnnotation,
                                                               Map<String, Object> methodAnnotationAttributes,
                                                               Map<String, Object> classAnnotationAttributes) {
        if (MapUtils.isNotEmpty(methodAnnotationAttributes)) {
            Map<LogAttribute, Object> attributes = new HashMap<>();

            for (Map.Entry<String, Object> entry : methodAnnotationAttributes.entrySet()) {
                LogAttribute key = LogAttribute.valueOf(entry.getKey().toUpperCase());
                Object value;

                if (entry.getValue() != null && StringUtils.isBlank(entry.getValue().toString())
                        && MapUtils.isNotEmpty(classAnnotationAttributes)
                        && classAnnotationAttributes.get(entry.getKey()) != null) {
                    value = classAnnotationAttributes.get(entry.getKey());
                } else {
                    value = entry.getValue();
                }

                attributes.put(key, value);
            }

            // @insert..等注解是没有LogType属性的，需要去取元注解@Log的属性
            if (existMetaAnnotation(methodLogAnnotation, Log.class)) {
                attributes.put(LogAttribute.TYPE, getMetaAnnotation(methodLogAnnotation, Log.class).type());
            }

            return attributes;
        }
        return null;
    }

    /**
     * 获取目标类上的注解
     *
     * @param jp
     * @param aClass
     * @param <A>
     * @return
     */
    private static <A extends Annotation> A getClassLogAnnotation(JoinPoint jp, Class<A> aClass) {
        Class<?> targetClass = jp.getTarget().getClass();

        if (targetClass.isAnnotationPresent(aClass)) {
            //noinspection unchecked
            return targetClass.getAnnotation(aClass);
        }
        return null;
    }

    /**
     * 获取目标方法上的注解
     *
     * @param jp
     * @param aClass
     * @param <A>
     * @return
     */
    public static <A extends Annotation> A getMethodLogAnnotation(JoinPoint jp, Class<A> aClass) {
        Method method = ((MethodSignature) jp.getSignature()).getMethod();

        if (method.isAnnotationPresent(aClass)) {
            //noinspection unchecked
            return method.getAnnotation(aClass);
        }

        return null;
    }

    /**
     * 获取元注解
     *
     * @param annotation 注解
     * @param aClass    元注解class
     * @param <A>
     * @return
     */
    private static <A extends Annotation> A getMetaAnnotation(Annotation annotation, Class<A> aClass) {
        if (existMetaAnnotation(annotation, aClass)) {
            return annotation.annotationType().getAnnotation(aClass);
        }

        throw new UnsupportedOperationException("找不到此元注解：" + aClass.getSimpleName());
    }


    /**
     * 判断当前注解是否有元注解
     *
     * @param annotation 注解
     * @param aClass     元注解class
     * @param <A>
     * @return
     */
    private static <A extends Annotation> Boolean existMetaAnnotation(Annotation annotation, Class<A> aClass) {
        return annotation != null && annotation.annotationType().isAnnotationPresent(aClass);
    }

    /**
     * 获取注解属性值
     *
     * @param annotation
     * @return
     */
    private static Map<String, Object> getAnnotationAttributes(Annotation annotation) {
        return AnnotationUtils.getAnnotationAttributes(annotation, false, true);
    }
}
