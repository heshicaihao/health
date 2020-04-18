package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.RoleDao;
import com.itheima.entity.PageResult;
import com.itheima.pojo.Role;
import com.itheima.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service(interfaceClass = RoleService.class)
@Transactional
public class RoleServiceImpl implements RoleService{

    @Autowired
    private RoleDao roleDao;


    /**
     * 晴天:
     * 分页查询
     * @param currentPage
     * @param pageSize
     * @param queryString
     * @return
     */
    @Override
    public PageResult getAllRoles(Integer currentPage, Integer pageSize, String queryString) {
        //引用分页插件
        PageHelper.startPage(currentPage,pageSize);
        //分页查询语句
        Page<Role> rolePage = roleDao.getAllRoles(queryString);
        return new PageResult(rolePage.getTotal(),rolePage.getResult());
    }
}
