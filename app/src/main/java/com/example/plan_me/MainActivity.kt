package com.example.plan_me

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.GravityCompat
import com.example.plan_me.databinding.ActivityMainBinding
import com.example.plan_me.ui.add.ScheduleAddActivity
import com.example.plan_me.ui.all.AllFragment
import com.example.plan_me.ui.dialog.DialogAddFragment
import com.example.plan_me.ui.mestory.MestoryActivity
import com.example.plan_me.ui.planner.PlannerFragment
import com.example.plan_me.ui.setting.SettingActivity
import com.example.plan_me.ui.timer.TimerFocusActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var isFabOpen = false
    private lateinit var drawerView:View
    private lateinit var drawerAdd: TextView

    private var fab_open: Animation? = null
    private var fab_close: Animation? = null

    private var isHome : Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        overridePendingTransition(R.anim.screen_start, R.anim.screen_none)

        fab_open = AnimationUtils.loadAnimation(this, R.anim.fab_open)
        fab_close = AnimationUtils.loadAnimation(this, R.anim.fab_close)
        drawerView = findViewById(R.id.drawer_layout)
        drawerAdd = findViewById(R.id.drawer_add_tv)


        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, PlannerFragment())
            .commitAllowingStateLoss()

        clickListener()
    }

    override fun onBackPressed() {
        if (binding.mainDrawerLayout.isDrawerOpen(GravityCompat.START)){
            binding.mainDrawerLayout.closeDrawers()
        }
        else {
            super.onBackPressed()
            overridePendingTransition(R.anim.screen_none, R.anim.screen_exit)
        }
    }
    private fun clickListener() {
        binding.mainFabMenuBtn.setOnClickListener {
            Log.d("fab","fab")
            toggleFab()
        }
        binding.mainFabMestoryBtn.setOnClickListener {
            Log.d("mestory", "mestory")
            switchActivity(MestoryActivity())
            overridePendingTransition(R.anim.screen_none, R.anim.screen_exit)
        }
        binding.mainFabTimerBtn.setOnClickListener {
            switchActivity(TimerFocusActivity())
            overridePendingTransition(R.anim.screen_none, R.anim.screen_exit)
        }
        binding.mainFabSettingBtn.setOnClickListener {
            switchActivity(SettingActivity())
            overridePendingTransition(R.anim.screen_none, R.anim.screen_exit)
        }
        binding.mainFabAddBtn.setOnClickListener {
            switchActivity(ScheduleAddActivity())
            overridePendingTransition(R.anim.screen_none, R.anim.screen_exit)
        }
        binding.mainMenu.setOnClickListener{
            binding.mainDrawerLayout.openDrawer(drawerView!!)
        }
        drawerAdd.setOnClickListener {
            showDialog(DialogAddFragment(this@MainActivity))
        }
        binding.mainAllBtn.setOnClickListener{
            if (isHome) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_frm, AllFragment())
                    .commitAllowingStateLoss()
                binding.mainAllBtn.setBackgroundResource(R.drawable.planner_btn_planner)
                binding.mainAllBtn.text = "HOME"
                binding.mainAllBtn.setTextColor(Color.WHITE)
                isHome=false
            }else {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_frm, PlannerFragment())
                    .commitAllowingStateLoss()
                binding.mainAllBtn.setBackgroundResource(R.drawable.planner_btn_all)
                binding.mainAllBtn.text = "ALL"
                binding.mainAllBtn.setTextColor(Color.BLACK)
                isHome=true
            }
        }
    }

    private fun showDialog(dialog: Dialog) {
        dialog.show()
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