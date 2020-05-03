package me.zhoudongyu.utils;


/**
 * 查询结果信息的枚举类
 */

public enum Messages {

    QUERY_SUCCESS("查询成功"), QUERY_FAILED("查询失败");

    public String desc;

    Messages(String desc) {
        this.desc = desc;
    }
}
