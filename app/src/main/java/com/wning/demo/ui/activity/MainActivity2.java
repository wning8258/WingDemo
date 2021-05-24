package com.wning.demo.ui.activity;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;
import com.guagua.modules.utils.LogUtils;
import com.wing.android.IpcFragment;
import com.wning.demo.BaseActivity;
import com.wning.demo.R;
import com.wning.demo.ui.fragment.AnimFragment;
import com.wning.demo.ui.fragment.ArchitectureFragment;
import com.wning.demo.ui.fragment.CustomViewFragment;
import com.wning.demo.ui.fragment.NetworkFragment;

import java.lang.reflect.Field;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.customview.widget.ViewDragHelper;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity2 extends BaseActivity {

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private Toolbar mToolbar;

    private FragmentManager mFragmentManager;
    private Fragment mDefaultFragment;
    private String TAG_VIEW ="view";
    private String TAG_ANIM="anim";
    private String TAG_NETWORK="network";
    private String TAG_ARCHITECTURE="architecture";
    private String TAG_IPC="ipc";

    @Override
    protected  int getLayoutId() {
        return R.layout.activity_main2;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDrawerLayout =findViewById(R.id.drawerLayout);
        mNavigationView =findViewById(R.id.navigation_view);
        mNavigationView.setItemIconTintList(null);

        mToolbar =findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        ActionBarDrawerToggle mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close);
        mToggle.syncState();
        mDrawerLayout.addDrawerListener(mToggle);
        setDrawerLeftEdgeSize(this, mDrawerLayout,0.1f);

        //头部点击事件
        mNavigationView.getHeaderView(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.i(TAG,"NavigationView head clicked");
            }
        });
        //item点击事件
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.customView:
                        switchFragment(new CustomViewFragment());
                        break;
                    case R.id.anim:
                        switchFragment(new AnimFragment());
                        break;
                    case R.id.network:
                        switchFragment(new NetworkFragment());
                        break;
                    case R.id.architecture:
                        switchFragment(new ArchitectureFragment());
                        break;
                     case R.id.ipc:
                        switchFragment(new IpcFragment());
                        break;
                }
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
        mFragmentManager=getSupportFragmentManager();

        //默认使用ipc
        mDefaultFragment = new IpcFragment();

        mFragmentManager.beginTransaction().show(mDefaultFragment)
                .replace(R.id.content, mDefaultFragment)
                .commit();

    }

    // 根据所点列表项的下标，切换fragment
    public void switchFragment(Fragment fragmentId) {
        mDrawerLayout.closeDrawers();
        if(mDefaultFragment == fragmentId)
            return;
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content, fragmentId).addToBackStack(fragmentId.getClass().getSimpleName()).commit();
        mDefaultFragment = fragmentId;
    }

    /**
     * drawerlayout 全屏滑动
     * @param activity
     * @param drawerLayout
     * @param displayWidthPercentage
     */
    private void setDrawerLeftEdgeSize (Activity activity, DrawerLayout drawerLayout, float displayWidthPercentage) {
        if (activity == null || drawerLayout == null) return;
        try {
            // 找到 ViewDragHelper 并设置 Accessible 为true
            Field leftDraggerField =
                    drawerLayout.getClass().getDeclaredField("mLeftDragger");//Right
            leftDraggerField.setAccessible(true);
            ViewDragHelper leftDragger = (ViewDragHelper) leftDraggerField.get(drawerLayout);

            // 找到 edgeSizeField 并设置 Accessible 为true
            Field edgeSizeField = leftDragger.getClass().getDeclaredField("mEdgeSize");
            edgeSizeField.setAccessible(true);
            int edgeSize = edgeSizeField.getInt(leftDragger);

            // 设置新的边缘大小
            Point displaySize = new Point();
            activity.getWindowManager().getDefaultDisplay().getSize(displaySize);
            edgeSizeField.setInt(leftDragger, Math.max(edgeSize, (int) (displaySize.x *
                    displayWidthPercentage)));
        } catch (Exception e) {
        }
    }

}
