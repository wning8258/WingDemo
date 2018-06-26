package com.wning.demo.net.retrofit;

import android.os.Bundle;
import android.widget.Button;

import com.guagua.modules.utils.LogUtils;
import com.guagua.qiqi.utils.QiQiHttpConfig;
import com.guagua.qiqi.utils.SignHelper;
import com.wning.demo.BaseActivity;
import com.wning.demo.R;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public class RetrofitActivity extends BaseActivity {

    private static final String TAG="RetrofitActivity";

    private String url="http://cgi.client.qxiu.com/cgi/fans/attention/attentionlist?userId=91338142&&lastId=0";

    @BindView(R.id.btn1)
    Button btn1;

    @BindView(R.id.btn2)
    Button btn2;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_net_retrofit;
    }


    public interface IGameListBiz
    {
      //  @GET("gamelist.do?sign=A27C690D44185D92AE125AC428C35F1CE82180BD&roomid=223981&accesstoken=CBE9458707D491548BFA1E8FA4343ED3&id=107&openid=5FF7711FA6FCAB6E3A4044C04DB7CB6F")
        @GET("gamelist.do")
        Call<Game> getGameList( @QueryMap Map<String, String> params);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://hall.m.qxiu.com/system/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(genericClient())
                .build();
        IGameListBiz gameListBiz = retrofit.create(IGameListBiz.class);

        //请求参数
        //?sign=A27C690D44185D92AE125AC428C35F1CE82180BD&roomid=223981&accesstoken=CBE9458707D491548BFA1E8FA4343ED3&openid=5FF7711FA6FCAB6E3A4044C04DB7CB6F
        HashMap<String,String > params=new HashMap<>();
        params.put("accesstoken","CBE9458707D491548BFA1E8FA4343ED3");
        params.put("openid","5FF7711FA6FCAB6E3A4044C04DB7CB6F");
        params.put("roomid","223981");
        params.put("id","107");

        SignHelper.addSign(params);

        Call<Game> call = gameListBiz.getGameList(params);
        call.enqueue(new Callback<Game>()
        {
            @Override
            public void onResponse(Call<Game> call, Response<Game> response)
            {

                LogUtils.e(TAG, "onResponse:" + response.body() + " , "+response.isSuccessful()+"，code :"+response.code());
            }

            @Override
            public void onFailure(Call<Game> call, Throwable t)
            {
                LogUtils.e(TAG, "onFailure:" + t.getMessage() + "");
                LogUtils.printStackTrace(t);
            }
        });

    }


    /**
     * 设置请求统一的header
     * @return
     */
    public static OkHttpClient genericClient() {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {

                        Request.Builder builder = chain.request().newBuilder();

                        for(Map.Entry<String,String> entry : QiQiHttpConfig.getHeader().entrySet()){
                            builder.addHeader(entry.getKey(),entry.getValue());
                        }

                        Request request = builder.build();

                        return chain.proceed(request);
                    }

                })
                .build();

        return httpClient;
    }
}
