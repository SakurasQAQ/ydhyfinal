package com.sakura.ydhyfinal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.sakura.ydhyfinal.R;
import com.sakura.ydhyfinal.bean.RankBean;

import java.util.List;

public class RankAdapter extends Adapter<RankAdapter.RanksViewHolder> {

    private Context context;

    private List<RankBean> list;


    public RankAdapter(Context context, List<RankBean> list){

        this.context = context;
        this.list = list;

    }



    @NonNull
    @Override
    public RanksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.rank_list_items,parent,false);

        return new RanksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RanksViewHolder holder, int position) {

        RankBean rank = list.get(position);

        switch (position){
            case 0:
                holder.img.setImageResource(R.drawable.ic_number1);
                break;
            case 1:
                holder.img.setImageResource(R.drawable.ic_number2);
                break;
            case 2:
                holder.img.setImageResource(R.drawable.ic_number3);
                break;
            case 3:
                holder.img.setImageResource(R.drawable.ic_number4);
                break;
            case 4:
                holder.img.setImageResource(R.drawable.ic_number5);
                break;
            default:
                holder.img.setVisibility(View.INVISIBLE);
                break;

        }

        holder.name.setText(rank.getName());
        holder.school.setText(rank.getSchoolName());
        if(rank.getRanktype() == 0){
            holder.info.setText(rank.getTotalIntegral()+"分");
        }else{
            holder.info.setText(rank.getBookNumber()+"本");
        }






    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class RanksViewHolder extends RecyclerView.ViewHolder {

        private TextView name,school,info;
        private ImageView img;



        public RanksViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.rankitem_name);
            school = itemView.findViewById(R.id.rankitem_school);
            img = itemView.findViewById(R.id.rankitem_img);
            info = itemView.findViewById(R.id.rankitem_info);

        }
    }

}

