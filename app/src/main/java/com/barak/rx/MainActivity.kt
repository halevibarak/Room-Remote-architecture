package com.barak.rx

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.barak.rx.features.cats.ArticleFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, ArticleFragment())
                .commit()
        }
    }
}
