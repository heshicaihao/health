package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.constant.MessageConstant;
import com.itheima.dao.CheckGroupDao;
import com.itheima.entity.PageResult;
import com.itheima.pojo.CheckGroup;
import com.itheima.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 新增检查组业务层
 * @author wangxin
 * @version 1.0
 */
@Service(interfaceClass = CheckGroupService.class)
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    private CheckGroupDao checkGroupDao;

    /**
     * 新增检查组
     *
     * 往检查组表新增一条记录
     * 往检查组和检查项中间表写记录
     * @param checkGroup
     * @param checkitemIds
     */
    @Override
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
        //往检查组表新增一条记录
        checkGroupDao.add(checkGroup);
        //往检查组和检查项中间表写记录(此方法有其它功能用 代码抽取)
        setCheckGroupAndCheckItem(checkGroup.getId(),checkitemIds);
    }

    /**
     * 检查组分页
     * @param currentPage
     * @param pageSize
     * @param queryString
     * @return
     */
    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage,pageSize);
        //2.需要分页的语句
        Page<CheckGroup> checkGroupPage = checkGroupDao.selectByCondition(queryString);
        return new PageResult(checkGroupPage.getTotal(),checkGroupPage.getResult());
    }

    /**
     * 根据检查组id查询检查组
     * @param groupId
     * @return
     */
    @Override
    public CheckGroup findById(Integer groupId) {
        return checkGroupDao.findById(groupId);
    }

    /**
     * 根据检查组id查询检查项ids
     * @param groupId
     * @return
     */
    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer groupId) {
        return checkGroupDao.findCheckItemIdsByCheckGroupId(groupId);
    }

    /**
     * 编辑检查组
     * @param checkGroup
     * @param checkitemIds
     */
    @Override
    public void edit(CheckGroup checkGroup, Integer[] checkitemIds) {
        //更新检查组表 update 语句
        checkGroupDao.edit(checkGroup);
        //先删除检查组关联的检查项记录 （中间表）
        checkGroupDao.deleteAssociation(checkGroup.getId());
        // 重新建立关联关系（插入中间表）
        setCheckGroupAndCheckItem(checkGroup.getId(),checkitemIds);
    }

    /**
     * 删除检查组
     * @param id 检查组id
     */
    @Override
    public void deleteById(Integer id) {
        //业务角度考虑
        //查询检查组和检查项表关系是否存在，存在则无法删除
        int count = checkGroupDao.findCountByCheckGroupIdO(id);
        if(count>0){
            throw new RuntimeException(MessageConstant.DELETE_CHECKITEM_GROUP_FAIL);//一定要install
        }
        //查询检查组和套餐表关系是否存在，存在则无法删除
        int count2 = checkGroupDao.findCountByCheckGroupIdT(id);
        if(count2>0){
            throw new RuntimeException(MessageConstant.DELETE_CHECKGROUP_SETMEAL_FAIL);//一定要install
        }
        //以上两个关系都不存在，则可以删除
        checkGroupDao.deleteById(id);
    }

    /**
     * 查询所有检查组列表
     *
     * @return
     */
    @Override
    public List<CheckGroup> findAll() {
        return checkGroupDao.findAll();
    }

    /*
    往检查组和检查项中间表写记录(新增检查组、编辑检查组公共的代码)
     */
    public void setCheckGroupAndCheckItem(Integer groupId,Integer[] checkitemIds){
        if(checkitemIds != null && checkitemIds.length> 0){
            for (Integer checkitemId : checkitemIds) {
                //为了方便测试传入map对象
                Map<String,Integer> map = new HashMap<>();
                map.put("groupId",groupId);
                map.put("checkitemId",checkitemId);
                checkGroupDao.setCheckGroupAndCheckItem(map);
            }
        }
    }
}
