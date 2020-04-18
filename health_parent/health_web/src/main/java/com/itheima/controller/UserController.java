package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisConstant;
import com.itheima.constant.UserStationConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.Role;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import com.itheima.service.UserService;
import org.mindrot.jbcrypt.BCrypt;
import com.itheima.pojo.Menu;
import com.itheima.service.MenuService;
import com.itheima.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户模块控制层
 * @author wangxin
 * @version 1.0
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Reference
    private UserService userService;

    @Reference
    private MenuService menuService;

    /**
     * 获取当前登录用户的用户名
     */
    @RequestMapping("/getUsername")
    public Result getUsername(){
        //.getContext().安全容器对象
        //getAuthentication():认证对象
        //.getPrincipal();当前用户对象 user
        try {
            User loginUser = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            //根据当前用户对象username,得到com.itheima.pojo.User对象
            com.itheima.pojo.User user = userService.findByUserName(loginUser.getUsername());

            //调用远程菜单服务,获取当前用户对应的菜单列表
            List<Menu> menuList = menuService.findMenuListByUserIdAndRoleId(user.getId());

            //组合数据返回给页面
            Map<String,Object> map = new HashMap<>();
            map.put("username",loginUser.getUsername());
            map.put("menuList",menuList);
            return new Result(true, MessageConstant.GET_USERNAME_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_USERNAME_FAIL);
        }
    }

    /**
     * 获取当用户的用户列表 分页
     */
    @RequestMapping("/findPage")
    public PageResult getUserlist(@RequestBody QueryPageBean queryPageBean) {
        try {
            PageResult pageResult = userService.findPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize(), queryPageBean.getQueryString());
            List<com.itheima.pojo.User> rows = pageResult.getRows();
            for (int i = 0; i < rows.size(); i++) {
                com.itheima.pojo.User user =rows.get(i);
                Set<Role> roles = user.getRoles();
                String rolesString  = "";
                for(Role role : roles){
                    if ("".equals(rolesString)){
                        rolesString = rolesString+role.getName();
                    }else {
                        rolesString = rolesString+","+role.getName();
                    }
                }
                user.setRoles_list_show(rolesString);

            }
            return pageResult;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 新增用户
     */
    @RequestMapping("/add")
    public Result add(@RequestBody com.itheima.pojo.User user, Integer[] roleIds) {
        try {
            //设置初始状态 正常
            user.setStation(UserStationConstant.NORMAL);
            //默认密码加密
            user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
            userService.add(user, roleIds);
            return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_SETMEAL_FAIL);
        }
    }

}
