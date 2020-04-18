package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.Role;

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
}
