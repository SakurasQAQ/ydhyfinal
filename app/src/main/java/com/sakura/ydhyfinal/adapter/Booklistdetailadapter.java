package com.sakura.ydhyfinal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.sakura.ydhyfinal.R;
import com.sakura.ydhyfinal.bean.MyWorks;
import com.sakura.ydhyfinal.utils.DelTagUtils;

import java.util.List;

//public class Booklistdetailadapter extends RecyclerView.Adapter<Booklistdetailadapter.BooklistdetailViewHolder>

public class Booklistdetailadapter extends PagedListAdapter<MyWorks,Booklistdetailadapter.BooklistdetailViewHolder> {



    private Context mContext;

    public Booklistdetailadapter(@NonNull DiffUtil.ItemCallback<MyWorks> diffCallback, Context mContext) {
        super(diffCallback);

        this.mContext = mContext;
    }

    @NonNull
    @Override
    public BooklistdetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {



        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.booksdetail_list_item,parent,false);


        return new BooklistdetailViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull BooklistdetailViewHolder holder, int position) {
        MyWorks worklist = getItem(position);



        holder.bltit.setText(worklist.getWorksName());
        holder.blaut.setText(worklist.getWorksAuthor());
        String contextTxt = "";
        contextTxt = DelTagUtils.delHtmlTags(worklist.getWorksIntroduction());
        holder.blcontext.setText("      "+contextTxt);
        //holder.blcontext.setText(worklist.getWorksIntroduction());

        Glide.with(mContext)
                .load(worklist.getWorksPicUrl())
                .placeholder(R.drawable.loading)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(5)))
                .into( holder.blimg);
    }






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


}
