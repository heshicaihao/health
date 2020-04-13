package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.MemberDao;
import com.itheima.service.MemeberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.jws.Oneway;
import java.util.ArrayList;
import java.util.List;

/**
 * 会员服务业务逻辑处理层
 * @author wangxin
 * @version 1.0
 */
@Service(interfaceClass = MemeberService.class)
@Transactional
public class MemeberServiceImpl implements MemeberService {

    @Autowired
    private MemberDao memberDao;

    /**
     * 根据年月获取累计会员数量
     * @param listMonth
     * @return
     */
    @Override
    public List<Integer> findMemberCountByMonth(List<String> listMonth) {
        //定义list集合存放会员数量
        List<Integer> memberCount = new ArrayList<>();
        if(listMonth != null && listMonth.size()>0){
            for (String yearMonth : listMonth) {
                //2020-02 select count(*) from t_member where regTime <= '2020-02-31'
                yearMonth = yearMonth+"-31";
                Integer count = memberDao.findMemberCountBeforeDate(yearMonth);
                memberCount.add(count);
            }
        }
        return memberCount;
    }
}
