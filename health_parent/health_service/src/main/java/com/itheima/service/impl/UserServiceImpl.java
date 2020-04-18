package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.constant.UserStationConstant;
import com.itheima.dao.UserDao;
import com.itheima.entity.PageResult;
import com.itheima.pojo.Setmeal;
import com.itheima.pojo.User;
import com.itheima.service.UserService;
import com.itheima.utils.QiniuUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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

    @Override
    public User findById(Integer id) {
        return userDao.findById(id);
    }

    @Override
    public void edit(User user, Integer[] roleIds) {
        //更新用户表 update 语句
        userDao.edit(user);
        //先删除用户关联角色记录 （中间表）
        userDao.deleteAssociation(user.getId());
        // 重新建立关联关系（插入中间表）
        setRoleIdAndUser(user.getId(), roleIds);
    }

    /**
     * 删除用户 只是标注 停用
     * @param id
     */
    @Override
    public void deleteById(Integer id) {
        //1.根据用户id 查询数据库用户对象
        User user = userDao.findById(id);
        //设置用户状态为停用
        user.setStation(UserStationConstant.STOP_USING);

        //更新用户表 update 语句
        userDao.edit(user);
        //先删除用户关联角色记录 （中间表）
        userDao.deleteAssociation(user.getId());
    }

    /**
     *
     * 忘记密码
     * @param map
     */
    @Override
    public void forgotPassword(Map map) {
        String telephone = (String)map.get("telephone");
        String password = (String)map.get("password");
        if ("".equals(telephone)){
            throw new RuntimeException("此用户手机号码不能为空");
        }else {
            User user = userDao.findByTelephone(telephone);
            if(user==null){
                throw new RuntimeException("此用户没有登记手机号码");
            }else{
                //更新用户密码
                user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
                //更新用户表 update 语句
                userDao.edit(user);
            }
        }

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
