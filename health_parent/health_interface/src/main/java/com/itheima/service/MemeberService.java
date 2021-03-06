package com.itheima.service;

import java.util.List;
import java.util.Map;

/**
 * 会员服务接口
 * @author wangxin
 * @version 1.0
 */
public interface MemeberService {
    /**
     * 根据年月 获取年月累计会员数据
     * @param listMonth
     * @return
     */
    List<Integer> findMemberCountByMonth(List<String> listMonth);

//    List<Integer> getMemberCountBetweenDates(List<String> listMonth);
    /**
     * 会员性别占比饼图
     */
    List<Map<String, Object>> findMemberSexCount();

    /**
     * 会员年龄段占比饼图
     */
    List<Integer> findMemberAgeCount();
}
