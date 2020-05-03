package me.zhoudongyu.service.impl;

import me.zhoudongyu.config.UrlConfig;
import me.zhoudongyu.core.Session.MySessionContext;
import me.zhoudongyu.core.consts.RequestType;
import me.zhoudongyu.service.IndexService;
import me.zhoudongyu.utils.HttpClientHelper;
import me.zhoudongyu.utils.JsonUtils;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("indexService")
public class IndexServiceImpl implements IndexService {

    @Override
    public boolean login(String studentNo, String password) {
        boolean loginFlag = false;
        HttpGet httpGet = null;
        HttpPost httpPost = null;
        try {
            String baseUrl = UrlConfig.LOGIN_URL;    //获取数字南商首页的Url
            HttpClient httpClient = HttpClientHelper.getHttpClient();        //通过HttpClientHelper得到HttpClient对象
            HttpClientHelper.getCookieStore().clear();    //每次登录前清除Cookie信息
            httpGet = HttpClientHelper.getHttpGet(UrlConfig.LOGIN_URL);

            //登录数字南商，获取首页信息
            Document indexDocument = HttpClientHelper.loadDocumentFromURL(baseUrl, RequestType.GET, null);
            //取去lt参数
            String lt = indexDocument.select("[name=lt]").attr("value");

            httpPost = HttpClientHelper.getHttpPost(baseUrl);
            //填充需要提交的表单
            List<NameValuePair> nvps = new ArrayList<>();
            nvps.add(new BasicNameValuePair("username", studentNo));     //填充用户名
            nvps.add(new BasicNameValuePair("password", password));     //填充密码
            nvps.add(new BasicNameValuePair("_eventId", "submit"));     //填充_eventId
            nvps.add(new BasicNameValuePair("lt", lt));        //填充lt
            //设置字符
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));

            //尝试登陆
            HttpResponse loginResponse = httpClient.execute(httpPost);
            String result = loginResponse.getStatusLine().toString();
            httpPost.abort();
            //判断是否登陆成功，根据登陆成功后会返回302跳转
            if ("HTTP/1.1 302 Found".equals(result)) {
                loginFlag = true;
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
        return loginFlag;
    }

    @Override
    public boolean logout(HttpServletRequest req) {
        try {
            MySessionContext myc = MySessionContext.getInstance();
            HttpSession session = myc.getSession(req.getHeader("Cookie").substring(11));
            if (null == session) {
                return true;
            }
            //清除Session和Cookie信息
            session.removeAttribute("onlineUser");
            session.removeAttribute("borrowList");
            session.invalidate();
            HttpClientHelper.getCookieStore().clear();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Map<String, Object> getStudentInfo(String studentNo) {
        //如果学号无效，则直接返回null
        if (StringUtil.isBlank(studentNo)) {
            return null;
        }
        //拼接查询学生信息的Url
        String studentsInfoUrl = UrlConfig.STUDENTS_INFO_URL + "&paramterName=" + studentNo;
        try {
            //发起Get请求，查询学生个人信息
            Document doc = HttpClientHelper.loadDocumentFromURL(studentsInfoUrl, RequestType.GET, null);
            //处理查询结果，通过JsonUtils转换为Json对象
            String bodyText = doc.getElementsByTag("body").get(0).text();
            return JsonUtils.toMap(bodyText);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
