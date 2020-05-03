package me.zhoudongyu.service.impl;

import me.zhoudongyu.config.UrlConfig;
import me.zhoudongyu.core.Session.MySessionContext;
import me.zhoudongyu.core.consts.Term;
import me.zhoudongyu.entity.Course;
import me.zhoudongyu.entity.OnlineUser;
import me.zhoudongyu.service.CourseService;
import me.zhoudongyu.utils.DateTimeUtils;
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
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service("courseService")
public class CourseServiceImpl implements CourseService {
    private final String mainUrl = UrlConfig.MAIN_URL;    //获取教务系统主页的Url
    private final String courseUrl = UrlConfig.COURSE_URL;    //获取课程表查询的Url

    @Override
    public List<ArrayList<Course>> getStudentCourse(String studentNo, String studentName, String year, Term term) {
        HttpClient httpClient = HttpClientHelper.getHttpClient();
        HttpGet httpGet = HttpClientHelper.getHttpGet(mainUrl);
        HttpPost httpPost = null;
        //填充需要提交的表单
        List<NameValuePair> nvps = new ArrayList<>();
        String courseUrl = null;
        HttpResponse response = null;
        Document document = null;
        List<ArrayList<Course>> courses = null;
        try {
            //拼接课程表查询的Url
            courseUrl = this.courseUrl + "?xh=" + studentNo + "&xm=" + studentName + "&gnmkdm=N121603";
            httpClient.execute(httpGet);    //登录教务系统首页，获取首页Cookie
            httpGet.abort();    //终止请求
            httpGet = HttpClientHelper.getHttpGet(courseUrl);

            //发起Get请求，查询课表信息，默认获取当前学期的课表信息
            response = httpClient.execute(httpGet);
            document = Jsoup.parse(EntityUtils.toString(response.getEntity(), Consts.UTF_8));
            String scriptElement = document.select("script").last().html();
            httpGet.abort();
            //如果没有选择查询条件，默认获取当前学期的课表信息
            if (StringUtil.isBlank(year) && null == term) {
                //如果本学期没有课程，返回null
                if (null != scriptElement && "alert('您本学期课所选学分小于 0分');".equals(scriptElement)) {
                    scriptElement = null;    //清空scriptElement记录的状态
                    return null;
                }
            } else {   //有查询条件，不是获取当前学期的课表信息，则再次发起post请求
                httpPost = HttpClientHelper.getHttpPost(courseUrl);

                //填充post请求信息
                String __VIEWSTATE = document.select("[name=__VIEWSTATE]").attr("value");    //获取__VIEWSTATE信息
                String __VIEWSTATEGENERATOR = document.select("[name=__VIEWSTATEGENERATOR]").attr("value");    //获取__VIEWSTATEGENERATOR信息
                nvps.add(new BasicNameValuePair("__EVENTTARGET", "xnd"));     //填充__EVENTTARGET
                nvps.add(new BasicNameValuePair("__VIEWSTATE", __VIEWSTATE));     //填充__VIEWSTATE
                nvps.add(new BasicNameValuePair("__VIEWSTATEGENERATOR", __VIEWSTATEGENERATOR));     //填充__VIEWSTATEGENERATOR
                nvps.add(new BasicNameValuePair("xnd", year));     //填充学年信息
                nvps.add(new BasicNameValuePair("xqd", term.desc));     //填充学期信息
                httpPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));    //设置字符
                response = httpClient.execute(httpPost);   //查询成绩
                document = Jsoup.parse(EntityUtils.toString(response.getEntity(), Consts.UTF_8));
                httpPost.abort();
                scriptElement = document.select("script").last().html();
                //如果查询的学期没有课程，返回null
                if (null != scriptElement && "alert('您本学期课所选学分小于 0分');".equals(scriptElement)) {    //如果本学期没有课程
                    scriptElement = null;    //清空scriptElement记录的状态
                    return null;
                }
            }

            //处理查询结果，放入Course对象中
            ArrayList<Elements> courseElements = new ArrayList();
            Elements tableElement = document.select("#Table1");    //获取存放课程表信息的表格
            //获得一周第几节课的信息
            for (int i = 2; i < 14; i = i + 2) {
                Elements tdElements = tableElement.select("tr:eq(" + i + ")").select("[align]");
                courseElements.add(tdElements);
            }
            courses = new ArrayList<>();
            //处理课程信息，去除HTML标记
            for (Elements e : courseElements) {
                ArrayList<Course> course = new ArrayList<>();
                for (int i = 0; i < 7; i++) {
                    String courseText = e.get(i).html();
                    if (StringUtil.isBlank(courseText) || "&nbsp;".equals(courseText)) {
                        course.add(null);
                    } else {
                        Course c = new Course();
                        String[] courseArray = courseText.split("<br>");
                        c.setStudentNo(studentNo);
                        c.setCourseName(courseArray[0]);
                        c.setSchoolTime(courseArray[1]);
                        c.setTeacher(courseArray[2]);
                        c.setClassroom(courseArray[3]);
                        course.add(c);
                    }
                }
                courses.add(course);
            }
            //添加两个，代替中午和傍晚，小程序好显示
            courses.add(2, null);
            courses.add(5, null);
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
        return courses;
    }

    public List<String> getRecentYear(HttpServletRequest req) {
        MySessionContext myc = MySessionContext.getInstance();
        HttpSession session = myc.getSession(req.getHeader("Cookie").substring(11));
        OnlineUser onlineUser = (OnlineUser) session.getAttribute("onlineUser");
        String enrollmentYear = onlineUser.getEnrollmentDate().substring(0, 4);
        return DateTimeUtils.getRecentYear(enrollmentYear);
    }
}
