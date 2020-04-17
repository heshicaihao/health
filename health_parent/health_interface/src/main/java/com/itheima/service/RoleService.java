package com.itheima.service;

import com.itheima.pojo.Role;

import java.util.List;

/**
 * 角色接口
 * heshicaihao
 */
public interface RoleService {

    /**
     * 查询所有角色列表
     */
    List<Role> findAll();

}
