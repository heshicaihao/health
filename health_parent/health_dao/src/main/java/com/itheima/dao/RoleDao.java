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

    //根据id查询角色信息
    Role getRoleById(Integer id);

    //更新角色表信息
    void updateById(Map map);
}
