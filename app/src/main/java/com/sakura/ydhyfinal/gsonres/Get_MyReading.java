package com.sakura.ydhyfinal.gsonres;

import java.util.ArrayList;

public class Get_MyReading {

    int totalPage;
    int currentPage;

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

    public ArrayList<GetList> getDataList() {
        return dataList;
    }

    public void setDataList(ArrayList<GetList> dataList) {
        this.dataList = dataList;
    }

    ArrayList<GetList> dataList;

    public class GetList{
        String id;

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

        String title;
        String coverImg;


    }

}
