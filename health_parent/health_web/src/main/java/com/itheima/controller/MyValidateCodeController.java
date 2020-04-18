package com.itheima.controller;

import com.aliyuncs.exceptions.ClientException;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.utils.SMSUtils;
import com.itheima.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

/**
 * 专门负责发送验证码
 * @author wangxin
 * @version 1.0
 */
@RestController
@RequestMapping("/myValidateCode")
public class MyValidateCodeController {

    @Autowired
    private JedisPool jedisPool;

    /**
     * 手机找回密码-发送验证码
     * @param telephone
     * @return
     */
    @RequestMapping("/send4ForgotPassword")
    public Result send4ForgotPassword(String telephone){
        //1生成验证码 ValidateCodeUtils
        Integer code = ValidateCodeUtils.generateValidateCode(4);
        //2.发送短信  SMSUtils
        try {
//            if(false) {//永远都不会被执行
                SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE, telephone, code.toString());
//            }
        } catch (ClientException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
        //为了方便测试 打印手机号 验证码
        System.out.println("手机号：：：："+telephone+":::验证码：：：："+code);
        //3.将生成的验证码存入redis RedisMessageConstant
        //key:手机号码+验证码类型RedisMessageConstant
        jedisPool.getResource().setex(telephone+ RedisMessageConstant.SENDTYPE_GETPWD,5*60,code.toString());
        return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }

}
