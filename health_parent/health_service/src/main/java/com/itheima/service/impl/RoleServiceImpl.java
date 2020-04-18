package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.MenuDao;
import com.itheima.dao.PermissionDao;
import com.itheima.dao.RoleDao;
import com.itheima.pojo.Role;
import com.itheima.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ljw
 * @Date: 2020/4/18 15:23
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

    @Override
    public void addRoles(Map map) {
        //查询所有的permissionIds
        List<Integer> permissionIds = (List<Integer>) map.get("permissionIds");
        //获取菜单ids
        List<Integer> menuIds = (List<Integer>) map.get("menuIds");
        //添加角色
        Role role = new Role();
        role.setName((String) map.get("name"));
        role.setKeyword((String) map.get("keyword"));
        role.setDescription((String) map.get("description"));
        roleDao.addRoles(role);

        Map<String,Integer> rsMap = new HashMap<>();
        rsMap.put("roleId",role.getId());
        //保存到对应的权限角色表
        if (permissionIds != null && permissionIds.size() >0) {
            for (Integer permissionId : permissionIds) {
                rsMap.put("permissionId",permissionId);
                roleDao.bindRoleAndPermission(rsMap);
            }
        }
        //保存到对应的菜单表
        if (menuIds != null && menuIds.size() > 0) {
            for (Integer menuId : menuIds) {
                rsMap.put("menuId",menuId);
                roleDao.bindRoleAndMenu(rsMap);
            }
        }
    }
}
