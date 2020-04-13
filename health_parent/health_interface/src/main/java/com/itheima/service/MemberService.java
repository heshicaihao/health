package com.itheima.service;

import com.itheima.pojo.Member;

/**
 * 会员业务逻辑接口层
 * @author wangxin
 * @version 1.0
 */
public interface MemberService {
    /**
     * 根据手机号查询会员信息
     * @param telephone
     * @return
     */
    Member findByTelephone(String telephone);

    /**
     * 注册会员
     * @param member
     */
    void add(Member member);
}
