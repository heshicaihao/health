package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.Setmeal;
import com.itheima.pojo.User;

import java.util.List;
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

    /*修改用户密码*/
    void updatePassword(Map map);

    /**
     *  根据id找 用户信息
     * @param id
     * @return
     */
    User findById(Integer id);

    /**
     * 编辑用户
     * @param user
     */
    void edit(User user);


    /**
     * 先删除用户关联角色记录 （中间表）
     * @param id
     */
    void deleteAssociation(Integer id);

    /**
     * 根据手机号码查找 用户
     * @param telephone
     * @return
     */
    List<User> findByTelephone(String telephone);


    /**
     * 手机修改用户密码
     * @param user
     */
    void changePassword(User user);
}
