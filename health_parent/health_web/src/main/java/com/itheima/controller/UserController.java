package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.constant.UserStationConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.Role;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import com.itheima.service.UserService;
import com.sun.org.apache.regexp.internal.RE;
import org.mindrot.jbcrypt.BCrypt;
import com.itheima.pojo.Menu;
import com.itheima.service.MenuService;
import com.itheima.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.lang.annotation.Retention;
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
    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private JedisPool jedisPool;

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
            return new Result(true, MessageConstant.ADD_USER_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_USER_FAIL);
        }
    }



    @RequestMapping("/updatePassword")
    public Result updatePassword(@RequestBody Map<String,Object> map) {
        String username =(String) map.get("user");
        String oldPassword = (String)map.get("oldPassword");
        String newPassword = (String)map.get("NewPassword");

        String encodeNewPassword = encoder.encode(newPassword);//获取新密码密文
        map.put("encodeNewPassword",encodeNewPassword);

        com.itheima.pojo.User user = userService.findByUserName(username);
        if(user !=null){
            boolean flag = encoder.matches(oldPassword, user.getPassword());//验证旧密码是否正确
            if(flag){

                try {
                    userService.updatePassword(map);
                    return new Result(true,MessageConstant.UPDATE_USER_PASSWORD_SUCCESS);
                } catch (Exception e) {
                    e.printStackTrace();
                    return new Result(flag,MessageConstant.UPDATE_USER_PASSWORD_FAIL);
                }
            }


        }

        return new Result(false,MessageConstant.UPDATE_USER_PASSWORD_FAIL);


    }
    /**
     * 根据id找 用户信息
     *
     * @return
     */
    @RequestMapping("/findById")
    public Result findById(Integer id) {
        try {
            com.itheima.pojo.User user = userService.findById(id);
            return new Result(true, MessageConstant.GET_PERMISSION_SUCCESS, user);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_PERMISSION_FAIL);
        }
    }

    /**
     * 编辑用户
     */
    @RequestMapping("/edit")
    public Result edit(@RequestBody com.itheima.pojo.User user, Integer[] roleIds) {
        try {
            userService.edit(user,roleIds);
            return new Result(true, MessageConstant.EDIT_MEMBER_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_MEMBER_FAIL);
        }
    }

    /**
     * 删除用户 只是标注 停用
     */
    @RequestMapping("/deleteById")
    public Result deleteById(Integer id) {
        try {
            userService.deleteById(id);
            return new Result(true, MessageConstant.DELETE_MEMBER_SUCCESS);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new Result(false, e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_MEMBER_FAIL);
        }
    }

    /**
     * 用户忘记密码 验证手机 重置密码
     */
    @RequestMapping("/forgotPassword")
    public Result forgotPassword(@RequestBody Map map) {
        try {
            String verification_code = (String)map.get("verification_code");
            String telephone = (String)map.get("telephone");
            String redisCode = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_GETPWD);
            if(verification_code == null || redisCode == null || !verification_code.equals(redisCode)){
                //redis 跟用户输入的验证码不一致直接返回
                return new Result(false, MessageConstant.VALIDATECODE_ERROR);
            }
            userService.forgotPassword(map);
            return new Result(true,MessageConstant.FORGOTPASSWORD_SUCCESS);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new Result(false, e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.FORGOTPASSWORD_FAIL);
        }

    }




}
