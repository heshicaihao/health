package com.itheima.service;

import com.itheima.pojo.Role;

import java.util.List;
import java.util.Map;

/**
 * 角色接口
 */
public interface RoleService {

    //    获取所有角色:getAllRoles
    List<Role> getAllRoles();

    //    获取所有角色的id:getAllRoleIds
    List<Integer> getAllRoleIds();

    //❤根据用户id获取用户所有角色的id:getRoleIdsByUserId
    List<Integer> getRoleIdsByUserId(Integer userId);

    //    新增角色:addRoles
    void addRoles(Role role);

    //    绑定权限与角色关系:bindRoleAndPermissions
    void bindRoleAndPermissions(Map map);

    //    删除权限与角色关系:releaseRoleAndPermissions
    void releaseRoleAndPermissions(Map map);

    //    根据角色id获取角色:getRoleById
    Role getRoleById(Integer roleId);

    //    根据角色id修改角色:updateRoleById
    void updateRoleById(Integer roleId);

    //    根据角色id删除角色:deleteRoleById
    void deleteRoleById(Integer roleId);

}
