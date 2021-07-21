package com.linchong.jira.utils;

public class JQLTemplate {

    public static final String JQL = "project in (CLOUD, PRODUCT, RENEW, NAGENCY, APP, CUST, DMC, UWS, AGENCY, CENTRAL, TECH, SAAS, CSS, APPLET, CAR) " +
            "AND issuetype = 常规需求 AND status = \"待资源分配(开发)\" AND " +
            "resolution = Unresolved  AND updated <-2d AND assignee in (membersOf(开发8组（丁亮）)) ORDER BY updated DESC";

    public static final String TEST_JQL = "assignee = currentUser() AND resolution = Unresolved order by updated DESC";

    public static String chooseJQL(int i) {
        switch (i){
            case 1:
                return TEST_JQL;
            default:
                return JQL;
        }
    }


}
