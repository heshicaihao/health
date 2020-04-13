package com.itheima.dao;

import com.itheima.pojo.Permission;

import java.util.Set;

/**
 * @author wangxin
 * @version 1.0
 */
public interface PermissionDao {

    Set<Permission> findPermissionsByRoleId(Integer roleId);
}
