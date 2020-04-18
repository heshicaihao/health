package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.MemberDao;
import com.itheima.service.MemeberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.jws.Oneway;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

  /*  @Override
    public List<Integer> getMemberCountBetweenDates(List<String> listMonth) {
        //定义list集合存放会员数量
        List<Integer> memberCount = new ArrayList<>();
        if (listMonth != null && listMonth.size() > 0) {
            for (String month : listMonth) {
                Integer count = memberDao.getMemberCountBetweenDates(month);
                memberCount.add(count);
            }
        }
        return memberCount;
    }*/
    /**
     * 会员性别占比饼图
     */
    @Override
    public List<Map<String, Object>> findMemberSexCount() {
        return memberDao.findMemberSexCount();
    }

    /**
     * 会员年龄段占比饼图
     */
    @Override
    public List<Integer> findMemberAgeCount() {
        List<Integer> memberCount = new ArrayList<>();

        int firstCount = memberDao.findMemberFirstAge();
        int secondCount = memberDao.findMemberSecondAge();
        int thirdCount = memberDao.findMemberThirdAge();
        int lastCount = memberDao.findMemberLastAge();

        memberCount.add(firstCount);
        memberCount.add(secondCount);
        memberCount.add(thirdCount);
        memberCount.add(lastCount);

        return memberCount;
    }
}
