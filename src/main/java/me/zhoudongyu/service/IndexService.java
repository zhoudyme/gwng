package me.zhoudongyu.service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface IndexService {

    /**
     * 登录
     *
     * @param studentNo 学号
     * @param password  密码
     */
    boolean login(String studentNo, String password);


    /**
     * 注销
     *
     * @param req HttpServletRequest
     */
    boolean logout(HttpServletRequest req);

    /**
     * 获取学生的个人信息
     *
     * @param studentNo 学号
     */
    Map<String, Object> getStudentInfo(String studentNo);
}
