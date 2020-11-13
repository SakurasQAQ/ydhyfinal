package com.sakura.ydhyfinal.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.sakura.ydhyfinal.R;
import com.sakura.ydhyfinal.bean.QuesAnwser;

import java.util.ArrayList;
import java.util.List;

public class AnswerPagerAdapter extends PagerAdapter {

    private Context context;
    private List<QuesAnwser> list;
    private ArrayList<String> chooselist;

    private String[] anserlist;

    private Ctxanswer ctxanswer;

    private int numconter = 0;


    public AnswerPagerAdapter(Context context, List<QuesAnwser> list,String[] anserlist){
        this.anserlist = anserlist;

        this.context = context;
        this.list = list;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    //判断一个页面(View)是否与instantiateItem方法返回的Object一致
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }



    //添加视图
    @SuppressLint("ResourceType")
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {




        //加载vp的布局
        View inflate = View.inflate(context, R.layout.answerviewpager, null);
        //给布局控件赋值
        TextView textViewtype = inflate.findViewById(R.id.anser_ques_type);
        textViewtype.setText(list.get(position).getQuestype());

        TextView totalpage = inflate.findViewById(R.id.anser_ques_total);
        TextView currentpage = inflate.findViewById(R.id.anser_ques_current);
        TextView question = inflate.findViewById(R.id.anser_quesdetail);


        totalpage.setText("/"+list.size());
        currentpage.setText(String.valueOf(position+1));
        question.setText(list.get(position).getQuestion());


        if(position == list.size()-1){
            Button buttons = inflate.findViewById(R.id.anser_btn_sub);
            buttons.setVisibility(View.VISIBLE);


            buttons.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ctxanswer.usersAnswer(anserlist);
                    Log.d("~~~~~", "onClick: "+numconter);
                }
            });
        }

        //选择区布局
        RadioGroup radioGroup = inflate.findViewById(R.id.anser_chooseblock);
        chooselist = new ArrayList<>();
        addtoChooselist(position,chooselist);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(list.get(position).getQuestype().equals("单项选择题")){
                    switch (checkedId){
                        case 0:
                            Toast.makeText(context,"A",Toast.LENGTH_SHORT).show();
                            anserlist[position] = "A";
                            break;
                        case 1:
                            Toast.makeText(context,"B",Toast.LENGTH_SHORT).show();
                            anserlist[position] = "B";
                            break;
                        case 2:
                            Toast.makeText(context,"C",Toast.LENGTH_SHORT).show();
                            anserlist[position] = "C";
                            break;
                        case 3:
                            Toast.makeText(context,"D",Toast.LENGTH_SHORT).show();
                            anserlist[position] = "D";
                            break;
                        default:
                            numconter+=1;
                            break;
                    }
                }
                if(list.get(position).getQuestype().equals("判断题")){
                    switch (checkedId){
                        case 0:
                            Toast.makeText(context,"0",Toast.LENGTH_SHORT).show();
                            anserlist[position] = "0";

                            break;
                        case 1:
                            Toast.makeText(context,"1",Toast.LENGTH_SHORT).show();
                            anserlist[position] = "1";

                            break;
                        default:
                            numconter+=1;
                            break;
                    }
                }

            }
        });

        //判断当前题目条件
        if(list.get(position).getQuestype().equals("单项选择题")){
            int choosenum = 0;
            if(list.get(position).getChoiceD().equals("")){
                choosenum = 3;
            }else{
                choosenum = 4;
            }

            for(int i = 0;i<choosenum;i++){
                RadioButton radioButton = new RadioButton(context);

                radioButton.setButtonDrawable(null);
                radioButton.setBackground(context.getResources().getDrawable(R.drawable.choose_select_menu));
                radioButton.setText((char)(65+i)+"   "+chooselist.get(i));

                radioButton.setPadding(35,35,35,35);

                radioButton.setId(i);

                radioButton.setTextColor(context.getResources().getColorStateList(R.color.choose_txtselect_menu));

                radioButton.setLeftTopRightBottom(10,10,10,10);

                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lp.setMargins(5,20,5,20);
                //radioButton.setLayoutParams(lp);

                radioGroup.addView(radioButton,lp);


            }
        }
        else if(list.get(position).getQuestype().equals("判断题")){
            RadioButton radioButton = new RadioButton(context);
            radioButton.setText("对");
            radioButton.setPadding(35,35,35,35);
            radioButton.setId(0);
            radioButton.setButtonDrawable(null);
            radioButton.setBackground(context.getResources().getDrawable(R.drawable.choose_select_menu));
            radioButton.setTextColor(context.getResources().getColorStateList(R.color.choose_txtselect_menu));
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(5,20,5,20);
            radioGroup.addView(radioButton,lp);

            RadioButton radioButton2 = new RadioButton(context);
            radioButton2.setId(1);
            radioButton2.setTextColor(context.getResources().getColorStateList(R.color.choose_txtselect_menu));
            radioButton2.setText("错");

            radioButton2.setPadding(35,35,35,35);
            radioButton2.setButtonDrawable(null);
            radioButton2.setBackground(context.getResources().getDrawable(R.drawable.choose_select_menu));

            LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(5,20,5,20);
            radioGroup.addView(radioButton2,lp2);
        }
        else if(list.get(position).getQuestype().equals("多项选择题")){
            int choosenum = 0;
            int marghight = 0;
            if(list.get(position).getChoiceF().equals("")){
                choosenum = 5;
                marghight = 20;
                if(list.get(position).getChoiceE().equals("")){
                    choosenum = 4;
                    marghight = 35;
                }
            }else{
                choosenum = 6;
                marghight = 5;
            }
            String[] chooseChice = new String[6];

            for(int i = 0;i<choosenum;i++){


                CheckBox radioButton = new CheckBox(context);

                radioButton.setId(i);
                radioButton.setButtonDrawable(null);
                radioButton.setBackground(context.getResources().getDrawable(R.drawable.choose_select_menu));
                radioButton.setText((char)(65+i) + "   " + chooselist.get(i));
                radioButton.setPadding(35,15,35,15);
                radioButton.setTextColor(context.getResources().getColorStateList(R.color.choose_txtselect_menu));
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lp.setMargins(5,marghight,5,marghight);

                int finalI = i;
                radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            chooseChice[finalI] = String.valueOf((char)(65+ finalI));

                        }else{
                            chooseChice[finalI] = "";
                        }
                        String sss = "";
                        for (String a:chooseChice){
                            if(a!=null)
                            sss+=a;
                        }

                        anserlist[position] = sss;
                    }

                });
                radioGroup.addView(radioButton,lp);
            }

        }


        //添加一个布局（不添加无效果）
        container.addView(inflate);
        return inflate;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//        super.destroyItem(container, position, object);
        //移除视图
        container.removeView((View) object);
    }

    private void addtoChooselist(int currentpage,ArrayList<String> chooselist){
        if(list.get(currentpage).getChoiceA()!=null){
            chooselist.add(list.get(currentpage).getChoiceA());
        }
        if(list.get(currentpage).getChoiceB()!=null){
            chooselist.add(list.get(currentpage).getChoiceB());
        }
        if(list.get(currentpage).getChoiceC()!=null){
            chooselist.add(list.get(currentpage).getChoiceC());
        }
        if(list.get(currentpage).getChoiceD()!=null){
            chooselist.add(list.get(currentpage).getChoiceD());
        }
        if(list.get(currentpage).getChoiceE()!=null){
            chooselist.add(list.get(currentpage).getChoiceE());
        }
        if(list.get(currentpage).getChoiceF()!=null){
            chooselist.add(list.get(currentpage).getChoiceF());
        }
    }

    public static interface Ctxanswer{
        void usersAnswer(String[] answer);
    }

    public void setAnswerContext(Ctxanswer ctxanswer){
        this.ctxanswer = ctxanswer;

    }



}
