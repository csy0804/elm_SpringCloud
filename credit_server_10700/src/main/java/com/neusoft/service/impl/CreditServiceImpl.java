package com.neusoft.service.impl;

import com.neusoft.mapper.CreditMapper;
import com.neusoft.po.Credit;
import com.neusoft.po.CreditList;
import com.neusoft.service.CreditService;
import com.neusoft.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Service
public class CreditServiceImpl implements CreditService {
    @Autowired
    private CreditMapper creditMapper;

    @Override
    public Integer getTotalCreditByUserId(String userId) {
        List<Credit> list = creditMapper.getTotalCreditByUserId(userId);
        CreditList creditList = new CreditList(list);
        Integer totalCredit = creditList.getTotalNum();
        for (Credit credit : creditList.getExpiredList())
            creditMapper.deleteCreditCopyByCreditId(credit.getCreditId());
        return totalCredit;
    }

    @Override
    public List<Credit> getAvailableCreditByUserId(String userId) {
        List<Credit> list = creditMapper.getTotalCreditByUserId(userId);
        List<Credit> returnlist = new ArrayList<>();
        //将list中所有expierdTime小于当前时间的积分删除
        for (Credit credit : list) {
            if (CommonUtil.compareDate(credit.getExpiredTime(), CommonUtil.getCurrentDate1()) == 1) {
                returnlist.add(credit);
            } else {
                creditMapper.deleteCreditCopyByCreditId(credit.getCreditId());
            }
        }
        return returnlist;
    }

    @Override
    public List<Credit> getCreditByUserId(String userId) {
        return creditMapper.getCreditByUserId(userId);
    }

    @Override
    public List<Credit> getCreditByUserIdAndChannelType(String userId, int channelType) {
        return creditMapper.getCreditByUserIdAndChannelType(userId, channelType);
    }

    @Override
    public int insertCredit(Credit credit) {
        credit.setCreateTime(CommonUtil.getCurrentDate1());
        if (credit.getNum() >= 0) {
            credit.setExpiredTime(CommonUtil.getCurrentDate2());
            creditMapper.insertCreditCopy(credit);
        } else {
            Integer left = -credit.getNum();
            List<Credit> list = creditMapper.getTotalCreditByUserId(credit.getUserId());
            CreditList creditList = new CreditList(list);
            Credit UpdateCredit = creditList.getUpdatedCredit(left);
            creditMapper.updateCreditCopyByCreditId(UpdateCredit.getCreditId(), UpdateCredit.getNum());
            for (Credit credit2 : creditList.getDeletedList()) {
                creditMapper.deleteCreditCopyByCreditId(credit2.getCreditId());
            }
        }
        return creditMapper.insertCredit(credit);
    }

    @Override
    public int deleteAllbyUser(String userId) {
        creditMapper.deleteAllbyUser2(userId);
        return creditMapper.deleteAllbyUser(userId);
    }

    @Override
    public int updCredit(Integer creditId,Integer num) {
        return creditMapper.updCredit(creditId,new BigDecimal(num));
    }
}