package com.sakura.ydhyfinal.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;

import com.sakura.ydhyfinal.bean.QuesAnwser;
import com.sakura.ydhyfinal.gsonres.Get_onlineJson_AllQues;
import com.sakura.ydhyfinal.utils.RequestManager;

import java.util.ArrayList;
import java.util.HashMap;

public class AnswerQuesViewModel extends AndroidViewModel {
    public AnswerQuesViewModel(@NonNull Application application) {
        super(application);
    }


    private Get_onlineJson_AllQues jsonres;

    private MutableLiveData<Integer> mumbers;

    public MutableLiveData<Integer> getMumbers() {
        if (mumbers == null){
            mumbers = new MutableLiveData<>();
            mumbers.setValue(0);
        }
        return mumbers;
    }

    private ArrayList<QuesAnwser> listdan = new ArrayList<>();



    private ArrayList<QuesAnwser> listduo = new ArrayList<>();
    private ArrayList<QuesAnwser> listpanduan = new ArrayList<>();

    private ArrayList<QuesAnwser> alllist = new ArrayList<>();

    public ArrayList<QuesAnwser> getAlllist() {
        return alllist;
    }

    public void getOnlineQuestionAns(String booksid){

        HashMap map = new HashMap();
        map.put("bookId",booksid);

        RequestManager requestManager = new RequestManager(getApplication());
        //单选题
        Gson gson = new Gson();
        requestManager.requestAsyn("mbookq/singleq/query", 0, map, new RequestManager.ReqCallBack<Object>() {
            @Override
            public void onReqSuccess(Object result) {

                jsonres = gson.fromJson(String.valueOf(result),Get_onlineJson_AllQues.class);

                if (jsonres.getDataList().size()!=0){
                    Log.d("单选", "onReqSuccess: "+jsonres.getDataList().get(0).getQuestion());
                    QuesAnwser anwser;
                    for (int i = 0;i<jsonres.getDataList().size();i++){
                        anwser = new QuesAnwser();
                        anwser.setQuestype("单项选择题");
                        anwser.setQuesid(jsonres.getDataList().get(i).getId());
                        anwser.setQuestion(jsonres.getDataList().get(i).getQuestion());
                        anwser.setAnswer(jsonres.getDataList().get(i).getAnswer());
                        anwser.setChoiceA(jsonres.getDataList().get(i).getChoiceA());
                        anwser.setChoiceB(jsonres.getDataList().get(i).getChoiceB());
                        anwser.setChoiceC(jsonres.getDataList().get(i).getChoiceC());
                        anwser.setChoiceD(jsonres.getDataList().get(i).getChoiceD());
                        listdan.add(anwser);
                        Log.d("ca", "onReqSuccess: "+listdan.get(i).getAnswer());

                    }
                    alllist.addAll(listdan);

                }else {


                    Log.d("暂无记录", "onReqSuccess: ");
                }

                mumbers.setValue(mumbers.getValue()+1);


            }

            @Override
            public void onReqFailed(String errorMsg) {

            }
        });

        //判断题
        requestManager.requestAsyn("mbookq/truefalseq/query", 0, map, new RequestManager.ReqCallBack<Object>() {
            @Override
            public void onReqSuccess(Object result) {

                jsonres = gson.fromJson(String.valueOf(result),Get_onlineJson_AllQues.class);
                if(jsonres.getDataList().size()!=0){
                    Log.d("判断", "onReqSuccess: "+jsonres.getDataList().get(0).getQuestion());

                    QuesAnwser anwser;
                    for (int i = 0;i<jsonres.getDataList().size();i++){
                        anwser = new QuesAnwser();
                        anwser.setQuestype("判断题");
                        anwser.setQuesid(jsonres.getDataList().get(i).getId());
                        anwser.setQuestion(jsonres.getDataList().get(i).getQuestion());
                        anwser.setAnswer(jsonres.getDataList().get(i).getAnswer());

                        listpanduan.add(anwser);
                        Log.d("ca", "onReqSuccess: "+listpanduan.get(i).getAnswer());
                    }
                    alllist.addAll(listpanduan);


                }else {
                    Log.d("暂无数据", "onReqSuccess: ");
                }
                mumbers.setValue(mumbers.getValue()+1);

            }

            @Override
            public void onReqFailed(String errorMsg) {

            }
        });

        //多选题
        requestManager.requestAsyn("mbookq/multipleq/query", 0, map, new RequestManager.ReqCallBack<Object>() {
            @Override
            public void onReqSuccess(Object result) {
                jsonres = gson.fromJson(String.valueOf(result),Get_onlineJson_AllQues.class);
                if(jsonres.getDataList().size()!=0){
                    Log.d("多选", "onReqSuccess: "+jsonres.getDataList().get(0).getQuestion());

                    QuesAnwser anwser;
                    for (int i = 0;i<jsonres.getDataList().size();i++){
                        anwser = new QuesAnwser();
                        anwser.setQuestype("多项选择题");
                        anwser.setQuesid(jsonres.getDataList().get(i).getId());
                        anwser.setQuestion(jsonres.getDataList().get(i).getQuestion());
                        anwser.setAnswer(jsonres.getDataList().get(i).getAnswer());
                        anwser.setChoiceA(jsonres.getDataList().get(i).getChoiceA());
                        anwser.setChoiceB(jsonres.getDataList().get(i).getChoiceB());
                        anwser.setChoiceC(jsonres.getDataList().get(i).getChoiceC());
                        anwser.setChoiceD(jsonres.getDataList().get(i).getChoiceD());
                        anwser.setChoiceE(jsonres.getDataList().get(i).getChoiceE());
                        anwser.setChoiceF(jsonres.getDataList().get(i).getChoiceF());
                        listduo.add(anwser);
                        Log.d("ca", "onReqSuccess: "+listduo.get(i).getAnswer());
                    }
                    alllist.addAll(listduo);

                }else {
                    Log.d("暂无数据", "onReqSuccess: ");
                }
                mumbers.setValue(mumbers.getValue()+1);

            }

            @Override
            public void onReqFailed(String errorMsg) {

            }
        });



    }



}
