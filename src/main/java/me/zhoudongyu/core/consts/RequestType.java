package me.zhoudongyu.core.consts;

/**
 * @author Steve
 * @date 2018-3-13 11:16
 * Http请求类型枚举类
 */
public enum RequestType {

    GET("GET"),
    POST("POST");

    public String desc;

    RequestType(String desc) {
        this.desc = desc;
    }

}