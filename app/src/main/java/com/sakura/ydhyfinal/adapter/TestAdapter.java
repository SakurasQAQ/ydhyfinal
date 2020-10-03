package com.sakura.ydhyfinal.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.sakura.ydhyfinal.R;
import com.sakura.ydhyfinal.bean.MyWorks;

import java.util.List;

public class TestAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final static int TYPE_CONTENT=0;//正常内容
    private final static int TYPE_FOOTER=1;//下拉刷新

    List<MyWorks> worksList;

    public TestAdapter(List<MyWorks> mList){
        worksList = mList;
    }


    @Override
    public int getItemViewType(int position) {
        if(position == worksList.size()){
            return TYPE_FOOTER;
        }
        return TYPE_CONTENT;
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == TYPE_FOOTER){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_footer,parent,false);

            return new FootViewHolder(view);
        }else{
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mywork_list_items,parent,false);
            return new WorkItemViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position)==TYPE_FOOTER){

        }else{
            WorkItemViewHolder viewHolder = (WorkItemViewHolder) holder;

            MyWorks works = worksList.get(position);

            viewHolder.tit.setText(works.getWorksName());

            Glide.with(holder.itemView)
                    .load(works.getWorksPicUrl())
                    .placeholder(R.drawable.loading)
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(5)))
                    .into(viewHolder.img);


            viewHolder.zan.setText(String.valueOf(works.getThumbNumbers()));
            viewHolder.lun.setText(String.valueOf(works.getPostNum()));
        }


    }

    @Override
    public int getItemCount() {
        return worksList.size()+1;
    }


    private class WorkItemViewHolder extends RecyclerView.ViewHolder{


        //private ImageView img;
        private ImageView img;

        private TextView tit,aut,contexts,zan,lun;

        private WorkItemViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.worksimg);
            tit = itemView.findViewById(R.id.worksTit);



            zan = itemView.findViewById(R.id.workzans);
            lun = itemView.findViewById(R.id.worklun);
        }
    }
    private class FootViewHolder extends RecyclerView.ViewHolder{
        private ContentLoadingProgressBar progressBar;
        public FootViewHolder(View itemView) {
            super(itemView);
            progressBar=itemView.findViewById(R.id.progressBar_loading);
        }
    }





}
