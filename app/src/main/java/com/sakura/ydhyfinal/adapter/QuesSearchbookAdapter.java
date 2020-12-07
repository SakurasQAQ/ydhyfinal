package com.sakura.ydhyfinal.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sakura.ydhyfinal.Activity.SubStoryQuesActivity;
import com.sakura.ydhyfinal.R;
import com.sakura.ydhyfinal.bean.Booksinfo;

import java.util.List;

public class QuesSearchbookAdapter extends RecyclerView.Adapter<QuesSearchbookAdapter.searchViewholder> {
    List<Booksinfo> list;

    public QuesSearchbookAdapter(List<Booksinfo> list){
        this.list = list;
    }

    @NonNull
    @Override
    public searchViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_quesearchbook,parent,false);

        return new QuesSearchbookAdapter.searchViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull searchViewholder holder, int position) {

        Booksinfo booksinfo = list.get(position);

        holder.txt.setText(booksinfo.getBooksName());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class searchViewholder extends RecyclerView.ViewHolder{

        private TextView txt;
        public searchViewholder(@NonNull View itemView)
        {
            super(itemView);
            txt = itemView.findViewById(R.id.quessub_bookname);
        }
    }
}
