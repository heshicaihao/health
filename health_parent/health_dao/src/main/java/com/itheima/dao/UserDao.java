package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.Setmeal;
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
    /**
     * 根据查询条件 获取的用户列表 分页
     */
    Page<Setmeal> selectByCondition(String queryString);

}
