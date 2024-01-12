package com.example.plan_me

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.core.view.GravityCompat
import com.example.plan_me.databinding.ActivityMainBinding
import com.example.plan_me.ui.all.AllFragment
import com.example.plan_me.ui.dialog.DialogAddFragment
import com.example.plan_me.ui.home.HomeFragment
import com.example.plan_me.ui.setting.SettingActivity
import com.example.plan_me.ui.timer.TimerFocusActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var isFabOpen = false
    private lateinit var dialogAdd : DialogAddFragment
    private lateinit var drawerView:View
    private lateinit var drawerCancel:ImageView

    private var fab_open: Animation? = null
    private var fab_close: Animation? = null

    private var userName: String? = ""
    private var userImg: String? = ""

    private var isHome : Boolean = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        fab_open = AnimationUtils.loadAnimation(this, R.anim.fab_open)
        fab_close = AnimationUtils.loadAnimation(this, R.anim.fab_close)
        drawerView = findViewById(R.id.drawer_layout)
        drawerCancel = findViewById(R.id.drawer_cancel)

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, HomeFragment())
            .commitAllowingStateLoss()

        clickListener()
    }

    override fun onBackPressed() {
        if (binding.mainDrawerLayout.isDrawerOpen(GravityCompat.START)){
            binding.mainDrawerLayout.closeDrawers()
        }
        else {
            super.onBackPressed()
        }
    }
    private fun clickListener() {
        binding.mainFabMenuBtn.setOnClickListener {
            Log.d("fab","fab")
            toggleFab()
        }
        binding.mainFabMestoryBtn.setOnClickListener {
        }
        binding.mainFabTimerBtn.setOnClickListener {
            switchActivity(TimerFocusActivity())
        }
        binding.mainFabSettingBtn.setOnClickListener {
            userName = intent.getStringExtra("userName")
            userImg = intent.getStringExtra("userImg")
            val settingIntent = Intent(this@MainActivity, SettingActivity::class.java)
            settingIntent.putExtra("userName", userName)
            settingIntent.putExtra("userImg", userImg)
            startActivity(settingIntent)
        }
        binding.mainFabAddBtn.setOnClickListener {
            dialogAdd = DialogAddFragment(this)
            dialogAdd.show()
        }
        binding.mainMenu.setOnClickListener{
            binding.mainDrawerLayout.openDrawer(drawerView!!)
        }
        drawerCancel.setOnClickListener {
            binding.mainDrawerLayout.closeDrawers()
        }
        binding.mainAllBtn.setOnClickListener{
            if (isHome) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_frm, AllFragment())
                    .commitAllowingStateLoss()
                binding.mainAllBtn.setBackgroundResource(R.drawable.planner_btn_home)
                binding.mainAllBtn.text = "HOME"
                binding.mainAllBtn.setTextColor(Color.WHITE)
                isHome=false
            }else {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_frm, HomeFragment())
                    .commitAllowingStateLoss()
                binding.mainAllBtn.setBackgroundResource(R.drawable.planner_btn_all)
                binding.mainAllBtn.text = "ALL"
                binding.mainAllBtn.setTextColor(Color.BLACK)
                isHome=true
            }
        }
    }
    private fun switchActivity(activity: Activity) {
        val intent = Intent(this, activity::class.java)
        startActivity(intent)
    }

    private fun toggleFab() {
        isFabOpen = if (isFabOpen) {
            binding.mainFabMestoryBtn.startAnimation(fab_close)
            binding.mainFabTimerBtn.startAnimation(fab_close)
            binding.mainFabSettingBtn.startAnimation(fab_close)
            binding.mainFabAddBtn.startAnimation(fab_close)
            false
        } else {
            binding.mainFabMestoryBtn.startAnimation(fab_open)
            binding.mainFabTimerBtn.startAnimation(fab_open)
            binding.mainFabSettingBtn.startAnimation(fab_open)
            binding.mainFabAddBtn.startAnimation(fab_open)

            binding.mainFabMestoryBtn.visibility = View.VISIBLE
            binding.mainFabTimerBtn.visibility = View.VISIBLE
            binding.mainFabSettingBtn.visibility = View.VISIBLE
            binding.mainFabAddBtn.visibility = View.VISIBLE

            binding.mainFabMestoryBtn.setClickable(true)
            binding.mainFabTimerBtn.setClickable(true)
            binding.mainFabSettingBtn.setClickable(true)
            binding.mainFabAddBtn.setClickable(true)
            true
        }
    }
}