package web.supports.log.core.repository;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import web.supports.log.core.LogException;
import web.supports.log.core.LogRecord;
import web.supports.log.core.LogRepository;
import web.supports.utils.ReflectHelper;

import static web.supports.log.core.utils.LogRecordUtils.getObjectId;

/**
 * Created with IntelliJ IDEA.
 *
 * @description:
 * @author: VAIO
 * @date: 2018-03-24 11:57
 */
@Component("databaseRepository")
public class DatabaseRepository implements LogRepository {

    @Override
    public int insert(LogRecord record) {
        // todo
        return 1;
    }

    @Override
    public void print(LogRecord logRecord) {
        System.out.println("数据库不支持打印");
    }

    public Boolean storeDataBase(LogRecord record) {
        System.out.println("保存到数据库成功: " + record);
        return true;
    }

    /**
     * 根据要修改的对象的主键查找被修改以前的记录
     *
     * @param obj
     * @return
     */
    @Override
    public Object select(Object obj) {
        try {
            String primaryKeyName = getObjectId(obj);
            if (StringUtils.isNotBlank(primaryKeyName)) {
                Object primaryKeyValue = ReflectHelper.getProperty(obj, primaryKeyName);
                if (primaryKeyValue != null) {
                    // todo
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new LogException("查找原纪录是失败", e);
        }
        return null;
    }
}
