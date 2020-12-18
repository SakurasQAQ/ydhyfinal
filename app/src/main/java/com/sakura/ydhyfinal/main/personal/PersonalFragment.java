package com.sakura.ydhyfinal.main.personal;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.bluetooth.BluetoothGattDescriptor;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.githang.statusbar.StatusBarCompat;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.sakura.ydhyfinal.Activity.FeedbackActivity;
import com.sakura.ydhyfinal.Activity.PersonalInfoActivity;
import com.sakura.ydhyfinal.LoginActivity;
import com.sakura.ydhyfinal.R;
import com.sakura.ydhyfinal.databinding.PersonalFragmentBinding;
import com.sakura.ydhyfinal.utils.OnMultiClickListener;

public class PersonalFragment extends Fragment {

    private PersonalFragmentBinding binding;
    private PersonalViewModel mViewModel;
    private boolean isloginsuccessful;

    private View.OnClickListener listener = new OnMultiClickListener() {
        @Override
        public void onMultiClick(View v) {
            switch (v.getId()){
                case R.id.cardView2:
                    startActivity(new Intent(getContext(), LoginActivity.class));
                    break;

                case R.id.btn_exitlogin:

                    MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
                    builder.setTitle("确定退出登录？");
                    builder.setNegativeButton("取消", (dialog, which) -> {

                    });
                    builder.setPositiveButton("确定", (dialog, which) -> {
                        mViewModel.Logout();
                    });
                    builder.show();

                    break;

                case R.id.per_feedback:
                    startActivity(new Intent(getContext(), FeedbackActivity.class));
                    break;

                case R.id.per_info:
                    startActivity(new Intent(getContext(), PersonalInfoActivity.class));
                    break;

            }
        }
    };



    public static PersonalFragment newInstance() {
        return new PersonalFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = PersonalFragmentBinding.inflate(inflater);
        StatusBarCompat.setStatusBarColor((Activity) getContext(), getResources().getColor(R.color.white));

        binding.setData(mViewModel);
        binding.setLifecycleOwner(this);

        addlistener();


        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PersonalViewModel.class);

        mViewModel.getUsername().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.perUsernameShow.setText(String.valueOf(s));
            }
        });

        mViewModel.getUserschool().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.perSchoolShow.setText(String.valueOf(s));
            }
        });

        mViewModel.getUpoint().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.perZongtxt.setText(s);
            }
        });

        mViewModel.getUlevel().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.perLeveltxt.setText(s);
            }
        });


        mViewModel.getUcheng().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.perChtxt.setText(s);
            }
        });

        mViewModel.getIsLogin().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
               if(aBoolean==true){
                   initperView(true);
               }else{
                   initperView(false);
               }
            }
        });

        //initperView();


        // TODO: Use the ViewModel
    }

    @Override
    public void onResume() {
        super.onResume();
        mViewModel.loginState();

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        mViewModel.loginState();
    }

    private void addlistener(){
        binding.cardView2.setOnClickListener(listener);
        binding.btnExitlogin.setOnClickListener(listener);

        binding.perFeedback.setOnClickListener(listener);

        binding.perInfo.setOnClickListener(listener);
    }



    private void initperView(boolean jude){


        if(jude){

            //binding.perUsernameShow.setVisibility(View.VISIBLE);
            //binding.perSchoolShow.setVisibility(View.VISIBLE);
            binding.perClassShow.setVisibility(View.VISIBLE);
            binding.perGradeShow.setVisibility(View.VISIBLE);




            //显示控件
            binding.btnExitlogin.setVisibility(View.VISIBLE);
            binding.cardView2.setClickable(false);

            setViewvalue();

        }else{


            binding.btnExitlogin.setVisibility(View.GONE);

            //binding.perUsernameShow.setVisibility(View.GONE);
            //binding.perSchoolShow.setVisibility(View.GONE);
            binding.perClassShow.setVisibility(View.GONE);
            binding.perGradeShow.setVisibility(View.GONE);
            binding.cardView2.setClickable(true);

            binding.cardView2.setImageResource(R.drawable.login_xuesheng);

        }




    }

    private void setViewvalue(){

        SharedPreferences userinfos = getContext().getSharedPreferences("user",Context.MODE_PRIVATE);

        Glide.with(getContext())
            .load(userinfos.getString("userimg",""))
            .into(binding.cardView2);

        //binding.perUsernameShow.setText(userinfos.getString("usertName",""));
        //binding.perSchoolShow.setText(userinfos.getString("schoolName",""));
        binding.perClassShow.setText(userinfos.getString("className",""));
        binding.perGradeShow.setText(userinfos.getString("grade",""));

//        binding.perZongtxt.setText(userinfos.getString("userPoints",""));
//        binding.perLeveltxt.setText(userinfos.getString("rank",""));
//        binding.perChtxt.setText(userinfos.getString("ranktit",""));




    }





}
