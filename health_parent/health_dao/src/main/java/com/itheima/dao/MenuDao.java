package com.itheima.dao;

import com.itheima.pojo.Menu;

import java.util.List;

/**
 * 菜单服务Dao
 */
public interface MenuDao {
    /**
     *  根据用户id查询用户的菜单列表
     * @param id
     * @return
     */
    List<Menu> findMenuListByUserIdAndRoleId(Integer id);

    /**
     * 查询所有菜单不分页
     */
    List<Menu> getAllMenus();

    /**
     * 根据roleId获取menuIds
     */
    List<Integer> getMenuIdsByRoleId(Integer id);

    /**
     * 查询子菜单对应的父菜单id
     * @param childrenMenuId
     * @return
     */
    Integer findParentIdById(Integer childrenMenuId);
}
