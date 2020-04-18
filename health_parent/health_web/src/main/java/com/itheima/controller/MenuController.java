package com.itheima.controller;

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

}
