package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckItem;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 检查项dao层
 * @author wangxin
 * @version 1.0
 */
public interface CheckItemDao {
    /**
     * 查询所有检查项
     * 方式一：注解方式 @Select("select * from t_checkitem")
     * 方式二：xml映射方式
     * @return
     */
    List<CheckItem> findAll();

    /**
     * 新增检查项
     * @param checkItem
     */
    void add(CheckItem checkItem);

    /**
     * 条件分页查询
     * @param queryString
     * @return
     */
    Page<CheckItem> selectByCondition(String queryString);

    /**
     * 根据检查项id 到t_checkgroup_checkitem查询关系是否存在
     * @param id
     * @return
     */
    int findCountByCheckItemId(Integer id);

    /**
     * 直接删除检查项
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 根据检查项id查询检查项
     * @param id
     * @return
     */
    CheckItem findById(Integer id);

    /**
     * 更新检查项
     * @param checkItem
     */
    void edit(CheckItem checkItem);
}
