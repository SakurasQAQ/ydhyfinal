package com.sakura.ydhyfinal.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.arch.core.executor.TaskExecutor;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.sakura.ydhyfinal.R;
import com.sakura.ydhyfinal.bean.StoryQues;
import com.sakura.ydhyfinal.homepage.StoryWrittingInfoActivity;
import com.sakura.ydhyfinal.utils.ChangeTime;
import com.sakura.ydhyfinal.utils.DelTagUtils;
import com.sakura.ydhyfinal.utils.OnMultiClickListener;

public class QuesAnswerAdapter extends PagedListAdapter<StoryQues,QuesAnswerAdapter.QuesAnswerViewHolder> {

    private Context context;

    public QuesAnswerAdapter(@NonNull DiffUtil.ItemCallback<StoryQues> diffCallback, Context context) {
        super(diffCallback);
        this.context = context;
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
        String quesInfo = DelTagUtils.delHtmlTags(ques.getQuestionDescription());
        holder.question.setText(quesInfo);

        holder.btn_line.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("questionId",ques.getQuestionId());
                intent.putExtra("studentName",ques.getStudentName());
                intent.putExtra("questionTime",String.valueOf(ques.getQuestionTime()));

                intent.putExtra("StudentId",ques.getStudentId());

                intent.putExtra("questionInfo",ques.getQuestionDescription());

                intent.setClass(context,StoryWrittingInfoActivity.class);

                context.startActivity(intent);

            }
        });

    }

    class QuesAnswerViewHolder extends RecyclerView.ViewHolder {


        TextView name,time,question;

        LinearLayout btn_line;




        public QuesAnswerViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.ques_username);
            time = itemView.findViewById(R.id.ques_datatime);
            question = itemView.findViewById(R.id.ques_question);

            btn_line = itemView.findViewById(R.id.quessub_info);
        }
    }

}
