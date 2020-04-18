package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.Permission;

import java.util.List;
import java.util.Set;

/**
 * @author wangxin
 * @version 1.0
 */
public interface PermissionDao {

    /**
     *
     * 分页查询
     */
    List<Permission> getAllPermissions(String queryString);

    /**
     * 编辑弹窗查询id
     * @param id
     * @return
     */
    Permission getPermissionById(Integer id);

    /**
     * 添加
     * @param permission
     */
    void addPermission(Permission permission);

    /**
     * 查询所有
     * @return
     */
    List<Permission> findAll();


    /**
     *添加名称数量查询
     */
    int findPermissionByNameCount(String name);

    /**
     * 添加关键字数量查询
     */
    int findPermissionByKeywordCount(String keyword);

    /**
     * 删除
     */
    void deletePermissionById(Integer id);

    /**
     * 编辑查询名称
     */
    int findPermissionsRoleById(Integer id);

    /**
     * 编辑修改
     */
    void updatePermissionById(Permission permission);

    /**
     * 查询所有权限
     * 不分页
     */
    List<Permission> getAllPermission();

    /**
     * 根据roleId查询关联的permissionIds
     */
    List<Integer> getPermissionIdsByRoleId(Integer id);
}
