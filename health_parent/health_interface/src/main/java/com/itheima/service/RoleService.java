package com.itheima.service;

import com.itheima.pojo.Role;
import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * 查询所有角色列表
     */
    List<Role> findAll();


    /**
     * 晴天:
     * 分页查询
     * @param currentPage
     * @param pageSize
     * @param queryString
     * @return
     */
    PageResult getAllRoles(Integer currentPage, Integer pageSize, String queryString);

    /**
     * 晴天:
     * 删除角色
     * @param id
     */
    void deleteRoleById(Integer id);
}
