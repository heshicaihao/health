package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.StringUtil;
import com.itheima.constant.MessageConstant;
import com.itheima.dao.PermissionDao;
import com.itheima.entity.PageResult;
import com.itheima.pojo.Permission;
import com.itheima.service.PermissionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = PermissionsService.class)
@Transactional
public class PermissionsServiceImpl implements PermissionsService {
    @Autowired
    PermissionDao permissionDao;

    /**
     * 查询所有权限
     * 分页查询
     */
    @Override
    public PageResult getAllPermissions(Integer currentPage, Integer pageSize, String queryString) {
        List<Permission> PermissionPage=null;
        if (StringUtil.isEmpty(queryString)){
            PageHelper.startPage(currentPage,pageSize);
            PermissionPage=permissionDao.findAll();
        }else {
            PageHelper.startPage(currentPage, pageSize);
            PermissionPage = permissionDao.getAllPermissions(queryString);
        }
        PageInfo<Permission> pageInfo = new PageInfo<>(PermissionPage);
        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }

    /**
     *  编辑弹窗查询id
     * @param id
     * @return
     */
    @Override
    public Permission getPermissionById(Integer id) {
        return permissionDao.getPermissionById(id);
    }

    /**
     * 添加
     */
    @Override
    public void addPermission(Permission permission) {
        if (permission != null) {
            int count1 = permissionDao.findPermissionByNameCount(permission.getName());
            if (count1 > 0) {
                throw new RuntimeException(MessageConstant.ADD_PERMISSION_SUCCESS);
            }
            int count2 = permissionDao.findPermissionByKeywordCount(permission.getKeyword());
            if (count2 > 0) {
                throw new RuntimeException(MessageConstant.ADD_PERMISSION_FAIL);
            }
            permissionDao.addPermission(permission);
        }
    }
}

