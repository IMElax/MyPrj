package com.linchong.jira.controller;


import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.IssueField;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.linchong.jira.utils.CallWX;
import com.linchong.jira.utils.ExcelTools;
import com.linchong.jira.utils.JiraTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;


@RestController
public class BaseController {

    Logger logger = LoggerFactory.getLogger(BaseController.class);

    @Autowired
    ExcelTools exportJira;

    @Autowired
    JiraTools jiraTools;

    @Autowired
    CallWX callWX;

    @RequestMapping("/download")
    public String webhook(HttpServletRequest request, HttpServletResponse response) throws IOException {

        System.out.println("this is my first springboot");
        System.out.println("===============================");

        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.setHeader("Content-Disposition", "attachment;filename=JIRA.xlsx");
        response.flushBuffer();

        exportJira.download(jiraTools.getSearchResult(2), response.getOutputStream());

        return "success";
    }

    @RequestMapping("/print")
    public String sendWX(HttpServletRequest request, HttpServletResponse response) throws IOException {

        SearchResult sr = jiraTools.getSearchResult(1);

        for (Issue i : sr.getIssues()) {
            System.out.printf("IssueKey：%s,项目：%s,经办人：%s\n",
                    i.getKey(), i.getProject().getName(), i.getAssignee().getName());
            Iterator var2 = i.getFields().iterator();
            IssueField issueField;
            do {
                issueField = (IssueField)var2.next();

                if (issueField.getValue()=="" || null == issueField.getValue())
                    continue;
                else {
//                  JSONObject t = JSON.parseObject((String) issueField.getValue());
                    logger.info("字段名是{}，字段值是：{}",issueField.getName(),issueField.getValue().toString());
                }
            } while(var2.hasNext());

        }

        return "success";
    }


}
