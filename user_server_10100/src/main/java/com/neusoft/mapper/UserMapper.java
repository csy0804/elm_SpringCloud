package com.neusoft.mapper;

import com.neusoft.po.User;
import com.neusoft.po.UserPsd;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
 @Select("select * from user where userId=#{userId} and password=#{password}")
 public User getUserByIdByPass(User user);

 @Select("select * from user where userId=#{userId}")
 public User getUserById(String userId);

 @Insert("insert into user values(#{userId},#{password},#{userName},#{userSex},null,1)")
 public int saveUser(User user);

 @Update("UPDATE user u SET u.userImg =null WHERE u.userId =#{userId}")
 public int prechangeUserPortrait(@Param("userId") String userId);

 @Update("UPDATE user u SET u.userImg =#{base64} WHERE u.userId =#{userId}")
 public int changeUserPortrait(@Param("userId") String userId, @Param("base64") String base64);

 @Update("update user set password=#{newPassword} where userId=#{userId} and password=#{oldPassword}")
 public int modifyPassword(UserPsd userPsd);
}