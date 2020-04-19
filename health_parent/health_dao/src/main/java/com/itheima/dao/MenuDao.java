package com.itheima.dao;

import com.github.pagehelper.Page;
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
     * 晴天:
     * 新增菜单:1.判断父id是否存在,存在可添加,不存在不添加
     * @param parentMenuId
     * @return
     */
    int findcountByparentMenuId(Integer parentMenuId);

    /**
     * 晴天:
     * 新增菜单:2.添加菜单项
     * @param menu
     */
    void addMenu(Menu menu);

    /**
     * 晴天:
     * 分页查询
     * @param queryString
     * @return
     */
    Page<Menu> getMenus(String queryString);

    /**
     * 晴天:
     * 弹出编辑窗口的数据
     * @param id
     * @return
     */
    Menu getMenuById(Integer id);

    /**
     * 晴天:
     * 编辑菜单
     * @param menu
     */
    void updateMenuById(Menu menu);

    /**
     * 晴天:
     * 判断是否存在关联表
     * @param id
     * @return
     */
    int findroleIdcountBymenuId(Integer id);

    /**
     * 晴天:
     * 删除菜单
     * @param id
     */
    void deleteMenuById(Integer id);

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

    /**
     * 根据id查询menu
     * @param parentMenuId
     * @return
     */
    Menu findMenuById(Integer parentMenuId);
}
