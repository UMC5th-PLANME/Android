package com.example.plan_me

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.plan_me.databinding.ActivityMainBinding
import com.example.plan_me.ui.dialog.DialogAddFragment
import com.example.plan_me.ui.dialog.DialogAlarmFragment
import com.example.plan_me.ui.dialog.DialogRepeatFragment
import com.example.plan_me.ui.mestory.MestoryActivity
import com.example.plan_me.ui.planner.PlannerFragment
import com.example.plan_me.ui.setting.SettingActivity
import com.example.plan_me.ui.timer.TimerFocusActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        fab_open = AnimationUtils.loadAnimation(this, R.anim.fab_open)
        fab_close = AnimationUtils.loadAnimation(this, R.anim.fab_close)
        drawerView = findViewById(R.id.drawer_layout)
        drawerCancel = findViewById(R.id.drawer_cancel)

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
        }
        binding.mainFabTimerBtn.setOnClickListener {
            switchActivity(TimerFocusActivity())
        }
        binding.mainFabSettingBtn.setOnClickListener {
            switchActivity(SettingActivity())
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