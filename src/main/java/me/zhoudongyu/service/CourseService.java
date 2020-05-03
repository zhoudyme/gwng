package me.zhoudongyu.service;

import me.zhoudongyu.core.consts.Term;
import me.zhoudongyu.entity.Course;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public interface CourseService {

    /**
     * 获得课程表信息
     *
     * @param studentNo   学号
     * @param studentName 姓名
     * @param year        学年
     * @param term        学期
     */
    List<ArrayList<Course>> getStudentCourse(String studentNo, String studentName, String year, Term term);


    /**
     * 获取学生的学年信息
     *
     * @param req HttpServletRequest
     */
    List<String> getRecentYear(HttpServletRequest req);
}
