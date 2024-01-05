package com.example.plan_me

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.plan_me.databinding.ActivityPlannerBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton


class PlannerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlannerBinding
    private var fab_planner: FloatingActionButton? = null
    private var fab_mestory: FloatingActionButton? = null
    private var fab_timer: FloatingActionButton? = null
    private var fab_setting: FloatingActionButton? = null
    private var fab_add: FloatingActionButton? = null

    private var fab_open: Animation? = null
    private var fab_close: Animation? = null

    private var isFabOpen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlannerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fab_open = AnimationUtils.loadAnimation(this@PlannerActivity, R.anim.fab_open)
        fab_close = AnimationUtils.loadAnimation(this@PlannerActivity, R.anim.fab_close)

        fab_planner = binding.plannerMenuBtn
        fab_mestory = binding.plannerMestoryBtn
        fab_timer = binding.plannerTimerBtn
        fab_setting = binding.plannerSettingBtn
        fab_add = binding.plannerAddBtn

        fab_planner!!.setOnClickListener {
            toggleFab()
        }
        fab_mestory!!.setOnClickListener {
            goMestoryActivity()
        }
        fab_timer!!.setOnClickListener {
            goTimerActivity()
        }
        fab_setting!!.setOnClickListener {
            goSettingActivity()
        }
        fab_add!!.setOnClickListener {
            addPopup()
        }
    }

    private fun toggleFab() {
        isFabOpen = if (isFabOpen) {
            fab_mestory!!.startAnimation(fab_close)
            fab_timer!!.startAnimation(fab_close)
            fab_setting!!.startAnimation(fab_close)
            fab_add!!.startAnimation(fab_close)
            false
        } else {
            fab_mestory!!.startAnimation(fab_open)
            fab_timer!!.startAnimation(fab_open)
            fab_setting!!.startAnimation(fab_open)
            fab_add!!.startAnimation(fab_open)

            fab_mestory!!.visibility = View.VISIBLE
            fab_timer!!.visibility = View.VISIBLE
            fab_setting!!.visibility = View.VISIBLE
            fab_add!!.visibility = View.VISIBLE

            fab_mestory!!.setClickable(true)
            fab_timer!!.setClickable(true)
            fab_setting!!.setClickable(true)
            fab_add!!.setClickable(true)
            true
        }
    }

    private fun goMestoryActivity() {

    }

    private fun goTimerActivity() {
        val intent = Intent(this@PlannerActivity, TimerActivity::class.java)
        startActivity(intent)
    }

    private fun goSettingActivity() {

    }

    private fun addPopup() {

    }
}