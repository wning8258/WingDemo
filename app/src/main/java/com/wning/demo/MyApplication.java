package com.wning.demo;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;

import com.example.Wing;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.nostra13.universalimageloader.cache.memory.impl.LRULimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.squareup.leakcanary.LeakCanary;
import com.wning.demo.net.volley.data.RequestManager;

import java.util.HashMap;
import java.util.Map;

//import com.wning.demo.base.AppComponent;
//import com.wning.demo.base.AppModule;

/**
 * Created by Administrator on 2015/12/30.
 */
@Wing
public class MyApplication  extends Application{

    //图片最大缓存大小15MB
    private static final int MAX_DISK_CACHE_SIZE = 15 * 1024 * 1024;
    private DisplayImageOptions defaultDisplayImageOptions;

    private static MyApplication mInstance;
   // private static AppComponent appComponent;

    public static MyApplication getInstance() {
        return mInstance;
    }

//    public static AppComponent getAppComponent(){
//        if(appComponent==null){
//            appComponent= DaggerAppComponent.builder().appModule(new AppModule(mInstance)).build();
//        }
//        return appComponent;
//    }

    public Map<Integer,String> map=new HashMap();

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance=this;
        LeakCanary.install(this);
        initImageLoader();

        RequestManager.init(this);

        Fresco.initialize(getApplicationContext());



//        ImagePipelineConfig imagePipelineConfig=ImagePipelineConfig
//                .newBuilder(getApplicationContext())
//               // .setBitmapsConfig()
//                //....
//                .build();
//
//
//       //一定要new一下factory,PipelineDraweeControllerBuilderSupplier初始化的时候使用到了ImagePipelineFactory对象
//       // ImagePipelineFactory factory=new ImagePipelineFactory(imagePipelineConfig);
//        //factory.getImagePipeline();
//
//        Fresco.initialize(getApplicationContext(),imagePipelineConfig);
    }



    private void initImageLoader() {
        int memoryCacheSize;
        int threadPoolSize;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
            int memClass = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
            memoryCacheSize = (memClass / 4) * 1024 * 1024;
            threadPoolSize=3;
        } else {
            memoryCacheSize = 2 * 1024 * 1024;
            threadPoolSize=2;
        }

        defaultDisplayImageOptions = new DisplayImageOptions.Builder().showImageOnLoading(R.mipmap.gg_icon_default_head)
                .showImageForEmptyUri(R.mipmap.gg_load_fail_big).showImageOnFail(R.mipmap.gg_load_fail_big).cacheInMemory(true)
                .bitmapConfig(Bitmap.Config.RGB_565).cacheOnDisk(true).build();

        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this).threadPriority(Thread.NORM_PRIORITY - 2)
                .memoryCacheSize(memoryCacheSize).denyCacheImageMultipleSizesInMemory()
                .diskCacheSize(MAX_DISK_CACHE_SIZE).tasksProcessingOrder(QueueProcessingType.LIFO)
                .threadPoolSize(threadPoolSize)
                .memoryCache(new LRULimitedMemoryCache(memoryCacheSize))
                .defaultDisplayImageOptions(defaultDisplayImageOptions).writeDebugLogs().build();
        ImageLoader.getInstance().init(configuration);
    }
}
