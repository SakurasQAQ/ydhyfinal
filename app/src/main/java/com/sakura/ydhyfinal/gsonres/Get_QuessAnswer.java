package com.sakura.ydhyfinal.gsonres;

import java.util.ArrayList;

public class Get_QuessAnswer {

    private int total;

    private ArrayList<Get_QuesanswerList> dataList;

    public ArrayList<Get_QuesanswerList> getDataList() {
        return dataList;
    }

    public class Get_QuesanswerList{



        public String getAnswerId() {
            return answerId;
        }

        public String getQuestionId() {
            return questionId;
        }

        public String getTeacherId() {
            return teacherId;
        }

        public String getAnswerContent() {
            return answerContent;
        }

        public long getAnswerTime() {
            return answerTime;
        }

        public int getIsBestAnswer() {
            return isBestAnswer;
        }

        public String getAnswerNum() {
            return answerNum;
        }

        public String getTeacherName() {
            return teacherName;
        }
        private String answerId;

        public void setAnswerId(String answerId) {
            this.answerId = answerId;
        }

        public void setQuestionId(String questionId) {
            this.questionId = questionId;
        }

        public void setTeacherId(String teacherId) {
            this.teacherId = teacherId;
        }

        public void setAnswerContent(String answerContent) {
            this.answerContent = answerContent;
        }

        public void setAnswerTime(long answerTime) {
            this.answerTime = answerTime;
        }

        public void setIsBestAnswer(int isBestAnswer) {
            this.isBestAnswer = isBestAnswer;
        }

        public void setAnswerNum(String answerNum) {
            this.answerNum = answerNum;
        }

        public void setTeacherName(String teacherName) {
            this.teacherName = teacherName;
        }

        public void setThumbUpNumbers(int thumbUpNumbers) {
            this.thumbUpNumbers = thumbUpNumbers;
        }

        private String questionId;
        private String teacherId;
        private String answerContent;
        private long answerTime;
        private int isBestAnswer;
        private String answerNum;
        private String teacherName;
        private int thumbUpNumbers;

        public int getThumbUpNumbers() {
            return thumbUpNumbers;
        }



    }

}
