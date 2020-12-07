package com.sakura.ydhyfinal.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.arch.core.executor.TaskExecutor;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.sakura.ydhyfinal.R;
import com.sakura.ydhyfinal.bean.StoryQues;
import com.sakura.ydhyfinal.utils.ChangeTime;

public class QuesAnswerAdapter extends PagedListAdapter<StoryQues,QuesAnswerAdapter.QuesAnswerViewHolder> {


    public QuesAnswerAdapter(@NonNull DiffUtil.ItemCallback<StoryQues> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public QuesAnswerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.question_list_items,parent,false);
        QuesAnswerViewHolder holder = new QuesAnswerViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull QuesAnswerViewHolder holder, int position) {
        StoryQues ques = getItem(position);
        String currenttime = ChangeTime.format(String.valueOf(ques.getQuestionTime()));


        holder.name.setText(ques.getStudentName());
        holder.time.setText(currenttime);
        holder.question.setText(ques.getQuestionDescription());

    }

    class QuesAnswerViewHolder extends RecyclerView.ViewHolder {


        TextView name,time,question;


        public QuesAnswerViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.ques_username);
            time = itemView.findViewById(R.id.ques_datatime);
            question = itemView.findViewById(R.id.ques_question);

        }
    }
}
