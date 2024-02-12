package com.example.mysololife.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.mysololife.R
import com.example.mysololife.board.BoadListLVAdapter
import com.example.mysololife.board.BoardInsideActivity
import com.example.mysololife.board.BoardModel
import com.example.mysololife.board.BoardWriteActivity
import com.example.mysololife.contentsList.ContentModel
import com.example.mysololife.databinding.FragmentTalkBinding
import com.example.mysololife.databinding.FragmentTipBinding
import com.example.mysololife.utils.FBRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener


class TalkFragment : Fragment() {

    private lateinit var binding : FragmentTalkBinding

    private val TAG = TalkFragment::class.java.simpleName

    private val boardDataList = mutableListOf<BoardModel>()

    private lateinit var boardRVAdapter : BoadListLVAdapter

    // 키값 넣어주는 리스트
    private val boardKeyList = mutableListOf<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_talk,container,false)


        // 리스트뷰 아이템 클릭시
        binding.boardListView.setOnItemClickListener { parent, view, position, id ->

//            val intent = Intent(context, BoardInsideActivity::class.java)
//            // 값 전달(넘겨줌)
//            intent.putExtra("title", boardDataList[position].title)     // 클릭된값의 title
//            intent.putExtra("content",boardDataList[position].content)  // 클릭된값의 content
//            intent.putExtra("time",boardDataList[position].time)        // 클릭된값의 time


            val intent = Intent(context, BoardInsideActivity::class.java)
            intent.putExtra("key",boardKeyList[position])   // key값 넘겨줌
            startActivity(intent)
        }


        // 어뎁터와 ListView 연결
        boardRVAdapter = BoadListLVAdapter(boardDataList)
        binding.boardListView.adapter = boardRVAdapter



        // 게시물 작성버튼 눌렀을때
        binding.writeBtn.setOnClickListener {
            val intent = Intent(context, BoardWriteActivity::class.java)
            startActivity(intent)
        }



        // 네비게이션바
        binding.homeTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_talkFragment_to_homeFragment)
        }

        binding.tipTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_talkFragment_to_tipFragment)

        }

        binding.bookmarkTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_talkFragment_to_bookmarkFragment)

        }

        binding.storeTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_talkFragment_to_storeFragment)

        }

        getFBBoardData()

        return binding.root

    }


    // 게시글 데이터 가져오는 함수
    private fun getFBBoardData(){

        // 데이터 가져오기
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                // 겹쳐서 출력되는 현상 방지 (데이터 초기화)
                boardDataList.clear()

                for (dataModel in dataSnapshot.children){

                    Log.d(TAG, dataModel.toString())

//                    // 키값 받아오기
//                    dataModel.key

                    // 데이터 받아오기
                    val item = dataModel.getValue(BoardModel::class.java)
                    boardDataList.add(item!!)   // BoardModel에 데이터 하나씩 넣어줌
                    boardKeyList.add(dataModel.key.toString())// key값 넣어줌
                }

                // 키 순서 최신순
                boardKeyList.reverse()
                // 데이터 최신순으로
                boardDataList.reverse()

                //동기화
                boardRVAdapter.notifyDataSetChanged()

                Log.d(TAG, boardDataList.toString())
            }



            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        FBRef.boardRef.addValueEventListener(postListener)

    }


}