package com.sakura.ydhyfinal.bean;


/*
* 用于封装论坛板块查询对象
* */

public class MyWorks {

    public String getBooksid() {
        return booksid;
    }

    public void setBooksid(String booksid) {
        this.booksid = booksid;
    }

    private String booksid;

    private String worksId;
    private String worksName;
    private String worksIntroduction;
    private String worksAuthor;
    private String worksRecommend;
    private String worksPublishingHouse;
    private String worksRemark;
    private String worksPicUrl;
    private int worksisDone;
    private String worksCategory;
    private String topicId;

    private long postNum;
    private long thumbNumbers;

    public long getPostNum() {
        return postNum;
    }

    public void setPostNum(long postNum) {
        this.postNum = postNum;
    }

    public long getThumbNumbers() {
        return thumbNumbers;
    }

    public void setThumbNumbers(long thumbNumbers) {
        this.thumbNumbers = thumbNumbers;
    }

    public String getWorksId() {
        return worksId;
    }

    public void setWorksId(String worksId) {
        this.worksId = worksId;
    }

    public String getWorksName() {
        return worksName;
    }

    public void setWorksName(String worksName) {
        this.worksName = worksName;
    }

    public String getWorksIntroduction() {
        return worksIntroduction;
    }

    public void setWorksIntroduction(String worksIntroduction) {
        this.worksIntroduction = worksIntroduction;
    }

    public String getWorksAuthor() {
        return worksAuthor;
    }

    public void setWorksAuthor(String worksAuthor) {
        this.worksAuthor = worksAuthor;
    }

    public String getWorksRecommend() {
        return worksRecommend;
    }

    public void setWorksRecommend(String worksRecommend) {
        this.worksRecommend = worksRecommend;
    }

    public String getWorksPublishingHouse() {
        return worksPublishingHouse;
    }

    public void setWorksPublishingHouse(String worksPublishingHouse) {
        this.worksPublishingHouse = worksPublishingHouse;
    }

    public String getWorksRemark() {
        return worksRemark;
    }

    public void setWorksRemark(String worksRemark) {
        this.worksRemark = worksRemark;
    }

    public String getWorksPicUrl() {
        return worksPicUrl;
    }

    public void setWorksPicUrl(String worksPicUrl) {
        this.worksPicUrl = worksPicUrl;
    }

    public int getWorksisDone() {
        return worksisDone;
    }

    public void setWorksisDone(int worksisDone) {
        this.worksisDone = worksisDone;
    }

    public String getWorksCategory() {
        return worksCategory;
    }

    public void setWorksCategory(String worksCategory) {
        this.worksCategory = worksCategory;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }



}
