package com.sakura.ydhyfinal.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.sakura.ydhyfinal.Activity.ShowBooksInfoActivity;
import com.sakura.ydhyfinal.R;
import com.sakura.ydhyfinal.bean.Booksinfo;
import com.sakura.ydhyfinal.utils.OnMultiClickListener;

import java.util.List;

public class SearchBookAdapter extends RecyclerView.Adapter<SearchBookAdapter.SearchBookViewHolder> {

    private Context context;
    private List<Booksinfo> list;

    private Booksinfo booksinfo;

    private int existnum;

    public SearchBookAdapter(Context context, List list){

        this.context = context;
        this.list = list;


    }



    @NonNull
    @Override
    public SearchBookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

//        if(existnum != 0){
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.item_searchbook,parent,false);

            return new SearchBookViewHolder(view);
//        }else{
//            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
//            View view = layoutInflater.inflate(R.layout.item_searchbook_none,parent,false);
//
//            return new SearchBookViewHolder(view);
//        }

    }

    @Override
    public void onBindViewHolder(@NonNull SearchBookViewHolder holder, int position) {
        Booksinfo booksinfo = list.get(position);

        holder.textView.setText(booksinfo.getBooksName());
        Glide.with(context)
                .load(booksinfo.getBooksimgurl())
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(5)))
                .into(holder.imageView);


        holder.imageView.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("booksid",booksinfo.getBooksId());
                intent.setClass(context, ShowBooksInfoActivity.class);
                context.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class SearchBookViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView textView;

        public SearchBookViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.serarchbook_img);
            textView = itemView.findViewById(R.id.searchbook_title);
        }
    }

//    @Override
//    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
//        super.onAttachedToRecyclerView(recyclerView);
//
//        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
//        if (manager instanceof GridLayoutManager) {
//            final GridLayoutManager g = (GridLayoutManager) manager;
//            g.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//                @Override
//                public int getSpanSize(int position) {
//                    return 1;
//
//                }
//            });
//        }
//    }
}
