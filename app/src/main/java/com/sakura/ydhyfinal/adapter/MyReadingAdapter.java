package com.sakura.ydhyfinal.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedList;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.sakura.ydhyfinal.Activity.ShowBooksInfoActivity;
import com.sakura.ydhyfinal.R;
import com.sakura.ydhyfinal.bean.MyTask;
import com.sakura.ydhyfinal.bean.TaskBooks;
import com.sakura.ydhyfinal.utils.OnMultiClickListener;

public class MyReadingAdapter extends PagedListAdapter<TaskBooks, MyReadingAdapter.MyReadingViewHolder> {

    Context context;

    public MyReadingAdapter(@NonNull DiffUtil.ItemCallback<TaskBooks> diffCallback, Context context) {
        super(diffCallback);
        this.context = context;
    }

    @NonNull
    @Override
    public MyReadingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.homebooks_list_item,parent,false);
        MyReadingViewHolder holder = new MyReadingViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyReadingViewHolder holder, int position) {

        TaskBooks taskBooks = getItem(position);

        holder.txt.setText(taskBooks.getTitle());
        Glide.with(context)
                .load(taskBooks.getCoverImg())
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(5)))
                .into(holder.img);

        holder.img.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("booksid",taskBooks.getId());
                intent.setClass(context, ShowBooksInfoActivity.class);
                context.startActivity(intent);
            }
        });

    }

    public class MyReadingViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView txt;

        public MyReadingViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.homelistimg);
            txt = itemView.findViewById(R.id.homelisttit);


        }
    }

}
