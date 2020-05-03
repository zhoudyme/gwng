package me.zhoudongyu.service;

import me.zhoudongyu.core.consts.CourseProperty;
import me.zhoudongyu.core.consts.Term;
import me.zhoudongyu.entity.Record;

import java.util.List;

public interface RecordService {

    /**
     * 获取学生成绩信息
     *
     * @param studentNo      学号
     * @param studentName    姓名
     * @param year           学年
     * @param term           学期
     * @param queryType      查询类型
     * @param courseProperty 课程性质
     */
    List<Record> getStudentRecord(String studentNo, String studentName, String year, Term term, String queryType, CourseProperty courseProperty);
}
