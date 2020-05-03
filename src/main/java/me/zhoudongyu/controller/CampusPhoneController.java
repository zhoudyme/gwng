package me.zhoudongyu.controller;


import me.zhoudongyu.entity.CampusPhone;
import me.zhoudongyu.service.CampusPhoneService;
import me.zhoudongyu.utils.HttpResult;
import me.zhoudongyu.utils.Messages;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/campusPhone")
public class CampusPhoneController {

    @Resource(name = "campusPhoneService")
    private CampusPhoneService campusPhoneService;

    @RequestMapping("getCampusPhoneInfo.do")
    @ResponseBody
    public HttpResult getCampusPhoneInfo(HttpServletRequest req, HttpResult httpResult) {
        List<CampusPhone> campusPhoneList = null;
        try {
            campusPhoneList = campusPhoneService.getCampusPhoneInfo();
            return httpResult.setSuccess(true).setData(campusPhoneList).setMessage(Messages.QUERY_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return httpResult.setSuccess(false).setMessage(Messages.QUERY_FAILED);
        }
    }
}
