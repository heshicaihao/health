package com.itheima.dao;

import com.itheima.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map; /**
 * 预约设置接口层
 * @author wangxin
 * @version 1.0
 */
public interface OrderSettingDao {
    /**
     * 检查此数据的预约日期是否存在
     * @param orderDate
     * @return
     */
    int findCoundByOrderDate(Date orderDate);

    /**
     * 执行更新操作  根据预约日期更新可预约人数
     * @param orderSetting
     */
    void editNumberByOrderDate(OrderSetting orderSetting);

    /**
     * 插入数据库
     * @param orderSetting
     */
    void add(OrderSetting orderSetting);

    /**
     * 日历数据展示 根据起始日期和结束日期查询预约数据
     * @param map
     * @return
     */
    List<OrderSetting> getOrderSettingByMonth(Map map);

    /**
     * 根据预约日期查询预约对象
     * @param orderDate
     * @return
     */
    OrderSetting findByOrderDate(Date orderDate);


    /**
     * 执行更新操作  根据预约日期更新已经预约人数
     * update t_ordersetting set  reservations = reservations+1 where orderDate = '2020-03-14'
     * @param orderDate
     */
    void editReservationsByOrderDate(Date orderDate);
}
