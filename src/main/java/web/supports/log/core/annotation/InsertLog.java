package web.supports.log.core.annotation;

import web.supports.log.core.emun.LogType;
import java.lang.annotation.*;


/**
 * Created with IntelliJ IDEA.
 *
 * @description:
 * @author: VAIO
 * @date: 2018-03-24 11:12
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Log(type = LogType.INSERT)
@Inherited
@Documented
public @interface InsertLog {
    String value() default "";
    String module() default "";
}
