package com.example.mysololife

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.example.mysololife.auth.IntroActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SplashActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        auth = Firebase.auth


        // 이미 로그인을 했다면 바로 메인페이지로 이동하도록

        if(auth.currentUser?.uid == null){
            // 로그인하지 않은 사용자일경우
            Log.d("SplashActivity", "null")
            // 3초있다가 SplashActivity 종료되고, IntroActivity로 이동
            Handler().postDelayed({
                startActivity(Intent(this, IntroActivity::class.java))
                finish()
            }, 3000)

        }else{
            // 로그인한 사용자일 경우
            Log.d("SplashActivity", "not null")
            // 3초있다가 SplashActivity 종료되고, MainActivity로 이동
            Handler().postDelayed({
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }, 3000)
        }


//        // 3초있다가 SplashActivity 종료되고, IntroActivity로 이동
//        Handler().postDelayed({
//            startActivity(Intent(this, IntroActivity::class.java))
//            finish()
//        }, 3000)

    }
}