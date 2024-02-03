package com.example.mysololife.utils

import com.google.firebase.auth.FirebaseAuth

class FBAuth {

    companion object{

        private lateinit var auth : FirebaseAuth

        fun getUid() : String{

            auth = FirebaseAuth.getInstance()

            // 현재 사용자의 uid값 리턴
            return auth.currentUser?.uid.toString()
        }

    }
}