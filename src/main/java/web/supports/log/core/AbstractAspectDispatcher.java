package web.supports.log.core;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import web.supports.log.aspects.DeleteAspect;
import web.supports.log.aspects.InsertAspect;
import web.supports.log.aspects.UpdateAspect;
import web.supports.log.core.annotation.Log;
import web.supports.log.core.emun.LogType;
import web.supports.log.core.utils.LogAnnotationUtils;

import java.lang.annotation.Annotation;

/**
 * Created with IntelliJ IDEA.
 *
 * @description:
 * @author: VAIO
 * @date: 2018-03-25 18:10
 */
@Aspect
public class AbstractAspectDispatcher<T extends Annotation> extends AbstractAspect<T> {

    @Autowired
    private InsertAspect insertAspect;
    @Autowired
    private UpdateAspect updateAspect;
    @Autowired
    private DeleteAspect deleteAspect;

    @Before("commonPointcut()")
    public void dispatchBefore(JoinPoint jp) {
        if (isDispatch(jp)) {
            Log log = getLogAnnotation(jp);
            switch (log.type()) {
                case INSERT:
                    insertAspect.before(jp);
                    break;
                case UPDATE:
                    updateAspect.before(jp);
                    break;
                case DELETE:
                    deleteAspect.before(jp);
                    break;
                default:
            }
            return;
        }
        before(jp);
    }

    @After("commonPointcut()")
    public void dispatchAfter(JoinPoint jp) {
        if (isDispatch(jp)) {
            Log log = getLogAnnotation(jp);

            switch (log.type()) {
                case INSERT:
                    insertAspect.after(jp);
                    break;
                case UPDATE:
                    updateAspect.after(jp);
                    break;
                case DELETE:
                    deleteAspect.after(jp);
                    break;
                default:
            }
            return;
        }
        after(jp);
    }

    @Around("commonPointcut()")
    public Object dispatchAround(ProceedingJoinPoint pjp) throws Throwable {
        if (isDispatch(pjp)) {
            Log log = getLogAnnotation(pjp);

            switch (log.type()) {
                case INSERT:
                    return insertAspect.around(pjp);
                case UPDATE:
                    return updateAspect.around(pjp);
                case DELETE:
                    return deleteAspect.around(pjp);
                default:
                    return pjp.proceed();
            }
        }
        return around(pjp);
    }

    private Boolean isDispatch(JoinPoint jp) {
        Log log = getLogAnnotation(jp);
        return log.type() != LogType.COMMON;
    }

    private Log getLogAnnotation(JoinPoint jp) {
        return LogAnnotationUtils.getMethodLogAnnotation(jp, Log.class);
    }

    @Override
    public void before(JoinPoint jp) {}

    @Override
    public void after(JoinPoint jp) {}

    @Override
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        return pjp.proceed();
    }
}
