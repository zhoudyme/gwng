package me.zhoudongyu.service;

import me.zhoudongyu.entity.Attendance;

import java.util.List;

public interface AttendanceService {

    /**
     * 获取考勤信息
     *
     * @param studentNo 学号
     * @param password  密码
     */
    List<Attendance> getAttendanceInfo(String studentNo, String password);
}
