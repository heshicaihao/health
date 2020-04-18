package com.itheima.dao;

import com.itheima.pojo.Role;

import java.util.Map;
import java.util.Set;

/**
 * 角色接口层
 * @author wangxin
 * @version 1.0
 */
public interface RoleDao {

    Set<Role> findRolesByUserId(Integer userId);

    void addRoles(Role role);
    /**
     *  添加角色和菜单关系表
     */

    void bindRoleAndMenu(Map<String, Integer> rsMap);

    /**
     *  添加角色和权限关系表
     */
    void bindRoleAndPermission(Map<String, Integer> rsMap);
}
