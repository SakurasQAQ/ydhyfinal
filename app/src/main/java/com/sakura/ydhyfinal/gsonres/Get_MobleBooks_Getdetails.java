package com.sakura.ydhyfinal.gsonres;

/*
* mobileBook/infoBook
*
* */

public class Get_MobleBooks_Getdetails {

    private int code;
    private String result;


    public Get_MobleBooks_Getdetails_data getData() {
        return data;
    }

    public void setData(Get_MobleBooks_Getdetails_data data) {
        this.data = data;
    }

    private Get_MobleBooks_Getdetails_data data;



    public class Get_MobleBooks_Getdetails_data{

        private String name;
        private String introduction;
        private String recommend;
        private int pages;
        private String author;
        private String publishingHouse;
        private String remark;
        private String picUrl;
        private String topicId;


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIntroduction() {
            return introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }

        public String getRecommend() {
            return recommend;
        }

        public void setRecommend(String recommend) {
            this.recommend = recommend;
        }

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getPublishingHouse() {
            return publishingHouse;
        }

        public void setPublishingHouse(String publishingHouse) {
            this.publishingHouse = publishingHouse;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getTopicId() {
            return topicId;
        }

        public void setTopicId(String topicId) {
            this.topicId = topicId;
        }



    }

}
