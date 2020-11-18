package com.sakura.ydhyfinal.homepage;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.sakura.ydhyfinal.bean.Coursesclass;
import com.sakura.ydhyfinal.gsonres.Get_Courseclass;
import com.sakura.ydhyfinal.utils.RequestManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CourseItemViewModel extends AndroidViewModel {
    public CourseItemViewModel(@NonNull Application application) {
        super(application);
    }
    // TODO: Implement the ViewModel

    private Get_Courseclass get_courses;

    private Coursesclass course;

    private ArrayList<Coursesclass> courseList = new ArrayList<>();

    public ArrayList<Coursesclass> getCourseList() {
        return courseList;
    }

    private MutableLiveData<Integer> judeload;

    public MutableLiveData<Integer> getJudeload() {
        if(judeload == null)
        {
            judeload = new MutableLiveData<>();
            judeload.setValue(0);
        }

        return judeload;
    }

    public void getOnlineCourse(String type){

        HashMap map = new HashMap();
        map.put("type",type);

        RequestManager requestManager = new RequestManager(getApplication());

        requestManager.requestAsyn("/mobileCourse/getCourses", 0, map, new RequestManager.ReqCallBack<Object>() {
            @Override
            public void onReqSuccess(Object result) {

                Gson gson = new Gson();
                get_courses = gson.fromJson(String.valueOf(result),Get_Courseclass.class);

                for(int i = 0;i<get_courses.getDataList().size();i++){
                    course = new Coursesclass();
                    course.setAuthor(get_courses.getDataList().get(i).getAuthor());
                    course.setTitle(get_courses.getDataList().get(i).getTitle());
                    course.setVideourl(get_courses.getDataList().get(i).getVideo());

                    courseList.add(course);
                }

                if(get_courses.getTotalPage()>get_courses.getCurrentPage()){
                    getOnlineCourseMore(type,get_courses.getTotalPage());
                }else{
                    judeload.setValue(1);
                }




            }

            @Override
            public void onReqFailed(String errorMsg) {

            }
        });

    }

    private void getOnlineCourseMore(String type,int totalnum){

        for(int i = 1;i<totalnum;i++){

            HashMap map = new HashMap();
            map.put("type",type);
            map.put("pageNum",String.valueOf(i+1));

            RequestManager requestManager = RequestManager.getInstance(getApplication());

            int finalI = i;
            requestManager.requestAsyn("/mobileCourse/getCourses", 0, map, new RequestManager.ReqCallBack<Object>() {
                @Override
                public void onReqSuccess(Object result) {
                    Gson gson = new Gson();
                    get_courses = gson.fromJson(String.valueOf(result),Get_Courseclass.class);

                    for(int i = 0;i<get_courses.getDataList().size();i++){
                        course = new Coursesclass();
                        course.setAuthor(get_courses.getDataList().get(i).getAuthor());
                        course.setTitle(get_courses.getDataList().get(i).getTitle());
                        course.setVideourl(get_courses.getDataList().get(i).getVideo());

                        courseList.add(course);
                    }

                    if(finalI == totalnum-1){
                        judeload.setValue(1);
                    }
                }

                @Override
                public void onReqFailed(String errorMsg) {

                }
            });

        }


    }

}