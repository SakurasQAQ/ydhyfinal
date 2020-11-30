package com.sakura.ydhyfinal.gsonres;

import java.util.ArrayList;

public class Get_searchResult {

    public ArrayList<resultList> getDataList() {
        return dataList;
    }

    public void setDataList(ArrayList<resultList> dataList) {
        this.dataList = dataList;
    }

    private ArrayList<resultList> dataList;

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    private int totalPage;
    private int currentPage;


    public class resultList{

        private String id;
        private String title;
        private String coverImg;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCoverImg() {
            return coverImg;
        }

        public void setCoverImg(String coverImg) {
            this.coverImg = coverImg;
        }
    }

}
