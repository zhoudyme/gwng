package me.zhoudongyu.service;

import me.zhoudongyu.core.consts.Term;
import me.zhoudongyu.entity.Course;

import java.util.List;

public interface ElectivesService {

    /**
     * 获取选课情况信息
     *
     * @param studentNo   学号
     * @param studentName 姓名
     * @param year        学年
     * @param term        学期
     */
    List<Course> getElectivesInfo(String studentNo, String studentName, String year, Term term);

}
