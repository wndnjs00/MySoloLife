package com.example.mysololife.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.mysololife.MainActivity
import com.example.mysololife.R
import com.example.mysololife.databinding.ActivityJoinBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app

class JoinActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth
    private lateinit var binding : ActivityJoinBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth

        binding = DataBindingUtil.setContentView(this, R.layout.activity_join)



        binding.joinBtn.setOnClickListener {

            var isGoToJoin = true


            // 사용자가 입력한 "이메일,비밀번호,비밀번호확인" 받아오기
            val email = binding.emailArea.text.toString()
            val password1 = binding.passwordArea1.text.toString()
            val password2 = binding.passwordArea2.text.toString()


            // 값이 비어있는지 확인
            if (email.isEmpty()){
                Toast.makeText(this, "이메일을 입력해주세요", Toast.LENGTH_SHORT).show()
                isGoToJoin = false
            }

            if(password1.isEmpty()){
                Toast.makeText(this, "password를 입력해주세요", Toast.LENGTH_SHORT).show()
                isGoToJoin = false
            }


            if(password2.isEmpty()){
                Toast.makeText(this, "password를 입력해주세요", Toast.LENGTH_SHORT).show()
                isGoToJoin = false
            }


            // password1과 password2가 같은지 확인
            if(!password1.equals(password2)){
                Toast.makeText(this, "비밀번호를 똑같이 입력해주세요", Toast.LENGTH_SHORT).show()
                isGoToJoin = false
            }

            // 비밀번호가 6자 이상인지
            if (password1.length < 6) {
                Toast.makeText(this, "password를 6자리 이상으로 입력해주세요", Toast.LENGTH_SHORT).show()
                isGoToJoin = false
            }

            // isGoToJoin이 true이면 해당 코드 실행
            if(isGoToJoin) {

                // 회원가입
                auth.createUserWithEmailAndPassword(email, password1)
                    .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "성공", Toast.LENGTH_SHORT).show()

                        // MainActivity로 이동
                        val intent = Intent(this, MainActivity::class.java)
                        // MainActivity에서 뒤로가기 눌렀을때 회원가입창 안나오도록(기존 엑티비티 날려버림)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)

                    } else {
                        Toast.makeText(this, "실패", Toast.LENGTH_SHORT).show()
                    }
            }

            }

        }



    }
}