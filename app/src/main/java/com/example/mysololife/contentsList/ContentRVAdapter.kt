package com.example.mysololife.contentsList

import android.content.Context
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mysololife.R

class ContentRVAdapter(val context : Context, val items : ArrayList<ContentModel>) : RecyclerView.Adapter<ContentRVAdapter.ViewHolder>(){


    interface ItemClick{
        fun onClick(view : View, position: Int)
    }

    var itemClick : ItemClick? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentRVAdapter.ViewHolder {
        // 아이템 레이아웃 연결
        val v = LayoutInflater.from(parent.context).inflate(R.layout.content_rv_item, parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ContentRVAdapter.ViewHolder, position: Int) {

        // 리사이클러뷰에 있는 아이템을 클릭하면 이동하도록 하는 이벤트 처리
        if(itemClick != null){
            holder.itemView.setOnClickListener {v ->
                itemClick?.onClick(v, position)

            }
        }

        holder.bindItems(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(item : ContentModel){

            // content_rv_item에서 텍스트, 이미지를 찾아옴
            val contentTitle = itemView.findViewById<TextView>(R.id.textArea)
            val imageViewArea = itemView.findViewById<ImageView>(R.id.imageArea)

            // ContentModel에서 받아온 하나하나의 아이템들을 가져와서 title을 넣어주겠다
            contentTitle.text = item.title

            // 이미지 불러오기
            Glide.with(context)
                .load(item.imageUrl)
                .into(imageViewArea)    // imageUrl을 imageViewArea변수에 집어넣겠다


        }
    }
}