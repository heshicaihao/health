package com.itheima.service;

import com.itheima.pojo.User;

/**
 *
 * 用户服务接口
 * @author wangxin
 * @version 1.0
 */
public interface UserService {
    /**
     *根据用户名查询数据用户信息
     * @param username
     * @return
     */
    User findByUserName(String username);
}
