package com.sakura.ydhyfinal.gsonres;

import java.util.List;

public class Get_Booksinfo {



    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBlockId() {
        return blockId;
    }

    public void setBlockId(String blockId) {
        this.blockId = blockId;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getPress() {
        return press;
    }

    public void setPress(String press) {
        this.press = press;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Integer getReadNum() {
        return readNum;
    }

    public void setReadNum(Integer readNum) {
        this.readNum = readNum;
    }

    public Integer getReadingNum() {
        return readingNum;
    }

    public void setReadingNum(Integer readingNum) {
        this.readingNum = readingNum;
    }

    private String coverImg;

    private String author;

    private String publishDate;

    private String title;

    private String blockId;

    private String review;

    private String press;

    private String introduction;

    private Integer readNum;

    private Integer readingNum;


    public List<AnswerList> getRecords() {
        return records;
    }

    public void setRecords(List<AnswerList> records) {
        this.records = records;
    }

    private List<AnswerList> records;

    public class AnswerList{

        public long getDate() {
            return date;
        }

        public void setDate(long date) {
            this.date = date;
        }

        long date;



        public double getAccuracy() {
            return accuracy;
        }

        public void setAccuracy(double accuracy) {
            this.accuracy = accuracy;
        }

        double accuracy;

        public int getConsume() {
            return consume;
        }

        public void setConsume(int consume) {
            this.consume = consume;
        }

        int consume;

    }


    public List<CourseList> getCourses() {
        return courses;
    }

    private List<CourseList> courses;

    public class CourseList{

        private String author;
        private String video;
        private String title;

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getVideo() {
            return video;
        }

        public void setVideo(String video) {
            this.video = video;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }



}
