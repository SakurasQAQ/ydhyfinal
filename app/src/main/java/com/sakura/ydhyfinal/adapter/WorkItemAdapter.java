package com.sakura.ydhyfinal.adapter;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import com.sakura.ydhyfinal.R;
import com.sakura.ydhyfinal.bean.MyTask;
import com.sakura.ydhyfinal.bean.MyWorks;

import java.util.List;

public class WorkItemAdapter extends RecyclerView.Adapter<WorkItemAdapter.WorkItemViewHolder> {

    List<MyWorks> worksList;

    public WorkItemAdapter(List<MyWorks> worksList){
        this.worksList = worksList;
    }



    @NonNull
    @Override
    public WorkItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.mywork_list_items,parent,false);
        return new WorkItemAdapter.WorkItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkItemViewHolder holder, int position) {

        MyWorks works = worksList.get(position);

        holder.tit.setText(works.getWorksName());

        Glide.with(holder.itemView)
                .load(works.getWorksPicUrl())
                .placeholder(R.drawable.loading)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(5)))
                .into(holder.img);


        holder.zan.setText(String.valueOf(works.getThumbNumbers()));
        holder.lun.setText(String.valueOf(works.getPostNum()));

    }

    @Override
    public int getItemCount() {
        return worksList.size();
    }



    public class WorkItemViewHolder extends RecyclerView.ViewHolder{


        //private ImageView img;
        private ImageView img;

        private TextView tit,aut,contexts,zan,lun;

        public WorkItemViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.worksimg);
            tit = itemView.findViewById(R.id.worksTit);



            zan = itemView.findViewById(R.id.workzans);
            lun = itemView.findViewById(R.id.worklun);
        }
    }
}
