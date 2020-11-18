package com.sakura.ydhyfinal.gsonres;

import java.util.ArrayList;

public class Get_Courseclass {

    private int totalPage;
    private int currentPage;

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

    public ArrayList<CourseclassData> getDataList() {
        return dataList;
    }

    public void setDataList(ArrayList<CourseclassData> dataList) {
        this.dataList = dataList;
    }

    private ArrayList<CourseclassData> dataList;

    public class CourseclassData{

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

        private String author;
        private String video;
        private String title;

    }




}
