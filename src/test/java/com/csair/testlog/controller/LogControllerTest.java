package com.csair.testlog.controller;

import com.csair.testlog.Entity.pojo.AppLog;
import com.csair.testlog.utils.HttpClientUtils;
import com.csair.testlog.utils.PropertiesUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * LogController的单元测试
 *
 * @author LW_Fung
 */
public class LogControllerTest {

    private HttpClientUtils httpClientUtils;
    private static final String MAIN_URL = PropertiesUtils.getProperty("/env.properties", "MAIN_URL");
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Before
    public void before() {
        ApplicationContext ac = new ClassPathXmlApplicationContext(
                "/ssm/applicationContext.xml",
                "/ssm/applicationContext-mybatis.xml",
                "/ssm/applicationContext-httpclient.xml");
        httpClientUtils = (HttpClientUtils) ac.getBean("httpClientUtils");
    }

    @Test
    public void testSaveLog() {
        String url = MAIN_URL + "/log/saveLog";
        AppLog appLog = new AppLog();
        appLog.setLogTime(new Date());
        appLog.setThreadName("main");
        appLog.setClassName("sender.EtermSession");
        appLog.setLogLevel("INFO");
        appLog.setLogInfo("test!test!test!");
        try {
            String json = MAPPER.writeValueAsString(appLog);
            String res = httpClientUtils.doPostJson(url, json);
            System.out.println("~~~~~testSaveLog: " + res);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testReadLog() {
        try {
            httpClientUtils.doGet(MAIN_URL + "/log/readLog");
            String json = httpClientUtils.doGet(MAIN_URL + "/log/queryAll");
            System.out.println(json);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testQuery() {
        String url = MAIN_URL + "/log/queryLog";
        Map<String, String> params = new HashMap<String, String>();
        params.put("timeStart", "2015-05-28 16:13:46,000");
        params.put("timeEnd", "2015-05-29 16:13:46,000");
        params.put("threadName", "main");
        params.put("className", "sender.EtermSession");
        params.put("logLevel", "INFO");
        try {
            String json = httpClientUtils.doPost(url, params);
            System.out.println("~~~~~testQuery: " + json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
