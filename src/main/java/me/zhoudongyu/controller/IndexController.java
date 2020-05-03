package me.zhoudongyu.controller;

import me.zhoudongyu.config.UrlConfig;
import me.zhoudongyu.core.Session.MySessionContext;
import me.zhoudongyu.core.consts.RequestType;
import me.zhoudongyu.entity.OnlineUser;
import me.zhoudongyu.service.IndexService;
import me.zhoudongyu.utils.Base64Utils;
import me.zhoudongyu.utils.HttpClientHelper;
import me.zhoudongyu.utils.HttpResult;
import me.zhoudongyu.utils.Messages;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/index")
public class IndexController {

    @Resource(name = "indexService")
    private IndexService indexService;

    @RequestMapping("index.do")
    @ResponseBody
    public HttpResult Login(HttpServletRequest req, HttpResult httpResult, String studentNo, String password) {
        Boolean loginFlag = indexService.login(studentNo, password);
        OnlineUser onlineUser = null;
        String studentsInfoUrl = UrlConfig.STUDENTS_INFO_URL + "&paramterName=" + studentNo;
        if (loginFlag) {
            //获取学生资料必须先调用一次，后面调用才有效
            HttpClientHelper.loadDocumentFromURL(studentsInfoUrl, RequestType.GET, null);
            Map<String, Object> studentInfoMap = indexService.getStudentInfo(studentNo);
            onlineUser = new OnlineUser();
            onlineUser.setStudentNo(studentNo);
            onlineUser.setPassword(Base64Utils.encode(password));
            onlineUser.setStudentName(String.valueOf(studentInfoMap.get("xm")));
            onlineUser.setNation(String.valueOf(studentInfoMap.get("mz")));
            onlineUser.setSex(String.valueOf(studentInfoMap.get("xb")));
            onlineUser.setPoliticalStatus(String.valueOf(studentInfoMap.get("zzmm")));
            onlineUser.setAcademy(String.valueOf(studentInfoMap.get("yx")));
            onlineUser.setAdmissionTicket(String.valueOf(studentInfoMap.get("ksh")));
            onlineUser.setMajor(String.valueOf(studentInfoMap.get("zymc")));
            onlineUser.setSchoolingLength(String.valueOf(studentInfoMap.get("xz")));
            onlineUser.setStudentStatus(String.valueOf(studentInfoMap.get("xjzt")));
            onlineUser.setInSchool(String.valueOf(studentInfoMap.get("sfzx")));
            onlineUser.setEnrollmentDate(String.valueOf(studentInfoMap.get("rxnd")));
            onlineUser.setActualCredits(String.valueOf(studentInfoMap.get("yxxf")));
            onlineUser.setCreditsOnGraduation(String.valueOf(studentInfoMap.get("sxxf")));
            HttpSession session = req.getSession();
            String sessionId = session.getId();
            session.setAttribute("onlineUser", onlineUser);
            return httpResult.setSuccess(true).setData(sessionId).setMessage("登录成功");
        }
        return httpResult.setSuccess(false).setMessage("登录失败");
    }

    @RequestMapping("logout.do")
    @ResponseBody
    public HttpResult logout(HttpServletRequest req, HttpResult httpResult) {
        if (indexService.logout(req)) {
            return httpResult.setSuccess(true).setMessage("退出登录成功");
        } else {
            return httpResult.setSuccess(false).setMessage("退出登录失败");
        }
    }

    @RequestMapping("getStudentInfo.do")
    @ResponseBody
    public HttpResult getStudentInfo(HttpServletRequest req, HttpResult httpResult) {
        MySessionContext myc = MySessionContext.getInstance();
        HttpSession session = myc.getSession(req.getHeader("Cookie").substring(11));
        OnlineUser sessionOnlineUser = (OnlineUser) session.getAttribute("onlineUser");

        if (null != sessionOnlineUser) {
            OnlineUser onlineUser = new OnlineUser();
            onlineUser.setStudentNo(sessionOnlineUser.getStudentNo());
            onlineUser.setStudentName(sessionOnlineUser.getStudentName());
            onlineUser.setNation(sessionOnlineUser.getNation());
            onlineUser.setSex(sessionOnlineUser.getSex());
            onlineUser.setPoliticalStatus(sessionOnlineUser.getPoliticalStatus());
            onlineUser.setAcademy(sessionOnlineUser.getAcademy());
            onlineUser.setAdmissionTicket(sessionOnlineUser.getAdmissionTicket());
            onlineUser.setMajor(sessionOnlineUser.getMajor());
            onlineUser.setSchoolingLength(sessionOnlineUser.getSchoolingLength());
            onlineUser.setStudentStatus(sessionOnlineUser.getStudentStatus());
            onlineUser.setInSchool(sessionOnlineUser.getInSchool());
            onlineUser.setEnrollmentDate(sessionOnlineUser.getEnrollmentDate());
            onlineUser.setActualCredits(sessionOnlineUser.getActualCredits());
            onlineUser.setCreditsOnGraduation(sessionOnlineUser.getCreditsOnGraduation());
            return httpResult.setSuccess(true).setData(onlineUser).setMessage(Messages.QUERY_SUCCESS);
        } else {
            return httpResult.setSuccess(false).setMessage(Messages.QUERY_FAILED);

        }
    }
}
