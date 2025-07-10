package com.neusoft.service;
import com.neusoft.po.User;
import com.neusoft.po.UserImg;
import com.neusoft.po.UserPsd;
import org.apache.ibatis.annotations.Param;

public interface UserService {
 public User getUserByIdByPass(User user);
 public User getUserById(String userId);
 public int saveUser(User user);


    public int modifyPassword(UserPsd userPsd);

    public int changeUserPortrait(UserImg userImg);
}

