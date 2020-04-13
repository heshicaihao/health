package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.SetmealDao;
import com.itheima.entity.PageResult;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import com.itheima.utils.QiniuUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 套餐服务层实现
 *
 * @author wangxin
 * @version 1.0
 */
@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    private SetmealDao setmealDao;

    //注入模板配置类
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    //通过@Value注解获取配置文件的值
    @Value("${out_put_path}")
    private String outputpath;

    /**
     * 新增套餐
     *
     * @param setmeal
     * @param checkgroupIds
     */
    @Override
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        //往套餐表新增一条记录
        setmealDao.add(setmeal);
        //往检查组和套餐中间表写记录(此方法有其它功能用 代码抽取)
        setCheckGroupAndSetmeal(setmeal.getId(), checkgroupIds);

        //套餐新增成功后  生成静态页面
        generateMobileStaticHtml();
    }

    //生成静态页面
    public void generateMobileStaticHtml(){
        //准备模板中所需要的数据
        List<Setmeal> setmealList = this.findAll();
        //生成套餐列表页面(只有一个套餐列表页面)
        generateMobileSetmealListHtml(setmealList);
        //生成套餐详情页面（多个）
        generateMobileSetmealDetailHtml(setmealList);
    }

    //生成套餐详情页面静态页面
    public void generateMobileSetmealDetailHtml(List<Setmeal> setmealList){
        //为每一个套餐生成一个静态页面
        if(setmealList != null && setmealList.size()>0){
            for (Setmeal setmeal : setmealList) {
                Map<String,Object> dataMap = new HashMap<>();
                //套餐详情页面 setmeal（套餐数据+ 检查组 + 检查项）
                dataMap.put("setmeal",this.findById(setmeal.getId()));///跟页面模板中一致
                ///生成静态页面（模板、生成静态页面的数据、m_setmeal.html）
                //setmeal_detail_2.html
                this.generateHtml("mobile_setmeal_detail.ftl",dataMap,"setmeal_detail_"+setmeal.getId()+".html");
            }
        }
    }

    //生成套餐列表静态页面
    public void generateMobileSetmealListHtml(List<Setmeal> setmealList){
        //将套餐列表数据放到map中
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("setmealList",setmealList);///跟页面模板中一致
        ///生成静态页面（模板、生成静态页面的数据、m_setmeal.html）
        this.generateHtml("mobile_setmeal.ftl",dataMap,"m_setmeal.html");//页面名称要跟index.html跳转页面保持一致
    }


    /**
     * 生成静态页面的公共方法 （模板名称、生成静态页面的数据、页面名称）
     */
    public void generateHtml(String templateName,Map<String,Object> dataMap,String htmlPageName){
        Writer out = null;
        try {
            //模板配置类
            Configuration configuration = freeMarkerConfigurer.getConfiguration();
            //加载模板
            Template template = configuration.getTemplate(templateName);

            //创建一个文件对象 C:/working/workspace/javaee-dev86/health_parent/healthmobile_web/src/main/webapp/pages/test.html
            File myFile = new File(outputpath+"\\"+htmlPageName);
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(myFile)));
            //往模板中写入数据
            template.process(dataMap,out);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(out != null){
                try {
                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    /**
     * 套餐分页
     *
     * @param currentPage
     * @param pageSize
     * @param queryString
     * @return
     */
    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage, pageSize);
        //2.需要分页的语句
        Page<Setmeal> setmealPage = setmealDao.selectByCondition(queryString);
        return new PageResult(setmealPage.getTotal(), setmealPage.getResult());
    }

    /**
     * 根据套餐id查询套餐对象
     *
     * @param id
     * @return
     */
    @Override
    public Setmeal findById(Integer id) {
        //1.获取检查组数据 <resultMap><collection>
        //2.获取检查项数据
        return setmealDao.findById(id);
    }

    /**
     * 根据套餐id查询关联的检查组ids
     *
     * @param id
     * @return
     */
    @Override
    public List<Integer> findCheckGroupIdsBySetmealId(Integer id) {
        return setmealDao.findCheckGroupIdsBySetmealId(id);
    }

    /**
     * 编辑套餐
     */
    @Override
    public void edit(Setmeal setmeal, Integer[] checkgroupIds) {
        //更新套餐表 update 语句
        setmealDao.edit(setmeal);
        //先删除套餐关联的检查组记录 （中间表）
        setmealDao.deleteAssociation(setmeal.getId());
        // 重新建立关联关系（插入中间表）
        setCheckGroupAndSetmeal(setmeal.getId(), checkgroupIds);
    }

    /**
     * 删除套餐
     */
    @Override
    public void deleteById(Integer id) {
        //1.根据套餐id 查询数据库套餐对象
        Setmeal setmeal = setmealDao.findById(id);

        //2.根据套餐id查询套餐和检查组中间表
        int count = setmealDao.findSetmealAndCheckGroupCountBySetMealId(id);

        //3.如果有关联关系 ，则不允许删除
        if(count>0){
            throw new RuntimeException("当前套餐和检查组存在关系，不能直接删除");
        }

        //4.删除套餐
        setmealDao.deleteById(id);
        //5.将七牛云的图片删除
        String img = setmeal.getImg();
        if(!StringUtils.isEmpty(img)){
            QiniuUtils.deleteFileFromQiniu(img);
        }

    }

    /**
     * 获取套餐列表数据
     */
    @Override
    public List<Setmeal> findAll() {
        return setmealDao.findAll();
    }

    /**
     * 套餐预约占比饼图
     * @return
     */
    @Override
    public List<Map<String, Object>> findSetmealCount() {
        return setmealDao.findSetmealCount();
    }


    /*
   往检查组和套餐中间表写记录(新增套餐、编辑套餐公共的代码)
    */
    public void setCheckGroupAndSetmeal(Integer setmealId, Integer[] checkgroupIds) {
        if (checkgroupIds != null && checkgroupIds.length > 0) {
            for (Integer checkgroupId : checkgroupIds) {
                //为了方便测试传入map对象
                Map<String, Integer> map = new HashMap<>();
                map.put("setmealId", setmealId);
                map.put("checkgroupId", checkgroupId);
                setmealDao.setCheckGroupAndSetmeal(map);
            }
        }
    }
}
