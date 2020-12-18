package com.sakura.ydhyfinal.homepage;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.sakura.ydhyfinal.bean.AnswerBean;
import com.sakura.ydhyfinal.gsonres.Get_MSG;
import com.sakura.ydhyfinal.gsonres.Get_QuessAnswer;
import com.sakura.ydhyfinal.utils.RequestManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StoryWrittingInfoViewModel extends AndroidViewModel {
    public StoryWrittingInfoViewModel(@NonNull Application application) {
        super(application);
    }

    private Get_MSG msg;

    private Get_QuessAnswer answer;

    private ArrayList<AnswerBean> answerList = new ArrayList<>();

    public Get_MSG getMsg() {
        return msg;
    }

    private MutableLiveData<String> deleteJude;

    public MutableLiveData<String> getDeleteJude() {
        if(deleteJude == null){
            deleteJude = new MutableLiveData<>();
        }
        return deleteJude;
    }

    private MutableLiveData<List<AnswerBean>> myList;

    public MutableLiveData<List<AnswerBean>> getMyList() {
        if(myList == null){
            myList = new MutableLiveData<>();
        }
        return myList;
    }

    MutableLiveData<Integer> ExsitList;

    public MutableLiveData<Integer> getExsitList() {
        if(ExsitList == null){
            ExsitList = new MutableLiveData<>();
        }

        return ExsitList;
    }

    public void getAnswerOnline(String quesId){

        HashMap map = new HashMap();

        map.put("questionId",quesId);

        RequestManager requestManager = new RequestManager(getApplication());
//        requestManager.requestAsyn();

        requestManager.requestAsyn("/mobileqa/answer", 0, map, new RequestManager.ReqCallBack<Object>() {
            @Override
            public void onReqSuccess(Object result) {
                Gson gson = new Gson();
                answer = gson.fromJson(String.valueOf(result),Get_QuessAnswer.class);

                AnswerBean answerBean;
                for(int i = 0;i<answer.getDataList().size();i++){
                    answerBean = new AnswerBean();
                    answerBean.setAnswerId(answer.getDataList().get(i).getAnswerId());
                    answerBean.setQuestionId(answer.getDataList().get(i).getQuestionId());
                    answerBean.setTeacherId(answer.getDataList().get(i).getTeacherId());
                    answerBean.setAnswerContent(answer.getDataList().get(i).getAnswerContent());
                    answerBean.setAnswerTime(answer.getDataList().get(i).getAnswerTime());
                    answerBean.setThumbUpNumbers(answer.getDataList().get(i).getThumbUpNumbers());
                    answerBean.setTeacherName(answer.getDataList().get(i).getTeacherName());
                    answerList.add(answerBean);

                }

                if(answerList.size()!=0){
                    myList.setValue(answerList);
                }else {
                    ExsitList.setValue(1);
                }


            }

            @Override
            public void onReqFailed(String errorMsg) {

            }
        });


    }

    public void questionDelete(String id,String quesid){

        HashMap map = new HashMap();
        map.put("userId",id);
        map.put("questionId",quesid);
        Log.d("info", "onReqSuccess: "+id+"      "+quesid);
        RequestManager requestManager = RequestManager.getInstance(getApplication());
        requestManager.requestAsyn("/mobileqa/deleteQuestion", 1, map, new RequestManager.ReqCallBack<Object>() {
            @Override
            public void onReqSuccess(Object result) {
                Gson gson = new Gson();
                msg = gson.fromJson(String.valueOf(result),Get_MSG.class);

                deleteJude.setValue(msg.getMsg());
                Log.d("show1", "onReqSuccess: ");
            }

            @Override
            public void onReqFailed(String errorMsg) {
                Log.d("show2", "onReqSuccess: "+errorMsg);
            }
        });

    }


}
