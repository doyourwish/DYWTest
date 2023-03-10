package com.example.navigationapplication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class HomeFragment : Fragment() {
    lateinit var  mySharedPrefs : MySharedPrefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        initializeResource(view)

        return view
    }

    fun onClick(view: View?) {
        // ①インテントの作成
        val intent = Intent(this, SubActivity::class.java)

        // ②遷移先画面の起動
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    fun initializeResource(view : View){
//        SharedPreferenceのインスタンス生成
        mySharedPrefs = MySharedPrefs(context!!)}

}
