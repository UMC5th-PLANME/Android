package com.example.plan_me.ui.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.example.plan_me.databinding.FragmentDialogCautionResetTimeBinding
import com.example.plan_me.data.local.database.SettingDatabase
import com.example.plan_me.data.local.database.TimeDatabase
import com.example.plan_me.ui.timer.ResetConfirmedListener

class DialogCautionResetTimeFragment(context : Context, resetConfirmedListener: ResetConfirmedListener): Dialog(context) {
    lateinit var binding: FragmentDialogCautionResetTimeBinding

    private val timeDB = TimeDatabase.getInstance(context)!!
    private val settingTimeDB = SettingDatabase.getInstance(context)!!

    private var resetConfirmedListener: ResetConfirmedListener

    init {
        this.resetConfirmedListener = resetConfirmedListener
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentDialogCautionResetTimeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        clickListener()
    }

    private fun clickListener() {
        binding.timerDialogCautionSaveTimeTv.setOnClickListener {
            // 지금 현재까지 진행 상황을 mestory에 기록

            // timeTable set:2 시간 -> 0:00:00 으로 바꾸기
            timeDB.timeDao().updateTime(0,0,1,2)

            // SettingTable set:2 시간 ->
            settingTimeDB.SettingTimeDao().updateTime(0,0,0,0,2)
            resetConfirmedListener.onResetConfirmed(true)

            dismiss()
        }

        binding.timerDialogCautionSaveCancelTimeTv.setOnClickListener {
            // mestory 에 기록 X
            resetConfirmedListener.onResetConfirmed(false)
            dismiss()
        }
    }

}