package com.example.mysololife.contentsList

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mysololife.R
import com.example.mysololife.utils.FBAuth
import com.example.mysololife.utils.FBRef

class BookmarkRVAdapter(val context : Context, val items : ArrayList<ContentModel>, val keyList : ArrayList<String>, val bookmarkIdList : MutableList<String> )
    : RecyclerView.Adapter<BookmarkRVAdapter.ViewHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkRVAdapter.ViewHolder {
        // 아이템 레이아웃 연결
        val v = LayoutInflater.from(parent.context).inflate(R.layout.content_rv_item, parent,false)

        Log.d("ContentRVAdapter", keyList.toString())
        Log.d("ContentRVAdapter", bookmarkIdList.toString())
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: BookmarkRVAdapter.ViewHolder, position: Int) {

        // 아이템 보내줌
        holder.bindItems(items[position], keyList[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(item : ContentModel, key : String ){

            // 리사이클러뷰 아이템 클릭시
            itemView.setOnClickListener {
                val intent = Intent(context, ContentShowActivity::class.java)
                //데이터도 함께 넘겨줌
                intent.putExtra("url", item.webUrl)
                itemView.context.startActivity(intent)

            }


            // content_rv_item에서 텍스트, 이미지,북마크를 찾아옴(연결)
            val contentTitle = itemView.findViewById<TextView>(R.id.textArea)
            val imageViewArea = itemView.findViewById<ImageView>(R.id.imageArea)
            val bookmarkArea = itemView.findViewById<ImageView>(R.id.bookmarkArea)


            // 북마크 클릭했을때 색칠되게
            // key리스트들이 bookmarkIdList에 포함되어있는지
            if(bookmarkIdList.contains(key)){
                bookmarkArea.setImageResource(R.drawable.bookmark_color)
            }else{
                bookmarkArea.setImageResource(R.drawable.bookmark_white)
            }


            // ContentModel에서 받아온 하나하나의 아이템들을 가져와서 title을 넣어주겠다
            contentTitle.text = item.title

            // 이미지 불러오기
            Glide.with(context)
                .load(item.imageUrl)
                .into(imageViewArea)    // imageUrl을 imageViewArea변수에 집어넣겠다


        }
    }


}