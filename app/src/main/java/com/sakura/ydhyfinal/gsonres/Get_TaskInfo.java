package com.sakura.ydhyfinal.gsonres;

import java.util.ArrayList;

public class Get_TaskInfo {
    private long endDate;
    private long startDate;

    private boolean hasComment;
    private boolean isDone;

    private String taskTitle;
    private String publisher;

    public long getEndDate() {
        return endDate;
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public boolean isHasComment() {
        return hasComment;
    }

    public void setHasComment(boolean hasComment) {
        this.hasComment = hasComment;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<taskBooksInfo> getTaskBooks() {
        return taskBooks;
    }

    public void setTaskBooks(ArrayList<taskBooksInfo> taskBooks) {
        this.taskBooks = taskBooks;
    }

    private String description;


    private ArrayList<taskBooksInfo> taskBooks;


    public class taskBooksInfo{
        String id;
        String title;

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

        public int getNecessary() {
            return necessary;
        }

        public void setNecessary(int necessary) {
            this.necessary = necessary;
        }

        String coverImg;
        int necessary;

    }


}
