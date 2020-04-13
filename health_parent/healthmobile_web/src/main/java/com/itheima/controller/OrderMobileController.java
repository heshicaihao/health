package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.aliyuncs.exceptions.ClientException;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.CheckItem;
import com.itheima.pojo.Order;
import com.itheima.pojo.Setmeal;
import com.itheima.service.OrderService;
import com.itheima.service.SetmealService;
import com.itheima.utils.SMSUtils;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 移动端-体检预约
 *
 * @author wangxin
 * @version 1.0
 */
@RestController
@RequestMapping("/order")
public class OrderMobileController {

    @Autowired
    private JedisPool jedisPool;

    @Reference
    private OrderService orderService;

    @Reference
    private SetmealService setmealService;

    /**
     * 体检预约
     *
     * @param map
     * @return
     */
    @RequestMapping("/submit")
    public Result submit(@RequestBody Map map) {
        //{"setmealId":"12","sex":"1","orderDate":"2020-03-11",
        // "name":"张三","telephone":"13332222334","validateCode":"1234","idCard":"512501197203035172"}
        String telephone = (String) map.get("telephone");
        String validateCode = (String) map.get("validateCode");
        String orderDate = (String) map.get("orderDate");

        //1.验证码校验
        //获取redis的验证码  获取用户输入的验证码
        String redisCode = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_ORDER);
        //2.校验失败 返回错误提示
        if (validateCode == null || redisCode == null || !redisCode.equals(validateCode)) {
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
        //3.校验通过 进行体检预约（业务逻辑）
        map.put("orderType", Order.ORDERTYPE_WEIXIN);//移动端 发起请求设置微信预约类型
        Result result = null;
        try {
            result = orderService.submitOrder(map);
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }
        //4.告知用户体检预约成功短信
        if (result.isFlag()) {
            //预约成功，发送短信通知
            try {
                SMSUtils.sendShortMessage(SMSUtils.ORDER_NOTICE, telephone, orderDate);
            } catch (ClientException e) {
                e.printStackTrace();
            }
        }
        return result;

    }


    /**
     * 体检预约成功页面数据展示
     */
    @RequestMapping("/findById")
    public Result findById(Integer id) {
        //map多个表关联查询的结果
        try {
            Map map = orderService.findById4Detail(id);
            return new Result(true, MessageConstant.QUERY_ORDER_SUCCESS, map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_ORDER_FAIL);
        }
    }

    /**
     * 导出PDF(IText方式)
     */
    @RequestMapping(value = "/exportSetmealInfo")
    public Result exportSetmealInfo(Integer id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        //1.使用t_order表中主键id,查询订单信息（体检人、体检套餐、体检日期、预约类型、套餐id）
        Map map = orderService.findById(id);
        //2.套餐id 获取检查组 检查项 数据
        Integer setmealId = (Integer) map.get("setmealId");
        Setmeal setmeal = setmealService.findById(setmealId);
        //3.导出PDF （IText）
        //设置导出PDF 头信息 (response head)
        response.setContentType("application/pdf");
        String filename = "exportPDF.pdf";
        response.setHeader("Content-Disposition", "attachment;filename=" + filename);

        Document document = new Document();
        //以输出流方式下载本地
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        //创建文档
        //设置文档数据
        // 设置表格字体
        BaseFont cn = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", false);
        Font font = new Font(cn, 10, Font.NORMAL, Color.BLUE);

        document.add(new Paragraph("体检人：" + (String) map.get("member"), font));
        document.add(new Paragraph("体检套餐：" + (String) map.get("setmeal"), font));
        document.add(new Paragraph("体检日期：" + (String) map.get("orderDate").toString(), font));
        document.add(new Paragraph("预约类型：" + (String) map.get("orderType"), font));

        //在文档中添加table表格
        // 向document 生成pdf表格
        Table table = new Table(3);//创建3列的表格
        table.setWidth(80); // 宽度
        table.setBorder(1); // 边框
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER); //水平对齐方式
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_TOP); // 垂直对齐方式
        /*设置表格属性*/
        table.setBorderColor(new Color(0, 0, 255)); //将边框的颜色设置为蓝色
        table.setPadding(5);//设置表格与字体间的间距
        //table.setSpacing(5);//设置表格上下的间距
        table.setAlignment(Element.ALIGN_CENTER);//设置字体显示居中样式
        //写表头
        table.addCell(buildCell("项目名称", font));
        table.addCell(buildCell("项目内容", font));
        table.addCell(buildCell("项目解读", font));
        //写数据
        List<CheckGroup> checkGroups = setmeal.getCheckGroups();
        if (checkGroups != null && checkGroups.size() > 0) {
            for (CheckGroup checkGroup : checkGroups) {
                //检查组名称
                table.addCell(buildCell(checkGroup.getName(), font));
                //检查项名称 拼接
                StringBuffer checkItemsBuffer = new StringBuffer();
                List<CheckItem> checkItems = checkGroup.getCheckItems();
                if (checkItems != null && checkItems.size() > 0) {
                    for (CheckItem checkItem : checkItems) {
                        checkItemsBuffer.append(checkItem.getName() + "   ");
                    }
                }
                table.addCell(buildCell(checkItemsBuffer.toString(), font));
                //设置项目解读 （备注）
                table.addCell(buildCell(checkGroup.getRemark(), font));
            }
        }
        //将table表格加入文档中
        document.add(table);
        //释放资源
        document.close();
        return null;

    }


    // 传递内容和字体样式，生成单元格
    private Cell buildCell(String content, Font font)
            throws BadElementException {
        Phrase phrase = new Phrase(content, font);
        return new Cell(phrase);
    }
}
