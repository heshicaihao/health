package com.itheima.dao;

import com.itheima.pojo.Role;

import java.util.List;
import java.util.Set;

/**
 * 角色接口层
 * @author wangxin
 * @version 1.0
 */
public interface RoleDao {

    Set<Role> findRolesByUserId(Integer userId);

    /**
     *查找全部角色
     */
    List<Role> findAll();

}
