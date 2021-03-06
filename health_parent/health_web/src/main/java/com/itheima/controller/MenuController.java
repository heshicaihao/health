package com.itheima.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.Menu;
import com.itheima.service.MenuService;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Menu;
import com.itheima.service.MenuService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 菜单服务
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Reference
    private MenuService menuService;

    /**
     * 晴天:
     * 新增菜单
     * @param menu
     * @return
     */
    @RequestMapping("/addMenu")
    public Result addMenu(@RequestBody Menu menu){
        try {
            menuService.addMenu(menu);
            return new Result(true, MessageConstant.ADD_MENU_SUCCESS);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new Result(false,e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_MENU_FAIL);
        }
    }

    /**
     * 晴天:
     * 分页查询
     * @param queryPageBean
     * @return
     */
    @RequestMapping("/getMenus")
    public PageResult getAllMenus(@RequestBody QueryPageBean queryPageBean){
        try {
            PageResult pageResult = menuService.getMenus(queryPageBean.getCurrentPage(),queryPageBean.getPageSize(),queryPageBean.getQueryString());
            return pageResult;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 晴天:
     * 弹出编辑窗口
     * @param id
     * @return
     */
    @RequestMapping("/getMenuById")
    public Result getMenuById(Integer id){
        try {
             Menu menu = menuService.getMenuById(id);
            return new Result(true,MessageConstant.CHECK_MENU_SUCCESS,menu);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.CHECK_MENU_FALL);
        }
    }


    /**
     * 晴天:
     * 编辑菜单
     * @param menu
     * @return
     */
    @RequestMapping("/updateMenuById")
    public Result updateMenuById(@RequestBody Menu menu){
        try {
            menuService.updateMenuById(menu);
            return new Result(true,MessageConstant.UPDATE_MENU_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.UPDATE_MENU_FALL);
        }
    }



    /**
     * 晴天:
     * 删除菜单
     * @param id
     * @return
     */
    @RequestMapping("/deleteMenuById")
    public Result deleteMenuById(Integer id){
        try {
            menuService.deleteMenuById(id);
            return new Result(true,MessageConstant.DELETE_MENU_SUCCESS);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new Result(false,e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_MENU_FALL);
        }
    }
    /**
     * 查询所有菜单
     */
    @RequestMapping("/getAllMenus")
    public Result getAllMenus(){
        try {
            List<Menu> menuList = menuService.getAllMenus();
            return new Result(true, MessageConstant.GET_ALLMENU_SUCCESS,menuList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_ALLMENU_FAIL);
        }
    }

    /**
     * 根据roleId查询关联的menuIds
     */
    @RequestMapping("/getMenuIdsByRoleId")
    public Result getMenuIdsByRoleId(Integer id){
        try {
            List<Integer> menuIds = menuService.getMenuIdsByRoleId(id);
            return new Result(true,MessageConstant.GET_ROLEWITHMENU_SUCCESS,menuIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_ROLEWITHMENU_FAIL);
        }
    }

}
