package web.supports.log.domain;


import web.supports.log.core.LogRecord;

import java.io.Serializable;
import java.util.Date;


public class SystemLog implements LogRecord, Serializable{
    /**
     * 主键ID
     */

    private Integer id;

    /**
     * 用户ID
     */
    private Integer userid;

    /**
     * 用户名
     */
    private String username;

    /**
     * 操作模块
     */
    private String opermodule;

    /**
     * 操作类型
     */
    private Integer opertype;

    /**
     * 操作内容
     */
    private String opertext;

    /**
     * 操作时间
     */
    private Date opertime;

    /**
     * 系统类型 1-商户后台 2-系统后台
     */
    private Integer systype;


    /**
     * 请求路径
     */
    private String requestUrl;

    /**
     * 请求参数
     */
    private String requestParams;

    /**
     * 操作ip
     */
    private String ip;

    /**
     * 操作地址
     */
    private String address;


    /**
     * 备注
     */
    private String remark;

    /**
     * 数据
     */
    private String data;

    /**
     * 获取主键ID
     *
     * @return id - 主键ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置主键ID
     *
     * @param id 主键ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取用户ID
     *
     * @return userid - 用户ID
     */
    public Integer getUserid() {
        return userid;
    }

    /**
     * 设置用户ID
     *
     * @param userid 用户ID
     */
    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    /**
     * 获取用户名
     *
     * @return username - 用户名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置用户名
     *
     * @param username 用户名
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    /**
     * 获取操作模块
     *
     * @return opermodule - 操作模块
     */
    public String getOpermodule() {
        return opermodule;
    }

    /**
     * 设置操作模块
     *
     * @param opermodule 操作模块
     */
    public void setOpermodule(String opermodule) {
        this.opermodule = opermodule == null ? null : opermodule.trim();
    }

    /**
     * 获取操作类型
     *
     * @return opertype - 操作类型
     */
    public Integer getOpertype() {
        return opertype;
    }

    /**
     * 设置操作类型
     *
     * @param opertype 操作类型
     */
    public void setOpertype(Integer opertype) {
        this.opertype = opertype;
    }

    /**
     * 获取操作内容
     *
     * @return opertext - 操作内容
     */
    public String getOpertext() {
        return opertext;
    }

    /**
     * 设置操作内容
     *
     * @param opertext 操作内容
     */
    public void setOpertext(String opertext) {
        this.opertext = opertext == null ? null : opertext.trim();
    }

    /**
     * 获取操作时间
     *
     * @return opertime - 操作时间
     */
    public Date getOpertime() {
        return opertime;
    }

    /**
     * 设置操作时间
     *
     * @param opertime 操作时间
     */
    public void setOpertime(Date opertime) {
        this.opertime = opertime;
    }

    /**
     * 获取系统类型 1-商户后台 2-系统后台
     *
     * @return systype - 系统类型 1-商户后台 2-系统后台
     */
    public Integer getSystype() {
        return systype;
    }

    /**
     * 设置系统类型 1-商户后台 2-系统后台
     *
     * @param systype 系统类型 1-商户后台 2-系统后台
     */
    public void setSystype(Integer systype) {
        this.systype = systype;
    }

    /**
     * 获取请求路径
     *
     * @return
     */
    public String getRequestUrl() {
        return requestUrl;
    }

    /**
     * 设置请求路径
     *
     * @param requestUrl
     */
    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    /**
     * 获取请求参数
     *
     * @return
     */
    public String getRequestParams() {
        return requestParams;
    }

    /**
     * 设置请求参数
     *
     * @param requestParams
     */
    public void setRequestParams(String requestParams) {
        this.requestParams = requestParams;
    }

    /**
     * 获取操作ip
     *
     * @return ip - 操作ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * 设置操作ip
     *
     * @param ip 操作ip
     */
    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    /**
     * 获取操作地址
     *
     * @return address - 操作地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置操作地址
     *
     * @param address 操作地址
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * 获取操作数据
     *
     * @return data
     */
    public String getData() {
        return data;
    }

    /**
     * 设置操作数据
     *
     * @param data 操作数据
     */
    public void setData(String data) {
        this.data = data == null ? null : data.trim();
    }

    /**
     * 获取备注
     *
     * @return 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注
     *
     * @param remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("SystemLog{");
        sb.append("id=").append(id);
        sb.append(", userid=").append(userid);
        sb.append(", username='").append(username).append('\'');
        sb.append(", opermodule='").append(opermodule).append('\'');
        sb.append(", opertype=").append(opertype);
        sb.append(", opertext='").append(opertext).append('\'');
        sb.append(", opertime=").append(opertime);
        sb.append(", systype=").append(systype);
        sb.append(", ip='").append(ip).append('\'');
        sb.append(", address='").append(address).append('\'');
        sb.append(", data='").append(data).append('\'');
        sb.append('}');
        return sb.toString();
    }
}