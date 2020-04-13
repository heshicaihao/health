package com.itheima.dao;

import com.itheima.pojo.User;

/**
 * 用户持久层接口
 * @author wangxin
 * @version 1.0
 */
public interface UserDao {
    /**
     * 根据用户名查询数据用户信息
     * @param username
     * @return
     */
    User findByUserName(String username);
}
