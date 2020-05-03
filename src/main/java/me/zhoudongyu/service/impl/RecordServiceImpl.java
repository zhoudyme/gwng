package me.zhoudongyu.service.impl;

import me.zhoudongyu.config.UrlConfig;
import me.zhoudongyu.core.consts.CourseProperty;
import me.zhoudongyu.core.consts.Term;
import me.zhoudongyu.entity.Record;
import me.zhoudongyu.service.RecordService;
import me.zhoudongyu.utils.HttpClientHelper;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service("recordService")
public class RecordServiceImpl implements RecordService {
    private final String mainUrl = UrlConfig.MAIN_URL;    //获取教务系统主页的Url
    private final String recordUrl = UrlConfig.RECORD_URL;    //获取成绩查询的Url

    @Override
    public List<Record> getStudentRecord(String studentNo, String studentName, String year, Term term, String queryType, CourseProperty courseProperty) {
        HttpClient httpClient = HttpClientHelper.getHttpClient();
        HttpGet httpGet = HttpClientHelper.getHttpGet(mainUrl);
        HttpPost httpPost = null;
        //填充需要提交的表单
        List<NameValuePair> nvps = new ArrayList<>();
        String recordUrl = null;
        HttpResponse response = null;
        Document document = null;
        ArrayList<Record> records = null;
        try {
            //拼接成绩查询的Url
            recordUrl = this.recordUrl + "?xh=" + studentNo + "&xm=" + studentName + "&gnmkdm=N121605";
            httpClient.execute(httpGet);    //登录教务系统首页，获取首页Cookie
            httpGet.abort();   //终止请求
            httpGet = HttpClientHelper.getHttpGet(recordUrl);
            //登录查询成绩的页面，获取页面信息
            response = httpClient.execute(httpGet);
            document = Jsoup.parse(EntityUtils.toString(response.getEntity(), Consts.UTF_8));
            httpGet.abort();
            httpPost = HttpClientHelper.getHttpPost(recordUrl);
            String __VIEWSTATE = document.select("[name=__VIEWSTATE]").attr("value");    //获得__VIEWSTATE信息
            String __VIEWSTATEGENERATOR = document.select("[name=__VIEWSTATEGENERATOR]").attr("value");    //获得__VIEWSTATEGENERATOR信息
            nvps.add(new BasicNameValuePair("__VIEWSTATE", __VIEWSTATE));     //填充__VIEWSTATE
            nvps.add(new BasicNameValuePair("__VIEWSTATEGENERATOR", __VIEWSTATEGENERATOR));     //填充__VIEWSTATEGENERATOR
            nvps.add(new BasicNameValuePair("ddlXN", year));     //填充学年信息
            nvps.add(new BasicNameValuePair("ddlXQ", null != term ? term.desc : ""));     //填充学期信息
            nvps.add(new BasicNameValuePair("ddl_kcxz", null != courseProperty ? courseProperty.desc : ""));     //填充课程性质信息
            //根据查询类型来填充查询的信息
            if (!StringUtil.isBlank(queryType) && "学期成绩".equals(queryType)) {
                nvps.add(new BasicNameValuePair("btn_xq", "学期成绩"));
            } else if (!StringUtil.isBlank(queryType) && "学年成绩".equals(queryType)) {
                nvps.add(new BasicNameValuePair("btn_xn", "学年成绩"));
            } else if (!StringUtil.isBlank(queryType) && "历年成绩".equals(queryType)) {
                nvps.add(new BasicNameValuePair("btn_zcj", "历年成绩"));
            }
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));    //设置字符
            //发起post请求，查询成绩
            response = httpClient.execute(httpPost);
            document = Jsoup.parse(EntityUtils.toString(response.getEntity(), Consts.UTF_8));
            httpPost.abort();
            Elements recordElement = document.select("#Datagrid1 tr");    //获取存放课程表信息的表格
            recordElement.remove(0);    //移除第一个说明信息
            records = new ArrayList<>();
            //处理课程信息，去除HTML标记，放入Record对象中
            for (Element e : recordElement) {
                Record record = new Record();
                String recordText = e.html();
                String[] recordArray = recordText.split("</td>");
                record.setStudentNo(studentNo);
                record.setCourseCode(recordArray[2].replace("\n<td>", "").replace("&nbsp;", ""));
                record.setCourseName(recordArray[3].replace("\n<td>", "").replace("&nbsp;", ""));
                record.setCourseProperty(recordArray[4].replace("\n<td>", "").replace("&nbsp;", ""));
                record.setCourseType(recordArray[5].replace("\n<td>", "").replace("&nbsp;", ""));
                record.setCourseCredit(recordArray[6].replace("\n<td>", "").replace("&nbsp;", ""));
                record.setGPA(recordArray[7].replace("\n<td>", "").replace("&nbsp;", ""));
                if (!recordArray[11].replace("\n<td>", "").equals("&nbsp;")) {
                    record.setCourseScore(recordArray[11].replace("\n<td>", "").replace("&nbsp;", ""));
                } else if (!recordArray[10].replace("\n<td>", "").equals("&nbsp;")) {
                    record.setCourseScore(recordArray[10].replace("\n<td>", "").replace("&nbsp;", ""));
                } else {
                    record.setCourseScore(recordArray[8].replace("\n<td>", "").replace("&nbsp;", ""));
                }
                record.setMinorFlag(recordArray[9].replace("\n<td>", "").replace("&nbsp;", ""));
                record.setResitScore(recordArray[10].replace("\n<td>", "").replace("&nbsp;", ""));
                record.setRetakeScore(recordArray[11].replace("\n<td>", "").replace("&nbsp;", ""));
                record.setCourseAcademy(recordArray[12].replace("\n<td>", "").replace("&nbsp;", ""));
                records.add(record);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != httpGet) {
                httpGet.releaseConnection();
            }
            if (null != httpPost) {
                httpPost.releaseConnection();
            }
        }
        return records;
    }
}
