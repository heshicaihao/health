package com.itheima.service;

import com.itheima.pojo.Menu;

import java.util.List;

/**
 * 菜单服务接口
 */
public interface MenuService {

    //根据用户id查询用户的菜单列表
    List<Menu> findMenuListByUserIdAndRoleId(Integer id);
}
