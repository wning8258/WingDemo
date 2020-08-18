package com.wning.demo.opengles;

import android.os.Bundle;

import com.wning.demo.BaseActivity;

public class OpenGLESTestActivity extends BaseActivity {

    private MyGLSurfaceView gLView;

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gLView = new MyGLSurfaceView(this);
    }
}
