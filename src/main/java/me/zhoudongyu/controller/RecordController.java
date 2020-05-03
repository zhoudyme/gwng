package me.zhoudongyu.controller;

import me.zhoudongyu.core.Session.MySessionContext;
import me.zhoudongyu.core.consts.CourseProperty;
import me.zhoudongyu.core.consts.Term;
import me.zhoudongyu.entity.OnlineUser;
import me.zhoudongyu.entity.Record;
import me.zhoudongyu.service.RecordService;
import me.zhoudongyu.utils.HttpResult;
import me.zhoudongyu.utils.Messages;
import org.jsoup.helper.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/record")
public class RecordController extends BaseController {

    @Resource(name = "recordService")
    private RecordService recordService;

    @RequestMapping("index.do")
    @ResponseBody
    public HttpResult getCourse(HttpServletRequest req, HttpResult httpResult) {
        String year = req.getParameter("year");
        String termString = req.getParameter("term");
        CourseProperty courseProperty = getEnumValue(req, CourseProperty.class, "courseProperty");
        String queryType = req.getParameter("queryType");
        List<Record> record = null;
        Term term = null;
        try {
            if (!StringUtil.isBlank(termString)) {
                if ("1".equals(termString)) {
                    term = Term.ONE;
                } else if ("2".equals(termString)) {
                    term = Term.TWO;
                }
            }
            MySessionContext myc = MySessionContext.getInstance();
            HttpSession session = myc.getSession(req.getHeader("Cookie").substring(11));
            OnlineUser onlineUser = (OnlineUser) session.getAttribute("onlineUser");
            record = recordService.getStudentRecord(onlineUser.getStudentNo(), onlineUser.getStudentName(), year, term, queryType, courseProperty);
            return httpResult.setSuccess(true).setData(record).setMessage(Messages.QUERY_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return httpResult.setSuccess(false).setMessage(Messages.QUERY_FAILED);
        }
    }
}
