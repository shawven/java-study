package web.supports.log.aspects;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;
import web.supports.log.SystemLogBuilder;
import web.supports.log.core.AbstractAspectDispatcher;
import web.supports.log.core.annotation.Log;

/**
 * Created with IntelliJ IDEA.
 *
 * @description:
 * @author: VAIO
 * @date: 2018-03-24 13:12
 */

@Component
public class CommonAspect extends AbstractAspectDispatcher<Log> {

    @Override
    public void before(JoinPoint jp) {

    }

    @Override
    public void after(JoinPoint jp) {
        // 操作成功记录基本的信息
        logger.log(getLogRecord(jp, new SystemLogBuilder(request)));
    }

    @Override
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        return super.around(pjp);
    }
}
