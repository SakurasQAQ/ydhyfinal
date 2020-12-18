package com.sakura.ydhyfinal.dialogView;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.sakura.ydhyfinal.R;
import com.sakura.ydhyfinal.utils.OnMultiClickListener;

public class AnimalsDialogMakesure extends Dialog {

    LinearLayout btn_yes,btn_no;
    TextView tit,cont;

    String wen1 = "";
    String wen2 = "";

    private AnimalsDialog.MyOnclickListener mMyOnclickListener;

    public AnimalsDialogMakesure(@NonNull Context context) {
        super(context);
    }

    public AnimalsDialogMakesure(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public AnimalsDialogMakesure(@NonNull Context context, int themeResId,String wen1,String wen2) {
        super(context, themeResId);
        this.wen1 = wen1;
        this.wen2 = wen2;
    }


    private OnMultiClickListener chilc = new OnMultiClickListener() {
        @Override
        public void onMultiClick(View v) {
            switch (v.getId()) {

                case R.id.btn_t:

                    break;

                case R.id.btn_f:
                    dismiss();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_animals);

        setCanceledOnTouchOutside(false);
        initView();



    }

    @Override
    public void show() {
        super.show();

        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.width= 880;
        layoutParams.height= 600;
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.x=0;
        layoutParams.y=50;
        //getWindow().setWindowAnimations(R.style.dialogWindowAnim);
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setAttributes(layoutParams);

    }

    private void initView(){
        btn_yes = findViewById(R.id.btn_t);
        btn_no = findViewById(R.id.btn_f);

        tit = findViewById(R.id.textView10xx);
        cont = findViewById(R.id.textView9xx);



        if(!wen1.equals("")){
            tit.setText(wen1);
            cont.setText(wen2);
        }else{
            tit.setText("确认订阅？");
            cont.setText("是否确定订阅这本书");
        }


        btn_yes.setOnClickListener(chilc);
        btn_no.setOnClickListener(chilc);

        btn_yes.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                mMyOnclickListener.onYesClick();
            }
        });

    }



    //创建外部实现的点击方法的内部接口
    public interface MyOnclickListener{
        public void onYesClick();

    }
    //set内部接口和String参数
    public void setMyOnclickListener(AnimalsDialog.MyOnclickListener myOnclickListener){

        this.mMyOnclickListener=myOnclickListener;
    }



}
