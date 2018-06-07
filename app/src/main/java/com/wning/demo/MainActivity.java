package com.wning.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.Wing;
import com.guagua.modules.utils.Utils;
import com.wning.demo.customview.activity.BezierQQBubbleViewActivity;
import com.wning.demo.customview.activity.ChoreographerActivity;
import com.wning.demo.customview.activity.CoordinatorLayoutActivity;
import com.wning.demo.customview.activity.CustomAnimActivity;
import com.wning.demo.customview.activity.CustomViewActivity;
import com.wning.demo.customview.activity.InterceptTouchEventActivity;
import com.wning.demo.customview.activity.LooperActivity;
import com.wning.demo.customview.activity.ReboundActivity;
import com.wning.demo.customview.activity.ShaderRoundImageViewActivity;
import com.wning.demo.customview.activity.ViewDragHelperActivity;
import com.wning.demo.customview.activity.XfermodeActivity;
import com.wning.demo.customview.activity.XfermodeRoundImageViewActivity;
import com.wning.demo.customview.activity.bezier.BezierActivity1;
import com.wning.demo.customview.activity.bezier.BezierActivity2;
import com.wning.demo.customview.activity.bezier.BezierWaveActivity;
import com.wning.demo.dagger2.Dagger2Activity;
import com.wning.demo.gifteffect.QIQIGiftEffectActivity;
import com.wning.demo.matrix.MatrixActivity;
import com.wning.demo.mvp.UserInfoActivity;
import com.wning.demo.net.okhttp.OkHttpActivity;
import com.wning.demo.net.retrofit.RetrofitActivity;
import com.wning.demo.net.volley.VolleyActivity;
import com.wning.demo.producer.ProducerActivity;
import com.wning.demo.rxjava.RxJavaActivity;

import java.util.ArrayList;

import butterknife.BindView;

@Wing
public class MainActivity extends BaseActivity {


//    @BindView(R.id.recyclerView)
//    RecyclerView recyclerView;
//    private MyAdapter mAdapter;
//
//    private ArrayList<DataItem> dataItems;

    @BindView(R.id.list)
    ExpandableListView listView;

    private ExpandableAdapter adapter;

    private ArrayList<DataList> dataItems;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_main);
        initData();
        initRecyclerView();
     //LogPrinter.enable(this);

        setSupportActionBar(toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
      //  LogPrinter.disable();
    }

    private void initData() {

        toolbar.setTitle("MainActivity");

        dataItems=new ArrayList<>();

        ArrayList<DataItem> items=new ArrayList<>();
        items.add(new DataItem().setTitle("自定义view学习").setClazz(CustomViewActivity.class));
        items.add(new DataItem().setTitle("xfermode模式真实效果").setClazz(XfermodeActivity.class));
        items.add(new DataItem().setTitle("xfermode圆形圆角图片").setClazz(XfermodeRoundImageViewActivity.class));
        items.add(new DataItem().setTitle("Shader圆形圆角图片").setClazz(ShaderRoundImageViewActivity.class));
        items.add(new DataItem().setTitle("贝塞尔曲线demo").setClazz(BezierActivity1.class));
        items.add(new DataItem().setTitle("贝塞尔曲线圆滑划线").setClazz(BezierActivity2.class));
        items.add(new DataItem().setTitle("贝塞尔曲线波浪").setClazz(BezierWaveActivity.class));
        items.add(new DataItem().setTitle("贝塞尔曲线qq点赞效果").setClazz(BezierQQBubbleViewActivity.class));
        items.add(new DataItem().setTitle("CoordinatorLayout").setClazz(CoordinatorLayoutActivity.class));
        items.add(new DataItem().setTitle("InterceptTouchEvent").setClazz(InterceptTouchEventActivity.class));
        items.add(new DataItem().setTitle("ViewDragHelper").setClazz(ViewDragHelperActivity.class));

        dataItems.add(new DataList().setTitle("自定义view").setList(items));

        items=new ArrayList<>();
        items.add(new DataItem().setTitle("自定义动画").setClazz(CustomAnimActivity.class));
        items.add(new DataItem().setTitle("Facebook Rebound效果").setClazz(ReboundActivity.class));
        items.add(new DataItem().setTitle("QIQI礼物特效").setClazz(QIQIGiftEffectActivity.class));
        items.add(new DataItem().setTitle("Matrix").setClazz(MatrixActivity.class));
        items.add(new DataItem().setTitle("子线程Looper").setClazz(LooperActivity.class));
        items.add(new DataItem().setTitle("Choreographer").setClazz(ChoreographerActivity.class));

        dataItems.add(new DataList().setTitle("动画").setList(items));

        items=new ArrayList<>();
        items.add(new DataItem().setTitle("volley").setClazz(VolleyActivity.class));
        items.add(new DataItem().setTitle("okhttp").setClazz(OkHttpActivity.class));
        items.add(new DataItem().setTitle("Retrofit").setClazz(RetrofitActivity.class));

        dataItems.add(new DataList().setTitle("网络").setList(items));

        items=new ArrayList<>();
        items.add(new DataItem().setTitle("RxJava").setClazz(RxJavaActivity.class));
        items.add(new DataItem().setTitle("MVP").setClazz(UserInfoActivity.class));
        items.add(new DataItem().setTitle("Dagger2").setClazz(Dagger2Activity.class));
        items.add(new DataItem().setTitle("Fresco Producer&Consumer").setClazz(ProducerActivity.class));

        dataItems.add(new DataList().setTitle("架构").setList(items));
    }

    private void initRecyclerView() {
      //  recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
      //  recyclerView.setLayoutManager(new LinearLayoutManager(this));
     //   mAdapter = new MyAdapter();
       // recyclerView.setAdapter(mAdapter);

        adapter=new ExpandableAdapter();

        listView.setAdapter(adapter);

       listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
           @Override
           public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
               startActivity(new Intent(MainActivity.this,dataItems.get(groupPosition).getList().get(childPosition).getClazz()));

//               Intent intent=new Intent(MainActivity.this,dataItems.get(groupPosition).getList().get(childPosition).getClazz());
//               startActivityWithTransition(intent);
               return true;
           }
       });


    }


//    private class MyAdapter extends RecyclerView.Adapter<MyHolder> {
//
//        @Override
//        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.recycler_view_item, parent, false);
//            //  View view= View.inflate(getApplicationContext(),R.layout.recycler_view_item,null);
//            MyHolder holder = new MyHolder(view);
//            return holder;
//        }
//
//        @Override
//        public void onBindViewHolder(MyHolder holder, final int position) {
//            holder.btn.setText(dataItems.get(position).getTitle());
//            holder.btn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    startActivity(new Intent(MainActivity.this, dataItems.get(position).getClazz()));
//                }
//            });
//        }
//
//        @Override
//        public int getItemCount() {
//            return dataItems.size();
//        }
//
//
//    }
//
//     class MyHolder extends RecyclerView.ViewHolder {
//
//        @BindView(R.id.btn) Button btn;
//
//        public MyHolder(View itemView) {
//            super(itemView);
//         //   btn = (Button) itemView.findViewById(R.id.btn);
//            ButterKnife.bind(this,itemView);
//        }
//    }

    private class ExpandableAdapter extends BaseExpandableListAdapter {
        @Override
        public int getGroupCount() {
            return dataItems.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return dataItems.get(groupPosition).getList().size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return dataItems.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return dataItems.get(groupPosition).getList().get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.recycler_view_item, parent, false);
            TextView btn = (TextView) view.findViewById(R.id.btn);
            ViewGroup.LayoutParams lp=btn.getLayoutParams();
            lp.height=Utils.dip2px(getApplicationContext(),60);
            btn.setLayoutParams(lp);
            btn.setText(dataItems.get(groupPosition).getTitle());
            return view;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.recycler_view_second_item, parent, false);
            TextView btn = (TextView) view.findViewById(R.id.btn);
            TextView content = (TextView) view.findViewById(R.id.content);
            btn.setText(dataItems.get(groupPosition).getList().get(childPosition).getTitle());
            content.setText("("+dataItems.get(groupPosition).getList().get(childPosition).getClazz().getSimpleName()+".java)");
            content.setVisibility(View.VISIBLE);
            return view;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }

}
