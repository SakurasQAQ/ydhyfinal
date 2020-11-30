package com.sakura.ydhyfinal.gsonres;

import java.util.ArrayList;

public class Get_Ranks {

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public ArrayList<Get_rankslist> getData() {
        return data;
    }


    private int code;
    private String result;


    private ArrayList<Get_rankslist> data;

    public class Get_rankslist{

        private String userId;
        private String studentId;
        private String name;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getStudentId() {
            return studentId;
        }

        public void setStudentId(String studentId) {
            this.studentId = studentId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getGradeName() {
            return gradeName;
        }

        public void setGradeName(String gradeName) {
            this.gradeName = gradeName;
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public String getSchoolName() {
            return schoolName;
        }

        public void setSchoolName(String schoolName) {
            this.schoolName = schoolName;
        }

        public int getBookNumber() {
            return bookNumber;
        }

        public void setBookNumber(int bookNumber) {
            this.bookNumber = bookNumber;
        }

        public int getTotalIntegral() {
            return totalIntegral;
        }

        public void setTotalIntegral(int totalIntegral) {
            this.totalIntegral = totalIntegral;
        }

        public double getQuantity() {
            return quantity;
        }

        public void setQuantity(double quantity) {
            this.quantity = quantity;
        }

        public int getTeatotalIntegral() {
            return teatotalIntegral;
        }

        public void setTeatotalIntegral(int teatotalIntegral) {
            this.teatotalIntegral = teatotalIntegral;
        }

        private String gradeName;

        private String className;

        private String schoolName;

        private int bookNumber;
        private int totalIntegral;
        private double quantity;
        private int teatotalIntegral;





    }

}
