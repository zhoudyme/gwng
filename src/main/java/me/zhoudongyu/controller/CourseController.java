package me.zhoudongyu.controller;

import me.zhoudongyu.core.Session.MySessionContext;
import me.zhoudongyu.core.consts.Term;
import me.zhoudongyu.entity.Course;
import me.zhoudongyu.entity.OnlineUser;
import me.zhoudongyu.service.CourseService;
import me.zhoudongyu.utils.HttpResult;
import me.zhoudongyu.utils.Messages;
import org.jsoup.helper.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/course")
public class CourseController {

    @Resource(name = "courseService")
    private CourseService courseService;

    @RequestMapping("index.do")
    @ResponseBody
    public HttpResult getCourse(HttpServletRequest req, HttpResult httpResult) {
        String year = req.getParameter("year");
        String termString = req.getParameter("term");
        List<ArrayList<Course>> courses = null;
        Term term = null;
        try {
            if (!StringUtil.isBlank(termString)) {
                if ("0".equals(termString)) {
                    term = Term.ONE;
                } else if ("1".equals(termString)) {
                    term = Term.TWO;
                }
            }
            MySessionContext myc = MySessionContext.getInstance();
            HttpSession session = myc.getSession(req.getHeader("Cookie").substring(11));
            OnlineUser onlineUser = (OnlineUser) session.getAttribute("onlineUser");
            courses = courseService.getStudentCourse(onlineUser.getStudentNo(), onlineUser.getStudentName(), year, term);
            return httpResult.setSuccess(true).setData(courses).setMessage(Messages.QUERY_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return httpResult.setSuccess(false).setMessage(Messages.QUERY_FAILED);
        }
    }

    @RequestMapping("getCurrentTerm.do")
    @ResponseBody
    public HttpResult getCurrentTerm(HttpServletRequest req, HttpResult httpResult) {
        try {
            return httpResult.setSuccess(true).setData(courseService.getRecentYear(req)).setMessage(Messages.QUERY_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return httpResult.setSuccess(false).setMessage(Messages.QUERY_FAILED);
        }
    }
}
