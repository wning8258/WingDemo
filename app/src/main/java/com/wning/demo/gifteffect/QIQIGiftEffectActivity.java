package com.wning.demo.gifteffect;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.guagua.anim.FlyDriverActivity;
import com.guagua.anim.GiftActivity;
import com.guagua.anim.LandDriverActivity;
import com.guagua.anim.GiftEffectActivity;
import com.wning.demo.R;

public class QIQIGiftEffectActivity extends AppCompatActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_qiqi_gift_effect);
	}
	
	public void onClick(View view){
		Button button = (Button) view;
		Intent intent = new Intent();
		if(button.getText().toString().equals("landDriver(地上跑的座驾)")){
			intent.setClass(this, LandDriverActivity.class);
		}else if(button.getText().toString().equals("flyDriver(天上飞的座驾)")){
			intent.setClass(this, FlyDriverActivity.class);
		}else if(button.getText().toString().equals("giftClick(各礼物的动画)")){
			intent.setClass(this, GiftActivity.class);
		}else if(button.getText().toString().equals("礼物特效")){
            intent.setClass(this, GiftEffectActivity.class);
        }
		startActivity(intent);
	}
	
}
