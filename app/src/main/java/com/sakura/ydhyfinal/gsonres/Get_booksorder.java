package com.sakura.ydhyfinal.gsonres;

public class Get_booksorder {

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    private String result;

    private Orderinfo data;

    public Orderinfo getdata() {
        return data;
    }

    public class Orderinfo{
        String studentId;
        public void setStudentId(String studentId) {
            this.studentId = studentId;
        }

        public String getStudentId() {
            return studentId;
        }
    }

}
