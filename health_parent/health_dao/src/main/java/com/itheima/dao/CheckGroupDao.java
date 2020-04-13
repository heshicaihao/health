package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckGroup;

import java.util.List;
import java.util.Map; /**
 * 新增检查组接口
 * @author wangxin
 * @version 1.0
 */
public interface CheckGroupDao {
    /**
     * 新增检查组
     * @param checkGroup
     */
    void add(CheckGroup checkGroup);

    /**
     * 往检查组和检查项中间表插入记录
     * @param map
     */
    void setCheckGroupAndCheckItem(Map<String, Integer> map);

    /**
     * 检查组分页
     * @param queryString
     * @return
     */
    Page<CheckGroup> selectByCondition(String queryString);

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
     * 更新检查组表
     * @param checkGroup
     */
    void edit(CheckGroup checkGroup);

    /**
     * 先删除检查组关联的检查项记录
     * @param groupId
     */
    void deleteAssociation(Integer groupId);

    /**
     * 查询检查组和检查项表关系是否存在
     * @param id
     * @return
     */
    int findCountByCheckGroupIdO(Integer id);

    /**
     * 查询检查组和套餐表关系是否存在
     * @param id
     * @return
     */
    int findCountByCheckGroupIdT(Integer id);

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
