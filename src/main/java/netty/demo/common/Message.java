package netty.demo.common;

import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author FS
 * @date 2018-08-14 11:26
 */
public class Message implements Serializable {

    private static final long serialVersionUID = -1277516354082140218L;

    private static final AtomicLong ID_GENERATOR = new AtomicLong(0);

    private Long id;

    private Date date;

    private Object data;

    public Message() {
        id = ID_GENERATOR.getAndIncrement();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Message{");
        sb.append("id=").append(id);
        sb.append(", date=").append(date);
        sb.append(", data=").append(data);
        sb.append('}');
        return sb.toString();
    }
}
