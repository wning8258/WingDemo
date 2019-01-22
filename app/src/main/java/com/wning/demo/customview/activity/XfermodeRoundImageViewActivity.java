package com.wning.demo.customview.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewTreeObserver;

import com.wning.demo.R;
import com.wning.demo.customview.view.XfermodeRoundImageView;

public class XfermodeRoundImageViewActivity extends AppCompatActivity {

    private XfermodeRoundImageView riv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xferode_round_image_view);

        riv1= (XfermodeRoundImageView) findViewById(R.id.riv1);

        riv1.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                riv1.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                Log.i("wning",riv1.getMeasuredWidth()+","+riv1.getMeasuredHeight());
            }
        });
    }
}
