package com.itheima.mysecurity;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.pojo.Permission;
import com.itheima.pojo.Role;
import com.itheima.pojo.User;
import com.itheima.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 认证授权类
 * @author wangxin
 * @version 1.0
 */
@Component
public class SpringSecurityUserService implements UserDetailsService {

    //调用服务查询用户信息（认证）
    @Reference
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //1.根据用户名查询数据用户信息
        User user = userService.findByUserName(username);

        //2.判断用户为空
        if(user == null){
            return  null;
        }
        //3.用户不为空
        String password = user.getPassword();///数据库已经加密的密码 bcryt

        //4.授权
        //定义一个空的list权限列表
        List<GrantedAuthority> list = new ArrayList<>();
        Set<Role> roles = user.getRoles();
        if(roles != null && roles.size()>0){
            for (Role role : roles) {
                Set<Permission> permissions = role.getPermissions();
                if(permissions!= null && permissions.size()>0){

                    for (Permission permission : permissions) {
                        //授予当前用户普通权限关键字  CHECKITEM_ADD（t_permission）
                        list.add(new SimpleGrantedAuthority(permission.getKeyword()));
                    }
                }
                //授予当前用户角色权限关键字  ROLE_ADMIN (t_role)
                list.add(new SimpleGrantedAuthority(role.getKeyword()));
            }
        }
        return new org.springframework.security.core.userdetails.User(username,password,list);
    }
}
