package me.zhoudongyu.service.impl;

import me.zhoudongyu.config.UrlConfig;
import me.zhoudongyu.core.consts.RequestType;
import me.zhoudongyu.entity.Attendance;
import me.zhoudongyu.service.AttendanceService;
import me.zhoudongyu.utils.HttpClientHelper;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service("attendanceService")
public class AttendanceServiceImpl implements AttendanceService {
    @Override
    public List<Attendance> getAttendanceInfo(String studentNo, String password) {
        List<Attendance> attendanceList = null;
        //如果学号或密码无效，则直接返回null
        if (StringUtil.isBlank(studentNo) || StringUtils.isBlank(password)) {
            return null;
        }
        //拼接考勤信息查询的Url
        String attendanceInfoUrl = UrlConfig.ATTENDANCE_URL + "?stuid=" + studentNo + "&verify=" + password;
        try {
            //发起Get请求，查询考勤信息
            Document doc = HttpClientHelper.loadDocumentFromURL(attendanceInfoUrl, RequestType.GET, null);
            Elements elements = doc.getElementsByTag("tbody").select("tr");
            attendanceList = new ArrayList<>();
            //处理查询结果，放入Attendance对象中
            for (int i = elements.size() - 1; i >= 0; i--) {
                Attendance attendance = new Attendance();
                attendance.setCourseName(elements.get(i).select("td:eq(0)").text());
                attendance.setClassRoom(elements.get(i).select("td:eq(1)").text());
                attendance.setCheckInTime(elements.get(i).select("td:eq(2)").text());
                attendance.setState(elements.get(i).select("td:eq(7)").text());
                attendanceList.add(attendance);
            }
            return attendanceList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
