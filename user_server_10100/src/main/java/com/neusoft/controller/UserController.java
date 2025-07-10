package com.neusoft.controller;

import com.neusoft.po.*;
import com.neusoft.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin("*")//跨域处理
@RefreshScope //开启动态刷新
@RestController
@RequestMapping("/UserController")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getUserByIdByPass/{userId}/{password}")
    public CommonResult<User> getUserByIdByPass(
            @PathVariable("userId") String userId,
            @PathVariable("password") String password
    ) throws Exception {
//  System.out.println(userId);
//  System.out.println(password);
        User param = new User();
        param.setUserId(userId);
        param.setPassword(password);
        User user = userService.getUserByIdByPass(param);
        return new CommonResult(200, "success", user);//状态码，响应信息，业务数据
    }

    @GetMapping("/getUserById/{userId}")
    public CommonResult<Integer> getUserById(@PathVariable("userId") String userId) throws
            Exception {
        System.out.println(userId);
        User result = userService.getUserById(userId);
        return new CommonResult(200, "success", result);
    }

    @PostMapping("/saveUser/{userId}/{password}/{userName}/{userSex}")
    public CommonResult<Integer> saveUser(
            @PathVariable("userId") String userId,
            @PathVariable("password") String password,
            @PathVariable("userName") String userName,
            @PathVariable("userSex") Integer userSex) throws Exception {
        User param = new User();
        param.setUserId(userId);
        param.setPassword(password);
        param.setUserName(userName);
        param.setUserSex(userSex);
        int result = userService.saveUser(param);
        return new CommonResult(200, "success", result);
    }

    // @RequestMapping("/changeUserPortrait")//base64
//    @PostMapping("/changeUserPortrait/{userImg}")
//    public CommonResult<Integer> changeUserPortrait(
//            @PathVariable("userImg") UserImg userImg
////            @PathVariable("base64") String base64
//            ) throws Exception {
//        int result = userService.changeUserPortrait(userImg);
//        return new CommonResult(200, "success", result);
//    }
    @PostMapping("/changeUserPortrait")
    public CommonResult<Integer> changeUserPortrait(@RequestBody UserImg userImg) throws Exception {
        // 解码Base64字符串为字节数组
        // 这里执行你的图像处理逻辑，可能需要调用userService.changeUserPortrait方法
        // int result = userService.changeUserPortrait(decodedImg);

        // 假设你的changeUserPortrait方法返回修改结果
        int result = userService.changeUserPortrait(userImg);

        return new CommonResult<>(200, "success", result);
    }

    @RequestMapping("/modifyPassword")
    public int modifyPassword(UserPsd userPsd) throws Exception {
        return userService.modifyPassword(userPsd);
    }

}