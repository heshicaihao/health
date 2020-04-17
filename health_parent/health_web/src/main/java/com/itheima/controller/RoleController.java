package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.Role;
import com.itheima.pojo.User;
import com.itheima.service.RoleService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * 角色模块控制层
 * @author heshicaihao
 * @version 1.0
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Reference
    private RoleService roleService;


    /**
     * 查询所有角色列表
     *
     * @return
     */
    @RequestMapping("/findAll")
    public Result findAll() {
        try {
            List<Role> roleList = roleService.findAll();
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, roleList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }

}
