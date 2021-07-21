package com.linchong.jira.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class ExcelTools {

    JSONObject excelJson = null;

    public ExcelTools() {
        this.excelJson = excelJsonRead();
    }

    public void download(SearchResult sr, OutputStream os) throws IOException {
        String[] header = {
                "项目名称", "JIRA编号", "模块", "问题类型", "摘要", "创建时间", "状态", "经办人",
                "需求分析人员", "开发组", "开发人员", "测试负责人", "测试人员", "任务所属月份", "计划上线时间", "实际上线时间", "计划开发工时", "预计开发开始时间", "预计开发结束时间", "实际开发工时", "实际开发开始时间", "实际开发结束时间",
                "预计测试开始时间", "预计测试结束时间", "实际测试工时", "实际测试开始时间", "实际测试结束时间", "工时差异率",
                "需求分析持续天数", "开发资源分配持续天数", "测试资源分配持续天数", "开发延期标志", "测试延期标志", "用户验收延期标志", "延迟完成天数", "实际任务延期标志"
        };


        //声明一个工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();

        //生成一个表格，设置表格名称为"JIRA表"
        HSSFSheet sheet = workbook.createSheet("JIRA");
        //设置表格列宽度为10个字节
        sheet.setDefaultColumnWidth(10);
        //创建标题的显示样式
        HSSFCellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        //创建第一行表头
        HSSFRow headrow = sheet.createRow(0);

        //遍历添加表头(下面模拟遍历学生，也是同样的操作过程)
        for (int i = 0; i < header.length; i++) {
            //创建一个单元格
            HSSFCell cell = headrow.createCell(i);

            //创建一个内容对象
            HSSFRichTextString text = new HSSFRichTextString(header[i]);

            //将内容对象的文字内容写入到单元格中
            cell.setCellValue(text);
            cell.setCellStyle(headerStyle);
        }

        int t = 0;
        for (Issue i : sr.getIssues()) {
            t++;
//            HSSFRow row = sheet.createRow(t);
//            row.createCell(0).setCellValue(i.getProject().getName());
//            row.createCell(1).setCellValue(i.getKey());
//            row.createCell(2).setCellValue(i.getComponents().iterator().next().getName());
//            row.createCell(3).setCellValue(i.getIssueType().getName());
//            row.createCell(4).setCellValue(i.getSummary());
//            row.createCell(5).setCellValue(i.getCreationDate().toDate());
//            row.createCell(6).setCellValue(i.getStatus().getName());
//            row.createCell(7).setCellValue(i.getAssignee().getName());
//            //需求负责人
//            row.createCell(8).setCellValue(i.getField("customfield_11801").getValue().toString());
//            //开发负责人
//            row.createCell(9).setCellValue(i.getField("customfield_10900").getValue().toString());
//            //测试负责人
//            row.createCell(10).setCellValue(i.getField("customfield_10901").getValue().toString());


        }

        workbook.write(os);


    }

    public JSONObject excelJsonRead() {

        StringBuffer sb = new StringBuffer(256);

        try {
            Resource resource = new ClassPathResource("ExcelConfig.json");
            InputStream is = resource.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String data = null;

            while ((data = br.readLine()) != null) {
                sb.append(data);
            }


            br.close();
            isr.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            return JSON.parseObject(sb.toString());
        }

    }


}
