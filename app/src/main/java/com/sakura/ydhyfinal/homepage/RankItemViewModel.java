package com.sakura.ydhyfinal.homepage;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.sakura.ydhyfinal.R;
import com.sakura.ydhyfinal.bean.RankBean;
import com.sakura.ydhyfinal.gsonres.Get_Ranks;
import com.sakura.ydhyfinal.utils.RequestManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Handler;


public class RankItemViewModel extends AndroidViewModel {
    public RankItemViewModel(@NonNull Application application) {
        super(application);
    }
    // TODO: Implement the ViewModel

    HashMap map = new HashMap();

    private Get_Ranks getRanks;

    private RankBean rankBean;

    private ArrayList<RankBean> ranklist = new ArrayList<>();

    public ArrayList<RankBean> getRanklist() {
        return ranklist;
    }

    private MutableLiveData<Integer> loadfinish;

    public MutableLiveData<Integer> getLoadfinish() {
        if(loadfinish == null){
            loadfinish = new MutableLiveData<>();
            loadfinish.setValue(0);
        }

        return loadfinish;
    }

    public void loadRankdara(String acurl){



        RequestManager requestManager = RequestManager.getInstance(getApplication());

        requestManager.requestAsyn(acurl, 0,map, new RequestManager.ReqCallBack<Object>() {
            @Override
            public void onReqSuccess(Object result) {

                Gson gson = new Gson();
                getRanks = gson.fromJson(String.valueOf(result),Get_Ranks.class);

                for(int i = 0;i<getRanks.getData().size();i++){
                    rankBean = new RankBean();
                    rankBean.setUserId(getRanks.getData().get(i).getUserId());
                    rankBean.setName(getRanks.getData().get(i).getName());
                    rankBean.setGradeName(getRanks.getData().get(i).getGradeName());
                    rankBean.setSchoolName(getRanks.getData().get(i).getSchoolName());
                    rankBean.setBookNumber(getRanks.getData().get(i).getBookNumber());
                    rankBean.setTotalIntegral(getRanks.getData().get(i).getTotalIntegral());
                    rankBean.setQuantity(getRanks.getData().get(i).getQuantity());

                    ranklist.add(rankBean);
                }

                loadfinish.setValue(1);

            }

            @Override
            public void onReqFailed(String errorMsg) {
                Toast.makeText(getApplication(),"噢呀，网络出现错误了",Toast.LENGTH_SHORT).show();

            }
        });


    }


}