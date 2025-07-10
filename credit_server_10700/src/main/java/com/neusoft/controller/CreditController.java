package com.neusoft.controller;

import com.neusoft.po.CommonResult;
import com.neusoft.po.Credit;
import com.neusoft.service.CreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin("*")
@RefreshScope //开启动态刷新
@RestController
@RequestMapping("/CreditController")
public class CreditController {
    @Autowired
    private CreditService creditService;

    @GetMapping("/creditNum/{userId}")
    public CommonResult<Integer> getTotalCreditByUserId(@PathVariable("userId") String userId) throws Exception {
        int result = creditService.getTotalCreditByUserId(userId);
        return new CommonResult(200,"success",result);
    }

    @GetMapping("/availableCreditList/{userId}")
    public CommonResult<List> getAvailableCreditByUserId(@PathVariable("userId") String userId) throws Exception {
        List<Credit> list = creditService.getAvailableCreditByUserId(userId);
        return  new CommonResult(200,"success",list);
    }
//得到积分列表
    @GetMapping("/getCreditByUserId/{userId}")
    public CommonResult<List> getCreditByUserId(@PathVariable("userId") String userId) throws Exception {
        List<Credit> list = creditService.getCreditByUserId(userId);
        return  new CommonResult(200,"success",list);
    }

    @GetMapping("/channelType/{userId}/{channelType}")
    public CommonResult<List> getCreditByUserIdAndChannelType(
            @PathVariable("userId") String userId,
            @PathVariable("channelType") Integer channelType) throws Exception {
        List<Credit> list = creditService.getCreditByUserIdAndChannelType(userId, channelType);
        return  new CommonResult(200,"success",list);
    }

    @PostMapping("/insertCredit/{userId}/{creditNum}/{channelType}")
    public CommonResult<Integer> insertCredit(
          @PathVariable("userId") String userId,
          @PathVariable("creditNum") Integer creditNum,
          @PathVariable("channelType") Integer channelType) throws Exception {
        Credit credit = new Credit();
        credit.setNum(creditNum);
        credit.setUserId(userId);
        credit.setChannelType(channelType);
        int result = creditService.insertCredit(credit);
        return new CommonResult(200,"success",result);
    }

    @PostMapping("/update/{userId}")
    public CommonResult<Integer> deleteAllbyUser(@PathVariable("userId") String userId) throws Exception {
        Integer result = creditService.deleteAllbyUser(userId);
        return  new CommonResult(200,"success",result);
    }
}
