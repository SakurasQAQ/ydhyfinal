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

        public void setStudentId(String studentId) {
            this.studentId = studentId;
        }

        public String getStudentId() {
            return studentId;
        }

        public String getBookId() {
            return bookId;
        }

        public void setBookId(String bookId) {
            this.bookId = bookId;
        }

        String studentId;

        String bookId;

        int pages;

        int isDone;

        String targetAnimalId;

        long createTime;

        long updateTime;

        String assess;

        String assessTime;

        String animalName;

        String url;

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }

        public int getIsDone() {
            return isDone;
        }

        public void setIsDone(int isDone) {
            this.isDone = isDone;
        }

        public String getTargetAnimalId() {
            return targetAnimalId;
        }

        public void setTargetAnimalId(String targetAnimalId) {
            this.targetAnimalId = targetAnimalId;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }

        public String getAssess() {
            return assess;
        }

        public void setAssess(String assess) {
            this.assess = assess;
        }

        public String getAssessTime() {
            return assessTime;
        }

        public void setAssessTime(String assessTime) {
            this.assessTime = assessTime;
        }

        public String getAnimalName() {
            return animalName;
        }

        public void setAnimalName(String animalName) {
            this.animalName = animalName;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

}
