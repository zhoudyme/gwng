package me.zhoudongyu.service.impl;

import me.zhoudongyu.config.UrlConfig;
import me.zhoudongyu.core.consts.RequestType;
import me.zhoudongyu.entity.CampusPhone;
import me.zhoudongyu.service.CampusPhoneService;
import me.zhoudongyu.utils.HttpClientHelper;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("campusPhoneService")
public class CampusPhoneServiceImpl implements CampusPhoneService {
    @Override
    public List<CampusPhone> getCampusPhoneInfo() {
        //获取校园电话查询的Url
        String campusPhoneUrl = UrlConfig.CAMPUSPHONE_URL;
        List<CampusPhone> campusPhoneList;
        try {
            //发起Get请求，查询校园电话信息
            Document doc = HttpClientHelper.loadDocumentFromURL(campusPhoneUrl, RequestType.GET, null);
            Elements campusPhoneElements = doc.getElementsByTag("table").get(1).select("tr");
            campusPhoneElements.remove(0);    //移除提示信息
            //处理查询结果，放入CampusPhone对象中
            campusPhoneList = new ArrayList<>();
            for (Element e : campusPhoneElements) {
                CampusPhone campusPhone = new CampusPhone();
                campusPhone.setUnitName(e.select("span").get(0).text());
                campusPhone.setPhone(e.select("span").get(1).text());
                campusPhoneList.add(campusPhone);
            }
            return campusPhoneList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
