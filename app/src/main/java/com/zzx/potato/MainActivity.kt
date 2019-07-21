package com.zzx.potato

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn.setOnClickListener {
            Potato.with(image)
                .url("https://ss0.baidu.com/94o3dSag_xI4khGko9WTAnF6hhy/image/h%3D300/sign=ae96796f44ed2e73e3e9802cb703a16d/6a63f6246b600c331fa84823144c510fd8f9a10c.jpg")
                .load()
        }

    }
}
