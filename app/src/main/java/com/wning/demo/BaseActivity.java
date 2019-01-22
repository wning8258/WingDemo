package com.wning.demo;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.wning.demo.logprint.LogPrinter;

import androidx.appcompat.app.AppCompatActivity;


/**
 * Created by Administrator on 2016/7/21.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected  String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG=getClass().getSimpleName();
        setContentView(getLayoutId());
        if(isNeedLogPrinter()){
            LogPrinter.enable(this);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
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
