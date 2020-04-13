package com.itheima.test;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *POI入门案例
 * @author wangxin
 * @version 1.0
 */
public class POITest {

    /**
     * 方式一:读取一个Excel数据 输出控制台
     * @throws IOException
     */
//    @Test
    public void readExcel01() throws IOException {
        //1.获取Excel对象
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook("C:\\working\\heima\\01-全天模式\\14传智健康\\第4天\\资料\\read.xlsx");
        //2.获取工作表
        //XSSFSheet sheet1 = xssfWorkbook.getSheet("Sheet1");
        XSSFSheet sheet1 = xssfWorkbook.getSheetAt(0);
        //3.遍历工作表 获取每一行对象
        for (Row cells : sheet1) {
            //4.遍历每一行对象，获取每一列数据
            for (Cell cell : cells) {
                //5.输出控制台
                System.out.println(cell.getStringCellValue());
            }
            System.out.println("*********************************************");
        }
        //6.资源释放
        xssfWorkbook.close();

    }


    /**
     * 方式二： 读取Excel输出控制台
     */
//    @Test
    public void readExcel02() throws IOException {
        //1.获取Excel对象
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook("C:\\working\\heima\\01-全天模式\\14传智健康\\第4天\\资料\\read.xlsx");
        //2.获取工作表
        //XSSFSheet sheet1 = xssfWorkbook.getSheet("Sheet1");
        XSSFSheet sheet1 = xssfWorkbook.getSheetAt(0);
        //获取行号
        int lastRowNum = sheet1.getLastRowNum();
        for (int i =1;i<=lastRowNum;i++){
            XSSFRow row = sheet1.getRow(i);
            short lastCellNum = row.getLastCellNum();
            for (int j=0;j<lastCellNum;j++){
                System.out.println(row.getCell(j).getStringCellValue());
            }
            System.out.println("**********************************");
        }
        xssfWorkbook.close();
    }


    /**
     * 创建Excel
     */
//    @Test
    public void createExcel() throws IOException {
        //  1.创建工作簿对象
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
        //  2.创建工作表对象
        XSSFSheet xssfSheet = xssfWorkbook.createSheet("传智播客");

        //  3.创建行对象
        XSSFRow titleRow = xssfSheet.createRow(0);//标题行

        //  4.创建列(单元格)对象, 设置内容
        titleRow.createCell(0).setCellValue("编号");
        titleRow.createCell(1).setCellValue("姓名");
        titleRow.createCell(2).setCellValue("年龄");

        XSSFRow dataRow = xssfSheet.createRow(1);//标题行
        dataRow.createCell(0).setCellValue("007");
        dataRow.createCell(1).setCellValue("小周");
        dataRow.createCell(2).setCellValue("60");

        //  5.通过输出流将workbook对象下载到磁盘
        FileOutputStream out = new FileOutputStream("C:\\working\\heima\\01-全天模式\\14传智健康\\第4天\\资料\\abc.xlsx");
        xssfWorkbook.write(out);
        out.flush();//刷新
        out.close();//关闭
        xssfWorkbook.close();//关闭
    }


}
