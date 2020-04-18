package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.constant.MessageConstant;
import com.itheima.dao.RoleDao;
import com.itheima.entity.PageResult;
import com.itheima.pojo.Role;
import com.itheima.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service(interfaceClass = RoleService.class)
@Transactional
public class RoleServiceImpl implements RoleService{

    @Autowired
    private RoleDao roleDao;


    /**
     * 晴天:
     * 分页查询
     * @param currentPage
     * @param pageSize
     * @param queryString
     * @return
     */
    @Override
    public PageResult getAllRoles(Integer currentPage, Integer pageSize, String queryString) {
        //引用分页插件
        PageHelper.startPage(currentPage,pageSize);
        //分页查询语句
        Page<Role> rolePage = roleDao.getAllRoles(queryString);
        return new PageResult(rolePage.getTotal(),rolePage.getResult());
    }

    /**
     * 晴天:
     * 删除角色
     * @param id
     */
    @Override
    public void deleteRoleById(Integer id) {
        //1.根据role的id查询与menu表是否有关联
        int count1 = roleDao.findmenuIdcountByroleId(id);
        if (count1>0){
            throw new RuntimeException(MessageConstant.DELETE_ROLE_MENU_FALL);
        }
        //2.根据role的id查询与permission表是否有关联
        int count2 = roleDao.findpermissionIdcountByroleId(id);
        if (count2>0){
            throw new RuntimeException(MessageConstant.DELETE_ROLE_PERMISSION_FALL);
        }
        //3.根据role的id查询与user表是否有关联
        int count3 = roleDao.finduserIdcountByroleId(id);
        if (count3>0){
            throw new RuntimeException(MessageConstant.DELETE_ROLE_USER_FALL);
        }

        //4.根据role的id删除角色
        roleDao.deleteRoleById(id);
    }
}
