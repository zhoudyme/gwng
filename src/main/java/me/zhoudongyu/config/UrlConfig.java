package me.zhoudongyu.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Url信息配置
 */
@Component
public class UrlConfig {
    public static String LOGIN_URL = null;    //数字南商Url
    public static String STUDENTS_INFO_URL = null;   //学生个人资料Url
    public static String CAMPUSCARD_INFO_URL = null;    //校园卡资料Url
    public static String MAIN_URL = null;    //教务系统Url
    public static String COURSE_URL = null;    //课程表Url
    public static String RECORD_URL = null;    //学生成绩Url
    public static String MY_LIBRARY_INFO_URL = null;    //我的图书借阅Url
    public static String BORROW_LIST_URL = null;    //图书借阅排行Url
    public static String ATTENDANCE_URL = null;    //图书详情Url
    public static String CAMPUSPHONE_URL = null;    //考勤信息Url
    public static String BOOKDETAIL_URL = null;    //校园电话Url
    public static String ELECTIVES_URL = null;    //选课情况Url

    @Value("${loginUrl}")
    private void setLoginUrl(String loginUrl) {
        UrlConfig.LOGIN_URL = loginUrl;
    }

    @Value("${studentsInfoUrl}")
    private void setStudentsInfoUrl(String studentsInfoUrl) {
        UrlConfig.STUDENTS_INFO_URL = studentsInfoUrl;
    }

    @Value("${campusCardInfoUrl}")
    public void setCampusCardInfoUrl(String campusCardInfoUrl) {
        UrlConfig.CAMPUSCARD_INFO_URL = campusCardInfoUrl;
    }

    @Value("${mainUrl}")
    private void setMainUrl(String mainUrl) {
        UrlConfig.MAIN_URL = mainUrl;
    }

    @Value("${courseUrl}")
    private void setCourseUrl(String courseUrl) {
        UrlConfig.COURSE_URL = courseUrl;
    }

    @Value("${recordUrl}")
    private void setInfoUrl(String recordUrl) {
        UrlConfig.RECORD_URL = recordUrl;
    }

    @Value("${myLibraryInfoUrl}")
    public void setMyLibraryInfoUrl(String myLibraryInfoUrl) {
        UrlConfig.MY_LIBRARY_INFO_URL = myLibraryInfoUrl;
    }

    @Value("${borrowListUrl}")
    public void setBorrowListUrl(String borrowListUrl) {
        UrlConfig.BORROW_LIST_URL = borrowListUrl;
    }

    @Value("${attendanceUrl}")
    public void setAttendanceUrl(String attendanceUrl) {
        UrlConfig.ATTENDANCE_URL = attendanceUrl;
    }

    @Value("${campusPhoneUrl}")
    public void setCampusPhoneUrl(String campusPhoneUrl) {
        UrlConfig.CAMPUSPHONE_URL = campusPhoneUrl;
    }

    @Value("${bookDetailUrl}")
    public void setBookDetailUrl(String bookDetailUrl) {
        UrlConfig.BOOKDETAIL_URL = bookDetailUrl;
    }

    @Value("${electivesUrl}")
    public void setElectivesUrl(String electivesUrl) {
        UrlConfig.ELECTIVES_URL = electivesUrl;
    }
}
