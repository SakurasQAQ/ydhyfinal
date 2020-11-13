package com.sakura.ydhyfinal.gsonres;

import java.util.ArrayList;
import java.util.List;

public class Get_onlineJson_AllQues {

    public List<Get_onlineJson_AllQues_data> getDataList() {
        return dataList;
    }

    public void setDataList(List<Get_onlineJson_AllQues_data> dataList) {
        this.dataList = dataList;
    }

    private List<Get_onlineJson_AllQues_data> dataList;

    public class Get_onlineJson_AllQues_data{

        private String id;
        private String question;
        private String choiceA;
        private String choiceB;
        private String choiceC;
        private String choiceD;
        private String choiceE;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public String getChoiceA() {
            return choiceA;
        }

        public void setChoiceA(String choiceA) {
            this.choiceA = choiceA;
        }

        public String getChoiceB() {
            return choiceB;
        }

        public void setChoiceB(String choiceB) {
            this.choiceB = choiceB;
        }

        public String getChoiceC() {
            return choiceC;
        }

        public void setChoiceC(String choiceC) {
            this.choiceC = choiceC;
        }

        public String getChoiceD() {
            return choiceD;
        }

        public void setChoiceD(String choiceD) {
            this.choiceD = choiceD;
        }

        public String getChoiceE() {
            return choiceE;
        }

        public void setChoiceE(String choiceE) {
            this.choiceE = choiceE;
        }

        public String getChoiceF() {
            return choiceF;
        }

        public void setChoiceF(String choiceF) {
            this.choiceF = choiceF;
        }

        public String getChoiceG() {
            return ChoiceG;
        }

        public void setChoiceG(String choiceG) {
            ChoiceG = choiceG;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public String getBookId() {
            return bookId;
        }

        public void setBookId(String bookId) {
            this.bookId = bookId;
        }

        public String getDifficultyType() {
            return difficultyType;
        }

        public void setDifficultyType(String difficultyType) {
            this.difficultyType = difficultyType;
        }

        public String getDiffName() {
            return diffName;
        }

        public void setDiffName(String diffName) {
            this.diffName = diffName;
        }

        private String choiceF;
        private String ChoiceG;
        private String answer;
        private String bookId;
        private String difficultyType;
        private String diffName;

    }

}
