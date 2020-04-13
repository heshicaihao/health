package com.itheima.service;

import com.itheima.entity.Result;

import java.util.Map;

/**
 * 提交预约业务逻辑接口
 * @author wangxin
 * @version 1.0
 */
public interface OrderService {
    /**
     * 提交预约
     *
     * @param map
     * @return
     */
    Result submitOrder(Map map) throws Exception;
    /**
     * 体检预约成功页面数据展示
     */
    Map findById4Detail(Integer id) throws Exception;

    /**
     * 获取套餐信息
     * @param id
     * @return
     */
    Map findById(Integer id);
}
