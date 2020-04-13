package com.itheima.job;

import com.itheima.constant.RedisConstant;
import com.itheima.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;

import java.util.Set;

/**
 * 清理垃圾图片
 * @author wangxin
 * @version 1.0
 */
public class ClearImg {
    @Autowired
    private JedisPool jedisPool;

    /**
     * 清理七牛云垃圾图片
     */
    public void deleteQiNiuImg(){
        System.out.println("******************任务被执行了******************");
        //获取两个redis集合差集
        Set<String> set = jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        for (String fileName : set) {
            //调用七牛云接口删除图片
            QiniuUtils.deleteFileFromQiniu(fileName);
            System.out.println("****删除七牛云垃圾图片成功*********"+fileName);
            //删除redis中垃圾记录
            jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES,fileName);
            System.out.println("*****删除redis垃圾图片记录成功***************");
        }
    }
}
