package com.example.mysololife.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.mysololife.R
import com.example.mysololife.contentsList.ContentListActivity
import com.example.mysololife.databinding.FragmentTipBinding


class TipFragment : Fragment() {

    private lateinit var binding : FragmentTipBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = DataBindingUtil.inflate(inflater,R.layout.fragment_tip,container,false)


        // category1 눌렀을때 ContentListActivity로 이동
        binding.category1.setOnClickListener {

            val intent = Intent(context, ContentListActivity::class.java)
            // category1 눌렀을때 category정보도 같이 넘겨줌
            intent.putExtra("category","category1")
            startActivity(intent)
        }

        // category2 눌렀을때 ContentListActivity로 이동
        binding.category2.setOnClickListener {

            val intent = Intent(context, ContentListActivity::class.java)
            // category1 눌렀을때 category정보도 같이 넘겨줌
            intent.putExtra("category","category2")
            startActivity(intent)
        }

        // category3 눌렀을때 ContentListActivity로 이동
        binding.category3.setOnClickListener {

            val intent = Intent(context, ContentListActivity::class.java)
            // category1 눌렀을때 category정보도 같이 넘겨줌
            intent.putExtra("category","category3")
            startActivity(intent)
        }

        // category4 눌렀을때 ContentListActivity로 이동
        binding.category4.setOnClickListener {

            val intent = Intent(context, ContentListActivity::class.java)
            // category1 눌렀을때 category정보도 같이 넘겨줌
            intent.putExtra("category","category4")
            startActivity(intent)
        }

        // category5 눌렀을때 ContentListActivity로 이동
        binding.category5.setOnClickListener {

            val intent = Intent(context, ContentListActivity::class.java)
            // category1 눌렀을때 category정보도 같이 넘겨줌
            intent.putExtra("category","category5")
            startActivity(intent)
        }

        // category6 눌렀을때 ContentListActivity로 이동
        binding.category6.setOnClickListener {

            val intent = Intent(context, ContentListActivity::class.java)
            // category1 눌렀을때 category정보도 같이 넘겨줌
            intent.putExtra("category","category6")
            startActivity(intent)
        }

        // category7 눌렀을때 ContentListActivity로 이동
        binding.category7.setOnClickListener {

            val intent = Intent(context, ContentListActivity::class.java)
            // category1 눌렀을때 category정보도 같이 넘겨줌
            intent.putExtra("category","category7")
            startActivity(intent)
        }

        // category8 눌렀을때 ContentListActivity로 이동
        binding.category8.setOnClickListener {

            val intent = Intent(context, ContentListActivity::class.java)
            // category1 눌렀을때 category정보도 같이 넘겨줌
            intent.putExtra("category","category8")
            startActivity(intent)
        }


        //하단 네비게이션바
        binding.homeTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_tipFragment_to_homeFragment)
        }

        binding.talkTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_tipFragment_to_talkFragment)

        }

        binding.bookmarkTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_tipFragment_to_bookmarkFragment)

        }

        binding.storeTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_tipFragment_to_storeFragment)

        }

        return binding.root
    }


}