package com.example.plan_me.ui.mestory

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.plan_me.MainActivity
import com.example.plan_me.R
import com.example.plan_me.databinding.ActivityMestoryBinding
import com.example.plan_me.ui.CircleTransform
import com.example.plan_me.ui.dialog.DialogAddFragment
import com.example.plan_me.ui.setting.SettingActivity
import com.example.plan_me.ui.timer.TimerFocusActivity
import com.squareup.picasso.Picasso

class MestoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMestoryBinding

    private var isFabOpen = false
    private lateinit var dialogAdd : DialogAddFragment
    private lateinit var drawerView: View
    private lateinit var drawerCancel: ImageView
    private var userName: String? = ""
    private var userImg: String? = ""

    private var fab_open: Animation? = null
    private var fab_close: Animation? = null

    val mestoryRVAdapter = MestoryRVAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMestoryBinding.inflate(layoutInflater)

        // RecyclerView 어댑터 설정
        binding.mestoryCategoryRv.layoutManager = LinearLayoutManager(this)
        // RecyclerView 레이아웃 매니저 설정
        binding.mestoryCategoryRv.adapter = mestoryRVAdapter
        setContentView(binding.root)

        fab_open = AnimationUtils.loadAnimation(this, R.anim.fab_open)
        fab_close = AnimationUtils.loadAnimation(this, R.anim.fab_close)

        getData()

        binding.mestoryProfileNameTv.text = userName
        if (userImg != "https://k.kakaocdn.net/dn/1G9kp/btsAot8liOn/8CWudi3uy07rvFNUkk3ER0/img_640x640.jpg" && userImg != "null") {
            Picasso.get().load(userImg).transform(CircleTransform())
                .into(binding.mestoryProfileIv)
        }

        clickListener()
    }

    private fun clickListener() {
        binding.mestoryFabMenuBtn.setOnClickListener {
            Log.d("fab: mestory","mestory")
            toggleFab()
        }
        binding.mestoryFabPlannerBtn.setOnClickListener {
            Log.d("fab: mestory", "mestory -> planner")
            switchActivity(MainActivity())
        }
        binding.mestoryFabTimerBtn.setOnClickListener {
            Log.d("fab: mestory", "mestory -> timer(focus)")
            switchActivity(TimerFocusActivity())
        }
        binding.mestoryFabSettingBtn.setOnClickListener {
            Log.d("fab: mestory", "mestory -> setting")
            switchActivity(SettingActivity())
        }
    }

    private fun switchActivity(activity: Activity) {
        val intent = Intent(this, activity::class.java)
        startActivity(intent)
    }

    private fun toggleFab() {
        isFabOpen = if (isFabOpen) {
            binding.mestoryFabPlannerBtn.startAnimation(fab_close)
            binding.mestoryFabTimerBtn.startAnimation(fab_close)
            binding.mestoryFabSettingBtn.startAnimation(fab_close)
            false
        } else {
            binding.mestoryFabPlannerBtn.startAnimation(fab_open)
            binding.mestoryFabTimerBtn.startAnimation(fab_open)
            binding.mestoryFabSettingBtn.startAnimation(fab_open)

            binding.mestoryFabPlannerBtn.visibility = View.VISIBLE
            binding.mestoryFabTimerBtn.visibility = View.VISIBLE
            binding.mestoryFabSettingBtn.visibility = View.VISIBLE

            binding.mestoryFabPlannerBtn.setClickable(true)
            binding.mestoryFabTimerBtn.setClickable(true)
            binding.mestoryFabSettingBtn.setClickable(true)
            true
        }
    }

    private fun getData() {
        // 데이터 읽어오기
        val sharedPreferences: SharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE)
        userName = sharedPreferences.getString("userName", userName)
        userImg = sharedPreferences.getString("userImg", userImg)
    }
}
