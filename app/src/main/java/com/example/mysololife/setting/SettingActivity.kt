package com.example.mysololife.setting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.mysololife.R
import com.example.mysololife.auth.IntroActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class SettingActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        auth = Firebase.auth


//        val loginButton  = findViewById<Button>(R.id.logoutBtn)
        val loginButton : Button = findViewById(R.id.logoutBtn)


        // logoutBtn클릭했을때
        loginButton.setOnClickListener {

            // 로그아웃
            auth.signOut()
            Toast.makeText(this, "로그아웃 되었습니다", Toast.LENGTH_SHORT).show()

            // IntroActivity로 이동
            val intent = Intent(this, IntroActivity::class.java)
            // 기존엑티비티 삭제(뒤로가기 눌렀을때 기존엑티비티로 안가도록)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)

        }
    }
}