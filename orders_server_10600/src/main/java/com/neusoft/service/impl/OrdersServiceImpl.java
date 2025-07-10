package com.neusoft.service.impl;


import com.neusoft.mapper.CartMapper;
import com.neusoft.mapper.OrderDetailetMapper;
import com.neusoft.mapper.OrdersMapper;
import com.neusoft.po.Cart;
//import com.neusoft.po.Credit;
import com.neusoft.po.OrderDetailet;
import com.neusoft.po.Orders;
//import com.neusoft.service.CreditService;
import com.neusoft.service.OrdersService;
import com.neusoft.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private OrderDetailetMapper orderDetailetMapper;
// @Autowired
// private CreditService creditService;

    @Override
    @Transactional
    public int createOrders(Orders orders) {
        //1、查询当前用户购物车中当前商家的所有食品
        Cart cart = new Cart();
        cart.setUserId(orders.getUserId());
        cart.setBusinessId(orders.getBusinessId());
        List<Cart> cartList = cartMapper.listCart(cart);
        //2、创建订单（返回生成的订单编号）

        orders.setOrderDate(CommonUtil.getCurrentDate());
        ordersMapper.saveOrders(orders);
        int orderId = orders.getOrderId();
//  orders.setOpen(1);


        //3、批量添加订单明细
        List<OrderDetailet> list = new ArrayList<>();
        for (Cart c : cartList) {
            OrderDetailet od = new OrderDetailet();
            od.setOrderId(orderId);
            od.setFoodId(c.getFoodId());
            od.setQuantity(c.getQuantity());
            list.add(od);
        }
        orderDetailetMapper.saveOrderDetailetBatch(list);

        //4、从购物车表中删除相关食品信息
        cartMapper.removeCart(cart);

        return orderId;
    }

    @Override
    public Orders getOrdersById(Integer orderId) {
        return ordersMapper.getOrdersById(orderId);
    }

    @Override
    public List<Orders> listOrdersByUserId(String userId) {
        return ordersMapper.listOrdersByUserId(userId);
    }

    @Override
    public void pay(Integer orderId) {

        int deductVal = 0;
        int countVal = 0;
//        System.out.println(orderId);
//        Orders orders = ordersMapper.getOrdersById(orderId);
        ordersMapper.updateOrderState(orderId);
//        System.out.println(222222);
//        // 可以使用的积分列表
//        List<Credit> creditList = creditService.getAvailableCreditByUserId(orders.getUserId());
//
//        // 根据过期时间排序
//        Collections.sort(creditList, Comparator.comparing(Credit::getExpiredTime));
//        orders.setOpen(1);
//        // 是否消耗积分，是的话为1
//        if (orders.getOpen() == 1) {
//            // 获取总积分
//            Integer totalCreditVal = creditService.getTotalCreditByUserId(orders.getUserId());
//
//            // 是否大于0，大于0执行逻辑，否则跳出if
//            if (totalCreditVal > 0) {
//                // 最多可以扣除的积分为原来的价格*10
//                int needVal = orders.getOrderTotal().multiply(new BigDecimal("10")).intValue();
//
//                // 如果拥有的积分比需要扣除的多，那么应该扣除的积分就是价格*10，否则应该扣除目前的所有积分
//                if (totalCreditVal > needVal) deductVal = needVal;
//                else deductVal = totalCreditVal;
//                countVal = deductVal;
//                Iterator<Credit> iterator = creditList.iterator();
//                while (iterator.hasNext()) {
//                    Credit credit = iterator.next();
//                    int creditPoints = credit.getNum();
//
//                    if (creditPoints <= deductVal) {
//                        // 当前条目的分数足够扣减
//                        // 执行删除操作
//                        creditService.delCredit(credit.getCreditId());
//
//                        deductVal -= creditPoints;
//
//                        // 从creditList中删除当前条目
//                        iterator.remove();
//                    } else {
//                        if (deductVal > 0) {
//                            // 执行更新操作
//                            creditService.updCredit(credit.getCreditId(), creditPoints - deductVal);
//                        }
//                        break;  // 退出循环
//                    }
//                }
//                // 设置使用的积分数目
//                orders.setUseCredit(countVal);
//                // 设置总金额，后期可以自行修改，=金额数目-积分数目/10；
////                orders.setOrderTotal(xxx);
//            }
//        } else {
//            // 设置使用的积分数目
//            orders.setUseCredit(0);
//        }
//        Credit credit = new Credit();
//        credit.setDeleted(0);
//        credit.setUserId(orders.getUserId());
//        credit.setChannelType(1);
//        credit.setNum(orders.getOrderTotal().divide(new BigDecimal(10)).intValue());
//        credit.setCreateTime(CommonUtil.getCurrentDate1());
//        credit.setExpiredTime(CommonUtil.getCurrentDate2());
//        // 添加新的积分记录
//        creditService.insertCredit(credit);
//        // 修改订单状态
//        ordersMapper.updateOrderStateAndCredit(orders.getOrderId(), countVal);

    }
}