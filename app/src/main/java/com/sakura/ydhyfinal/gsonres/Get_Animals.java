package com.sakura.ydhyfinal.gsonres;

import java.util.ArrayList;

public class Get_Animals {

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

    public ArrayList<Animals_Datalist> getDataList() {
        return dataList;
    }

    public void setDataLists(ArrayList<Animals_Datalist> dataList) {
        this.dataList = dataList;
    }

    private int totalPage;
    private int currentPage;
    private ArrayList<Animals_Datalist> dataList;


    public class Animals_Datalist{
        private String img;
        private int level;
        private String name;
        private int hasNum;
        private String id;
        private String introduction;

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getHasNum() {
            return hasNum;
        }

        public void setHasNum(int hasNum) {
            this.hasNum = hasNum;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIntroduction() {
            return introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }



    }

}
