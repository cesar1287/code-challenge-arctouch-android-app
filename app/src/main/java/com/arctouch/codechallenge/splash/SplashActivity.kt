package com.arctouch.codechallenge.splash

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.home.HomeActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)

        startActivity(Intent(this, HomeActivity::class.java))

//        api.genres(API_KEY, DEFAULT_LANGUAGE)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe {
//                Cache.cacheGenres(it.genres)
//                startActivity(Intent(this, HomeActivity::class.java))
//                finish()
//            }
    }
}
