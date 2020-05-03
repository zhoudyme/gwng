package me.zhoudongyu.utils;

import java.io.Serializable;


/**
 * 存放请求结果的工具类
 */
public class HttpResult implements Serializable {

    private static final long serialVersionUID = -5607520202412033547L;

    private Object data;    //返回的数据

    private Pagination pagination;    //返回的分页信息

    private boolean isSuccess;    //返回请求是否成功的标志

    private String message;    //返回的附加信息

    public Object getData() {
        return this.data;
    }

    public HttpResult setData(Object data) {
        this.data = data;
        return this;
    }

    public Pagination getPagination() {
        return this.pagination;
    }

    public HttpResult setPagination(Pagination pagination) {
        this.pagination = pagination;
        return this;
    }

    public boolean isSuccess() {
        return this.isSuccess;
    }

    public HttpResult setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
        return this;
    }

    public String getMessage() {
        return this.message;
    }

    public HttpResult setMessage(String message) {
        this.message = message;
        return this;
    }

    public HttpResult setMessage(Messages message) {
        this.message = message.desc;
        return this;
    }

    public HttpResult setMessage(Messages message, Exception e) {
        this.message = message.desc + e.getMessage();
        return this;
    }
}
