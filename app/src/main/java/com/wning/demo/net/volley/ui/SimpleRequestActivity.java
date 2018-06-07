/*
 * Created by Storm Zhang, Feb 11, 2014.
 */

package com.wning.demo.net.volley.ui;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.wning.demo.R;
import com.wning.demo.net.volley.vendor.VolleyApi;

public class SimpleRequestActivity extends VolleyBaseActivity {
	private TextView mTvResult;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_simple_request);

		mTvResult = (TextView) findViewById(R.id.tv_result);

		Button btnRequest = (Button) findViewById(R.id.btn_simple_request);
		btnRequest.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				executeRequest(new StringRequest(Method.GET, VolleyApi.BAIDU, responseListener(),
						errorListener()));
			}
		});
	}

	private Response.Listener<String> responseListener() {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				mTvResult.setText(response);
			}
		};
	}
}
