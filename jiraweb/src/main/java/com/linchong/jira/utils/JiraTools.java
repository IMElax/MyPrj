package com.linchong.jira.utils;

import com.alibaba.fastjson.JSON;
import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import org.springframework.stereotype.Component;

import java.net.URI;

@Component
public class JiraTools {

    public static final  String uri ="http://10.253.76.129:8080";
    public static final  String user = "linchong";
    public final static String password = "Pass2020";

    private JiraRestClient jiraRestClient;

    public JiraTools() {
        AsynchronousJiraRestClientFactory asynchronousJiraRestClientFactory = new AsynchronousJiraRestClientFactory();
        jiraRestClient = asynchronousJiraRestClientFactory.createWithBasicHttpAuthentication(URI.create(JiraTools.uri), JiraTools.user, JiraTools.password);
    }

    private SearchResult queryByJQL(String JQL) {
        SearchResult issues = jiraRestClient.getSearchClient().searchJql(JQL).claim();
        System.out.println(JSON.toJSONString(issues));
        for (Issue i : issues.getIssues()) {
            System.out.printf("IssueKey：%s,项目：%s,经办人：%s\n",
                    i.getKey(),i.getProject().getName(),i.getAssignee().getName());
        }
        return issues;
    }


    public SearchResult getSearchResult(int i){
        return queryByJQL(JQLTemplate.chooseJQL(i));
    }

}
