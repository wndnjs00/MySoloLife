package com.example.mysololife.contentsList

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mysololife.R
import com.example.mysololife.utils.FBAuth
import com.example.mysololife.utils.FBRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ContentListActivity : AppCompatActivity() {

    lateinit var myRef : DatabaseReference

    // id값을 담을 리스트 생성
    val bookmarkIdList = mutableListOf<String>()

    lateinit var rvAdapter : ContentRVAdapter


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content_list)

        val items = ArrayList<ContentModel>()
        val itemKeyList = ArrayList<String>()     //키값을 받아서 저장
        rvAdapter = ContentRVAdapter(baseContext, items ,itemKeyList , bookmarkIdList)

        // Write a message to the database
        val database = Firebase.database

        // TipFragment에서 넘겨준 데이터 받아옴
        val category = intent.getStringExtra("category")



        if (category == "category1"){
            myRef = database.getReference("contents")

        }else if(category == "category2"){
            myRef = database.getReference("contents2")

        }else if(category == "category3"){
            myRef = database.getReference("contents3")

        }else if(category == "category4"){
            myRef = database.getReference("contents4")

        }else if (category == "category5"){
            myRef = database.getReference("contents5")

        }else if (category == "category6"){
            myRef = database.getReference("contents6")

        }else if (category == "category7"){
            myRef = database.getReference("contents7")

        }else if (category == "category8") {
            myRef = database.getReference("contents8")
        }



        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (dataModel in dataSnapshot.children){
                    Log.d("ContentListActivity",dataModel.toString())
                    Log.d("ContentListActivity", dataModel.key.toString())
                    val item = dataModel.getValue(ContentModel::class.java)
                    items.add(item!!)
                    itemKeyList.add(dataModel.key.toString())

                }
                rvAdapter.notifyDataSetChanged()
                Log.d("ContentListActivity",items.toString())

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("ContentListActivity", "loadPost:onCancelled", databaseError.toException())
            }
        }
        myRef.addValueEventListener(postListener)




        //리사이클러뷰 연결
        val rv : RecyclerView = findViewById(R.id.rv)



        // ContentRVAdapter를 rvAdapter에 연결

        rv.adapter = rvAdapter

        rv.layoutManager = GridLayoutManager(this, 2)   // 한줄에 2개씩

        // getBookmarkData함수 실행
        getBookmarkData()


//        val myRef8 = database.getReference("contents8")
//        myRef8.push()
//            .setValue(ContentModel("title33", "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FbdIKDG%2Fbtq64M96JFa%2FKcJiYgKuwKuP3fIyviXm90%2Fimg.png", "https://philosopher-chan.tistory.com/1247?category=941578"))
//
//        myRef8.push()
//            .setValue(ContentModel("title34", "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FFtY3t%2Fbtq65q6P4Zr%2FWe64GM8KzHAlGE3xQ2nDjk%2Fimg.png", "https://philosopher-chan.tistory.com/1248?category=941578"))
//
//        myRef8.push()
//            .setValue(ContentModel("title35", "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FOtaMq%2Fbtq67OMpk4W%2FH1cd0mda3n2wNWgVL9Dqy0%2Fimg.png", "https://philosopher-chan.tistory.com/1249?category=941578"))
//
//        myRef8.push()
//            .setValue(ContentModel("title36", "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FeEO4sy%2Fbtq69SgK8L3%2FttCUxYHx9aPNebNwkPcI21%2Fimg.png", "https://philosopher-chan.tistory.com/1246?category=941578"))


//        items.add(ContentModel("title1", "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FblYPPY%2Fbtq66v0S4wu%2FRmuhpkXUO4FOcrlOmVG4G1%2Fimg.png", "https://philosopher-chan.tistory.com/1235?category=941578"))
//        items.add(ContentModel("title2", "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FznKK4%2Fbtq665AUWem%2FRUawPn5Wwb4cQ8BetEwN40%2Fimg.png", "https://philosopher-chan.tistory.com/1236?category=941578"))
//        items.add(ContentModel("title3", "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fbtig9C%2Fbtq65UGxyWI%2FPRBIGUKJ4rjMkI7KTGrxtK%2Fimg.png", "https://philosopher-chan.tistory.com/1237?category=941578"))
//        items.add(ContentModel("title4", "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FcOYyBM%2Fbtq67Or43WW%2F17lZ3tKajnNwGPSCLtfnE1%2Fimg.png", "https://philosopher-chan.tistory.com/1238?category=941578"))
//        items.add(ContentModel("title5", "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fekn5wI%2Fbtq66UlN4bC%2F8NEzlyot7HT4PcjbdYAINk%2Fimg.png", "https://philosopher-chan.tistory.com/1239?category=941578"))
//        items.add(ContentModel("title6", "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2F123LP%2Fbtq65qy4hAd%2F6dgpC13wgrdsnHigepoVT1%2Fimg.png", "https://philosopher-chan.tistory.com/1240?category=941578"))
//        items.add(ContentModel("title7", "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fl2KC3%2Fbtq64lkUJIN%2FeSwUPyQOddzcj6OAkPKZuk%2Fimg.png","https://philosopher-chan.tistory.com/1241?category=941578"))
//        items.add(ContentModel("title8", "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FmBh5u%2Fbtq651yYxop%2FX3idRXeJ0VQEoT1d6Hln30%2Fimg.png", "https://philosopher-chan.tistory.com/1242?category=941578"))
//        items.add(ContentModel("title9", "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FlOnja%2Fbtq69Tmp7X4%2FoUvdIEteFbq4Z0ZtgCd4p0%2Fimg.png", "https://philosopher-chan.tistory.com/1243?category=941578"))
//        items.add(ContentModel("title10", "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FNNrYR%2Fbtq64wsW5VN%2FqIaAsfmFtcvh4Bketug9m0%2Fimg.png", "https://philosopher-chan.tistory.com/1244?category=941578"))
//        items.add(ContentModel("title11", "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FK917N%2Fbtq64SP5gxj%2FNzsfNAykamW7qv1hdusp1K%2Fimg.png", "https://philosopher-chan.tistory.com/1245?category=941578"))
//        items.add(ContentModel("title12", "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FeEO4sy%2Fbtq69SgK8L3%2FttCUxYHx9aPNebNwkPcI21%2Fimg.png", "https://philosopher-chan.tistory.com/1246?category=941578"))
//        items.add(ContentModel("title13", "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FbdIKDG%2Fbtq64M96JFa%2FKcJiYgKuwKuP3fIyviXm90%2Fimg.png", "https://philosopher-chan.tistory.com/1247?category=941578"))
//        items.add(ContentModel("title14", "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FFtY3t%2Fbtq65q6P4Zr%2FWe64GM8KzHAlGE3xQ2nDjk%2Fimg.png", "https://philosopher-chan.tistory.com/1248?category=941578"))
//        items.add(ContentModel("title15", "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FOtaMq%2Fbtq67OMpk4W%2FH1cd0mda3n2wNWgVL9Dqy0%2Fimg.png", "https://philosopher-chan.tistory.com/1249?category=941578"))

    }


    // 북마크 데이터 가져오는 함수
    private fun getBookmarkData(){

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                bookmarkIdList.clear()

                for (dataModel in dataSnapshot.children){

                    bookmarkIdList.add(dataModel.key.toString())
                }
                Log.d("ContentListActivity", bookmarkIdList.toString())
                rvAdapter.notifyDataSetChanged()

            }



            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("ContentListActivity", "loadPost:onCancelled", databaseError.toException())
            }
        }
        //데이터받아옴
        FBRef.bookmarkRef.child(FBAuth.getUid()).addValueEventListener(postListener)

    }





}