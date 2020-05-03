package me.zhoudongyu.service.impl;

import me.zhoudongyu.config.UrlConfig;
import me.zhoudongyu.core.consts.RequestType;
import me.zhoudongyu.entity.CampusCard;
import me.zhoudongyu.service.CampusCardService;
import me.zhoudongyu.utils.HttpClientHelper;
import me.zhoudongyu.utils.JsonUtils;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("campusCardService")
public class CampusCardServiceImpl implements CampusCardService {
    @Override
    public CampusCard getCampusCardInfo(String studentNo) {
        CampusCard campusCard = null;
        //如果学号无效，则直接返回null
        if (StringUtil.isBlank(studentNo)) {
            return null;
        }
        //拼接校园卡查询的Url
        String campusCardInfoUrl = UrlConfig.CAMPUSCARD_INFO_URL + "&paramterName=" + studentNo;
        try {
            //发起Get请求，查询校园卡信息
            Document doc = HttpClientHelper.loadDocumentFromURL(campusCardInfoUrl, RequestType.GET, null);
            String bodyText = doc.getElementsByTag("body").get(0).text();
            //处理查询结果，放入CampusCard对象中
            Map<String, Object> campusCardInfo = JsonUtils.toMap(bodyText);
            campusCard = new CampusCard();
            campusCard.setStudentNo(String.valueOf(campusCardInfo.get("xgh")));
            campusCard.setState(String.valueOf(campusCardInfo.get("kzt")));
            campusCard.setBalance(String.valueOf(campusCardInfo.get("kye")));
            campusCard.setYesterdayConsume(String.valueOf(campusCardInfo.get("zrxf")));
            campusCard.setWeekConsume(String.valueOf(campusCardInfo.get("yzxf")));
            campusCard.setEndTime(String.valueOf(campusCardInfo.get("tjjssj")));
            return campusCard;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
