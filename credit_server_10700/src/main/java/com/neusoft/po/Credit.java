package com.neusoft.po;


public class Credit {
    private Integer creditId;//自然增长

    private Integer deleted;//0:未删除 1:已删除

    private String userId;//用户id和用户表相连
    private Integer channelType;//类别
    private Integer creditNum;//积分数目
    private String createTime;//积分创建时间
    private String expiredTime;//积分失效日期

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public void setCreditId(Integer creditId) {
        this.creditId = creditId;
    }
    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public void setChannelType(Integer channelType) {
        this.channelType = channelType;
    }

    public void setNum(Integer creditNum) {
        this.creditNum = creditNum;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public void setExpiredTime(String expiredTime) {
        this.expiredTime = expiredTime;
    }

    public Integer getCreditId() {
        return creditId;
    }


    public Integer getChannelType() {
        return channelType;
    }

    public Integer getNum() {
        return creditNum;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getExpiredTime() {
        return expiredTime;
    }


}
