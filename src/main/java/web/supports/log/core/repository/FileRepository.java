package web.supports.log.core.repository;

import org.springframework.stereotype.Component;
import web.supports.log.core.LogRecord;
import web.supports.log.core.LogRepository;

/**
 * Created with IntelliJ IDEA.
 *
 * @description:
 * @author: VAIO
 * @date: 2018-03-24 15:43
 */
@Component("fileRepository")
public class FileRepository implements LogRepository {
    @Override
    public Object select(Object object) {
        System.out.println("文本查询中：未查询到相关信息");
        return null;
    }

    @Override
    public int insert(LogRecord record) {
        System.out.println("文本写入中：" + record);
        return 1;
    }

    @Override
    public void print(LogRecord logRecord) {
        System.out.println("文本打印中：" + logRecord);
    }
}
