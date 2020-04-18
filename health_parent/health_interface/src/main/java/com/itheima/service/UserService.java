package com.itheima.service;

import com.itheima.entity.PageResult;
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

    /**
     * 获取当用户的用户列表 分页
     */
    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    /**
     * 新增用户
     * @param user
     * @param roleIds
     */
    void add(com.itheima.pojo.User user, Integer[] roleIds);
}
