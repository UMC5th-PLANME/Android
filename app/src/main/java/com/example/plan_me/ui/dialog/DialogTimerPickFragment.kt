package com.example.plan_me.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import com.example.plan_me.databinding.FragmentDialogTimerpickBinding
import com.example.plan_me.ui.timer.TimerFragment

class DialogTimerPickFragment(timerFragment: TimerFragment, context : Context): Dialog(context) {
    private lateinit var binding: FragmentDialogTimerpickBinding

    fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        binding = FragmentDialogTimerpickBinding.inflate(layoutInflater)

        initSetting()

        return binding.root
    }

    private fun initSetting() {

        // FocusTime
        val focusHour: NumberPicker = binding.timerFocusNumberPickerHour
        val focusMin: NumberPicker = binding.timerFocusNumberPickerMin
        // 순환 막기
        focusHour.wrapSelectorWheel = false
        focusMin.wrapSelectorWheel = false
        // editText 설정 해제
        focusHour.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        focusMin.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        // 최소값 설정
        focusHour.minValue = 0
        focusMin.minValue = 0
        // 최대값 설정
        focusHour.maxValue = 2
        focusMin.maxValue = 5
        // 보여질 값 설정 (string)
        focusHour.displayedValues = arrayOf("0", "1", "2")
        focusMin.displayedValues = arrayOf("0", "10", "20", "30", "40", "50")


        // BreakTime
        val breakMin: NumberPicker = binding.timerBreakNumberPickerMin
        // 순환 막기
        breakMin.wrapSelectorWheel = false
        // editText 설정 해제
        breakMin.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        // 최소값 설정
        breakMin.minValue = 0
        // 최대값 설정
        breakMin.maxValue = 8
        // 보여질 값 설정 (string)
        breakMin.displayedValues = arrayOf("10", "15", "20", "25", "30", "35", "40", "45", "50")


        // Repeat Count -> 반복 횟수
        val count: NumberPicker = binding.timerRepeatNumberPickerTime
        // 순환 막기
        count.wrapSelectorWheel = false
        // editText 설정 해제
        count.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        // 최소값 설정
        count.minValue = 0
        // 최대값 설정
        count.maxValue = 8
        // 값 설정 (string)
        count.displayedValues = arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9")
    }

    fun clickListener() {
        // 확인 버튼 클릭 리스너 설정
        binding.dialogTimerpickConfirm.setOnClickListener {
            // TimerFragment에 반영하는 코드 호출
            // updateTimerFragment()
            // 다이얼로그 닫기
            dismiss()
        }

        binding.dialogTimerpickCancel.setOnClickListener {
            dismiss()
        }
    }

//    private fun updateTimerFragment() {
//        val timerFragment = TimerFragment
//
//        // 집중 시간 설정
//        val focusTime = getFocusTime()
//        timerFragment.setFocusTime(focusTime)
//
//        // 종료 시간 설정
//        val breakTime = getBreakTime()
//        timerFragment.setBreakTime(breakTime)
//
//        // 반복 횟수 설정
//        val repeatCount = getRepeatCount()
//    }

    fun getFocusTime(): Int {
        val focusHour = binding.timerFocusNumberPickerHour.value
        val focusMin = binding.timerFocusNumberPickerMin.value
        return focusHour * 60 + focusMin  // 분으로 넘겨줌
    }

    fun getBreakTime(): Int {
        val breakMin = binding.timerBreakNumberPickerMin.value * 10
        return breakMin
    }

    fun getRepeatCount(): Int {
        return binding.timerRepeatNumberPickerTime.value
    }

}