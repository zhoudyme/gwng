package me.zhoudongyu.utils;

import me.zhoudongyu.config.MyRequestConfig;
import me.zhoudongyu.core.consts.RequestType;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.Map;

/**
 * HttpClient工具类
 */
public class HttpClientHelper {

    private static CloseableHttpClient httpClient = null;    //CloseableHttpClient对象
    private static CookieStore cookieStore = new BasicCookieStore();    //CookieStore对象

    //RequestConfig对象
    private static RequestConfig requestConfig = RequestConfig.custom()
            .setConnectTimeout(MyRequestConfig.CONNECT_TIMEOUT).setConnectionRequestTimeout(MyRequestConfig.CONNECTION_REQUEST_TIMEOUT)
            .setSocketTimeout(MyRequestConfig.SOCKET_TIMEOUT).build();

    /**
     * 单例模式，保证每个线程只有一个httpClient对象
     */
    public static CloseableHttpClient getHttpClient() {
        if (null == httpClient) {
            httpClient = SSLUtils.SslHttpClientBuild(cookieStore);
            return httpClient;
        }
        return httpClient;
    }

    /**
     * 获取CookieStore对象
     */
    public static CookieStore getCookieStore() {
        return cookieStore;
    }

    /**
     * 获取HttpGet对象
     *
     * @param targetUrl 请求地址
     */
    public static HttpGet getHttpGet(String targetUrl) {
        HttpGet httpGet = new HttpGet(targetUrl);
        httpGet.setConfig(requestConfig);
        return httpGet;
    }

    /**
     * 获取HttpPost对象
     *
     * @param targetUrl 请求地址
     */
    public static HttpPost getHttpPost(String targetUrl) {
        HttpPost httpPost = new HttpPost(targetUrl);
        httpPost.setConfig(requestConfig);
        return httpPost;
    }

    /**
     * 访问Url，获得Document对象
     *
     * @param url         访问的Url地址
     * @param requestType 请求类型，Get或Post
     * @param headers     请求得到headers
     */
    public static Document loadDocumentFromURL(String url, RequestType requestType, Map<String, Object> headers) {
        Document doc = null;
        HttpGet httpGet = null;
        HttpPost httpPost = null;
        HttpClient httpClient = getHttpClient();
        //根据请求类型来实例化不同的对象
        if (null != requestType && requestType == RequestType.GET) {
            httpGet = getHttpGet(url);
            if (null != headers && !headers.isEmpty()) {
                for (Map.Entry<String, Object> entry : headers.entrySet()) {
                    httpGet.setHeader(entry.getKey(), String.valueOf(entry.getValue()));
                }
            }
        } else if (null != requestType && requestType == RequestType.POST) {
            httpPost = getHttpPost(url);
            if (null != headers && !headers.isEmpty()) {
                for (Map.Entry<String, Object> entry : headers.entrySet()) {
                    httpPost.setHeader(entry.getKey(), String.valueOf(entry.getValue()));
                }
            }
        }
        try {
            // 调用HttpClient对象的execute()方法，参数是刚才创建的HttpGet或HttpPost对象，返回值是HttpResponse对象；
            HttpResponse response = httpClient.execute(null != httpGet ? httpGet : httpPost);
            // 通过response对象中的getStatusLine()方法和getStatusCode()方法获取服务器响应状态，如果请求成功，Http状态码为200
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                // response对象的getEntity()方法，返回HttpEntity对象并转换为Document对象。
                doc = Jsoup.parse(EntityUtils.toString(response.getEntity(), "utf-8"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("网络异常");
        } finally {    //释放Http连接
            if (null != httpGet) {
                httpGet.releaseConnection();
            } else if (null != httpPost) {
                httpPost.releaseConnection();
            }
        }
        return doc;
    }
}