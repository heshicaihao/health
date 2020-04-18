package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.constant.MessageConstant;
import com.itheima.dao.RoleDao;
import com.itheima.entity.PageResult;
import com.itheima.dao.MenuDao;
import com.itheima.dao.RoleDao;
import com.itheima.dao.RoleMenuDao;
import com.itheima.dao.RolePermissionDao;
import com.itheima.dao.MenuDao;
import com.itheima.dao.PermissionDao;
import com.itheima.dao.RoleDao;
import com.itheima.pojo.Role;
import com.itheima.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 角色服务接口实现类
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ljw
 * @Date: 2020/4/18 15:23
 */
@Service(interfaceClass = RoleService.class)
@Transactional
public class RoleServiceImpl implements RoleService {

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
    public PageResult getRoles(Integer currentPage, Integer pageSize, String queryString) {
        //引用分页插件
        PageHelper.startPage(currentPage,pageSize);
        //分页查询语句
        Page<Role> rolePage = roleDao.getRoles(queryString);
        return new PageResult(rolePage.getTotal(),rolePage.getResult());
    }

    /**
     * 查询所有角色
     * @return
     */
    @Override
    public List<Role> getAllRoles() {
        return roleDao.findAll();
    }

    @Override
    public List<Integer> getAllRoleIds() {
        return null;
    }

    @Override
    public List<Integer> getRoleIdsByUserId(Integer userId) {
        return null;
    }


    @Override
    public void bindRoleAndPermissions(Map map) {

    }

    @Override
    public void releaseRoleAndPermissions(Map map) {

    }



    /**
     * 查询所有角色列表
     *
     * @return
     */
    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }

    @Autowired
    private RolePermissionDao rolePermissionDao;

    @Autowired
    private RoleMenuDao roleMenuDao;

    @Autowired
    private MenuDao menuDao;

    /**
     * 根据id查询角色信息
     * @param id
     * @return
     */
    @Override
    public Role getRoleById(Integer id) {
        Role role = roleDao.getRoleById(id);
        return role;
    }


    /**
     * 编辑角色信息
     */
    @Override
    public void updateRoleById(Map map) {
        Integer id = (Integer) map.get("id");
        List<Integer> permissions = (List<Integer>) map.get("permissions");
        List<Integer> menus = (List<Integer>) map.get("menus");
        Map<String,Integer> map1 = new HashMap<>();
        map1.put("roleId",id);
        //根据角色id删除 角色菜单表 关联数据
        roleMenuDao.deleteByRoleId(id);

        //更新角色表信息
        roleDao.updateById(map);

        //向角色菜单表插入新的关联数据
        if (menus != null && menus.size() > 0) {
            Set<Integer> set = new LinkedHashSet<>();
            for (Integer childrenMenuId : menus) {
                //插入子菜单关联数据
                map1.put("menuId",childrenMenuId);
                roleMenuDao.add(map1);
                //根据子菜单id获取对应的父菜单id
                //将父菜单id放入set集合中
                Integer parentMenuId = menuDao.findParentIdById(childrenMenuId);
                set.add(parentMenuId);
            }
            //插入父菜单关联数据
            /*for (Integer parentMenuId : set) {
                map1.put("menuId",parentMenuId);
                roleMenuDao.add(map1);
            }*/
            /*Map<String,Integer> map2 = new HashMap<>();
            map2.put("roleId",id);
            Iterator<Integer> iterator = set.iterator();
            while (iterator.hasNext()){
                Integer parentMenuId = iterator.next();
                if (parentMenuId != null) {
                    map2.put("menuId", parentMenuId);
                    roleMenuDao.add(map2);
                }
            }*/
        }


        //根据角色id删除 角色权限表 关联数据
        rolePermissionDao.deleteByRoleId(id);

        //向角色权限表插入新的关联数据
        if (permissions != null && permissions.size() > 0){
            for (Integer permission : permissions) {
                map1.put("permissionId",permission);
                rolePermissionDao.add(map1);
            }
        }

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

    /**
     * 金旺
     * @param map
     */
    @Override
    public void addRoles(Map map) {
        //查询所有的permissionIds
        List<Integer> permissionIds = (List<Integer>) map.get("permissionIds");
        //获取菜单ids
        List<Integer> menuIds = (List<Integer>) map.get("menuIds");
        //添加角色
        Role role = new Role();
        role.setName((String) map.get("name"));
        role.setKeyword((String) map.get("keyword"));
        role.setDescription((String) map.get("description"));
        roleDao.addRoles(role);

        Map<String,Integer> rsMap = new HashMap<>();
        rsMap.put("roleId",role.getId());
        //保存到对应的权限角色表
        if (permissionIds != null && permissionIds.size() >0) {
            for (Integer permissionId : permissionIds) {
                rsMap.put("permissionId",permissionId);
                roleDao.bindRoleAndPermission(rsMap);
            }
        }
        //保存到对应的菜单表
        if (menuIds != null && menuIds.size() > 0) {
            for (Integer menuId : menuIds) {
                rsMap.put("menuId",menuId);
                roleDao.bindRoleAndMenu(rsMap);
            }
        }
    }
}
