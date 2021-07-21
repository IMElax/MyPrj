package com.linchong.jira.utils;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CallWX {


    public void sendWXTS(String issueKey) {
        String url = "http://63317-iyb-auth-center.test.za-tech.net/weChat/notice/jira/message?issueKey=";
        StringBuffer sb = new StringBuffer(10);
        RestTemplate restTemplate = new RestTemplate();
        sb.append(url).append(issueKey);
        String response = restTemplate.getForObject(sb.toString(), String.class);

        JSONObject rootObject = JSONObject.parseObject(response);

        if (rootObject.getBoolean("isSuccess")) {
            System.out.println("已推送");
        } else {
            System.out.printf("推送失败:%s \n", response);
        }

    }
}
