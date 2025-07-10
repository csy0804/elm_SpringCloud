package com.neusoft.service.impl;
import com.neusoft.po.UserImg;
import com.neusoft.po.UserPsd;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.neusoft.mapper.UserMapper;
import com.neusoft.po.User;
import com.neusoft.service.UserService;
@Service
public class UserServiceImpl implements UserService {

 @Autowired
 private UserMapper userMapper;

 @Override
 public User getUserByIdByPass(User user) {
  return userMapper.getUserByIdByPass(user);
 }

 @Override
 public User getUserById(String userId) {
  return userMapper.getUserById(userId);
 }

 @Override
 public int saveUser(User user) {
  return userMapper.saveUser(user);
 }

 @Override
 public int modifyPassword(UserPsd userPsd) {

  return userMapper.modifyPassword(userPsd);
 }

 @Override
 public int changeUserPortrait(UserImg userImg) {//base64
  String userId = userImg.getUserId();
  String base64 = userImg.getBase64();
//     System.out.println(url);
//     System.out.println(userId);
//     String base64Encoder = UrlToBase64.imageChangeBase64(url);
//     System.out.println(base64Encoder);
//        byte[] buffer = null;
//        try {
//            buffer = file.getBytes();
//            base64Encoder = Base64.encodeBase64String(buffer);
//            // 防止Base64编码中含有换行符，统一全部替换掉
//            base64Encoder = base64Encoder.replaceAll("[\\s*\t\n\r]", "");
//            // 添加前端读取需要的前缀
//            base64Encoder = "data:image/png;base64," + base64Encoder;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
  userMapper.prechangeUserPortrait(userId);
  return userMapper.changeUserPortrait(userId, base64);

 }
}