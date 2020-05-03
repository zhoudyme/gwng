import me.zhoudongyu.utils.HttpClientHelper;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {
    @org.junit.Test
    public void test() {
        getStudentInfo("学号");
    }

    public void getStudentInfo(String studentNo) {
        String studentsInfoUrl = "http://portal.gwng.edu.cn:8080/web/portal/index?p_p_id=wsinfo_INSTANCE_GQs0&p_p_lifecycle=0&p_p_state=exclusive&p_p_mode=view&p_p_col_id=column-2&p_p_col_count=5&_wsinfo_INSTANCE_GQs0_struts_action=%2Fext%2Fwsinfo%2Fajaxcall&_wsinfo_INSTANCE_GQs0_wsName=studentsinfo";
        if (StringUtil.isBlank(studentNo)) {
        } else {
            try {
                HttpClient httpClient = HttpClientHelper.getHttpClient();
                HttpGet httpGet = new HttpGet(studentsInfoUrl);
                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                Document doc = Jsoup.parse(EntityUtils.toString(httpEntity, "utf-8"));
                System.out.println(doc);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String ClientEduSystem() throws IOException {

        Map<String, String> map = new HashMap<String, String>();

        //学校教务系统登录的url
        String baseUrl = "https://cas.gwng.edu.cn/cas_server/IndexService?service=http%3A%2F%2Fportal.gwng.edu.cn%3A8080%2Fc%2Fportal%2Flogin";
        HttpClient httpClient = HttpClientHelper.getHttpClient();        //通过HttpClientHelper得到HttpClient对象//将从服务器中获取到所有内容放到InputStream对象中
//        Document loginDocument = HttpClientHelper.loadFileFromURL(baseUrl,);    //通过Jsoup将InputStream对象的内容转换成Documen
//        String lt = loginDocument.select("[name=lt]").attr("value");


        HttpPost httpPost = new HttpPost(baseUrl);      //实例化一个HttpPost对象，用于提交post请求
        //填充需要提交的表单
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("username", "学号"));     //填充用户名
        nvps.add(new BasicNameValuePair("password", "密码"));     //填充密码
        nvps.add(new BasicNameValuePair("_eventId", "submit"));     //填充_eventId
//        nvps.add(new BasicNameValuePair("lt", lt));       //填充lt

        /*设置字符*/
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
        /*尝试登陆*/

        HttpResponse response;
        try {
            response = httpClient.execute(httpPost);

            String cookie = response.getLastHeader("Set-Cookie").getValue();
            /*判断是否登陆成功，根据登陆成功后会返回302跳转*/
            // 获得跳转的网址
            Header locationHeader = response.getFirstHeader("Location");
            if (locationHeader != null && "HTTP/1.1 302 Found".equals(response.getStatusLine().toString())) {
                String login_success = locationHeader.getValue();// 获取登陆成功之后跳转链接
                HttpGet httpget = new HttpGet(login_success);
                httpget.setHeader("Cookie", cookie);//设置cookie
                HttpResponse re2 = httpClient.execute(httpget);
                Document doc = Jsoup.parse(EntityUtils.toString(re2.getEntity(), "utf-8"));
                Element e = doc.getElementById("_wsinfo_INSTANCE_GQs0_xh");

                HttpGet httpget2 = new HttpGet("http://portal.gwng.edu.cn:8080/web/portal/index?p_p_id=wsinfo_INSTANCE_GQs0&p_p_lifecycle=0&p_p_state=exclusive&p_p_mode=view&p_p_col_id=column-2&p_p_col_count=5&_wsinfo_INSTANCE_GQs0_struts_action=%2Fext%2Fwsinfo%2Fajaxcall&_wsinfo_INSTANCE_GQs0_wsName=studentsinfo&paramterName=学号");

                HttpResponse re3 = httpClient.execute(httpget2);

                Document doc2 = Jsoup.parse(EntityUtils.toString(re3.getEntity(), "utf-8"));
                if (e == null) {
                    return null;
                } else {
                    System.out.println("登陆成功");
                    return e.text();
                }
            } else {
                System.out.println("登陆不成功，请稍后再试!");
                return null;
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpPost != null) {
                httpPost.abort();
            }
        }
        return null;
    }


}