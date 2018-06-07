package com.wning.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.wning.demo.logprint.LogPrinter;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/7/21.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected  String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG=getClass().getSimpleName();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {  //ActivityTranstion(5.0以上)
//            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
//            getWindow().setEnterTransition(new Explode());
//            getWindow().setExitTransition(new Explode());
//        }
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        if(isNeedLogPrinter()){
            LogPrinter.enable(this);
        }
    }

    @Override
    protected void onDestroy() {
        if(isNeedLogPrinter()){
            LogPrinter.clear();
            LogPrinter.disable();
        }
        super.onDestroy();

    }

    protected  abstract int getLayoutId();

    protected boolean isNeedLogPrinter(){
        return false;
    }

//    protected final void startActivityWithTransition(Intent intent){
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//            LogUtils.i("wn","startActivityWithTransition this :"+this);
//            ActivityOptions option = ActivityOptions.makeSceneTransitionAnimation(this);
//            startActivity(intent, option.toBundle());
//        }else{
//            startActivity(intent);
//        }
//    }
}
