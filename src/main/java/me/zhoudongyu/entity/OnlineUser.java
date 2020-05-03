package me.zhoudongyu.entity;

/**
 * 登录用户实体类
 */
public class OnlineUser {
    private String studentNo;    //学号
    private String password;    //密码
    private String studentName;   //姓名
    private String nation;    //民族
    private String sex;    //性别
    private String politicalStatus;    //政治面貌
    private String academy;    //学院
    private String admissionTicket;    //考生号
    private String major;    //专业
    private String schoolingLength;    //学制
    private String studentStatus;    //学籍状态
    private String inSchool;    //是否在校
    private String enrollmentDate;    //入学日期
    private String actualCredits;    //以修学分
    private String creditsOnGraduation;    //应修学分

    public String getStudentNo() {
        return studentNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPoliticalStatus() {
        return politicalStatus;
    }

    public void setPoliticalStatus(String politicalStatus) {
        this.politicalStatus = politicalStatus;
    }

    public String getAcademy() {
        return academy;
    }

    public void setAcademy(String academy) {
        this.academy = academy;
    }

    public String getAdmissionTicket() {
        return admissionTicket;
    }

    public void setAdmissionTicket(String admissionTicket) {
        this.admissionTicket = admissionTicket;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getSchoolingLength() {
        return schoolingLength;
    }

    public void setSchoolingLength(String schoolingLength) {
        this.schoolingLength = schoolingLength;
    }

    public String getStudentStatus() {
        return studentStatus;
    }

    public void setStudentStatus(String studentStatus) {
        this.studentStatus = studentStatus;
    }

    public String getInSchool() {
        return inSchool;
    }

    public void setInSchool(String inSchool) {
        this.inSchool = inSchool;
    }

    public String getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(String enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public String getActualCredits() {
        return actualCredits;
    }

    public void setActualCredits(String actualCredits) {
        this.actualCredits = actualCredits;
    }

    public String getCreditsOnGraduation() {
        return creditsOnGraduation;
    }

    public void setCreditsOnGraduation(String creditsOnGraduation) {
        this.creditsOnGraduation = creditsOnGraduation;
    }
}
