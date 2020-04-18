package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.Role;
import com.itheima.pojo.User;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.Role;
import com.itheima.service.RoleService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 角色服务
import java.util.List;
import java.util.Set;

/**
 * 角色模块控制层
 * @author heshicaihao
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
     * 晴天:
     * 分页查询
     * @param queryPageBean
     * @return
     */
    @RequestMapping("/getAllRoles")
    public PageResult getAllRoles(@RequestBody QueryPageBean queryPageBean){
        try {
            PageResult pageResult = roleService.getAllRoles(queryPageBean.getCurrentPage(),queryPageBean.getPageSize(),queryPageBean.getQueryString());
            return pageResult;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

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


    /**
     * 新增角色
     */
    @RequestMapping("/addRoles")
    public Result addRoles(){
        return null;
    }


    /**
     * 根据角色id删除角色
     */
    @RequestMapping("/deleteRoleById")
    public Result deleteRoleById(){
        return null;
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




}
