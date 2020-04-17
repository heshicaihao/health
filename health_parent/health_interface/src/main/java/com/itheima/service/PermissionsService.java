package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.pojo.Permission;

public interface PermissionsService {

    /**
     * 查询所有权限
     * 分页查询
     */
    PageResult getAllPermissions(Integer currentPage, Integer pageSize, String queryString);

    /**
     *  编辑弹窗查询id
     * @param id
     * @return
     */
    Permission getPermissionById(Integer id);

    /**
     * 添加
     */
    void addPermission(Permission permission);
}
