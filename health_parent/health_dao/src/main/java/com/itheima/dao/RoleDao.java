package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.Role;

import java.util.Set;

/**
 * 角色接口层
 * @author wangxin
 * @version 1.0
 */
public interface RoleDao {

    Set<Role> findRolesByUserId(Integer userId);

    /**
     * 晴天:
     * 分页查询
     * @param queryString
     * @return
     */
    Page<Role> getAllRoles(String queryString);
}
