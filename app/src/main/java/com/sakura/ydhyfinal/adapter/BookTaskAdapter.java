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
import com.sakura.ydhyfinal.bean.TaskBooks;

import java.util.List;

public class BookTaskAdapter extends RecyclerView.Adapter<BookTaskAdapter.BookTaskViewHolder> {

    Context context;
    List<TaskBooks> list;

    public BookTaskAdapter(Context context, List<TaskBooks> list){

        this.list = list;
        this.context = context;

    };

    @NonNull
    @Override
    public BookTaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.homebooks_list_item, parent, false);


        return new BookTaskViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BookTaskViewHolder holder, int position) {

        TaskBooks taskBooks = list.get(position);

        holder.text.setText(taskBooks.getTitle());

        Glide.with(context)
                .load(taskBooks.getCoverImg())
                .placeholder(R.drawable.loading)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(5)))
                .into(holder.img);

        if(taskBooks.getNecessary() == 1){
            holder.text.setTextColor(context.getResources().getColor(R.color.lightseagreen));
        }else {
            holder.text.setTextColor(context.getResources().getColor(R.color.indianred));
        }





    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class BookTaskViewHolder extends RecyclerView.ViewHolder {

        TextView text;
        ImageView img;

        public BookTaskViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.homelistimg);
            text = itemView.findViewById(R.id.homelisttit);
        }
    }
}
