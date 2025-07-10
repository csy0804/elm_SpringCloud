package com.neusoft.mapper;

import com.neusoft.po.Credit;
import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;
import java.util.List;


@Mapper
public interface CreditMapper {
    //get total credit by userId and not deleted
    @Select("select * from credit_copy where userId=#{userId} and deleted=0")
    public List<Credit> getTotalCreditByUserId(@Param("userId") String userId);

    //get credit by userId
    @Select("select * from credit where userId=#{userId}")
    public List<Credit> getCreditByUserId(String userId);

    //get credit by userId and channelType
    @Select("select * from credit where userId=#{userId} and channelType=#{channelType}")
    public List<Credit> getCreditByUserIdAndChannelType(String userId, int channelType);

    //insert credit
    @Insert("insert into credit(userId, creditNum, channelType, createTime,expiredTime) values(#{userId}, #{creditNum}, #{channelType}, #{createTime},#{expiredTime})")
    @Options(useGeneratedKeys = true, keyProperty = "creditId", keyColumn = "creditId")
    public int insertCredit(Credit credit);

    @Insert("insert into credit_copy(userId, creditNum, channelType, createTime,expiredTime,deleted) values(#{userId}, #{creditNum}, #{channelType}, #{createTime},#{expiredTime},0)")
    @Options(useGeneratedKeys = true, keyProperty = "creditId", keyColumn = "creditId")
    public int insertCreditCopy(Credit credit);

    //update credit_copy deleted=1 by creditId
    @Update("update credit_copy set deleted=1 where creditId=#{creditId}")
    public int deleteCreditCopyByCreditId(int creditId);

    //update credit_copy set num=leftnum by creditId
    @Update("update credit_copy set creditNum=#{leftNum} where creditId=#{creditId}")
    public int updateCreditCopyByCreditId(int creditId, int leftNum);

    @Update("update credit_copy set deleted=1 where creditId=#{creditId}")
    int delCredit(Integer creditId);

    @Update("update credit_copy set creditNum=#{num} where creditId=#{creditId}")
    int updCredit(Integer creditId, BigDecimal num);


    @Update("update credit set channelType=1 where userId=#{userId}  and creditId<(select t from(select max(creditId) t from credit) k)")
    public int deleteAllbyUser(String userId);

    @Update("update credit_copy set deleted=1 ,channelType=1 where userId=#{userId} and creditId<(select t from(select max(creditId) t from credit_copy) k)")
    public int deleteAllbyUser2(String userId);
}
