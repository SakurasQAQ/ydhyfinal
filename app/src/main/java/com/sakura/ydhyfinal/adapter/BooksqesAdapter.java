package com.sakura.ydhyfinal.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sakura.ydhyfinal.R;
import com.sakura.ydhyfinal.bean.Recordsmark;

import java.text.NumberFormat;
import java.util.List;

public class BooksqesAdapter extends RecyclerView.Adapter<BooksqesAdapter.BooksqesViewHolder>{

    private Context mContext;
    private List<Recordsmark> reclist;

    public BooksqesAdapter(Context mContext, List<Recordsmark> reclist){
        this.mContext = mContext;
        this.reclist = reclist;
    }



    @NonNull
    @Override
    public BooksqesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.booksdetail_ques_item, parent, false);
        return new BooksqesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BooksqesViewHolder holder, int position) {


        //转化小数百分数
        NumberFormat nt = NumberFormat.getPercentInstance();//获取格式化对象
        nt.setMinimumFractionDigits(1);//设置百分数精确度2即保留两位小数

        Recordsmark recordsmark = reclist.get(position);
        holder.date.setText(recordsmark.getData());
        holder.rote.setText(nt.format(recordsmark.getAccuracy()));
        holder.totaltime.setText("答题时长："+recordsmark.getTimes()+"秒");

        if(recordsmark.getAccuracy() >= 0.6){
            holder.img.setImageResource(R.drawable.ic_emoji_xiao);
            holder.rote.setTextColor(mContext.getResources().getColor(R.color.mediumaquamarine));
        }else if(recordsmark.getAccuracy()<0.6){
            holder.img.setImageResource(R.drawable.ic_emoji_wuyu);
            holder.rote.setTextColor(mContext.getResources().getColor(R.color.indianred));
        }

    }

    @Override
    public int getItemCount() {
        return reclist.size();
    }

    class BooksqesViewHolder extends RecyclerView.ViewHolder {

        private TextView date,rote,totaltime;
        private ImageView img;


        public BooksqesViewHolder(@NonNull View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.qa_date);
            rote = itemView.findViewById(R.id.qa_rote);
            totaltime  = itemView.findViewById(R.id.qa_time);

            img = itemView.findViewById(R.id.qa_img);

        }
    }
}
