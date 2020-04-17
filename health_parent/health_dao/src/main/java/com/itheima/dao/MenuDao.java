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
}
