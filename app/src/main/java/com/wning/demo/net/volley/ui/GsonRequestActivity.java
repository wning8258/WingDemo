/*
 * Created by Storm Zhang, Feb 13, 2014.
 */

package com.wning.demo.net.volley.ui;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Response;
import com.google.gson.Gson;
import com.wning.demo.R;
import com.wning.demo.net.volley.data.GsonRequest;
import com.wning.demo.net.volley.model.MyClass;
import com.wning.demo.net.volley.vendor.VolleyApi;


public class GsonRequestActivity extends VolleyBaseActivity {
	private TextView mTvResult;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gson_request);

		mTvResult = (TextView) findViewById(R.id.tv_result);

		Button btnRequest = (Button) findViewById(R.id.btn_gson_request);
		btnRequest.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				executeRequest(new GsonRequest<MyClass>(VolleyApi.GSON_TEST, MyClass.class,
						responseListener(), errorListener()));
			}
		});
	}

	private Response.Listener<MyClass> responseListener() {
		return new Response.Listener<MyClass>() {
			@Override
			public void onResponse(MyClass response) {
				mTvResult.setText(new Gson().toJson(response));
			}
		};
	}
}
