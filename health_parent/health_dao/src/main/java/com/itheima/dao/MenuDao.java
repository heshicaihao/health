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
}
