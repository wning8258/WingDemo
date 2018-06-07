/*
 * Copyright (C) 2014 Inaka.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * @author Henrique Boregio (henrique@inakanetworks.com)
 */
package com.wning.demo.logprint;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.guagua.modules.utils.LogUtils;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Queue;

public class LogPrinterService extends Service {

    private static final String TAG="LogPrinterService";

    private TextView mTextView;
    private LogPrinter.Options mOptions;
    private final Queue<String> mLines = new ArrayDeque<>();


    @Override
    public void onCreate() {
        super.onCreate();

        mTextView = new TextView(this);
        mTextView.setMovementMethod(ScrollingMovementMethod.getInstance());

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_TOAST ,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE ,
                PixelFormat.TRANSLUCENT);
        params.gravity= Gravity.TOP|Gravity.LEFT;
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        wm.addView(mTextView, params);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        int flag=intent.getIntExtra(LogPrinter.FLAG,0);
        if(flag== LogPrinter.FLAG_CLEAR){
            mLines.clear();
            mTextView.setVisibility(View.GONE);
           // displayText("");
        }else {
            //首次创建有options
            LogPrinter.Options options = intent.getExtras().getParcelable(LogPrinter.ARG_OPTIONS);
            if (options != null) {
                mOptions = options;
            }
            //显示log的时候有Message
            String message = intent.getExtras().getString(LogPrinter.MESSAGE);
            if (!TextUtils.isEmpty(message)) {
                mTextView.setVisibility(View.VISIBLE);
                displayText(message);
            }
        }


        return super.onStartCommand(intent, flags, startId);
    }


    public void displayText(String text) {
        mLines.add("\n"+text);
       // LogUtils.i(TAG,"displayText :"+mOptions);
        if (mLines.size() > mOptions.numberOfLines) {
            mLines.poll();
        }

        redraw(mLines);
    }

    private void redraw(Collection<String> texts) {
        mTextView.setTextSize(mOptions.textSize);
        mTextView.setTextColor(mOptions.textColor);

        Spannable spannable = new SpannableString(TextUtils.join("\n", texts));
        spannable.setSpan(new BackgroundColorSpan(mOptions.backgroundColor), 0, spannable.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        mTextView.setText(spannable);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mTextView != null) {
            WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
            wm.removeView(mTextView);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}