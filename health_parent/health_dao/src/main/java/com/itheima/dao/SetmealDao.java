package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.Setmeal;

import java.util.List;
import java.util.Map;
/**
 * 套餐接口层
 * @author wangxin
 * @version 1.0
 */
public interface SetmealDao {
    /**
     * 新增套餐
     * @param setmeal
     */
    void add(Setmeal setmeal);

    /**
     * 套餐检查组中间表插入
     * @param map
     */
    void setCheckGroupAndSetmeal(Map<String, Integer> map);

    /**
     * 查询套餐数据
     * @param queryString
     * @return
     */
    Page<Setmeal> selectByCondition(String queryString);

    /**
     * 根据套餐id查询套餐对象
     * @param id
     * @return
     */
    Setmeal findById(Integer id);

    /***
     * 根据套餐id查询关联的检查组ids
     * @param id
     * @return
     */
    List<Integer> findCheckGroupIdsBySetmealId(Integer id);

    /**
     * 更新套餐表
     * @param setmeal
     */
    void edit(Setmeal setmeal);

    /**
     * 先删除套餐关联的检查组记录
     * @param setmealId
     */
    void deleteAssociation(Integer setmealId);

    /**
     * 根据套餐id查询套餐和检查组中间表
     * @param id
     * @return
     */
    int findSetmealAndCheckGroupCountBySetMealId(Integer id);

    /**
     * 删除套餐
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 获取套餐列表数据
     */
    List<Setmeal> findAll();

    /**
     * 套餐预约占比饼图
     * @return
     */
    List<Map<String,Object>> findSetmealCount();
}
