package com.sakura.ydhyfinal.gsonres;

import java.util.ArrayList;

public class Get_MobileBook_GetBooks {

    private String totalPage;

    public String getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(String totalPage) {
        this.totalPage = totalPage;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    private String currentPage;

    public ArrayList<Get_MobileBook_GetBooks_dataList> dataList;

    public class Get_MobileBook_GetBooks_dataList{
        private String id;

        @Override
        public String toString() {
            return "Get_MobileBook_GetBooks_dataList{" +
                    "id='" + id + '\'' +
                    ", title='" + title + '\'' +
                    ", coverImg='" + coverImg + '\'' +
                    '}';
        }

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
