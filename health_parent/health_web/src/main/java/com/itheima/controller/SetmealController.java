package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.Setmeal;
import com.itheima.service.CheckGroupService;
import com.itheima.service.SetmealService;
import com.itheima.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.UUID;

/**
 * 套餐控制层
 *
 * @author wangxin
 * @version 1.0
 */
@RestController//@Controller+@ResponseBody
@RequestMapping("/setmeal")
public class SetmealController {
    @Reference
    private SetmealService setmealService;

    @Autowired
    private JedisPool jedisPool;

    /**
     * 图片上传
     * 方式一：MultipartFile imgFile
     * 方式二：@RequestParam("imgFile") MultipartFile imgFile
     *
     * @return
     */
    @RequestMapping("/upload")
    public Result upload(@RequestParam("imgFile") MultipartFile imgFile) {
        try {
            System.out.println(imgFile.getOriginalFilename());//原始文件名 3.jpg
            //1.将原始文件名改为一个唯一的文件名（保证文件不会被覆盖）
            String oldFileName = imgFile.getOriginalFilename();
            int lastIndexOf = oldFileName.lastIndexOf(".");
            //获取文件后缀
            String suffx = oldFileName.substring(lastIndexOf);
            //UUID方式生成一个唯一文件名
            String newFileName = UUID.randomUUID().toString() + suffx;//UUID+.jpg
            //2.将图片上传七牛云
            QiniuUtils.upload2Qiniu(imgFile.getBytes(), newFileName);

            //第一步写入redis  key(常量类定义)setmealPicResources value（集合）
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES, newFileName);
            //3.页面还需要回显图片
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS, newFileName);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
    }

    /**
     * 新增套餐
     */
    @RequestMapping("/add")
    public Result add(@RequestBody Setmeal setmeal, Integer[] checkgroupIds) {
        try {
            setmealService.add(setmeal, checkgroupIds);
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES, setmeal.getImg());
            return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_SETMEAL_FAIL);
        }
    }

    /**
     * 套餐分页
     */
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        try {
            PageResult pageResult = setmealService.findPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize(), queryPageBean.getQueryString());
            return pageResult;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据套餐id查询套餐对象
     *
     * @return
     */
    @RequestMapping("/findById")
    public Result findById(Integer id) {
        try {
            Setmeal setmeal = setmealService.findById(id);
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, setmeal);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }

    /**
     * 根据套餐id查询关联的检查组ids
     *
     * @return
     */
    @RequestMapping("/findCheckGroupIdsBySetmealId")
    public List<Integer> findCheckGroupIdsBySetmealId(Integer id) {
        try {
            List<Integer> integerList = setmealService.findCheckGroupIdsBySetmealId(id);
            return integerList;
        } catch (Exception e) {
            e.printStackTrace();
            return null; //没有关联检查组
        }
    }

    /**
     * 编辑套餐
     */
    @RequestMapping("/edit")
    public Result edit(@RequestBody Setmeal setmeal, Integer[] checkgroupIds) {
        try {
            setmealService.edit(setmeal,checkgroupIds);
            return new Result(true, MessageConstant.EDIT_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_SETMEAL_FAIL);
        }
    }


    /**
     * 删除套餐
     */
    @RequestMapping("/deleteById")
    public Result deleteById(Integer id) {
        try {
            setmealService.deleteById(id);
            return new Result(true, MessageConstant.DELETE_SETMEAL_SUCCESS);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new Result(false, e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_SETMEAL_FAIL);
        }
    }
}