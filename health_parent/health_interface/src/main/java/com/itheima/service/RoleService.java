package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;




public interface RoleService {

    /**
     * 晴天:
     * 分页查询
     * @param currentPage
     * @param pageSize
     * @param queryString
     * @return
     */
    PageResult getAllRoles(Integer currentPage, Integer pageSize, String queryString);

    /**
     * 晴天:
     * 删除角色
     * @param id
     */
    void deleteRoleById(Integer id);
}
