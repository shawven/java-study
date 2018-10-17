package web.supports.log.core;

/**
 * Created with IntelliJ IDEA.
 *
 * @description:
 * @author: VAIO
 * @date: 2018-03-24 11:28
 */
public interface Logger<T> {

    /**
     * 保存日志
     *
     * @param record
     * @return
     */
    void log(LogRecord record);

    /**
     * 获取slf4
     *
     * @return
     */
    public org.slf4j.Logger getSlf4jLogger();
}
