package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.constant.MessageConstant;
import com.itheima.dao.MenuDao;
import com.itheima.pojo.Menu;
import com.itheima.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

        if (menulist != null && menulist.size() > 0) {
            for (Menu menu : menulist) {
                Integer parentMenuId = menu.getParentMenuId();
                //如果父菜单id为空
                if (parentMenuId == null) {
                    parentList.add(menu);
                } else {
                    //父菜单id不为空
                    childrenList.add(menu);
                }
            }
        }
        //将子菜单放入对应的父菜单的children属性中
        if (parentList != null && parentList.size() > 0){
            //得到每一个父菜单对象
            for (Menu parentMenu : parentList) {
                if (childrenList != null && childrenList.size() > 0){
                    List<Menu> list = new ArrayList<>();
                    //得到每一个子菜单对象
                    for (Menu childrenMenu : childrenList) {
                        //将对应的子菜单放入集合
                        if (childrenMenu.getParentMenuId() == parentMenu.getId()){
                            list.add(childrenMenu);
                        }
                    }
                    //将子菜单集合放入父菜单对象中
                    parentMenu.setChildren(list);
                }
            }
        }
        //只需返回父菜单集合,子菜单在对应的父菜单中
        return parentList;
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
}
