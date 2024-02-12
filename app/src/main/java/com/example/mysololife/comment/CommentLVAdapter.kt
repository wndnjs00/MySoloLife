package com.example.mysololife.comment

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView
import com.example.mysololife.R
import com.example.mysololife.utils.FBAuth

class CommentLVAdapter(val commentList : MutableList<CommentModel>) : BaseAdapter(){
    override fun getCount(): Int {
        return commentList.size
    }

    override fun getItem(position: Int): Any {
        return commentList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        // 아이템뷰를 가져와서 연결
        var view = convertView

        if(view == null){
            view = LayoutInflater.from(parent?.context).inflate(R.layout.comment_list_item, parent,false)
        }



        // 내가 작성한 댓글값이 commentArea1에 적용되게
        val title = view?.findViewById<TextView>(R.id.commentArea2)
        title!!.text = commentList[position].commentTitle


        // 내가 작성한 time값이 timeArea1에 적용되게
        val time = view?.findViewById<TextView>(R.id.timeArea2)
        time!!.text = commentList[position].commentCreatedTime



        return view!!

    }


}