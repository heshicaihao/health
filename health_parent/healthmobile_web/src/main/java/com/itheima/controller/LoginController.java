package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Member;
import com.itheima.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * 移动端-快速登录
 * @author wangxin
 * @version 1.0
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private JedisPool jedisPool;

    @Reference
    private MemberService memberService;

    /**
     * 手机号码快速登录
     * map:手机号码 验证码
     * response:返回对象 将cookie通过此对象返回
     */
    @RequestMapping("/check")
    public Result check(@RequestBody Map map, HttpServletResponse response){
        // 对比验证码是否正确，如果错误，登录失败
        String userValidateCode = (String)map.get("validateCode");
        String telephone = (String)map.get("telephone");
        String redisCode = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_LOGIN);
        if(userValidateCode == null || redisCode == null || !userValidateCode.equals(redisCode)){
            //redis 跟用户输入的验证码不一致直接返回
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
        // 验证码正确，判断当前用户是否是会员，如果不是自动注册
        Member member = memberService.findByTelephone(telephone);
        if(member == null){
            //自动注册
            member = new Member();
            member.setPhoneNumber(telephone);//手机号码
            member.setRegTime(new Date());//注册时间
            memberService.add(member);
        }
        // 后台向客户端写入cookie信息，cookie内容用户手机号码
        Cookie cookie = new Cookie("login_member_telephone",telephone);
        cookie.setPath("/");///所有页面都能使用此cookie
        cookie.setMaxAge(60*60*24*30);//有效期30天
        response.addCookie(cookie);
        return new Result(true,MessageConstant.LOGIN_SUCCESS);
    }
}
