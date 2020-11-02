package com.sakura.ydhyfinal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sakura.ydhyfinal.R;
import com.sakura.ydhyfinal.bean.Animals;
import com.sakura.ydhyfinal.utils.OnMultiClickListener;

import java.util.List;

public class AnimalImgsAdapter extends RecyclerView.Adapter<AnimalImgsAdapter.AnimalImgeViewHolder> {


    private Context context;
    private List<Animals> list;

    private AdapterOnClicklistener clicklistener;

    public AnimalImgsAdapter(Context context, List<Animals> animalsList){

        this.context = context;
        list = animalsList;


    }


    @NonNull
    @Override
    public AnimalImgeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.animal_img_item, parent, false);
        return new AnimalImgeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimalImgeViewHolder holder, int position) {

        Animals animals = list.get(position);

        Glide.with(context)
                .load(animals.getImg())
                .into(holder.imgs);

        holder.itemView.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                clicklistener.OnImgClick(position);
            }
        });


    }

    public interface AdapterOnClicklistener{
        public void OnImgClick(int pos);
    }

    public void SetAdapterOnClicklistener(AdapterOnClicklistener clicklistener){

        this.clicklistener = clicklistener;
    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    class AnimalImgeViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgs;

        public AnimalImgeViewHolder(@NonNull View itemView) {
            super(itemView);

            imgs = itemView.findViewById(R.id.animalall_img);
        }
    }
}
