package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.service.RoleService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * 晴天:
     * 删除角色
     * @param id
     * @return
     */
    @RequestMapping("/deleteRoleById")
    public Result deleteRoleById(Integer id){

        try {
            roleService.deleteRoleById(id);
            return new Result(true, MessageConstant.DELETE_ROLE_SUCCESS);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new Result(false,e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_ROLE_FALL);
        }
    }
}
