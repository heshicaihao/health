package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.OrderSettingDao;
import com.itheima.dao.OrderSettingHistoryDao;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 预约设置业务逻辑处理层
 * @author wangxin
 * @version 1.0
 */
@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {
    @Autowired
    private OrderSettingDao orderSettingDao;
    @Autowired
    private OrderSettingHistoryDao orderSettingHistoryDao;

    @Override
    public void add(List<OrderSetting> orderSettingList) {
        //1.校验
        if(orderSettingList != null && orderSettingList.size()>0){
            for (OrderSetting orderSetting : orderSettingList) {
                //2.检查此数据的预约日期是否存在
                int count =orderSettingDao.findCoundByOrderDate(orderSetting.getOrderDate());
                //3.数据已经存在，执行更新操作
                if(count >0){
                    orderSettingDao.editNumberByOrderDate(orderSetting);//orderDate number
                }else {
                    //4.数据不存在，则插入数据库
                    orderSettingDao.add(orderSetting);
                }

            }
        }

    }

    /**
     * 日历数据展示
     */
    @Override
    public List<Map> getOrderSettingByMonth(String date) {
        //Select * from t_ordersetting where orderDate between ‘2020-03-01’ and ‘2020-03-31’
        //date 2020-03
        ///拼接起始日期 和 结束日期
        String startDate = date+"-1";
        String endDate = date+"-31";

        //查询起始日期和结束日期的预约数据 List<OrderSetting>
        Map map = new HashMap();
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        List<OrderSetting> orderSettingList = orderSettingDao.getOrderSettingByMonth(map);
        //List<OrderSetting> 转成  List<Map>   (Map:{ date: 1, number: 120, reservations: 1 })
        //定义一个返回的List<Map>
        List<Map> mapList = new ArrayList<>();
        if(orderSettingList != null && orderSettingList.size()>0){
            for (OrderSetting orderSetting : orderSettingList) {
                Map oMap = new HashMap();
                oMap.put("date",orderSetting.getOrderDate().getDate());//页面日历组件 对应的几号
                oMap.put("number",orderSetting.getNumber());//可预约人数
                oMap.put("reservations",orderSetting.getReservations());//已经预约人数
                mapList.add(oMap);
            }
        }
        return mapList;
    }

    /**
     * 单个预约设置
     * @param orderSetting
     */
    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        //根据预约日期查询预约设置记录是否存在
        int count =orderSettingDao.findCoundByOrderDate(orderSetting.getOrderDate());
        //如果存在，则根据预约日期修改预约人数 update
        if(count >0){
            orderSettingDao.editNumberByOrderDate(orderSetting);//orderDate number
        }else {
            //4.数据不存在，则插入数据库
            orderSettingDao.add(orderSetting);
        }
    }
   /* 删除一周前的预约数据*/
    @Override
    public void deleteLastMonthOrderSetting(String lastWeekDate) {
        //删除数据前先备份数据到表t_orderrsetting_history中
         List<OrderSetting> orderSettingList =  orderSettingDao.findLastWeek(lastWeekDate);

         if (orderSettingList != null && orderSettingList.size()>0){
             Map<String,Object> objectMap = new HashMap<>();

             for (OrderSetting orderSetting : orderSettingList) {
                 Date orderDate = orderSetting.getOrderDate();
                 int number = orderSetting.getNumber();
                 Integer id = orderSetting.getId();
                 int reservations = orderSetting.getReservations();
                 objectMap.put("id",id);
                 objectMap.put("orderDate",orderDate);
                 objectMap.put("number",number);
                 objectMap.put("reservations",reservations);
                 orderSettingHistoryDao.backUp(objectMap);//备份数据
             }

             orderSettingDao.deleteLastMonthOrderSetting(lastWeekDate);//删除数据
         }






    }
}
