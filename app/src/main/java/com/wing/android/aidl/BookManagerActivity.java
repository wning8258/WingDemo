package com.wing.android.aidl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;

import com.wning.demo.databinding.ActivityBookManagerBinding;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class BookManagerActivity extends AppCompatActivity {

    private static final String TAG = "BookManagerActivity";
    private static final int MESSAGE_NEW_BOOK_ARRIVED = 1;
    private ActivityBookManagerBinding inflate;

    private TestServerCallClientBinder testServerCallClientBinder = new TestServerCallClientBinder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inflate = ActivityBookManagerBinding.inflate(getLayoutInflater());
        setContentView(inflate.getRoot());

        Intent intent = new Intent (this, BookManagerService.class);
        bindService(intent,connection, Context.BIND_AUTO_CREATE);
    }

    /**
     * 创建一个binder，尝试直接扔给服务器。让服务器去调用客户端的方法
     */
    private class TestServerCallClientBinder extends  ITestServerCallClient.Stub{

        @Override
        public void testServerCallClient() throws RemoteException {
            /**
             * https://blog.csdn.net/braintt/article/details/88046162
             * ITestServerCallClient.aidl如果不加oneWay,
             * setClientBinder调用testServerCallClient这里的线程会是main,并不是binder线程
             * （因为主线程调用了服务器的方法，主线程是被阻塞的。binder做了优化,直接让主线程处理请求）
             *
             * onNewBookArrived调用testServerCallClient 是binder线程（因为这里主线程不是阻塞的）
             *
             *
             * 加了oneway之后，请求变为异步的，直接会在binder线程执行
             *
             */
            Log.i("testServerCallClient","testServerCallClient ，thread:" + Thread.currentThread().getName());
        }
    }

    IBookManager remoteManager;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //转换
            remoteManager = IBookManager.Stub.asInterface(service);

            try {
                List<Book> bookList = remoteManager.getBookList(); //java.util.ArrayList
                Log.i(TAG, "query book list, list type :" + bookList.getClass().getCanonicalName());
                Log.i(TAG, "query book list, list :" + bookList.toString());

                Book newBook = new Book(3, "h55555555");
                remoteManager.addBook(newBook);
                Log.i(TAG,"add book :" +newBook);
                bookList = remoteManager.getBookList();
                Log.i(TAG, "query book new list, list :" + bookList.toString());

                remoteManager.registerListener(newBookArrivedListener);


                //尝试客户端直接创建一个binder，扔给服务器
                remoteManager.setClientBinder(testServerCallClientBinder);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            remoteManager = null;
            Log.i(TAG,"onServiceDisconnected binder died");
        }
    };

    private IOnNewBookArrivedListener newBookArrivedListener = new IOnNewBookArrivedListener.Stub() {

        @Override
        public void onNewBookArrived(Book book) throws RemoteException {
            //服务器通知
            handler.obtainMessage(MESSAGE_NEW_BOOK_ARRIVED, book).sendToTarget();
        }
    };

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case MESSAGE_NEW_BOOK_ARRIVED:
                    Log.i(TAG, "receive new Book :" + msg.obj);
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        if (remoteManager != null && remoteManager.asBinder().isBinderAlive()) {
            try {
                Log.i(TAG,"onDestroy unRegisterListener :" + newBookArrivedListener);
                //unregister会提示 unRegisterListener not found（因为binder 多进程传递后，反序列化 会生成跟registerListener不同的对象）
                remoteManager.unRegisterListener(newBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            unbindService(connection);
        }
        super.onDestroy();
    }
}