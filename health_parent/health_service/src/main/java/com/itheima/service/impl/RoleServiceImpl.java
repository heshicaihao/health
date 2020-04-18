package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.MenuDao;
import com.itheima.dao.RoleDao;
import com.itheima.dao.RoleMenuDao;
import com.itheima.dao.RolePermissionDao;
import com.itheima.pojo.Role;
import com.itheima.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 角色服务接口实现类
 */
@Service(interfaceClass = RoleService.class)
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

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
}
