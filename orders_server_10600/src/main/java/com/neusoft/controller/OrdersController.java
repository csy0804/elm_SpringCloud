package com.neusoft.controller;

import com.neusoft.po.CommonResult;
import com.neusoft.po.Orders;
import com.neusoft.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

//@CrossOrigin("*")
@RefreshScope //开启动态刷新
@RestController
@RequestMapping("/OrdersController")
public class OrdersController {
 @Autowired
 private OrdersService ordersService;

 @PostMapping("/createOrders/{userId}/{businessId}/{daId}/{orderTotal}/{open}")
 public CommonResult<Integer> createOrders(
         @PathVariable("userId") String userId,
         @PathVariable("businessId") Integer businessId,
         @PathVariable("daId") Integer daId,
         @PathVariable("orderTotal") BigDecimal orderTotal,
         @PathVariable("open") Integer open) throws Exception {
  Orders orders = new Orders();
  orders.setUserId(userId);
  orders.setBusinessId(businessId);
  orders.setDaId(daId);
  orders.setOrderTotal(orderTotal);
  orders.setOpen(open);
  int orderId = ordersService.createOrders(orders);
  return new CommonResult(200, "success（10600）", orderId);
 }

 @GetMapping("/getOrdersById/{orderId}")
 public CommonResult<Orders> getOrdersById(
         @PathVariable("orderId") Integer orderId) throws Exception {
  Orders orders = ordersService.getOrdersById(orderId);
  return new CommonResult(200, "success（10600）", orders);
 }

 @GetMapping("/listOrdersByUserId/{userId}")
 public CommonResult<List> listOrdersByUserId(
         @PathVariable("userId") String userId) throws Exception {
  List<Orders> list = ordersService.listOrdersByUserId(userId);
  return new CommonResult(200, "success（10600）", list);
 }

 @GetMapping("/pay/{orderId}")
 public CommonResult<Integer> createOrders(
         @PathVariable("orderId") Integer orderId) throws Exception {
  ordersService.pay(orderId);
  return new CommonResult(200, "success（10600）", orderId);
 }

}