package com.wning.demo.customview.view.test;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.wning.demo.R;

/**
 * Created by wning on 2017/3/3.
 */

public class ColorMatrixColorFilterSeekView extends LinearLayout {


    private Bitmap mOriginBmp, mTempBmp;

    private ImageView img;

    private TextView tv_saturation, tv_rotate;
    private SeekBar sb_saturation, sb_rotate;

    public ColorMatrixColorFilterSeekView(Context context) {
        super(context);
        init();
    }

    public ColorMatrixColorFilterSeekView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init() {

        mOriginBmp = BitmapFactory.decodeResource(getResources(), R.mipmap.dog);
        mTempBmp = Bitmap.createBitmap(mOriginBmp.getWidth(), mOriginBmp.getHeight(),
                Bitmap.Config.ARGB_8888);

        View.inflate(getContext(), R.layout.view_color_matrix_color_filter_seek, this);
        img = (ImageView) findViewById(R.id.img);
        tv_saturation = (TextView) findViewById(R.id.tv_saturation);
        tv_rotate = (TextView) findViewById(R.id.tv_rotate);
        sb_saturation = (SeekBar) findViewById(R.id.sb_saturation);
        sb_rotate = (SeekBar) findViewById(R.id.sb_rotate);

        sb_saturation.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                Bitmap bitmap = handleColorMatrixBmp(progress);
                img.setImageBitmap(bitmap);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sb_saturation.setMax(20);
        sb_saturation.setProgress(1);

        sb_rotate.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                Bitmap bitmap = handleColorRotateBmp(progress);
                img.setImageBitmap(bitmap);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sb_rotate.setMax(360);
        sb_rotate.setProgress(180);

    }

    private Bitmap handleColorMatrixBmp(int progress) {
        // 创建一个相同尺寸的可变的位图区,用于绘制调色后的图片
        Canvas canvas = new Canvas(mTempBmp); // 得到画笔对象
        Paint paint = new Paint(); // 新建paint
        paint.setAntiAlias(true); // 设置抗锯齿,也即是边缘做平滑处理
        ColorMatrix mSaturationMatrix = new ColorMatrix();
        mSaturationMatrix.setSaturation(progress);

        paint.setColorFilter(new ColorMatrixColorFilter(mSaturationMatrix));// 设置颜色变换效果
        canvas.drawBitmap(mOriginBmp, 0, 0, paint); // 将颜色变化后的图片输出到新创建的位图区
        // 返回新的位图，也即调色处理后的图片
        return mTempBmp;
    }

    private Bitmap handleColorRotateBmp(int progress) {

        // 创建一个相同尺寸的可变的位图区,用于绘制调色后的图片
        Canvas canvas = new Canvas(mTempBmp); // 得到画笔对象
        Paint paint = new Paint(); // 新建paint
        paint.setAntiAlias(true); // 设置抗锯齿,也即是边缘做平滑处理
        ColorMatrix colorMatrix = new ColorMatrix();

        colorMatrix.setRotate(0, progress - 180);
        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));// 设置颜色变换效果
        canvas.drawBitmap(mOriginBmp, 0, 0, paint); // 将颜色变化后的图片输出到新创建的位图区
        // 返回新的位图，也即调色处理后的图片
        return mTempBmp;
    }

}
