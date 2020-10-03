package com.sakura.ydhyfinal.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataBean {
    public Integer imageRes;
    public String imageUrl;
    public String title;
    public int msgType;

    public DataBean(String title, int msgType) {
        this.title = title;
        this.msgType = msgType;
    }

    public DataBean(String imageUrl, String title, int msgType) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.msgType = msgType;
    }



    public static List<DataBean> getTestData3() {
        List<DataBean> list = new ArrayList<>();
        list.add(new DataBean("https://ro.bnuz.edu.cn/ReadingOcean/wechat/static/images/banner.jpg?70f597405336f951f055de74a13ed783", null, 1));
        list.add(new DataBean("https://ro.bnuz.edu.cn/ReadingOcean/wechat/static/images/banner.jpg?70f597405336f951f055de74a13ed783", null, 2));
        list.add(new DataBean("https://ro.bnuz.edu.cn/ReadingOcean/wechat/static/images/banner.jpg?70f597405336f951f055de74a13ed783", null, 3));
        return list;
    }


}
