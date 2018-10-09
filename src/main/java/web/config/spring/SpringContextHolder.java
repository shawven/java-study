package web.config.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.Objects;

/**
 * @author FS
 * @date 2018-09-30 10:32
 */
@Configuration
public class SpringContextHolder implements ApplicationContextAware {

    private static org.springframework.context.ApplicationContext ApplicationContext;

    private static SpringContextHolder SpringContext;

    public SpringContextHolder(){}

    public static SpringContextHolder getInstance(){
        if(SpringContext == null) {
            SpringContext = new SpringContextHolder();
        }
        return SpringContext;
    }

    @Override
    public void setApplicationContext(org.springframework.context.ApplicationContext applicationContext) throws BeansException {
        ApplicationContext = applicationContext;
    }

    public static org.springframework.context.ApplicationContext getApplicationContext() {
        return ApplicationContext;
    }

    public static Object getBean(String beanName) {
        return getApplicationContext().getBean(beanName);
    }
    
    public static Locale getLocale() {
        RequestAttributes attributes = Objects.requireNonNull(RequestContextHolder.getRequestAttributes());
        HttpServletRequest request = ((ServletRequestAttributes)attributes).getRequest();
    	return (Locale)request.getSession().getAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME);
    }
    
}
