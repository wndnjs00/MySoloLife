package com.example.mysololife.board

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.mysololife.R
import com.example.mysololife.contentsList.BookmarkModel
import com.example.mysololife.databinding.ActivityBoardWriteBinding
import com.example.mysololife.utils.FBAuth
import com.example.mysololife.utils.FBRef
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import java.io.ByteArrayOutputStream
import java.io.StringReader

class BoardWriteActivity : AppCompatActivity() {

    private lateinit var binding : ActivityBoardWriteBinding

    private val TAG = BoardWriteActivity::class.java.simpleName

    val storage = Firebase.storage

    // 이미지 업로드 노
    private var isImageUploade = false



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_board_write)


        // 업로드 버튼 클릭시
        binding.writeBtn.setOnClickListener {

//            // titleArea,contentArea 값을 받아옴
//            val title = binding.titleArea.text.toString()
//            val content = binding.contentArea.text.toString()
//            // uid값 가져오기
//            val uid = FBAuth.getUid()
//            // tiem값 가져오기
//            val time = FBAuth.getTime()
//
//            Log.d(TAG, title)
//            Log.d(TAG, content)
//
            // 키값 받아오기 (키값 알아내기)
            val key = FBRef.boardRef.push().key.toString()
//
//            // 데이터 집어넣기
//            FBRef.boardRef
//                .child(key)
//                .setValue(BoardModel(title,content,uid,time,""))
//
//            Toast.makeText(this, "게시글 입력완료", Toast.LENGTH_SHORT).show()




            if (isImageUploade == true){

                // 이미지 업로드(키값을 기준으로)
                imageUpload(key)
            }


            // 엑티비티 사라짐
            finish()
        }


        // imageArea 클릭시
        binding.imageArea.setOnClickListener {

            // 갤러리로 이동
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, 100)

            isImageUploade = true


        }
    }


    // 이미지 업로드 함수
    private fun imageUpload(key :String){

        val storageRef = storage.reference
        val mountainsRef = storageRef.child(key + ".png")

        val imageView = binding.imageArea

        imageView.isDrawingCacheEnabled = true
        imageView.buildDrawingCache()
        val bitmap = (imageView.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        var uploadTask = mountainsRef.putBytes(data)
        uploadTask.addOnFailureListener {
            // Handle unsuccessful uploads
        }.addOnSuccessListener { taskSnapshot ->

            mountainsRef.downloadUrl.addOnSuccessListener{uri ->

                val imageUrl = uri.toString()

                saveDataToDatabase(key, imageUrl)

            }.addOnFailureListener {

            }
        }
    }



    private fun saveDataToDatabase(key: String, imageUrl: String) {
        // titleArea,contentArea 값을 받아옴
        val title = binding.titleArea.text.toString()
        val content = binding.contentArea.text.toString()
        // uid값 가져오기
        val uid = FBAuth.getUid()
        // tiem값 가져오기
        val time = FBAuth.getTime()

        Log.d(TAG, title)
        Log.d(TAG, content)

        // Push data to the Firebase Realtime Database
        val boardModel = BoardModel(title, content, uid, time, imageUrl)

        // Save data to Firebase
        FBRef.boardRef
            .child(key)
            .setValue(boardModel)
            .addOnSuccessListener {
                Toast.makeText(this, "게시글 입력완료", Toast.LENGTH_SHORT).show()
                // Finish activity
                finish()
            }
            .addOnFailureListener {
                // Handle errors
            }
    }





    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // 갤러리에서 데이터 받아오기
        if(resultCode == RESULT_OK && requestCode == 100){

            // 받아온 데이터를 레이아웃에 표시(갤러리 데이터가 imageArea에 표시)
            binding.imageArea.setImageURI(data?.data)


        }
    }


}