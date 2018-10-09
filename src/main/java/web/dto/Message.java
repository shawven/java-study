package web.dto;

import java.util.Date;

/**
 * @author FS
 * @date 2018-09-27 16:45
 */
public class Message {
    private Integer formUser;
    private Integer toUser;
    private String command;
    private Body body;
    private Date date;

    public Integer getFormUser() {
        return formUser;
    }

    public void setFormUser(Integer formUser) {
        this.formUser = formUser;
    }

    public Integer getToUser() {
        return toUser;
    }

    public void setToUser(Integer toUser) {
        this.toUser = toUser;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Message{" +
                "formUser=" + formUser +
                ", toUser=" + toUser +
                ", command='" + command + '\'' +
                ", body=" + body +
                ", date=" + date +
                '}';
    }

    public static class Body {
        private Integer id;
        private Integer type;
        private String subject;
        private String content;
        private String images;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getImages() {
            return images;
        }

        public void setImages(String images) {
            this.images = images;
        }

        @Override
        public String toString() {
            return "Body{" +
                    "id=" + id +
                    ", type=" + type +
                    ", subject='" + subject + '\'' +
                    ", content='" + content + '\'' +
                    ", images='" + images + '\'' +
                    '}';
        }
    }
}