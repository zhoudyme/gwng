package me.zhoudongyu.entity;

/**
 * 校园卡实体类
 */
public class CampusCard {
    private String studentNo;    //卡号
    private String state;    //卡状态
    private String balance;    //卡余额
    private String yesterdayConsume;    //昨日消费
    private String weekConsume;    //一周消费
    private String endTime;    //统计结束时间

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getYesterdayConsume() {
        return yesterdayConsume;
    }

    public void setYesterdayConsume(String yesterdayConsume) {
        this.yesterdayConsume = yesterdayConsume;
    }

    public String getWeekConsume() {
        return weekConsume;
    }

    public void setWeekConsume(String weekConsume) {
        this.weekConsume = weekConsume;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
