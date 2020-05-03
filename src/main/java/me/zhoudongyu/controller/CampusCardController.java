package me.zhoudongyu.controller;

import me.zhoudongyu.core.Session.MySessionContext;
import me.zhoudongyu.entity.CampusCard;
import me.zhoudongyu.entity.OnlineUser;
import me.zhoudongyu.service.CampusCardService;
import me.zhoudongyu.utils.HttpResult;
import me.zhoudongyu.utils.Messages;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/campusCard")
public class CampusCardController extends BaseController {

    @Resource(name = "campusCardService")
    private CampusCardService campusCardService;

    @RequestMapping("getCampusCardInfo.do")
    @ResponseBody
    public HttpResult getCampusCardInfo(HttpServletRequest req, HttpResult httpResult) {
        MySessionContext myc = MySessionContext.getInstance();
        HttpSession session = myc.getSession(req.getHeader("Cookie").substring(11));
        OnlineUser onlineUser = (OnlineUser) session.getAttribute("onlineUser");
        CampusCard campusCard = null;
        try {
            campusCard = campusCardService.getCampusCardInfo(onlineUser.getStudentNo());
            return httpResult.setSuccess(true).setData(campusCard).setMessage(Messages.QUERY_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return httpResult.setSuccess(false).setMessage(Messages.QUERY_FAILED);
        }
    }
}
