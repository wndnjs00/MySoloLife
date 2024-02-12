package com.example.mysololife.board

import android.graphics.Color
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView
import com.example.mysololife.R
import com.example.mysololife.utils.FBAuth


// BoardModel 데이터모델을 받아옴
class BoadListLVAdapter(val boardList: MutableList<BoardModel>) : BaseAdapter() {
    override fun getCount(): Int {
        // boardList의 사이즈만큼 리턴
        return boardList.size
    }

    override fun getItem(position: Int): Any {
        // boardList를 클릭한값 리턴
        return boardList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        // 아이템뷰를 가져와서 연결
        var view = convertView

        view = LayoutInflater.from(parent?.context).inflate(R.layout.board_list_item, parent,false)



        // 내가 작성한 title값이 titleArea1에 적용되게
        val title = view?.findViewById<TextView>(R.id.titleArea1)
        title!!.text = boardList[position].title

        // 내가 작성한 content값이 contentArea1에 적용되게
        val content = view?.findViewById<TextView>(R.id.contentArea1)
        content!!.text = boardList[position].content

        // 내가 작성한 time값이 timeArea1에 적용되게
        val time = view?.findViewById<TextView>(R.id.timeArea1)
        time!!.text = boardList[position].time


        val itemLinearLayoutView = view?.findViewById<LinearLayout>(R.id.itemView)
        // 내가 작성한 글의 백그라운드 색상바뀌게
        // 글작성한 uid와 현재나의uid가 같다면
        if (boardList[position].uid.equals(FBAuth.getUid())){
            itemLinearLayoutView?.setBackgroundColor(Color.parseColor("#ffa500"))
        }

        return view!!
    }
}