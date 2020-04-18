package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.Setmeal;
import com.itheima.pojo.User;

import java.util.Map;

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
    /**
     * 根据查询条件 获取的用户列表 分页
     */
    Page<Setmeal> selectByCondition(String queryString);

    /**
     * 往角色表和用户中间表写记录
     * @param map
     */
    void setRoleIdAndUser(Map<String, Integer> map);

    /**
     *
     *  新增用户
     * @param user
     */
    void add(User user);

}
