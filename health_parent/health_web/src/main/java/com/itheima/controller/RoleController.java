package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Menu;
import com.itheima.service.MenuService;
import com.itheima.service.RoleService;
import com.itheima.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 用户模块控制层
 * @author wangxin
 * @version 1.0
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Reference
    private RoleService roleService;

    /**
     * 获取所有角色
     */
    @RequestMapping("/getAllRoles")
    public Result getAllRoles(){
        return null;
    }

    /**
     * 新增角色
     */
    @RequestMapping("/addRoles")
    public Result addRoles(){
        return null;
    }

    /**
     * 根据角色id修改角色
     */
    @RequestMapping("/updateRoleById")
    public Result updateRoleById(){
        return null;
    }


    /**
     * 根据角色id删除角色
     */
    @RequestMapping("/deleteRoleById")
    public Result deleteRoleById(){
        return null;
    }




}
