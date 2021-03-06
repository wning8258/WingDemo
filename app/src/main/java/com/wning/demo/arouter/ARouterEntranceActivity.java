package com.wning.demo.arouter;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.wning.demo.BaseActivity;
import com.wning.demo.R;

@Route(path = "/test/activity")
public class ARouterEntranceActivity extends BaseActivity implements View.OnClickListener {

    private Button btn;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_arouter_entrance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        btn= (Button) findViewById(R.id.btn);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn:
                ARouter.getInstance().build("/test/activity").navigation();
                break;
        }
    }
}
