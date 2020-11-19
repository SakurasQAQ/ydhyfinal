package com.sakura.ydhyfinal.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sakura.ydhyfinal.LoginActivity;
import com.sakura.ydhyfinal.R;
import com.sakura.ydhyfinal.bean.Coursesclass;
import com.sakura.ydhyfinal.homepage.CourseWebActivity;
import com.sakura.ydhyfinal.homepage.VideoPlayActivity;
import com.sakura.ydhyfinal.utils.OnMultiClickListener;

import java.util.ArrayList;
import java.util.List;

public class CourseClassAdapter extends RecyclerView.Adapter<CourseClassAdapter.CourseViewHolder> {

    private List<Coursesclass> list;
    private Context context;

    public CourseClassAdapter(Context context,List<Coursesclass> list){
        this.context = context;
        this.list = list;

    }



    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.course_class_item,parent,false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        Coursesclass course = list.get(position);

        holder.textView.setText(course.getTitle());

        holder.linearLayout.setBackground(context.getResources().getDrawable(R.drawable.videobackground));
        holder.imageView.setImageResource(R.drawable.ic_classplay);

        holder.itemView.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                String url = course.getVideourl();
                String urltype = url.substring(url.length()-3,url.length());
                if(urltype.equals("mp4")){
                    Toast.makeText(context,"加载中...",Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent();
                    intent.putExtra("url",course.getVideourl());
                    intent.putExtra("author",course.getAuthor());
                    intent.putExtra("title",course.getTitle());
                    intent.setClass(context, VideoPlayActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                    context.startActivity(intent);
                    Log.d("链接值", "onClick: "+course.getVideourl());

                }else {

                    Intent intent = new Intent();
                    intent.putExtra("url",course.getVideourl());
                    intent.setClass(context, CourseWebActivity.class);
                    context.startActivity(intent);

                }


            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class CourseViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        ImageView imageView;
        LinearLayout linearLayout;
        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.class_icon);
            linearLayout = itemView.findViewById(R.id.class_bag);
            textView = itemView.findViewById(R.id.class_name);
        }
    }
}
