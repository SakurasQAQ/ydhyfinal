package com.sakura.ydhyfinal.bean;

public class RankBean {


//    书籍排行类型  1：
//                 2：
    private int ranktype;



    public int getRanktype() {
        if(totalIntegral != 0 && quantity == 0){
            ranktype=0;
        }

        if(totalIntegral == 0 && quantity != 0){
            ranktype = 1;
        }

        return ranktype;
    }



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
