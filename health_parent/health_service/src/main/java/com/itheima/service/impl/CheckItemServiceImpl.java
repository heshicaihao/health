package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.constant.MessageConstant;
import com.itheima.dao.CheckItemDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 检查项业务层逻辑
 * @author wangxin
 * @version 1.0
 */
@Service(interfaceClass = CheckItemService.class)
@Transactional //事务控制
public class CheckItemServiceImpl implements CheckItemService {

    @Autowired
    private CheckItemDao checkItemDao;
    @Override
    public List<CheckItem> findAll() {
        return checkItemDao.findAll();
    }

    @Override
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
        //业务比较复杂的时候
    }

    /**
     * 检查项分页 (企业中一直在用pageHelper)
     * @return
     */
    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        //之前：业务逻辑1.查询总记录数  2.查询当前页面显示数据
        //1.设置分页参数
        PageHelper.startPage(currentPage,pageSize);
        //2.需要分页的语句
        Page<CheckItem> checkItemPage = checkItemDao.selectByCondition(queryString);
        return new PageResult(checkItemPage.getTotal(),checkItemPage.getResult());
    }

    /**
     * 根据检查项id删除检查项
     * @param id
     */
    @Override
    public void deleteById(Integer id) {
        //1.根据检查项id 到t_checkgroup_checkitem查询关系是否存在
        int count = checkItemDao.findCountByCheckItemId(id);
        //2.存在 抛出异常 当前检查和下和检查组已经关联，无法删除
        if(count > 0){
            throw new RuntimeException(MessageConstant.DELETE_CHECKITEM_GROUP_FAIL);//一定要install
        }
        //3.不存在 直接删除
        checkItemDao.deleteById(id);
    }

    /*
    根据检查项id查询检查项
     */
    @Override
    public CheckItem findById(Integer id) {

        return checkItemDao.findById(id);
    }

    /**
     * 更新检查项
     * @param checkItem
     */
    @Override
    public void edit(CheckItem checkItem) {
        checkItemDao.edit(checkItem);
    }
}
