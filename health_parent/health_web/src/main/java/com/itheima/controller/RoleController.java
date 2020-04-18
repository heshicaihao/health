package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Role;
import com.itheima.service.RoleService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 角色服务
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Reference
    private RoleService roleService;

    /**
     * 根据id查询角色信息
     */
    @RequestMapping("/getRoleById")
    public Result getRoleById(Integer id){
        try {
            Role role = roleService.getRoleById(id);
            return new Result(true, MessageConstant.GET_ROLE_SUCCESS,role);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_ROLE_FAIL);
        }
    }

    /**
     * 修改角色信息
     */
    @RequestMapping("/updateRoleById")
    public Result updateRoleById(@RequestBody Map map){
        try {
            roleService.updateRoleById(map);
            return new Result(true,MessageConstant.EDIT_ROLE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_ROLE_FAIL);
        }
    }

}
