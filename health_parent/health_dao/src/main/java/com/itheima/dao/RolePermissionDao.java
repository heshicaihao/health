package com.itheima.dao;

import java.util.Map;

/**
 * 角色权限Dao
 */
public interface RolePermissionDao {

    //根据角色id删除关联数据
    void deleteByRoleId(Integer id);

    //向中间关系表插入数据
    void add(Map map);
}
