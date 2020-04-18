package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.UserDao;
import com.itheima.entity.PageResult;
import com.itheima.pojo.Setmeal;
import com.itheima.pojo.User;
import com.itheima.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户服务接口实现类
 * @author wangxin
 * @version 1.0
 */
@Service(interfaceClass = UserService.class)
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    /**
     * 根据用户名查询数据用户信息
     * @param username
     * @return
     */
    @Override
    public User findByUserName(String username) {
        return userDao.findByUserName(username);
    }

    /**
     * 获取当用户的用户列表 分页
     */
    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage, pageSize);
        //2.需要分页的语句
        Page<Setmeal> setmealPage = userDao.selectByCondition(queryString);
        return new PageResult(setmealPage.getTotal(), setmealPage.getResult());
    }

    /**
     * 新增用户
     * @param user
     * @param roleIds
     */
    @Override
    public void add(User user, Integer[] roleIds) {
        //userDao
        userDao.add(user);
        //往角色表和用户中间表写记录(此方法有其它功能用 代码抽取)
        setRoleIdAndUser(user.getId(), roleIds);
    }

    /*修改用户密码*/

    @Override
    public void updatePassword(Map<String, Object> map) {

        userDao.updatePassword(map);

    }

    /**
     *往角色表和用户中间表写记录(此方法有其它功能用 代码抽取)
     * @param user_id
     * @param roleIds
     */
    private void setRoleIdAndUser(Integer user_id, Integer[] roleIds) {
        if (roleIds != null && roleIds.length > 0) {
            for (Integer role_id : roleIds) {
                //为了方便测试传入map对象
                Map<String, Integer> map = new HashMap<>();
                map.put("user_id", user_id);
                map.put("role_id", role_id);
                userDao.setRoleIdAndUser(map);
            }
        }
    }


}
