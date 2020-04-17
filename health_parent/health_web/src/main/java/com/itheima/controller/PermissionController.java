package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.Permission;
import com.itheima.service.PermissionsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/permission")
public class PermissionController {
    @Reference
    PermissionsService permissionsService;

    /**
     * 查询所有权限
     * 分页查询
     */
    @RequestMapping("/getAllPermissions")
    public Result getAllPermissions(@RequestBody QueryPageBean queryPageBean) {
        try {
            PageResult PageResult = permissionsService.getAllPermissions(queryPageBean.getCurrentPage(),queryPageBean.getPageSize(),queryPageBean.getQueryString());
            return new Result(true,MessageConstant.GET_PERMISSION_SUCCESS,PageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_PERMISSION_FAIL);
        }
    }

    /**
     * 编辑弹窗查询id
     * @param id
     * @return
     */
    @RequestMapping("/getPermissionById")
    public Result getPermissionById(Integer id){
        try {
            Permission permission = permissionsService.getPermissionById(id);
            return new Result(true, MessageConstant.GET_PERMISSION_SUCCESS,permission);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_PERMISSION_FAIL);
        }
    }
    /**
     * 添加
     */
    @RequestMapping("/addPermission")
    public Result addPermission(@RequestBody Permission permission){
        try {
            permissionsService.addPermission(permission);
            return new Result(true,MessageConstant.ADD_PERMISSION_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return  new Result(false,MessageConstant.ADD_PERMISSION_FAIL);
        }
    }

    /**
     * 删除
     */
    @RequestMapping("/deletePermissionById")
    public Result deletePermissionById(Integer id){
        try {
            permissionsService.deletePermissionById(id);
            return new Result(true,MessageConstant.DELETE_PERMISSION_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_PERMISSION_FAIL);
        }
    }
    /**
     * 编辑
     */


    /*@RequestMapping("/updatePermissionById")
    public Result updatePermissionById(@RequestBody Permission permission){
     permissionsService.updatePermissionById(permission);
        return new Result(true,MessageConstant.EDIT_PERMISSION_SUCCESS);

    }*/
}
