package com.itheima.dao;

import com.itheima.pojo.Order;

import java.util.List;
import java.util.Map;

public interface OrderDao {
    public void add(Order order);
    public List<Order> findByCondition(Order order);
    public Map findById4Detail(Integer id);
    public Integer findOrderCountByDate(String date);
    public Integer findOrderCountAfterDate(String date);
    public Integer findVisitsCountByDate(String date);
    public Integer findVisitsCountAfterDate(Map<String, Object> weekMap);
    public List<Map> findHotSetmeal();

    /**
     * 获取指定日期间的预约数量
     * @param weekMap
     * @return
     */
    Integer findOrderCountBetweenDate(Map<String, Object> weekMap);

    /**
     *获取套餐信息
     * @param id
     * @return
     */
    Map findById(Integer id);
}
