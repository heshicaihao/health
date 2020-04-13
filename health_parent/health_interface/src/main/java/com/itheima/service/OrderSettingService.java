package com.itheima.service;

import com.itheima.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

/**
 * 预约设置接口
 *
 * @author wangxin
 * @version 1.0
 */
public interface OrderSettingService {
    void add(List<OrderSetting> orderSettingList);

    /**
     * 日历数据展示
     */
    List<Map> getOrderSettingByMonth(String date);

    /**
     * 单个预约设置
     * @param orderSetting
     */
    void editNumberByDate(OrderSetting orderSetting);
}
