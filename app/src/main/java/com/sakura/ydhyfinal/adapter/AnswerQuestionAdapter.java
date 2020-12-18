package com.sakura.ydhyfinal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import com.sakura.ydhyfinal.R;
import com.sakura.ydhyfinal.bean.AnswerBean;
import com.sakura.ydhyfinal.utils.ChangeTime;

import java.util.ArrayList;
import java.util.List;

public class AnswerQuestionAdapter extends RecyclerView.Adapter<AnswerQuestionAdapter.AnswerQuestionViewHolder> {

    Context context;

    List<AnswerBean> list;

    public AnswerQuestionAdapter(List<AnswerBean> list, Context context){
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public AnswerQuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_answerques,parent,false);


        return new AnswerQuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnswerQuestionViewHolder holder, int position) {

        AnswerBean bean = list.get(position);

        holder.teachername.setText(bean.getTeacherName());

        String ctime = ChangeTime.format(String.valueOf(bean.getAnswerTime()));
        holder.time.setText(ctime);

        holder.zannum.setText(String.valueOf(bean.getThumbUpNumbers()));

        holder.mainContext.setText(bean.getAnswerContent());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class AnswerQuestionViewHolder extends RecyclerView.ViewHolder {


        TextView teachername,zannum,time,mainContext;




        public AnswerQuestionViewHolder(@NonNull View itemView) {
            super(itemView);

            teachername = itemView.findViewById(R.id.answer_teacher_name);
            zannum = itemView.findViewById(R.id.answer_zannumber);
            time = itemView.findViewById(R.id.answer_times);
            mainContext = itemView.findViewById(R.id.answer_mainans);

        }
    }

}
