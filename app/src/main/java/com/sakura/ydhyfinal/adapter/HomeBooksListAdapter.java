package com.sakura.ydhyfinal.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.sakura.ydhyfinal.Activity.ShowBooksInfoActivity;
import com.sakura.ydhyfinal.R;
import com.sakura.ydhyfinal.bean.Booksinfo;

import java.util.ArrayList;
import java.util.List;

public class HomeBooksListAdapter extends RecyclerView.Adapter<HomeBooksListAdapter.BooksListViewHolder> {

    private Context mContext;

    private List<Booksinfo> list;

    public HomeBooksListAdapter(List<Booksinfo> list,Context context){
        this.mContext = context;

        this.list = list;
    }



//    public HomeBooksListAdapter(ArrayList<Booksinfo> bookslist) {
//    }

    @NonNull
    @Override
    public HomeBooksListAdapter.BooksListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.homebooks_list_item, parent, false);
//        BooksListViewHolder Blvs = new BooksListViewHolder(itemView);
//        int parentHeight= parent.getHeight();
//        parent.getWidth();
//        ViewGroup.LayoutParams layoutParams = Blvs.itemView.getLayoutParams();
//        layoutParams.height = (parentHeight/4);

        return new BooksListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeBooksListAdapter.BooksListViewHolder holder, int position) {
        Booksinfo booksinfo = list.get(position);

        holder.txtv.setText(booksinfo.getBooksName());

        Glide.with(mContext)
                .load(booksinfo.getBooksimgurl())
                .placeholder(R.drawable.loading)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(5)))
                .into(holder.imgv);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "我被点击了"+booksinfo.getBooksId(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.putExtra("booksid",booksinfo.getBooksId());
                intent.setClass(mContext, ShowBooksInfoActivity.class);
                mContext.startActivity(intent);
            }
        });

    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    class BooksListViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgv;
        private TextView txtv;

        public BooksListViewHolder(@NonNull View itemView) {
            super(itemView);

            imgv = itemView.findViewById(R.id.homelistimg);
            txtv = itemView.findViewById(R.id.homelisttit);
        }
    }

}
