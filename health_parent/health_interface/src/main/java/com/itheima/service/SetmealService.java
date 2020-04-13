package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.pojo.Setmeal;

import java.util.List;
import java.util.Map;

/**
 * 套餐接口层
 * @author wangxin
 * @version 1.0
 */
public interface SetmealService {
    /**
     * 新增套餐
     * @param setmeal
     * @param checkgroupIds
     */
    void add(Setmeal setmeal, Integer[] checkgroupIds);
    /**
     * 套餐分页
     */
    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    /**
     * 根据套餐id查询套餐对象
     * @param id
     * @return
     */
    Setmeal findById(Integer id);

    /**
     * 根据套餐id查询关联的检查组ids
     * @param id
     * @return
     */
    List<Integer> findCheckGroupIdsBySetmealId(Integer id);

    /**
     * 编辑套餐
     */
    void edit(Setmeal setmeal, Integer[] checkgroupIds);

    /**
     * 删除套餐
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
