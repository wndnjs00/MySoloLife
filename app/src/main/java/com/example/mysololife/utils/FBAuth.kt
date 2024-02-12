package com.example.mysololife.utils

import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class FBAuth {

    companion object{

        private lateinit var auth : FirebaseAuth

        // uid가져오는 함수
        fun getUid() : String{

            auth = FirebaseAuth.getInstance()
            // 현재 사용자의 uid값 리턴
            return auth.currentUser?.uid.toString()

        }


        // 시간가져오는 함수
        fun getTime() : String{

            val currentDateTime = Calendar.getInstance().time
            val dateFormat = SimpleDateFormat("yyyy.MM.dd HH:mm:ss", Locale.KOREA).format(currentDateTime)

            return dateFormat
        }


    }
}