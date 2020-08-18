package com.wning.demo;

import android.os.Bundle;

import com.wning.demo.utils.UIUtils;

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
        UIUtils.setCustomDensity(this,getApplication());
        setContentView(getLayoutId());

    }
    protected  abstract int getLayoutId();
}
