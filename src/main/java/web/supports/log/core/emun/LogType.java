package web.supports.log.core.emun;

/**
 * Created with IntelliJ IDEA.
 *
 * @description:
 * @author: VAIO
 * @date: 2018-03-24 11:10
 */
public enum LogType {
    /**
     * 普通日志
     */
    COMMON(0, "普通日志"),

    /**
     * 插入数据日志
     */
    INSERT(1, "插入数据日志"),

    /**
     * 更新数据日志
     */
    UPDATE(2, "更新数据日志"),

    /**
     * 删除数据日志
     */
    DELETE(3, "删除数据日志");

    private int type;

    private String typeName;

    LogType(int type, String typeName) {
        this.type = type;
        this.typeName = typeName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public static LogType typeOf(int type) {
        LogType[] logTypes = values();
        for (LogType logType : logTypes) {
            if (logType.getType() == type) {
                return logType;
            }
        }
        return null;
    }
}
