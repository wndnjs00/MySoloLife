package com.example.mysololife.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mysololife.R
import com.example.mysololife.contentsList.BookmarkRVAdapter
import com.example.mysololife.contentsList.ContentModel
import com.example.mysololife.databinding.FragmentHomeBinding
import com.example.mysololife.utils.FBAuth
import com.example.mysololife.utils.FBRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener


class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding

    private val TAG = HomeFragment::class.java.simpleName

    val bookmarkIdList = mutableListOf<String>()
    val items = ArrayList<ContentModel>()
    val itemKeyList = ArrayList<String>()

    lateinit var rvAdapter: BookmarkRVAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)




        // tipTap 눌렀을때 tipFragment로 이동
        binding.tipTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_tipFragment)
        }

        // talkTap 눌렀을때 talkFragment로 이동
        binding.talkTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_talkFragment)
        }

        // bookmarkTap 눌렀을때 bookmarkFragment로 이동
        binding.bookmarkTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_bookmarkFragment)

        }

        // storeTap 눌렀을때 storeFragment로 이동
        binding.storeTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_storeFragment)

        }


        rvAdapter = BookmarkRVAdapter(requireContext(), items, itemKeyList, bookmarkIdList)

        val rv : RecyclerView = binding.mainRV
        rv.adapter = rvAdapter

        rv.layoutManager = GridLayoutManager(requireContext(),2)

        getBookmarkData()

        return binding.root
    }


    // 북마크데이터 가져오기
    private fun getCategoryData(){

        // 전체 데이터 가져오기
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (dataModel in dataSnapshot.children){

                    val item = dataModel.getValue(ContentModel::class.java)

                    // 3. 전체 컨텐츠 중에서, 사용자가 북마크한 정보만 가져옴
                    // bookmarkList가 키값을 포함하고 있으면 값들을 넣어줌
                    if (bookmarkIdList.contains(dataModel.key.toString())){
                        items.add(item!!)
                        itemKeyList.add(dataModel.key.toString())
                    }

                }
                rvAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("ContentListActivity", "loadPost:onCancelled", databaseError.toException())
            }
        }
        // 전체 데이터 받아옴
        FBRef.category1.addValueEventListener(postListener)
        FBRef.category2.addValueEventListener(postListener)
        FBRef.category3.addValueEventListener(postListener)
        FBRef.category4.addValueEventListener(postListener)
        FBRef.category5.addValueEventListener(postListener)
        FBRef.category6.addValueEventListener(postListener)
        FBRef.category7.addValueEventListener(postListener)
        FBRef.category8.addValueEventListener(postListener)
    }

    // 북마크 데이터 가져오기
    private fun getBookmarkData(){

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (dataModel in dataSnapshot.children){

                    Log.e(TAG, dataModel.toString())
                    bookmarkIdList.add(dataModel.key.toString())

                }
                // 1. 전체 카테고리에 있는 컨텐츠 데이터들을 다 가져옴
                getCategoryData()

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("ContentListActivity", "loadPost:onCancelled", databaseError.toException())
            }
        }
        // 북마크 데이터받아옴
        FBRef.bookmarkRef.child(FBAuth.getUid()).addValueEventListener(postListener)

    }




}