package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.constant.MessageConstant;
import com.itheima.dao.MemberDao;
import com.itheima.dao.OrderDao;
import com.itheima.dao.OrderSettingDao;
import com.itheima.entity.Result;
import com.itheima.pojo.Member;
import com.itheima.pojo.Order;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderService;
import com.itheima.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 体检预约业务逻辑
 * @author wangxin
 * @version 1.0
 */
@Service(interfaceClass = OrderService.class)
@Transactional //一定要加事务 业务逻辑非常多
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderSettingDao orderSettingDao;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private OrderDao orderDao;

    /**
     * 体检预约
     * @param map
     * @return
     */
    @Override
    public Result submitOrder(Map map) throws Exception {
        //1.获取预约日期
        String userOrderDate = (String)map.get("orderDate");//2020-03-02
        String telephone = (String)map.get("telephone");//手机号码
        String name = (String)map.get("name");//姓名
        String sex = (String)map.get("sex");//性别
        String orderType = (String)map.get("orderType");//预约类型
        String idCard = (String)map.get("idCard");//身份证
        Integer setmealId = Integer.parseInt((String)map.get("setmealId"));//套餐id
        //orderDate 从string转date
        Date orderDate = DateUtils.parseString2Date(userOrderDate);
        //第一步：根据预约日期 查询 预约设置表记录是否存在
        OrderSetting orderSetting = orderSettingDao.findByOrderDate(orderDate);
        if(orderSetting == null){
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }
        // 第二步：后台校验预约人数是否已满
        int number = orderSetting.getNumber();//可预约人数
        int reservations = orderSetting.getReservations();//已经预约人数
        if(reservations>=number){
            return new Result(false, MessageConstant.ORDER_FULL);
        }
        // 第三步：检查当前用户是否重复预约--条件 会员id+套餐id+预约时间  （先查询当前用户是否是会员）
            //先查询当前用户是否是会员  跟手机号码查询会员信息
        Member member = memberDao.findByTelephone(telephone);
        //存在
        if(member != null){
            //根据会员id+套餐id+预约时间 到 预约表
            Order order = new Order();
            order.setMemberId(member.getId());
            order.setSetmealId(setmealId);
            order.setOrderDate(orderDate);
            //预约list
            List<Order> listOrder = orderDao.findByCondition(order);
            if(listOrder != null && listOrder.size()>0){
                return new Result(false,MessageConstant.HAS_ORDERED);
            }
        }
        // 第四步：不存在会员自动注册，得到member_id
        if(member == null){
            member = new Member();
            member.setName(name);//姓名
            member.setSex(sex);//性别
            member.setIdCard(idCard);//身份证
            member.setPhoneNumber(telephone);//手机号码
            member.setRegTime(new Date());//注册时间
            memberDao.add(member);
        }
        //  第五步：后台往体检预约表插入记录
        Order order = new Order(member.getId(),orderDate,orderType,Order.ORDERSTATUS_NO,setmealId);
        orderDao.add(order);

        // 第六步：后台更新更新已经预约人数+1
        orderSettingDao.editReservationsByOrderDate(orderDate);
        //order:后续页面需要用到这个order对象
        return new Result(true,MessageConstant.ORDER_SUCCESS,order);
    }

    /**
     * 体检预约成功页面数据展示
     */
    @Override
    public Map findById4Detail(Integer id) throws Exception {
        Map map = orderDao.findById4Detail(id);
        //将数据库查询的日期对象类型转string字符串 方便前端页面展示
        if(map != null){
            Date orderDate = (Date)map.get("orderDate");
            map.put("orderDate",DateUtils.parseDate2String(orderDate));//orderDate跟页面保存一致
        }
        return map;
    }

    /**
     * 获取套餐信息
     * @param id
     * @return
     */
    @Override
    public Map findById(Integer id) {
        return orderDao.findById(id);
    }
}
