package me.zhoudongyu.service.impl;

import me.zhoudongyu.config.UrlConfig;
import me.zhoudongyu.core.consts.Term;
import me.zhoudongyu.entity.Course;
import me.zhoudongyu.service.ElectivesService;
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

@Service("electivesService")
public class ElectivesServiceImpl implements ElectivesService {
    private final String mainUrl = UrlConfig.MAIN_URL;    //获取教务系统主页的Url
    private final String electivesUrl = UrlConfig.ELECTIVES_URL;  //获取课程表查询的Url

    @Override
    public List<Course> getElectivesInfo(String studentNo, String studentName, String year, Term term) {
        HttpClient httpClient = HttpClientHelper.getHttpClient();
        HttpGet httpGet = HttpClientHelper.getHttpGet(mainUrl);
        HttpPost httpPost = null;
        //填充需要提交的表单
        List<NameValuePair> nvps = new ArrayList<>();

        String electivesUrl = null;
        HttpResponse response = null;
        Document document = null;
        ArrayList<Course> courseList = null;
        try {
            //拼接选课情况查询的Url
            electivesUrl = this.electivesUrl + "?xh=" + studentNo + "&xm=" + studentName + "&gnmkdm=N121615";
            httpClient.execute(httpGet);    //登录教务系统首页，获取首页Cookie
            httpGet.abort();    //终止请求
            httpGet = HttpClientHelper.getHttpGet(electivesUrl);

            //发起Get请求，查询选课信息
            response = httpClient.execute(httpGet);
            document = Jsoup.parse(EntityUtils.toString(response.getEntity(), Consts.UTF_8));
            Elements elements = document.select(".datelist").select("tr");
            httpGet.abort();
            //如果没有选择查询条件，默认获取当前学期的选课信息
            if (StringUtil.isBlank(year) && null == term) {
                if (null != elements && elements.size() < 2) {
                    elements = null;    //清空elements记录的状态
                    return null;
                }
            } else {     //有查询条件，不是获取当前学期的选课信息，则再次发起post请求
                httpPost = HttpClientHelper.getHttpPost(electivesUrl);
                String __VIEWSTATE = document.select("[name=__VIEWSTATE]").attr("value");    //获取__VIEWSTATE信息
                String __VIEWSTATEGENERATOR = document.select("[name=__VIEWSTATEGENERATOR]").attr("value");    //获取__VIEWSTATEGENERATOR信息
                nvps.add(new BasicNameValuePair("__EVENTTARGET", "ddlXQ"));     //__EVENTTARGET
                nvps.add(new BasicNameValuePair("__VIEWSTATE", __VIEWSTATE));     //填充__VIEWSTATE
                nvps.add(new BasicNameValuePair("__VIEWSTATEGENERATOR", __VIEWSTATEGENERATOR));     //填充__VIEWSTATEGENERATOR
                nvps.add(new BasicNameValuePair("ddlXN", year));     //填充学年信息
                nvps.add(new BasicNameValuePair("ddlXQ", term.desc));     //填充学期信息
                httpPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));    //设置字符
                response = httpClient.execute(httpPost);   //查询成绩
                document = Jsoup.parse(EntityUtils.toString(response.getEntity(), Consts.UTF_8));
                httpPost.abort();
                elements = document.select(".datelist").select("tr");
                //如果查询的学期没有选课信息，返回null
                if (null != elements && elements.size() < 2) {
                    elements = null;    //清空elements记录的状态
                    return null;
                }
            }

            //处理查询结果，放入Course对象中
            courseList = new ArrayList();
            elements.remove(0);
            for (Element e : elements) {
                Course course = new Course();
                course.setCourseName(e.select("td").get(2).text());
                course.setCourseProperty(e.select("td").get(3).text());
                course.setTeacher(e.select("td").get(5).text());
                course.setCourseCredit(e.select("td").get(6).text());
                course.setSchoolTime(e.select("td").get(8).text());
                course.setClassroom(e.select("td").get(9).text());
                courseList.add(course);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {    //释放连接
            if (null != httpGet) {
                httpGet.releaseConnection();
            }
            if (null != httpPost) {
                httpPost.releaseConnection();
            }
        }
        return courseList;
    }
}
