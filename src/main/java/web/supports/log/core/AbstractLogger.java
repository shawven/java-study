package web.supports.log.core;

import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 *
 * @description:
 * @author: VAIO
 * @date: 2018-03-24 15:19
 */
public abstract class AbstractLogger<T> implements Logger<T> {

    private org.slf4j.Logger slf4jLogger =  LoggerFactory.getLogger(getClass());


    @Resource(name = "databaseRepository")
    protected LogRepository dataBaseRepository;

    @Resource(name = "fileRepository")
    protected LogRepository fileRepository;

    @Override
    public void log(LogRecord record) {
        dataBaseRepository.insert(record) ;
    }

    @Override
    public org.slf4j.Logger getSlf4jLogger() {
        return slf4jLogger;
    }
}
