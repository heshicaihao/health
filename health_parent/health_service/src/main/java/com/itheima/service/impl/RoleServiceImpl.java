package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.RoleDao;
import com.itheima.pojo.Role;
import com.itheima.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 角色服务 实现类
 * heshicaihao
 *
 */
@Service(interfaceClass = RoleService.class)
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Override
    public List<Role> getAllRoles() {
        return null;
    }

    @Override
    public List<Integer> getAllRoleIds() {
        return null;
    }

    @Override
    public List<Integer> getRoleIdsByUserId(Integer userId) {
        return null;
    }

    @Override
    public void addRoles(Role role) {

    }

    @Override
    public void bindRoleAndPermissions(Map map) {

    }

    @Override
    public void releaseRoleAndPermissions(Map map) {

    }

    @Override
    public Role getRoleById(Integer roleId) {
        return null;
    }

    @Override
    public void updateRoleById(Integer roleId) {

    }

    @Override
    public void deleteRoleById(Integer roleId) {

    }

    /**
     * 查询所有角色列表
     *
     * @return
     */
    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }

}
