package com.example.plan_me.ui.planner

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.plan_me.R
import com.example.plan_me.databinding.ActivityPlannerBinding
import com.example.plan_me.ui.timer.TimerFocusActivity
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

    private var ignoreCheckChange = false

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
        val intent = Intent(this@PlannerActivity, TimerFocusActivity::class.java)
        startActivity(intent)
    }

    private fun goSettingActivity() {

    }

    private fun addPopup() {
        val mDialogView = LayoutInflater.from(this@PlannerActivity).inflate(R.layout.fragment_dialog_add_category, null)
        val mBuilder = AlertDialog.Builder(this@PlannerActivity)
            .setView(mDialogView)

        val mAlertDialog = mBuilder.show()

        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(mAlertDialog.window?.attributes)
        layoutParams.gravity = Gravity.CENTER
        mAlertDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


        mAlertDialog.setCancelable(true)

        val radioGroup1 = mAlertDialog.findViewById<RadioGroup>(R.id.color_group1)
        val radioGroup2 = mAlertDialog.findViewById<RadioGroup>(R.id.color_group2)

        // 라디오 그룹1 체크 상태 변경 리스너
        radioGroup1!!.setOnCheckedChangeListener { group, checkedId ->
            if (!ignoreCheckChange) {
                ignoreCheckChange = true
                // 라디오 그룹2의 체크 여부 확인
                val isCheckedInGroup2 = radioGroup2!!.checkedRadioButtonId != -1
                if (isCheckedInGroup2) {
                    // 라디오 그룹2에 체크된 버튼이 있는 경우
                    radioGroup2!!.clearCheck()
                }
                ignoreCheckChange = false
            }
        }

        // 라디오 그룹2 체크 상태 변경 리스너
        radioGroup2!!.setOnCheckedChangeListener { group, checkedId ->
            if (!ignoreCheckChange) {
                ignoreCheckChange = true
                // 라디오 그룹1의 체크 여부 확인
                val isCheckedInGroup1 = radioGroup1!!.checkedRadioButtonId != -1
                if (isCheckedInGroup1) {
                    // 라디오 그룹1에 체크된 버튼이 있는 경우
                    radioGroup1!!.clearCheck()
                }
                ignoreCheckChange = false
            }
        }

        val okBtn = mDialogView.findViewById<Button>(R.id.add_category_save_btn)
        okBtn.setOnClickListener {
            //임시설정
            Toast.makeText(this@PlannerActivity, "저장되었습니다.",Toast.LENGTH_SHORT).show()
        }

        val noBtn = mDialogView.findViewById<Button>(R.id.add_category_cancel_btn)
        noBtn.setOnClickListener {
            mAlertDialog.dismiss()
        }
    }
}