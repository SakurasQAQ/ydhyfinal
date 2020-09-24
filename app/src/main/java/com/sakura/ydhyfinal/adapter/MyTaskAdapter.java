package com.sakura.ydhyfinal.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sakura.ydhyfinal.R;
import com.sakura.ydhyfinal.bean.MyTask;

import java.util.List;

public class MyTaskAdapter extends RecyclerView.Adapter<MyTaskAdapter.MyTaskViewHolder> {

    private List<MyTask> taskslist;

    public MyTaskAdapter(List<MyTask> taskslist){
        this.taskslist = taskslist;
    }


    @NonNull
    @Override
    public MyTaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.mytask_list_item,parent,false);
        return new MyTaskViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyTaskViewHolder holder, int position) {
        MyTask myTask = taskslist.get(position);

        holder.TaskTitle.setText(myTask.getTaskTitle());

        if(myTask.isDone()){
            holder.isDone.setText("评教情况：已评教");
            holder.isDone.setTextColor(Color.parseColor("#3CB371"));
        }else{
            holder.isDone.setText("评教情况：未完成");
            holder.isDone.setTextColor(Color.parseColor("#FFA07A"));
        }

        holder.Taskjiaoshi.setText(myTask.getPublisher());
        holder.Taskstart.setText(myTask.getStartDate());
        holder.Taskend.setText(myTask.getEndDate());

    }

    @Override
    public int getItemCount() {
        return taskslist.size();
    }

    class MyTaskViewHolder extends RecyclerView.ViewHolder{

        private TextView TaskTitle,Taskjiaoshi,Taskstart,Taskend,isDone;
        private Button Taskbtn;

        public MyTaskViewHolder(@NonNull View itemView) {
            super(itemView);

            TaskTitle = itemView.findViewById(R.id.rask_tit);
            Taskjiaoshi = itemView.findViewById(R.id.taskcgjiaoshi);
            Taskstart = itemView.findViewById(R.id.taskcgstart);
            Taskend = itemView.findViewById(R.id.taskcgend);

            isDone = itemView.findViewById(R.id.taskisDone);

            Taskbtn = itemView.findViewById(R.id.task_btn);
        }
    }

}
