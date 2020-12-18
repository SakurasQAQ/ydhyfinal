package com.sakura.ydhyfinal.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;

import java.security.MessageDigest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

@SuppressLint("AppCompatCustomView")
public class MaskImageView extends ImageView {
    private Context context;

    //蒙版色值
    private int color = Color.parseColor("#81FFFFFF");

    //是否显示蒙版
    private boolean isShowMask = false;

    public MaskImageView(Context context) {
        super(context);
        this.context = context;
    }

    public MaskImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public MaskImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }


    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isShowMask) {
            canvas.drawColor(color);
        }
    }

    /**
     * 设置蒙版颜色，可以通过类似于（#81FFFFFF）来控制透明度
     */
    public MaskImageView setMaskColor(int color) {
        this.color = color;
        return this;
    }

    /**
     * 显示蒙版
     */
    public void showMask() {
        isShowMask = true;
        invalidate();
    }

    /**
     * 关闭蒙版
     */
    public void dismissMask() {
        isShowMask = false;
        invalidate();
    }

    /**
     * 高斯模糊处理
     *
     * @param radius
     * @return
     */
    public Bitmap setGaussblur(int radius, Bitmap source) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            RenderScript renderScript = RenderScript.create(context);
            final Allocation input = Allocation.createFromBitmap(renderScript, source);
            final Allocation output = Allocation.createTyped(renderScript, input.getType());
            ScriptIntrinsicBlur scriptIntrinsicBlur;
            scriptIntrinsicBlur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
            scriptIntrinsicBlur.setInput(input);
            scriptIntrinsicBlur.setRadius(radius);
            scriptIntrinsicBlur.forEach(output);
            output.copyTo(source);
            renderScript.destroy();
            return source;
        } else {
            return source;
        }
    }

    /**
     * 开启高斯模糊
     *
     * @return
     */
    public RequestOptions setGaussBlur() {
        return RequestOptions.bitmapTransform(new GlideBlurTransformation(context));
    }

    /**
     * 压缩Bitmap
     */
    public class GlideBlurTransformation extends CenterCrop {
        private Context context;

        GlideBlurTransformation(Context context) {
            this.context = context;
        }

        @Override
        protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
            Bitmap bitmap = super.transform(pool, toTransform, outWidth, outHeight);
            return blurBitmap(context, bitmap, 25, (int) (outWidth * 0.2), (int) (outHeight * 0.2));
        }

        @Override
        public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        }
    }

    /**
     * 模糊Bitmap处理
     *
     * @param context
     * @param source
     * @param blurRadius
     * @param outWidth
     * @param outHeight
     * @return
     */
    public Bitmap blurBitmap(Context context, Bitmap source, float blurRadius, int outWidth, int outHeight) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Bitmap inputBitmap = Bitmap.createScaledBitmap(source, outWidth, outHeight, false);
            Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);
            RenderScript rs = RenderScript.create(context);
            ScriptIntrinsicBlur blurScript;
            blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
            Allocation tmpIn = Allocation.createFromBitmap(rs, inputBitmap);
            Allocation tmpOut = Allocation.createFromBitmap(rs, outputBitmap);
            blurScript.setRadius(blurRadius);
            blurScript.setInput(tmpIn);
            blurScript.forEach(tmpOut);
            tmpOut.copyTo(outputBitmap);
            return outputBitmap;
        } else {
            return source;
        }
    }
}
