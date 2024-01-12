package com.example.plan_me.ui.timer

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.example.plan_me.MainActivity
import com.example.plan_me.R
import com.example.plan_me.databinding.ActivityTimerFocusBinding
import com.example.plan_me.ui.dialog.DialogAddFragment
import com.example.plan_me.ui.mestory.MestoryActivity

class TimerFocusActivity: AppCompatActivity() {
    private lateinit var binding: ActivityTimerFocusBinding

    private var isFabOpen = false
    private lateinit var dialogAdd : DialogAddFragment
    private lateinit var drawerView: View
    private lateinit var drawerCancel: ImageView

    private var fab_open: Animation? = null
    private var fab_close: Animation? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimerFocusBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fab_open = AnimationUtils.loadAnimation(this, R.anim.fab_open)
        fab_close = AnimationUtils.loadAnimation(this, R.anim.fab_close)
        drawerView = findViewById(R.id.drawer_layout)
        drawerCancel = findViewById(R.id.drawer_cancel)

        clickListener()
    }
    override fun onBackPressed() {
        if (binding.timerFocusDrawerLayout.isDrawerOpen(GravityCompat.START)){
            binding.timerFocusDrawerLayout.closeDrawers()
        }
        else {
            super.onBackPressed()
        }
    }

    private fun clickListener() {
        binding.timerFocusFabMenuBtn.setOnClickListener {
            Log.d("fab: timer-break","timer-break")
            toggleFab()
        }
        binding.timerFocusFabMestoryBtn.setOnClickListener {
            Log.d("fab: timer-break", "timer-break -> mestory")
            switchActivity(MestoryActivity())
        }
        binding.timerFocusFabPlannerBtn.setOnClickListener {
            Log.d("fab: timer-break", "timer-break -> mestory")
            switchActivity(MainActivity())
        }
        binding.timerFocusFabSettingBtn.setOnClickListener {
            Log.d("fab: timer-break", "timer-break -> mestory")
        }
        binding.timerFocusFabAddBtn.setOnClickListener {
            Log.d("fab: timer-break", "timer-break -> mestory")
            dialogAdd = DialogAddFragment(this)
            dialogAdd.show()
        }
        binding.timerFocusMenuBtn.setOnClickListener{
            Log.d("menu: timer-break", "Open menu")
            binding.timerFocusDrawerLayout.openDrawer(drawerView!!)
        }
        drawerCancel.setOnClickListener {
            Log.d("menu: timer-break", "Close menu")
            binding.timerFocusDrawerLayout.closeDrawers()
        }
    }

    private fun switchActivity(activity: Activity) {
        val intent = Intent(this, activity::class.java)
        startActivity(intent)
    }

    private fun toggleFab() {
        isFabOpen = if (isFabOpen) {
            binding.timerFocusFabMestoryBtn.startAnimation(fab_close)
            binding.timerFocusFabPlannerBtn.startAnimation(fab_close)
            binding.timerFocusFabSettingBtn.startAnimation(fab_close)
            binding.timerFocusFabAddBtn.startAnimation(fab_close)
            false
        } else {
            binding.timerFocusFabMestoryBtn.startAnimation(fab_open)
            binding.timerFocusFabPlannerBtn.startAnimation(fab_open)
            binding.timerFocusFabSettingBtn.startAnimation(fab_open)
            binding.timerFocusFabAddBtn.startAnimation(fab_open)

            binding.timerFocusFabMestoryBtn.visibility = View.VISIBLE
            binding.timerFocusFabPlannerBtn.visibility = View.VISIBLE
            binding.timerFocusFabSettingBtn.visibility = View.VISIBLE
            binding.timerFocusFabAddBtn.visibility = View.VISIBLE

            binding.timerFocusFabMestoryBtn.setClickable(true)
            binding.timerFocusFabPlannerBtn.setClickable(true)
            binding.timerFocusFabSettingBtn.setClickable(true)
            binding.timerFocusFabAddBtn.setClickable(true)
            true
        }
    }
}