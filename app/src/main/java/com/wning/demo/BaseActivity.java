package com.wning.demo;

import android.os.Bundle;

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

    }
    protected  abstract int getLayoutId();
}
