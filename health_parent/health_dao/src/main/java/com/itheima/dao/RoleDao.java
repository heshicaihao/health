package com.itheima.dao;

import com.github.pagehelper.Page;
import com.github.pagehelper.Page;
import com.itheima.pojo.Role;

import java.util.List;
import java.util.Map;
import java.util.List;
import java.util.Map;
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
    Page<Role> getRoles(String queryString);

    /**
     *查找全部角色
     */
    List<Role> findAll();


    /**
     * 晴天:
     * 删除角色
     * 1.根据role的id查询与menu表是否有关联
     * @param id
     * @return
     */
    int findmenuIdcountByroleId(Integer id);

    /**
     * 晴天:
     * 删除角色
     * 2.根据role的id查询与permission表是否有关联
     * @param id
     * @return
     */
    int findpermissionIdcountByroleId(Integer id);

    /**
     * 晴天:
     * 删除角色
     * 3.根据role的id查询与user表是否有关联
     * @param id
     * @return
     */
    int finduserIdcountByroleId(Integer id);

    /**
     * 晴天:
     * 删除角色
     * 4.根据role的id删除角色
     * @param id
     */
    void deleteRoleById(Integer id);

    //根据id查询角色信息
    Role getRoleById(Integer id);

    //更新角色表信息
    void updateById(Map map);

    void addRoles(Role role);
    /**
     *  添加角色和菜单关系表
     */

    void bindRoleAndMenu(Map<String, Integer> rsMap);

    /**
     *  添加角色和权限关系表
     */
    void bindRoleAndPermission(Map<String, Integer> rsMap);

    /**
     * 根据userId获取用户role
     * @param id
     * @return
     */
    List<Integer> getRoleIdsByUserId(Integer id);
}
