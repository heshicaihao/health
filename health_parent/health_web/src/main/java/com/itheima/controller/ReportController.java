package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.service.MemeberService;
import com.itheima.service.ReportService;
import com.itheima.service.SetmealService;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 报表控制层
 * @author wangxin
 * @version 1.0
 */
@RestController
@RequestMapping("/report")
public class ReportController {
    @Reference
   private MemeberService memeberService;


    @Reference
    private SetmealService setmealService;

    @Reference
    private ReportService reportService;


    /**
     * 会员数量折线图
     */
    @RequestMapping("/getMemberReport")
    public Result getMemberReport(){
        try {
            List<String> listMonth = new ArrayList<>();
            //1.获取最近一年的年月  res.data.data.months  ['2020-01','2020-02'...]
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH,-12);//获取当前日期之前的12个月之前时间
            for (int i=0;i<12;i++){
                calendar.add(Calendar.MONTH,1);
                listMonth.add(new SimpleDateFormat("yyyy-MM").format(calendar.getTime()));
            }
            //2.调用会员服务 获取年月会员数量
            List<Integer> memberCount =  memeberService.findMemberCountByMonth(listMonth);
            //3.将年月 以及 会员数量 存入map返回前端
            Map<String,Object>  map = new HashMap<>();
            map.put("months",listMonth);
            map.put("memberCount",memberCount);
            return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_MEMBER_NUMBER_REPORT_FAIL);
        }

    }

    /**
     * 套餐预约占比饼图
     */
    @RequestMapping("/getSetmealReport")
    public Result getSetmealReport() {
        //套餐名称：setmealNames  套餐名称以及对应的预约数量：setmealCount
        //定义返回结果的map
        Map<String, Object> map = new HashMap<>();

        //map:value套餐预约数  name:套餐名称
        List<Map<String, Object>> setmealCount = setmealService.findSetmealCount();

        //定一个List<String>放套餐名称
        List<String> setmealNames = new ArrayList<>();

        if (setmealCount != null && setmealCount.size() > 0) {
            for (Map<String, Object> map1 : setmealCount) {
                String name = (String) map1.get("name");
                setmealNames.add(name);
            }
        }
        map.put("setmealNames", setmealNames);
        map.put("setmealCount", setmealCount);



        return new Result(true,MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS,map);
    }


    /**
     * 运营数据统计报表
     */
    @RequestMapping("/getBusinessReportData")
    public Result getBusinessReportData() {
        try {
            Map<String,Object> map = reportService.getBusinessReportData();
            return new Result(true,MessageConstant.GET_BUSINESS_REPORT_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_BUSINESS_REPORT_FAIL);
        }
    }

   /* *//**
     * 导出运营数据统计报表
     *//*
    @RequestMapping("/exportBusinessReport")
    public Result exportBusinessReport(HttpServletRequest request, HttpServletResponse response) {
        try {
            //1.获取模板需要的数据（已经实现getBusinessReportData）
            Map<String,Object> map = reportService.getBusinessReportData();
            //获取日期
            String reportDate = (String)map.get("reportDate");


            Integer todayNewMember = (Integer) map.get("todayNewMember");
            Integer totalMember = (Integer) map.get("totalMember");
            Integer thisWeekNewMember = (Integer) map.get("thisWeekNewMember");
            Integer thisMonthNewMember = (Integer) map.get("thisMonthNewMember");
            Integer todayOrderNumber = (Integer) map.get("todayOrderNumber");
            Integer thisWeekOrderNumber = (Integer) map.get("thisWeekOrderNumber");
            Integer thisMonthOrderNumber = (Integer) map.get("thisMonthOrderNumber");
            Integer todayVisitsNumber = (Integer) map.get("todayVisitsNumber");
            Integer thisWeekVisitsNumber = (Integer) map.get("thisWeekVisitsNumber");
            Integer thisMonthVisitsNumber = (Integer) map.get("thisMonthVisitsNumber");



            List<Map> hotSetmeal= (List<Map>)map.get("hotSetmeal");

            //2.获取模板  File.separator:windows和linux /可以通用
            String templateRealPath = request.getSession().getServletContext().getRealPath("template") + File.separator + "report_template.xlsx";
            //3.到模板对象后(内存中)
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(new FileInputStream(new File(templateRealPath)));
            //找到第几行 第几列 设置单元格数据
            XSSFSheet sheetAt = xssfWorkbook.getSheetAt(0);
            //获取第二行 设置日期
            XSSFRow row = sheetAt.getRow(2);
            row.getCell(5).setCellValue(reportDate);

            row = sheetAt.getRow(4);
            //新增会员数据
            row.getCell(5).setCellValue(todayNewMember);
            //总会员数量
            row.getCell(7).setCellValue(totalMember);

            row = sheetAt.getRow(5);
            //本周新增会员数
            row.getCell(5).setCellValue(thisWeekNewMember);
            //本月新增会员数
            row.getCell(7).setCellValue(thisMonthNewMember);

            row = sheetAt.getRow(7);
            row.getCell(5).setCellValue(todayOrderNumber);//今日预约数
            row.getCell(7).setCellValue(todayVisitsNumber);//今日到诊数

            row = sheetAt.getRow(8);
            row.getCell(5).setCellValue(thisWeekOrderNumber);//本周预约数
            row.getCell(7).setCellValue(thisWeekVisitsNumber);//本周到诊数

            row = sheetAt.getRow(9);
            row.getCell(5).setCellValue(thisMonthOrderNumber);//本月预约数
            row.getCell(7).setCellValue(thisMonthVisitsNumber);//本月到诊数

            //循环遍历展示套餐数据
            int rowNum = 12;//从13行开始设置套餐数据
            if(hotSetmeal != null && hotSetmeal.size()>0){
                for (Map mapSetmeal : hotSetmeal) {
                    String name =  (String)mapSetmeal.get("name");//套餐名称
                    Long  setmeal_count = (Long)mapSetmeal.get("setmeal_count");//预约数量
                    BigDecimal proportion = (BigDecimal)mapSetmeal.get("proportion");//预约占比
                    String remark = (String)mapSetmeal.get("remark");
                    //设置热门套餐数据
                    XSSFRow rowSetmeal = sheetAt.getRow(rowNum);
                    rowSetmeal.getCell(4).setCellValue(name);
                    rowSetmeal.getCell(5).setCellValue(setmeal_count);
                    rowSetmeal.getCell(6).setCellValue(proportion.doubleValue());
                    rowSetmeal.getCell(7).setCellValue(remark);
                    //每次循环+1
                    rowNum++;
                }
            }
            //4.通过输出流方式下载到本地
            ServletOutputStream outputStream = response.getOutputStream();
            //设置返回的数据类型  告诉浏览器下载excel文件
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");//
            //设置响应头信息 告诉浏览器下载文件名称
            response.setHeader("content-Disposition","attachment;filename=report.xlsx");
            xssfWorkbook.write(outputStream);
            //5.资源释放
            outputStream.flush();//刷新
            outputStream.close();//输出流资源释放
            xssfWorkbook.close();//关闭资源
            return null;//以输出流形式下载文件到本地
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_BUSINESS_REPORT_FAIL);
        }
    }*/


    /**
     * 导出运营数据统计报表
     */
    @RequestMapping("/exportBusinessReport")
    public Result exportBusinessReport(HttpServletRequest request, HttpServletResponse response) {
        try {
            //1.获取模板需要的数据（已经实现getBusinessReportData）
            Map<String,Object> map = reportService.getBusinessReportData();
            //2.获取模板  File.separator:windows和linux /可以通用
            String templateRealPath = request.getSession().getServletContext().getRealPath("template") + File.separator + "report_template.xlsx";
            //3.到模板对象后(内存中)
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(new FileInputStream(new File(templateRealPath)));
            //jxls模板技术
            XLSTransformer transformer = new XLSTransformer();
            //设置模板对象 以及模板需要的数据
            transformer.transformWorkbook(xssfWorkbook, map);
            //4.通过输出流方式下载到本地
            ServletOutputStream outputStream = response.getOutputStream();
            //设置返回的数据类型  告诉浏览器下载excel文件
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");//
            //设置响应头信息 告诉浏览器下载文件名称
            response.setHeader("content-Disposition","attachment;filename=report.xlsx");
            xssfWorkbook.write(outputStream);
            //5.资源释放
            outputStream.flush();//刷新
            outputStream.close();//输出流资源释放
            xssfWorkbook.close();//关闭资源
            return null;//以输出流形式下载文件到本地
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_BUSINESS_REPORT_FAIL);
        }
    }


    /**
     * 导出PDF
     */
    @RequestMapping("/exportBusinessReportPDF")
    public Result exportBusinessReportPDF(HttpServletRequest request, HttpServletResponse response) {

        try {
            // 获取PDF报表数据（代码已经存在）
            Map<String, Object> businessReportData = reportService.getBusinessReportData();
            List<Map> hotSetmeal = (List<Map>)businessReportData.get("hotSetmeal");
            // 编译模板
            //获取模板文件路径
            String jrxmlTemplate = request.getSession().getServletContext().getRealPath("template") + File.separator + "health_bus.jrxml";
            //设置编译后的模板文件路径
            String jasperTemplate = request.getSession().getServletContext().getRealPath("template") + File.separator + "health_bus.jasper";
            JasperCompileManager.compileReportToFile(jrxmlTemplate,jasperTemplate);
            // 填充数据（编译后的模板、parameters，hotSetmeal热门套餐数据）
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperTemplate, businessReportData, new JRBeanCollectionDataSource(hotSetmeal));
            // 导出报表
            ServletOutputStream outputStream = response.getOutputStream();
            //设置返回的数据类型  告诉浏览器下载excel文件
            response.setContentType("application/pdf");//
            //设置响应头信息 告诉浏览器下载文件名称
            response.setHeader("content-Disposition","attachment;filename=report.pdf");
            JasperExportManager.exportReportToPdfStream(jasperPrint,outputStream);
            outputStream.flush();//刷新
            outputStream.close();//输出流资源释放

            return  null;
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_BUSINESS_REPORT_FAIL);
        }

    }
}
