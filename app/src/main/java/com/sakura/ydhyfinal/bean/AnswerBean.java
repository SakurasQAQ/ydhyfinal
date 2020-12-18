package com.sakura.ydhyfinal.bean;

public class AnswerBean {

    private String answerId;
    private String questionId;
    private String teacherId;
    private String answerContent;

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getAnswerContent() {
        return answerContent;
    }

    public void setAnswerContent(String answerContent) {
        this.answerContent = answerContent;
    }

    public long getAnswerTime() {
        return answerTime;
    }

    public void setAnswerTime(long answerTime) {
        this.answerTime = answerTime;
    }

    public int getIsBestAnswer() {
        return isBestAnswer;
    }

    public void setIsBestAnswer(int isBestAnswer) {
        this.isBestAnswer = isBestAnswer;
    }

    public String getAnswerNum() {
        return answerNum;
    }

    public void setAnswerNum(String answerNum) {
        this.answerNum = answerNum;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public int getThumbUpNumbers() {
        return thumbUpNumbers;
    }

    public void setThumbUpNumbers(int thumbUpNumbers) {
        this.thumbUpNumbers = thumbUpNumbers;
    }

    private long answerTime;
    private int isBestAnswer;
    private String answerNum;
    private String teacherName;
    private int thumbUpNumbers;

}
