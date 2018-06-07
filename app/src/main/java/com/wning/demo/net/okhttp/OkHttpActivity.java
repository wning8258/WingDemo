package com.wning.demo.net.okhttp;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

import com.guagua.modules.utils.LogUtils;
import com.guagua.qiqi.utils.QiQiHttpConfig;
import com.guagua.qiqi.utils.SignHelper;
import com.guagua.qiqi.utils.UrlUtil;
import com.wning.demo.BaseActivity;
import com.wning.demo.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class OkHttpActivity extends BaseActivity {

    private static final String TAG = "OkHttpActivity";

    @BindView(R.id.btn1)
    Button btn1;

    @BindView(R.id.btn2)
    Button btn2;

    @BindView(R.id.btn3)
    Button btn3;

    @BindView(R.id.btn4)
    Button btn4;

    private OkHttpClient mClient;
    private Headers mHeaders;

    private String finalUrl;

   // private String apkUrl = "http://192.168.27.114:8087/QiQi.apk";
    private String apkUrl = "http://192.168.27.114:8087/dev-tools.apk ";

    CompositeSubscription subscription = new CompositeSubscription();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_okhttp;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        mClient= new OkHttpClient.Builder()
//                .build();

        mClient = genericClient();

        makeHeader();

        String url = "http://hall.m.qxiu.com/system/gamelist.do?sign=A27C690D44185D92AE125AC428C35F1CE82180BD&roomid=223981&accesstoken=CBE9458707D491548BFA1E8FA4343ED3&id=107&openid=5FF7711FA6FCAB6E3A4044C04DB7CB6F";
        HashMap params = new HashMap();
        //params.put("orderNo", "123");

        finalUrl = makeFinalUrl(url, params);

    }


    /**
     * 设置请求统一的header
     *
     * @return
     */
    public static OkHttpClient genericClient() {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {

                        Request.Builder builder = chain.request().newBuilder();

//                        for (Map.Entry<String, String> entry : QiQiHttpConfig.getHeader().entrySet()) {
//                            builder.addHeader(entry.getKey(), entry.getValue());
//                        }

                        Request request = builder.headers(Headers.of(QiQiHttpConfig.getHeader())).build();

                        return chain.proceed(request);
                    }

                })
                .build();

        return httpClient;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.i(TAG, "onDestroy subscription hasSubscriptions :" + subscription.hasSubscriptions());
        if (subscription != null && !subscription.hasSubscriptions()) {
            subscription.unsubscribe();
        }
    }

    private void makeHeader() {
        Headers.Builder builder = new Headers.Builder();
        for (Map.Entry<String, String> entry : QiQiHttpConfig.getHeader().entrySet()) {
            builder.add(entry.getKey(), entry.getValue());
        }
        mHeaders = builder.build();
    }

    private String makeFinalUrl(String url, HashMap params) {
        SignHelper.makePaySign(params);
        String finalUrl = UrlUtil.makeGetUrl(url, params);
        return finalUrl;
    }

    @OnClick({R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:  //get异步请求enqueue
                // Request request=new Request.Builder().headers(mHeaders).url(finalUrl).build();  //这个可以给某个请求设置header
                Request request = new Request.Builder().url(finalUrl).build();
                mClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        LogUtils.i(TAG, "onFailure 1" + e.getLocalizedMessage());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        LogUtils.i(TAG, "onResponse 1" + response.body().string());
                    }
                });
                break;
            case R.id.btn2:  //get阻塞请求execute
                subscription.add(Observable.just(finalUrl)
                        .map(new Func1<String, String>() {
                            @Override
                            public String call(String s) {
                                LogUtils.i(TAG, "map call s " + s);
                                // Request request=new Request.Builder().headers(mHeaders).url(s).build();
                                Request request = new Request.Builder().url(s).build();
                                try {
                                    Response response = mClient.newCall(request).execute();
                                    String result = response.body().string();
                                    LogUtils.i(TAG, "onResponse " + result);
                                    return result;
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                return null;
                            }
                        }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(new Action1<String>() {
//                    @Override
//                    public void call(String result) {
//                        Toast.makeText(OkHttpActivity.this, "result : "+result, Toast.LENGTH_SHORT).show();
//                    }
//                });
                        .subscribe(new Observer<String>() {
                            @Override
                            public void onCompleted() {
                                LogUtils.i(TAG, "onResponse onCompleted ");
                            }

                            @Override
                            public void onError(Throwable e) {
                                LogUtils.i(TAG, "onResponse onError " + e.getMessage());
                            }

                            @Override
                            public void onNext(String s) {
                                LogUtils.i(TAG, "onResponse onNext " + s);
                            }
                        }));


                break;

            case R.id.btn3:  //post 异步请求
                FormBody formBody = new FormBody.Builder()
//                        .add("platform", "android")
//                        .add("name", "bug")
//                        .add("subject", "XXXXXXXXXXXXXXX")
                        .build();

                Request request1 = new Request.Builder()
                        .url(finalUrl)
                        .post(formBody)
                        //  .headers(mHeaders)
                        .build();

                mClient.newCall(request1).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        LogUtils.i(TAG, "onFailure 1" + e.getLocalizedMessage());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        LogUtils.i(TAG, "onResponse 1" + response.body().string());
                    }
                });
                break;
            case R.id.btn4:

                Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(final Subscriber<? super String> subscriber) {
                        subscriber.onStart();
                        Request request2 = new Request.Builder().url(apkUrl).build();

                        mClient.newCall(request2).enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                LogUtils.i(TAG, "onFailure " + e.getLocalizedMessage());

                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                LogUtils.i(TAG, "onResponse 1");
                                long total = response.body().contentLength();
                                float current = 0;
                                InputStream is = null;
                                byte[] buf = new byte[2048];
                                int len = 0;
                                FileOutputStream fos = null;
                                try {
                                    is = response.body().byteStream();
                                    File file = new File(Environment.getExternalStorageDirectory(), "test.apk");
                                    fos = new FileOutputStream(file);
                                    while ((len = is.read(buf)) != -1) {
                                        current += len;
                                        fos.write(buf, 0, len);
                                        subscriber.onNext(current * 100 / total + "");
                                       // LogUtils.i(TAG, "download current : " + current + ",total :" + total);
                                    }
                                    fos.flush();
                                    LogUtils.i(TAG, "download success ");
                                    subscriber.onCompleted();
                                } catch (IOException e) {
                                    subscriber.onError(e);
                                    LogUtils.printStackTrace(e);
                                    LogUtils.i(TAG, "download error");
                                } finally {
                                    try {
                                        if (is != null) is.close();
                                        if (fos != null) fos.close();
                                    } catch (IOException e) {
                                    }
                                }
                            }
                        });
                    }
                }) .subscribeOn(Schedulers.io())  //指定subscribe
                        .onBackpressureBuffer()
                        .observeOn(AndroidSchedulers.mainThread()); //指定observer

                Subscriber<String> subscriber=new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        LogUtils.i(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.i(TAG, "onError");
                        LogUtils.printStackTrace(e);
                    }

                    @Override
                    public void onNext(String s) {
                        LogUtils.i(TAG, "onNext "+s);
                    }
                };

                observable.subscribe(subscriber);

                break;
        }
    }
}
