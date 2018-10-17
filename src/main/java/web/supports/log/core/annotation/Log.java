package web.supports.log.core.annotation;


import web.supports.log.core.emun.LogType;

import java.lang.annotation.*;


/**
 * Created with IntelliJ IDEA.
 *
 * @description:
 * @author: VAIO
 * @date: 2018-03-24 11:10
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Log {
    String value() default "";

    String module() default "";

    LogType type() default LogType.COMMON;
}
