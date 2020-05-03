package me.zhoudongyu.entity;

/**
 * 学生成绩实体类
 */
public class Record {
    private String studentNo;    //学号
    private String courseCode;    //课程代码
    private String courseName;    //课程名称
    private String courseProperty;    //课程性质
    private String courseType;    //课程归属
    private String courseCredit;    //学分
    private String GPA;    //绩点
    private String courseScore;    //成绩
    private String minorFlag;    //辅修标记
    private String resitScore;    //补考成绩
    private String retakeScore;    //重修成绩
    private String courseAcademy;    //开课学院

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseProperty() {
        return courseProperty;
    }

    public void setCourseProperty(String courseProperty) {
        this.courseProperty = courseProperty;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public String getCourseCredit() {
        return courseCredit;
    }

    public void setCourseCredit(String courseCredit) {
        this.courseCredit = courseCredit;
    }

    public String getGPA() {
        return GPA;
    }

    public void setGPA(String GPA) {
        this.GPA = GPA;
    }

    public String getCourseScore() {
        return courseScore;
    }

    public void setCourseScore(String courseScore) {
        this.courseScore = courseScore;
    }

    public String getMinorFlag() {
        return minorFlag;
    }

    public void setMinorFlag(String minorFlag) {
        this.minorFlag = minorFlag;
    }

    public String getResitScore() {
        return resitScore;
    }

    public void setResitScore(String resitScore) {
        this.resitScore = resitScore;
    }

    public String getRetakeScore() {
        return retakeScore;
    }

    public void setRetakeScore(String retakeScore) {
        this.retakeScore = retakeScore;
    }

    public String getCourseAcademy() {
        return courseAcademy;
    }

    public void setCourseAcademy(String courseAcademy) {
        this.courseAcademy = courseAcademy;
    }
}
