package com.sakura.ydhyfinal.gsonres;

import java.util.ArrayList;

public class Get_StoryQues {


    public ArrayList<Get_StoryDatalist> getDataList() {
        return dataList;
    }

    public void setDataList(ArrayList<Get_StoryDatalist> dataList) {
        this.dataList = dataList;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    private ArrayList<Get_StoryDatalist> dataList;
    private int pageNo;
    private int total;
    private int pages;


    public class Get_StoryDatalist{

        public String getQuestionId() {
            return questionId;
        }

        public void setQuestionId(String questionId) {
            this.questionId = questionId;
        }

        public String getStudentId() {
            return studentId;
        }

        public void setStudentId(String studentId) {
            this.studentId = studentId;
        }

        public String getQuestionDescription() {
            return questionDescription;
        }

        public void setQuestionDescription(String questionDescription) {
            this.questionDescription = questionDescription;
        }

        public int getRewardScore() {
            return rewardScore;
        }

        public void setRewardScore(int rewardScore) {
            this.rewardScore = rewardScore;
        }

        public long getQuestionTime() {
            return questionTime;
        }

        public void setQuestionTime(long questionTime) {
            this.questionTime = questionTime;
        }

        public int getReadPay() {
            return readPay;
        }

        public void setReadPay(int readPay) {
            this.readPay = readPay;
        }

        public int getCheckState() {
            return checkState;
        }

        public void setCheckState(int checkState) {
            this.checkState = checkState;
        }

        public int getThumbUpNumbers() {
            return thumbUpNumbers;
        }

        public void setThumbUpNumbers(int thumbUpNumbers) {
            this.thumbUpNumbers = thumbUpNumbers;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public int getCheckNum() {
            return checkNum;
        }

        public void setCheckNum(int checkNum) {
            this.checkNum = checkNum;
        }

        public String getTeacherId() {
            return teacherId;
        }

        public void setTeacherId(String teacherId) {
            this.teacherId = teacherId;
        }

        public String getTeacherName() {
            return teacherName;
        }

        public void setTeacherName(String teacherName) {
            this.teacherName = teacherName;
        }

        public String getStudentName() {
            return studentName;
        }

        public void setStudentName(String studentName) {
            this.studentName = studentName;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getAnswerNum() {
            return answerNum;
        }

        public void setAnswerNum(int answerNum) {
            this.answerNum = answerNum;
        }

        public String getBookId() {
            return bookId;
        }

        public void setBookId(String bookId) {
            this.bookId = bookId;
        }

        public String getBookName() {
            return bookName;
        }

        public void setBookName(String bookName) {
            this.bookName = bookName;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        private String questionId;
        private String studentId;
        private String questionDescription;
        private int rewardScore;
        private long questionTime;

        private int readPay;
        private int checkState;
        private int thumbUpNumbers;
        private String region;

        private int checkNum;
        private String teacherId;
        private String teacherName;
        private String studentName;
        private String type;
        private int answerNum;
        private String bookId;
        private String bookName;

        private String picUrl;

    }




}
