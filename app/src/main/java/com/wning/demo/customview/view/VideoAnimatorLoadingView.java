package com.wning.demo.customview.view;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.guagua.modules.utils.LogUtils;
import com.guagua.modules.utils.Utils;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.PropertyValuesHolder;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.view.ViewHelper;
import com.wning.demo.R;

public class VideoAnimatorLoadingView  extends RelativeLayout{
    private static final String TAG="VideoLoadingView";
    
    private static final int mFrameAnimationDuration=100;
    private ImageView mLoadingBear;
    private AnimationDrawable mAnimationDrawable;
    private ValueAnimator mDotAnimator;
    private ObjectAnimator mShadowAnimator;
    private ImageView mDot1,mDot2,mDot3;
    private ImageView mShadow;
    private RelativeLayout mDustLayout;
    private ImageView mBigDust;
    private ImageView mSmallDust;
    private boolean mIsStarted=false;
    private boolean mIsLoadingSuccess;
    public VideoAnimatorLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context,R.layout.view_video_animator_loading,this);
        init();
    }

//    @Override
//    protected void onFinishInflate() {
//        super.onFinishInflate();
//        init();
//    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startLoading();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopLoading();
    }

    private void init() {
        mDot1= (ImageView) findViewById(R.id.qiqi_room_video_loading_dot1);
        mDot2= (ImageView) findViewById(R.id.qiqi_room_video_loading_dot2);
        mDot3= (ImageView) findViewById(R.id.qiqi_room_video_loading_dot3);
        mShadow= (ImageView) findViewById(R.id.qiqi_room_video_loading_shadow);
        mDustLayout= (RelativeLayout) findViewById(R.id.qiqi_room_video_loading_dust);
        mBigDust= (ImageView) findViewById(R.id.qiqi_room_video_loading_dust1);
        mSmallDust= (ImageView) findViewById(R.id.qiqi_room_video_loading_dust2);
        mLoadingBear= (ImageView) findViewById(R.id.qiqi_room_video_loading_bear);

    }

    private void initLoadingAnimation() {
        //小熊奔跑
        mAnimationDrawable = new AnimationDrawable();
        try {
            mIsLoadingSuccess=true;
            mAnimationDrawable.addFrame(getResources().getDrawable(R.drawable.qiqi_room_video_loading_bear6), mFrameAnimationDuration);
            mAnimationDrawable.addFrame(getResources().getDrawable(R.drawable.qiqi_room_video_loading_bear0), mFrameAnimationDuration);
            mAnimationDrawable.addFrame(getResources().getDrawable(R.drawable.qiqi_room_video_loading_bear1), mFrameAnimationDuration);
            mAnimationDrawable.addFrame(getResources().getDrawable(R.drawable.qiqi_room_video_loading_bear2), mFrameAnimationDuration);
            mAnimationDrawable.addFrame(getResources().getDrawable(R.drawable.qiqi_room_video_loading_bear3), mFrameAnimationDuration);
            mAnimationDrawable.addFrame(getResources().getDrawable(R.drawable.qiqi_room_video_loading_bear4), mFrameAnimationDuration);
            mAnimationDrawable.addFrame(getResources().getDrawable(R.drawable.qiqi_room_video_loading_bear5), mFrameAnimationDuration);
        } catch (Exception e) {
            LogUtils.i(TAG,"videoloadingview exception :"+e.getMessage());
            mIsLoadingSuccess=false;
        } catch (OutOfMemoryError oe){
            LogUtils.i(TAG,"videoloadingview oom");
            System.gc();
            mIsLoadingSuccess=false;
        }
        if(!mIsLoadingSuccess) {
            mAnimationDrawable=null;
            mLoadingBear.setImageResource(R.drawable.qiqi_room_video_loading_bear6);
            return;
        }

        mAnimationDrawable.setOneShot(false);
        mLoadingBear.setImageDrawable(mAnimationDrawable);
        //阴影动画
        mShadow.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mShadow.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                ViewHelper.setPivotX(mShadow,mShadow.getMeasuredWidth()/2);
                ViewHelper.setPivotY(mShadow,0);
                ViewHelper.setPivotX(mBigDust,mBigDust.getMeasuredWidth());
                ViewHelper.setPivotY(mBigDust,mBigDust.getMeasuredHeight());
                ViewHelper.setPivotX(mSmallDust,mSmallDust.getMeasuredWidth());
                ViewHelper.setPivotY(mSmallDust,mSmallDust.getMeasuredHeight());
            }
        });
        PropertyValuesHolder pvh1=PropertyValuesHolder.ofFloat("scaleX",1f,0.7f,0.6f,0.9f,0.8f,0.7f,0.8f);
        PropertyValuesHolder pvh2= PropertyValuesHolder.ofFloat("scaleY",1f,0.7f,0.6f,0.9f,0.8f,0.7f,0.8f);
        mShadowAnimator = ObjectAnimator.ofPropertyValuesHolder(mShadow,pvh1, pvh2);
        mShadowAnimator.setRepeatMode(ValueAnimator.RESTART);
        mShadowAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mShadowAnimator.setDuration(mFrameAnimationDuration*7);
        //省略号动画
        mDotAnimator = ValueAnimator.ofFloat(0, 3);
        mDotAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                ViewHelper.setAlpha(mDot1,0);
                ViewHelper.setAlpha(mDot2,0);
                ViewHelper.setAlpha(mDot3,0);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                ViewHelper.setAlpha(mDot1,0);
                ViewHelper.setAlpha(mDot2,0);
                ViewHelper.setAlpha(mDot3,0);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                ViewHelper.setAlpha(mDot1,0);
                ViewHelper.setAlpha(mDot2,0);
                ViewHelper.setAlpha(mDot3,0);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                ViewHelper.setAlpha(mDot1,0);
                ViewHelper.setAlpha(mDot2,0);
                ViewHelper.setAlpha(mDot3,0);
            }
        });
        mDotAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            int distance= Utils.dip2px(getContext(),7);
            float fraction;
            float value;
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                value= (Float) animation.getAnimatedValue();
                if(value>=0&&value<=1){  //有些手机上会频繁闪动
                    ViewHelper.setAlpha(mDot1,value);
                }
                if(value-0.8f>=0&&value-0.8f<=1){
                    ViewHelper.setAlpha(mDot2,value-0.8f);
                }
                if(value-1.8f>=0&&value-1.8f<=1) {
                    ViewHelper.setAlpha(mDot3, value-1.8f);
                }

                fraction= animation.getAnimatedFraction();
                ViewHelper.setTranslationX(mDustLayout,-distance*fraction*3);
                ViewHelper.setTranslationY(mDustLayout,-distance*fraction);
                if(fraction>=0.5f){
                    ViewHelper.setRotation(mDustLayout,5f-10*fraction);
                    ViewHelper.setScaleX(mBigDust, 1f);
                    ViewHelper.setScaleY(mBigDust, 1f);
                    ViewHelper.setAlpha(mBigDust,-fraction*1.6f+1.8f);
                    ViewHelper.setScaleX(mSmallDust, 1f);
                    ViewHelper.setScaleY(mSmallDust, 1f);
                    ViewHelper.setAlpha(mSmallDust,-fraction*1.6f+1.8f);
                }else{
                    ViewHelper.setRotation(mDustLayout,0);
                    ViewHelper.setScaleX(mBigDust, 1.8f * fraction +0.1f);
                    ViewHelper.setScaleY(mBigDust, 1.8f * fraction +0.1f);
                    ViewHelper.setAlpha(mBigDust,1f);
                    ViewHelper.setScaleX(mSmallDust, 1.2f * fraction +0.4f);
                    ViewHelper.setScaleY(mSmallDust, 1.2f * fraction +0.4f);
                    ViewHelper.setAlpha(mSmallDust,1f);
                }
            }
        });
        mDotAnimator.setRepeatMode(ValueAnimator.RESTART);
        mDotAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mDotAnimator.setDuration(mFrameAnimationDuration*7);
    }

    public void startLoading() {
        if(!mIsStarted) {
       //     LogUtils.i(TAG,"startloading");
            mIsStarted=true;
            initLoadingAnimation();
            if(mIsLoadingSuccess) {
                if (mAnimationDrawable != null && !mAnimationDrawable.isRunning()) {
                    mAnimationDrawable.start();
                }
                if (mDotAnimator != null && !mDotAnimator.isRunning()) {
                    mDotAnimator.start();
                }
                if (mShadowAnimator != null && !mShadowAnimator.isRunning()) {
                    mShadowAnimator.start();
                }
            }
        }
    }

    public void stopLoading(){
    //    LogUtils.i(TAG,"stopLoading");
        if(mIsLoadingSuccess) {
            if (mAnimationDrawable != null && mAnimationDrawable.isRunning()) {
                mAnimationDrawable.stop();
                mLoadingBear.setImageResource(R.drawable.qiqi_room_video_loading_bear6);
                mAnimationDrawable = null;
            }
            if (mDotAnimator != null && mDotAnimator.isRunning()) {
                mDotAnimator.cancel();
                mDotAnimator.removeAllUpdateListeners();
                mDotAnimator.removeAllListeners();
                mDotAnimator = null;
            }
            if (mShadowAnimator != null && mShadowAnimator.isRunning()) {
                mShadowAnimator.cancel();
                mShadowAnimator.removeAllUpdateListeners();
                mShadowAnimator.removeAllListeners();
                mShadowAnimator = null;
            }
        }else{
            mLoadingBear.setImageResource(R.drawable.qiqi_room_video_loading_bear6);
        }
        mIsStarted=false;
    }
}
