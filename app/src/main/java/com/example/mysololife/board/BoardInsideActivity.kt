package com.example.mysololife.board

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.mysololife.R
import com.example.mysololife.comment.CommentLVAdapter
import com.example.mysololife.comment.CommentModel
import com.example.mysololife.databinding.ActivityBoardInsideBinding
import com.example.mysololife.utils.FBAuth
import com.example.mysololife.utils.FBRef
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.storage

class BoardInsideActivity : AppCompatActivity() {

    private val TAG = BoardInsideActivity::class.java.simpleName

    private lateinit var binding : ActivityBoardInsideBinding

    // key값 선언
    private lateinit var key : String

    private val commentDataList = mutableListOf<CommentModel>()

    private lateinit var commentAdapter : CommentLVAdapter


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_board_inside )


        // 첫번째 방법
//        // TalkFragment에서 넘겨준값 받아오기
//        val title = intent.getStringExtra("title").toString()
//        val content = intent.getStringExtra("content").toString()
//        val time = intent.getStringExtra("time").toString()
//
//        // 레이아웃과 데이터 연결
//        binding.titleArea.text = title
//        binding.textArea.text = content
//        binding.timeArea.text = time


        // 햄버거바 클릭시
        binding.boardSettingIcon.setOnClickListener {

            // 다이얼로그 띄우기
            showDialog()
        }


        // TalkFragment에서 보낸 key값 받아오기
        key = intent.getStringExtra("key").toString()

        getBoardData(key)
        // 이미지 업로드
        getImageData(key)


        // 댓글 입력버튼 눌렀을때
        binding.commentBtn.setOnClickListener {
            insertComment(key)
        }


        // 어뎁터와 ListView 연결
        commentAdapter= CommentLVAdapter(commentDataList)
        binding.commentLV.adapter = commentAdapter


        getCommentData(key)

    }



    // CommentData 받아오는(가져오는) 함수
    fun getCommentData(key : String){

        // 데이터 가져오기
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                // 겹쳐서 출력되는 현상 방지 (데이터 초기화)
                commentDataList.clear()

                for (dataModel in dataSnapshot.children){

                    // 데이터 받아오기
                    val item = dataModel.getValue(CommentModel::class.java)
                    commentDataList.add(item!!)   // commentDataList에 데이터 하나씩 넣어줌

                }

                commentAdapter.notifyDataSetChanged()

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        FBRef.commentRef.child(key).addValueEventListener(postListener)

    }




    // 파이어베이스에 입력한 댓글 저장하는 함수
    fun insertComment(key : String){
//        <데이터구조>
//        comment
//          - Boardkey
//             - Commentkey
//                - CommentData
//                - CommentData
//                - CommentData

        FBRef.commentRef
            .child(key)     // Boardkey
            .push()         // Commentkey
            .setValue(CommentModel(binding.commentArea.text.toString(),     // 내가 입력한 댓글 집어넣음
                                    FBAuth.getTime()))                      // 시간 집어넣음

        Toast.makeText(this, "댓글 입력완료", Toast.LENGTH_SHORT).show()
        // 텍스트 지워줌
        binding.commentArea.setText("")


    }



    // 다이얼로그창 띄우는 함수
    private fun showDialog(){

        val mDialogView = LayoutInflater.from(this).inflate(R.layout.custom_dialog, null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
            .setTitle("게시글 수정,삭제")

        val alertDialog = mBuilder.show()


        // 수정버튼 눌렀을때
        alertDialog.findViewById<Button>(R.id.editBtn)?.setOnClickListener {

            // BoardEditActivity로 이동
            val intent = Intent(this, BoardEditActivity::class.java)

            // BoardEditActivity로 key값 넘겨줌
            intent.putExtra("key", key)

            startActivity(intent)

            // 엑티비티 종료(수정버튼 눌렀을때 이미지가 즉시 적용되지 않는 문제해결)
            finish()
        }


        // 삭제버튼 눌렀을때
        alertDialog.findViewById<Button>(R.id.removeBtn)?.setOnClickListener {

            // "정말 삭제하겠습니까?" 다이얼로그 추가
            val builder = AlertDialog.Builder(this)
            builder.setTitle("게시글 삭제")
                .setMessage("정말로 삭제하시겠습니까? 삭제하시면 복구할수없습니다")

                .setPositiveButton("네",
                    DialogInterface.OnClickListener{ dialog, id ->

                        // 파이어베이스에 board안에 key값을 찾아와서 삭제
                        FBRef.boardRef.child(key).removeValue()
                        Toast.makeText(this, "삭제완료", Toast.LENGTH_SHORT).show()
                        finish()    // 엑티비티 종료
                    })
                .setNegativeButton("아니오",
                    DialogInterface.OnClickListener{ dialog, id ->
                        finish()
                    })

            // 다이얼로그 띄워주기
            builder.show()
        }


    }


    // 이미지 다운로드 함수(게시글 작성한 이미지 클릭했을때 보이게)
    private fun getImageData(key : String){

        // 이미지 다운로드

        // Reference to an image file in Cloud Storage
        val storageReference = Firebase.storage.reference.child(key + ".png")

        // ImageView in your Activity
        val imageViewFromFB = binding.getImageArea

        storageReference.downloadUrl.addOnCompleteListener (OnCompleteListener { task ->

            // 이미지 업로드 성공
            if(task.isSuccessful){
                // Glide를 사용하여 task에서 이미지 직접 다운로드
                Glide.with(this)
                    .load(task.result)
                    .into(imageViewFromFB)

                // 이미지 업로드 실패
            }else{

                // 이미지를 업로드하지 않았을때는 getImageArea를 보이지않도록
                binding.getImageArea.isVisible = false

            }
        })

    }



    // board데이터 받아오는 함수
    private fun getBoardData(key : String){

        // 데이터 가져오기
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {


                // try문에서 에러발생하면 catch문 실행
                try {

                    //데이터 받아오기
                    val dataModel = dataSnapshot.getValue(BoardModel::class.java)

                    // 레이아읏과 연결
                    binding.titleArea.text = dataModel!!.title   // titleArea와 BoardModel 연결
                    binding.textArea.text = dataModel!!.content  // textArea와 BoardModel 연결
                    binding.timeArea.text = dataModel!!.time     // timeArea와 BoardModel 연결


                    val myUid = FBAuth.getUid()  // 현재 내 uid
                    val writerUid = dataModel.uid // 글쓴사람의 uid

                    // 글쓴사람만 게시글 수정, 삭제 가능하도록
                    // 현재 내 uid와 글쓴사람의 uid가 같다면
                    if(myUid.equals(writerUid)){
                        // 햄버거버튼 보이게
                        binding.boardSettingIcon.isVisible = true

                    }else{
                        // 햄버거버튼 안보이게
//                        binding.boardSettingIcon.isVisible = false
                    }


                } catch (e: Exception){
                    Log.d(TAG, "삭제완료")
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        // board안에있는 key값을 가져오기
        FBRef.boardRef.child(key).addValueEventListener(postListener)

    }
}