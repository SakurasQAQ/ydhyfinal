package com.sakura.ydhyfinal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.sakura.ydhyfinal.R;
import com.sakura.ydhyfinal.bean.MyWorks;

import java.util.List;

//public class Booklistdetailadapter extends RecyclerView.Adapter<Booklistdetailadapter.BooklistdetailViewHolder>

public class Booklistdetailadapter extends RecyclerView.Adapter<Booklistdetailadapter.BooklistdetailViewHolder> {

    private List<MyWorks> list;

    private Context mContext;

    public Booklistdetailadapter(List<MyWorks> list, Context mContext){


        this.list = list;
        this.mContext = mContext;
    }



    @NonNull
    @Override
    public BooklistdetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {



        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.booksdetail_list_item,parent,false);


        return new BooklistdetailViewHolder(view);
//        }else{
//            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
//            View view = layoutInflater.inflate(R.layout.adapter_footer, parent, false);
//
//            return new FooterHolder(view);
//            //FooterHolder footerHolder = new FooterHolder(view);
//
//        }

    }

    @Override
    public void onBindViewHolder(@NonNull BooklistdetailViewHolder holder, int position) {
        MyWorks worklist = list.get(position);



        ((BooklistdetailViewHolder) holder).bltit.setText(worklist.getWorksName());
        ((BooklistdetailViewHolder) holder).blaut.setText(worklist.getWorksAuthor());
        ((BooklistdetailViewHolder) holder).blcontext.setText(worklist.getWorksIntroduction());

        Glide.with(mContext)
                .load(worklist.getWorksPicUrl())
                .placeholder(R.drawable.loading)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(5)))
                .into(((BooklistdetailViewHolder) holder).blimg);
    }

//    @Override
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//
//        if (holder instanceof BooklistdetailViewHolder) {
//
//            MyWorks worklist = list.get(position);
//
//
//
//            ((BooklistdetailViewHolder) holder).bltit.setText(worklist.getWorksName());
//            ((BooklistdetailViewHolder) holder).blaut.setText(worklist.getWorksAuthor());
//            ((BooklistdetailViewHolder) holder).blcontext.setText(worklist.getWorksIntroduction());
//
//            Glide.with(mContext)
//                    .load(worklist.getWorksPicUrl())
//                    .placeholder(R.drawable.loading)
//                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(5)))
//                    .into(((BooklistdetailViewHolder) holder).blimg);
//
//        }else if(holder instanceof FooterHolder){
//            //底部“加载更多”item （等待动画用一个gif去实现）
//            Glide.with(mContext)
//                    .load(R.mipmap.loading)
//                    .into(((FooterHolder) holder).img);
//        }
//
//    }


//    //控件赋值
//    @Override
//    public void onBindViewHolder(@NonNull BooklistdetailViewHolder holder, int position) {
//
//        MyWorks worklist = list.get(position);
//        holder.bltit.setText(worklist.getWorksName());
//        holder.blaut.setText(worklist.getWorksAuthor());
//        holder.blcontext.setText(worklist.getWorksIntroduction());
//
//        Glide.with(mContext)
//                .load(worklist.getWorksPicUrl())
//                .placeholder(R.drawable.loading)
//                .apply(RequestOptions.bitmapTransform(new RoundedCorners(5)))
//                .into(holder.blimg);
//
//
//    }

//    @Override
//    public int getItemViewType(int position) {
//        if (position == list.size()) {
//            //最后一个 是底部item
//            return 1;
//        } else {
//            return 0;
//        }
//    }

//    //提供给外部调用的方法 刷新数据
//    public void updateData(List<MyWorks> mlist){
//        //再此处理获得的数据  list为传进来的数据
//        //... list传进来的数据 添加到mList中
//        //最后记得刷新item
//        list = mlist;
//
//        notifyDataSetChanged();
//    }



    @Override
    public int getItemCount() {
        return list.size();
    }

//    public int getItemCount() {
//        return list.size();
//    }


    //获取控件映射
    public class BooklistdetailViewHolder extends RecyclerView.ViewHolder{

        private ImageView blimg;
        private TextView bltit,blaut,blcontext;

        public BooklistdetailViewHolder(@NonNull View itemView) {
            super(itemView);

            blimg = itemView.findViewById(R.id.bookslistimg);
            bltit = itemView.findViewById(R.id.booksliststit);
            blaut = itemView.findViewById(R.id.worksaut);
            blcontext = itemView.findViewById(R.id.booksdtlistcontext);

        }
    }


    //底部"加载更多"item viewholder
    class FooterHolder extends RecyclerView.ViewHolder {
        ImageView img;

        public FooterHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.progressBar_loading);
        }


    }

}
