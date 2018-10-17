package web.supports.log.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @description:
 * @author: VAIO
 * @date: 2018-03-25 21:08
 */
public class LogData {

    private String idName;

    private String objectName;

    private List<DataItem> changes = new ArrayList<>();

    public String getIdName() {
        return idName;
    }

    public void setIdName(String idName) {
        this.idName = idName;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public List<DataItem> getChanges() {
        return changes;
    }

    public void setChanges(List<DataItem> changes) {
        this.changes = changes;
    }

    @Override
    public String toString() {
        return "LogData{" +
                "idName='" + idName + '\'' +
                ", objectName='" + objectName + '\'' +
                ", changes=" + changes +
                '}';
    }
}
