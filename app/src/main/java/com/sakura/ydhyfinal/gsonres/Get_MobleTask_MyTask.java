package com.sakura.ydhyfinal.gsonres;

import java.util.ArrayList;

/*
*
* mytask任务获取
* */

public class Get_MobleTask_MyTask {


    public ArrayList<Get_MobleTask_MyTask_data> getDataList() {
        return dataList;
    }

    public void setDataList(ArrayList<Get_MobleTask_MyTask_data> dataList) {
        this.dataList = dataList;
    }

    private ArrayList<Get_MobleTask_MyTask_data> dataList;




    public class Get_MobleTask_MyTask_data{

        private String id;
        private String title;

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

        public String getPublisher() {
            return publisher;
        }

        public void setPublisher(String publisher) {
            this.publisher = publisher;
        }

        public long getStartDate() {
            return startDate;
        }

        public void setStartDate(long startDate) {
            this.startDate = startDate;
        }

        public long getEndDate() {
            return endDate;
        }

        public void setEndDate(long endDate) {
            this.endDate = endDate;
        }

        public boolean isDone() {
            return isDone;
        }

        public void setDone(boolean done) {
            isDone = done;
        }

        private String publisher;
        private long startDate;
        private long endDate;
        private boolean isDone;


    }






}
