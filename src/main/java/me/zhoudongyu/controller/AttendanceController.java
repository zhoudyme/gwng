package me.zhoudongyu.controller;


import me.zhoudongyu.core.Session.MySessionContext;
import me.zhoudongyu.entity.Attendance;
import me.zhoudongyu.entity.OnlineUser;
import me.zhoudongyu.service.AttendanceService;
import me.zhoudongyu.utils.Base64Utils;
import me.zhoudongyu.utils.HttpResult;
import me.zhoudongyu.utils.Messages;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/attendance")
public class AttendanceController {

    @Resource(name = "attendanceService")
    private AttendanceService attendanceService;

    @RequestMapping("getAttendanceInfo.do")
    @ResponseBody
    public HttpResult getAttendanceInfo(HttpServletRequest req, HttpResult httpResult) {
        MySessionContext myc = MySessionContext.getInstance();
        HttpSession session = myc.getSession(req.getHeader("Cookie").substring(11));
        OnlineUser onlineUser = (OnlineUser) session.getAttribute("onlineUser");
        List<Attendance> attendanceList = null;
        try {
            attendanceList = attendanceService.getAttendanceInfo(onlineUser.getStudentNo(), Base64Utils.decode(onlineUser.getPassword()));
            return httpResult.setSuccess(true).setData(attendanceList).setMessage(Messages.QUERY_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return httpResult.setSuccess(false).setMessage(Messages.QUERY_FAILED);
        }
    }
}
