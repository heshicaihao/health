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


}
