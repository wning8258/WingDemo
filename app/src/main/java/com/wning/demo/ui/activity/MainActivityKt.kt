package com.wning.demo.ui.activity

import android.app.Activity
import android.os.Bundle
import android.view.View
import com.wning.demo.R
import kotlinx.android.synthetic.main.activity_main2.*

class MainActivityKt : Activity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        toolbar.setOnClickListener{
            println("123")
        }
        toolbar.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                TODO("Not yet implemented")
            }

        })

    }
}