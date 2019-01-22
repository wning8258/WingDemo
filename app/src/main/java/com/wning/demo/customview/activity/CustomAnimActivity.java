package com.wning.demo.customview.activity;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.TextView;

import com.nineoldandroids.animation.ArgbEvaluator;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;
import com.wning.demo.BaseActivity;
import com.wning.demo.R;
import com.wning.demo.customview.view.FansGroupUpTextView;
import com.wning.demo.customview.view.PullToRefreshBearView;
import com.wning.demo.customview.view.VideoLoadingSpringView;


public class CustomAnimActivity extends BaseActivity {


     PullToRefreshBearView bearView;

    TextView tv_argb;

    VideoLoadingSpringView mSpringView;

    private FansGroupUpTextView fans_group_text1,fans_group_text2;

    private ObjectAnimator colorAnim;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_anim_custom;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bearView=findViewById(R.id.bear);
        tv_argb=findViewById(R.id.tv_argb);
        mSpringView=findViewById(R.id.loading_spring);
        fans_group_text1=  findViewById(R.id.fans_group_text1);
        fans_group_text2=  findViewById(R.id.fans_group_text2);


//        fans_group_text1.setBackgroundResource(R.drawable.qiqi_room_fansgroup_up_toast);
//        fans_group_text1.setText("白羊"," LV"+"2",0);
//        fans_group_text1.setVisibility(View.VISIBLE);
//
//        fans_group_text2.setBackgroundResource(R.drawable.qiqi_room_fansgroup_up_dialog);
//        fans_group_text2.setText("白羊"," LV"+"3",R.mipmap.ic_launcher);
//        fans_group_text2.setVisibility(View.VISIBLE);

        Drawable background = fans_group_text2.getBackground();

        Rect rect=new Rect();
        if(background!=null){
            background.getPadding(rect);  //Rect(85, 107 - 105, 88)
                     //Rect(121, 308 - 113, 47)
        }
//
//
//        LogUtils.i("wn","rect.top :"+rect.top+"，rect.bottom :"+rect.bottom);  //308,47    //107,88

//        ValueAnimator animator=  ValueAnimator
//                .ofFloat(0,1)
//                .setDuration(1000);
//
//        animator.setInterpolator(null);   //默认是AccelerateDecelerateInterpolator，传空的话，会new一个LinearInterpolator
//        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                    @Override
//                    public void onAnimationUpdate(ValueAnimator animation) {
//                        LogUtils.i("wn","current :"+animation.getAnimatedValue());
//                    }
//                });
//        animator.start();


        colorAnim = ObjectAnimator.ofInt(tv_argb, "backgroundColor", 0xFFFF8080, 0xFF8080FF);
        colorAnim.setDuration(3000);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.setRepeatCount(ValueAnimator.INFINITE);
        colorAnim.setRepeatMode(ValueAnimator.REVERSE);
        colorAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                tv_argb.setText(Integer.toHexString((Integer) animation.getAnimatedValue()));
            }
        });
        colorAnim.start();

        bearView.start();
        mSpringView.startLoading();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mSpringView!=null) {
            mSpringView.stopLoading();
        }
        if(bearView!=null) {
            bearView.stop();
        }

        if(colorAnim!=null&&colorAnim.isRunning()){
            colorAnim.cancel();
        }
    }
}
