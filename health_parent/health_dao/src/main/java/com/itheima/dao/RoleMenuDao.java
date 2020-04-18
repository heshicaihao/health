package com.itheima.dao;

import java.util.Map;

/**
 * 角色菜单Dao层
 */
public interface RoleMenuDao {

    //根据id删除中间关系表的关联数据
    void deleteByRoleId(Integer id);

    //添加新的关系数据
    void add(Map map);
}
