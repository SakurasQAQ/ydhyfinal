package com.sakura.ydhyfinal.gsonres;


/*
* 论坛板块查询
* */

import java.util.ArrayList;

public class Get_MobleBlocks_Booksblocks {

    private String code;
    private String result;

    public Get_MobleBlocks_Booksblocks_data getData() {
        return data;
    }

    public void setData(Get_MobleBlocks_Booksblocks_data data) {
        this.data = data;
    }

    private Get_MobleBlocks_Booksblocks_data data;


    public class Get_MobleBlocks_Booksblocks_data
    {
        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public int getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }

        private int totalPage;
        private int currentPage;

        public ArrayList<Get_MobleBlocks_Booksblocks_datalist> getDataList() {
            return dataList;
        }

        public void setDataList(ArrayList<Get_MobleBlocks_Booksblocks_datalist> dataList) {
            this.dataList = dataList;
        }

        private ArrayList<Get_MobleBlocks_Booksblocks_datalist> dataList;


        public class Get_MobleBlocks_Booksblocks_datalist{



            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

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

            public String getPages() {
                return pages;
            }

            public void setPages(String pages) {
                this.pages = pages;
            }

            public String getAuthor() {
                return author;
            }

            public void setAuthor(String author) {
                this.author = author;
            }

            public String getCategoryId() {
                return categoryId;
            }

            public void setCategoryId(String categoryId) {
                this.categoryId = categoryId;
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

            public String getCategory() {
                return category;
            }

            public void setCategory(String category) {
                this.category = category;
            }

            public int getIsDone() {
                return isDone;
            }

            public void setIsDone(int isDone) {
                this.isDone = isDone;
            }

            public String getTopicId() {
                return topicId;
            }

            public void setTopicId(String topicId) {
                this.topicId = topicId;
            }


            private String id;
            private String name;
            private String introduction;
            private String recommend;
            private String pages;
            private String author;
            private String categoryId;
            private String publishingHouse;
            private String remark;
            private String picUrl;
            private String category;
            private int isDone;
            private String topicId;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            private String title;

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            private String img;

            public Long getPostNum() {
                return postNum;
            }

            public void setPostNum(Long postNum) {
                this.postNum = postNum;
            }

            public Long getThumbNumbers() {
                return likeNum;
            }

            public void setThumbNumbers(Long thumbNumbers) {
                this.likeNum = thumbNumbers;
            }

            private Long postNum;
            private Long likeNum;


        }

    }

}

