package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.pojo.Menu;

import java.util.List;

/**
 * 菜单服务接口
 */
public interface MenuService {

    //根据用户id查询用户的菜单列表
    List<Menu> findMenuListByUserIdAndRoleId(Integer id);


    /**
     * 晴天:
     * 新增菜单
     * @param menu
     */
    void addMenu(Menu menu);

    /**
     * 晴天:
     * 分页查询
     * @param currentPage
     * @param pageSize
     * @param queryString
     * @return
     */
    PageResult getMenus(Integer currentPage, Integer pageSize, String queryString);

    /**
     * 晴天:
     * 弹出编辑窗口数据
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
     * 删除菜单
     * @param id
     */
    void deleteMenuById(Integer id);

    //查询所有菜单
    List<Menu> getAllMenus();

    //根据roleId获取关联的menuIds
    List<Integer> getMenuIdsByRoleId(Integer id);
}
