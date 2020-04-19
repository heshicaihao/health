package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.constant.MessageConstant;
import com.itheima.dao.MenuDao;
import com.itheima.entity.PageResult;
import com.itheima.pojo.Menu;
import com.itheima.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * 菜单服务接口实现类
 */
@Service(interfaceClass = MenuService.class)
@Transactional
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuDao menuDao;

    /**
     * 根据用户id查询菜单列表
     * @param id
     * @return
     */
    @Override
    public List<Menu> findMenuListByUserIdAndRoleId(Integer id) {
        //根据用户id查询菜单列表
        List<Menu> menulist = menuDao.findMenuListByUserIdAndRoleId(id);
        //分为父菜单列表与子菜单列表
        List<Menu> childrenList = new ArrayList<>();
        List<Menu> parentList = new ArrayList<>();
        // 放置父Id
        HashSet<Integer> parentIdsSet = new HashSet<>();

        if (menulist != null && menulist.size() > 0) {
            for (Menu menu : menulist) {
                // 获取父id
                Integer parentMenuId = menu.getParentMenuId();
                //如果父菜单id为空
                if (parentMenuId == null) {
                    parentList.add(menu);
                } else {
                    //父菜单id不为空
//                    childrenList.add(menu);放入set中
                    // 查询父id对应的menu
                    parentIdsSet.add(parentMenuId);
//
                }
            }

            // 遍历父id
            for (Integer parentMenuId : parentIdsSet) {
                // 根据id查询menu
                Menu parentMenu = menuDao.findMenuById(parentMenuId);
                menulist.add(parentMenu);
            }
        }

//
//        //将子菜单放入对应的父菜单的children属性中
//        if (parentList != null && parentList.size() > 0){
//            //得到每一个父菜单对象
//            for (Menu parentMenu : parentList) {
//                if (childrenList != null && childrenList.size() > 0){
//                    List<Menu> list = new ArrayList<>();
//                    //得到每一个子菜单对象
//                    for (Menu childrenMenu : childrenList) {
//                        //将对应的子菜单放入集合
//                        if (childrenMenu.getParentMenuId() == parentMenu.getId()){
//                            list.add(childrenMenu);
//                        }
//                    }
//                    //将子菜单集合放入父菜单对象中
//                    parentMenu.setChildren(list);
//                }
//            }
//        }
//        for (Menu menu : parentList) {
//            if (menu.getChildren() == null || menu.getChildren().size() < 1){
//                parentList.remove(menu);
//            }
//        }
        //只需返回父菜单集合,子菜单在对应的父菜单中
        return menulist;
    }


    /**
     * 晴天:
     * 新增菜单
     * @param menu
     */
    @Override
    public void addMenu(Menu menu) {
        //判断是否输入parentMenuId,有就允许添加,没有久不允许添加
        Integer parentMenuId = menu.getParentMenuId();
        int count = menuDao.findcountByparentMenuId(parentMenuId);
        if (count<=0){
            throw new RuntimeException(MessageConstant.ADD_MENUS_FALL);
        }
        menuDao.addMenu(menu);
    }

    /**
     * 晴天:
     * 分页查询
     * @param currentPage
     * @param pageSize
     * @param queryString
     * @return
     */
    @Override
    public PageResult getMenus(Integer currentPage, Integer pageSize, String queryString) {
        //调用分页插件
        PageHelper.startPage(currentPage,pageSize);
        //
        Page<Menu> menuPage = menuDao.getMenus(queryString);
        return new PageResult(menuPage.getTotal(),menuPage.getResult());
    }

    /**
     * 晴天:
     * 弹出编辑窗口数据
     * @param id
     * @return
     */
    @Override
    public Menu getMenuById(Integer id) {

        return menuDao.getMenuById(id);
    }

    /**
     * 晴天:
     * 编辑菜单
     * @param menu
     */
    @Override
    public void updateMenuById(Menu menu) {
        menuDao.updateMenuById(menu);
    }

    /**
     * 晴天:
     * 删除菜单
     * @param id
     */
    @Override
    public void deleteMenuById(Integer id) {
        //判断role和menu是否存在关联
        int count = menuDao.findroleIdcountBymenuId(id);
        if (count>0){
            throw new RuntimeException(MessageConstant.DELETE_MENU_ROLE_FALL);
        }

        menuDao.deleteMenuById(id);
    }

    /**lcl
     * 查询所有菜单 不分页lcl
     */
    @Override
    public List<Menu> getAllMenus() {
        return menuDao.getAllMenus();
    }

    /**
     * 根据roleId获取关联menuIds
     */
    @Override
    public List<Integer> getMenuIdsByRoleId(Integer id) {
        return menuDao.getMenuIdsByRoleId(id);
    }
}
