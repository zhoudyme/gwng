package me.zhoudongyu.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * HttpClient信息配置
 */
@Component
public class MyRequestConfig {
    public static Integer CONNECT_TIMEOUT = null;
    public static Integer CONNECTION_REQUEST_TIMEOUT = null;
    public static Integer SOCKET_TIMEOUT = null;


    @Value("${connectTimeout}")
    private void setConnectTimeout(Integer connectTimeout) {
        MyRequestConfig.CONNECT_TIMEOUT = connectTimeout;
    }

    @Value("${connectionRequestTimeout}")
    private void setConnectionRequestTimeout(Integer connectionRequestTimeout) {
        MyRequestConfig.CONNECTION_REQUEST_TIMEOUT = connectionRequestTimeout;
    }

    @Value("${socketTimeout}")
    private void setSocketTimeout(Integer socketTimeout) {
        MyRequestConfig.SOCKET_TIMEOUT = socketTimeout;
    }

}
