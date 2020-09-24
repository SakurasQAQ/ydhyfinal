package com.sakura.ydhyfinal.gsonres;

import java.util.ArrayList;

public class GetbookListP2_data {

    public ArrayList<Getbooklistp2_Item> dataList;

    //private List dataList;

    @Override
    public String toString() {
        return "GetbookListP2_data{" +
                "dataList=" + dataList +
                '}';
    }






    public class Getbooklistp2_Item {

        public String name;
        public String author;
        public String picUrl;
        public String id;


        @Override
        public String toString() {
            return "Getbooklistp2_Item{" +
                    "name='" + name + '\'' +
                    ", author='" + author + '\'' +
                    ", picUrl='" + picUrl + '\'' +
                    '}';
        }


    }
}
