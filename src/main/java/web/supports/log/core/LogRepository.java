package web.supports.log.core;

/**
 * Created with IntelliJ IDEA.
 *
 * @description:
 * @author: VAIO
 * @date: 2018-03-24 11:49
 */
public interface LogRepository {


    Object select(Object object);

    /**
     * 存储日志记录
     *
     * @param record
     * @return
     */
    int insert(LogRecord record);

    /**
     * 打印日志记录
     *
     * @param logRecord
     */
    void print(LogRecord logRecord);
}
