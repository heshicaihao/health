package com.itheima.dao;

import com.github.pagehelper.Page;
import com.github.pagehelper.Page;
import com.itheima.pojo.Role;

import java.util.List;
import java.util.Map;
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
     * 晴天:
     * 分页查询
     * @param queryString
     * @return
     */
    Page<Role> getAllRoles(String queryString);

    /**
     *查找全部角色
     */
    List<Role> findAll();


    //根据id查询角色信息
    Role getRoleById(Integer id);

    //更新角色表信息
    void updateById(Map map);
}
