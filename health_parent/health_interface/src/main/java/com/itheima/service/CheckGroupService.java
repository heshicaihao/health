package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.pojo.CheckGroup;

import java.util.List;

/**
 * 检查组接口服务
 * @author wangxin
 * @version 1.0
 */
public interface CheckGroupService {
    /**
     * 新增检查组
     * @param checkGroup
     * @param checkitemIds
     */
    void add(CheckGroup checkGroup, Integer[] checkitemIds);

    /**
     * 检查组分页
     * @param currentPage
     * @param pageSize
     * @param queryString
     * @return
     */
    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    /**
     * 根据检查组id查询检查组
     * @param groupId
     * @return
     */
    CheckGroup findById(Integer groupId);

    /**
     * 根据检查组id查询检查项ids
     * @param groupId
     * @return
     */
    List<Integer> findCheckItemIdsByCheckGroupId(Integer groupId);

    /**
     * 编辑检查组
     * @param checkGroup
     * @param checkitemIds
     */
    void edit(CheckGroup checkGroup, Integer[] checkitemIds);

    /**
     * 删除检查组
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 查询所有检查组列表
     *
     * @return
     */
    List<CheckGroup> findAll();
}
