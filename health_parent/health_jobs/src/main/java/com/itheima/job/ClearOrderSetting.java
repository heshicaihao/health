package com.itheima.job;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.service.OrderSettingService;
import com.itheima.utils.DateUtils;
import org.springframework.web.bind.annotation.RestController;
import java.util.Calendar;
import java.util.Date;


@RestController
public class ClearOrderSetting {

   @Reference
    private OrderSettingService orderSettingService;

    /*删除一周前的预约数据*/
    public void deleteOrderSettting() throws Exception {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, - 7);
        Date date = calendar.getTime();
        String lastWeekDate = DateUtils.parseDate2String(date); //当前日期的上一周日期


        try {
            orderSettingService.deleteLastMonthOrderSetting(lastWeekDate);
            System.out.println("删除预约数据成功");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("删除预约数据失败");

        }

    }


    }




