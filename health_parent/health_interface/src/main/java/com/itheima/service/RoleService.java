package com.itheima.service;

import com.itheima.pojo.Role;

import java.util.Map;

/**
 * 角色服务接口
 */
public interface RoleService {

    //根据id查询角色信息
    Role getRoleById(Integer id);

    //编辑角色信息
    void updateRoleById(Map map);
}
