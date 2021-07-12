package com.wning.demo.ui.activity

import android.app.Activity
import android.graphics.Point
import android.os.Bundle
import android.os.Looper
import android.os.MessageQueue
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.customview.widget.ViewDragHelper
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.navigation.NavigationView
import com.guagua.modules.utils.LogUtils
import com.wing.android.IpcFragment
import com.wning.demo.BaseActivity
import com.wning.demo.R
import com.wning.demo.ui.fragment.AnimFragment
import com.wning.demo.ui.fragment.ArchitectureFragment
import com.wning.demo.ui.fragment.CustomViewFragment
import com.wning.demo.ui.fragment.NetworkFragment

class MainActivity2 : BaseActivity() {
    private var mDrawerLayout: DrawerLayout? = null
    private var mNavigationView: NavigationView? = null
    private var mToolbar: Toolbar? = null
    private var mFragmentManager: FragmentManager? = null
    private var mDefaultFragment: Fragment? = null
    private val TAG_VIEW = "view"
    private val TAG_ANIM = "anim"
    private val TAG_NETWORK = "network"
    private val TAG_ARCHITECTURE = "architecture"
    private val TAG_IPC = "ipc"
    override fun getLayoutId(): Int {
        return R.layout.activity_main2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDrawerLayout = findViewById(R.id.drawerLayout)
        mNavigationView = findViewById(R.id.navigation_view)
        mNavigationView?.setItemIconTintList(null)
        mToolbar = findViewById(R.id.toolbar)
        setSupportActionBar(mToolbar)
        val mToggle = ActionBarDrawerToggle(
            this,
            mDrawerLayout,
            mToolbar,
            R.string.drawer_open,
            R.string.drawer_close
        )
        mToggle.syncState()
        mDrawerLayout?.addDrawerListener(mToggle)
        setDrawerLeftEdgeSize(this, mDrawerLayout, 0.1f)

        //头部点击事件
        mNavigationView?.getHeaderView(0)?.setOnClickListener { LogUtils.i(TAG, "NavigationView head clicked") }
        //item点击事件
        mNavigationView?.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.customView -> switchFragment(CustomViewFragment())
                R.id.anim -> switchFragment(AnimFragment())
                R.id.network -> switchFragment(NetworkFragment())
                R.id.architecture -> switchFragment(ArchitectureFragment())
                R.id.ipc -> switchFragment(IpcFragment())
            }
            menuItem.isChecked = true
            mDrawerLayout?.closeDrawers()
            true
        })
        mFragmentManager = supportFragmentManager

        //默认使用ipc
        mDefaultFragment = IpcFragment()
        mDefaultFragment?.let {
            mFragmentManager!!.beginTransaction().show(it)
                .replace(R.id.content, it)
                .commit()
        }

        testKotlin()


    }

    private fun testKotlin() {
        Looper.myQueue().addIdleHandler ({ ->
            println("queueIdle return true")
            true
        })

        Looper.myQueue().addIdleHandler {
            println("queueIdle return true")
            true
        }
        Looper.myQueue().addIdleHandler (object : MessageQueue.IdleHandler {
            override fun queueIdle(): Boolean {
                TODO("Not yet implemented")
                return true
            }
        })

        mToolbar?.setOnClickListener ({ v ->
            v?.visibility = View.GONE
            println("queueIdle return true")

        })

        mToolbar?.setOnClickListener { v ->
            v.visibility = View.GONE
            println("queueIdle return true")

        }

        mToolbar?.setOnClickListener {
            println("11333")
            println("queueIdle return true")

        }

        mToolbar?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                v?.visibility = View.GONE
            }
        })

    }

    // 根据所点列表项的下标，切换fragment
    fun switchFragment(fragmentId: Fragment) {
        mDrawerLayout!!.closeDrawers()
        if (mDefaultFragment === fragmentId) return
        val fragmentTransaction = mFragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.content, fragmentId)
            .addToBackStack(fragmentId.javaClass.simpleName).commit()
        mDefaultFragment = fragmentId
    }

    /**
     * drawerlayout 全屏滑动
     * @param activity
     * @param drawerLayout
     * @param displayWidthPercentage
     */
    private fun setDrawerLeftEdgeSize(
        activity: Activity?,
        drawerLayout: DrawerLayout?,
        displayWidthPercentage: Float
    ) {
        if (activity == null || drawerLayout == null) return
        try {
            // 找到 ViewDragHelper 并设置 Accessible 为true
            val leftDraggerField = drawerLayout.javaClass.getDeclaredField("mLeftDragger") //Right
            leftDraggerField.isAccessible = true
            val leftDragger = leftDraggerField[drawerLayout] as ViewDragHelper

            // 找到 edgeSizeField 并设置 Accessible 为true
            val edgeSizeField = leftDragger.javaClass.getDeclaredField("mEdgeSize")
            edgeSizeField.isAccessible = true
            val edgeSize = edgeSizeField.getInt(leftDragger)

            // 设置新的边缘大小
            val displaySize = Point()
            activity.windowManager.defaultDisplay.getSize(displaySize)
            edgeSizeField.setInt(
                leftDragger, Math.max(
                    edgeSize, (displaySize.x *
                            displayWidthPercentage).toInt()
                )
            )
        } catch (e: Exception) {
        }
    }
}