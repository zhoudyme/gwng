package me.zhoudongyu.entity;

/**
 * 考勤情况实体类
 */
public class Attendance {

    private String courseName;    //课程名称
    private String classRoom;    //教师
    private String checkInTime;    //点名时间
    private String state;    //考勤状态

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(String classRoom) {
        this.classRoom = classRoom;
    }

    public String getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(String checkInTime) {
        this.checkInTime = checkInTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
