package com.example.plan_me.ui.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.NumberPicker
import com.example.plan_me.databinding.FragmentDialogTimerpickBinding

class DialogTimerPickFragment(context: Context, dialogTimerPickInterface: DialogTimerPickInterface) : Dialog(context) {
    private lateinit var binding: FragmentDialogTimerpickBinding
    private var dialogTimerPickInterface : DialogTimerPickInterface
    private var focusHour: Int = 0
    private var focusMin: Int = 0
    private var breakMin: Int = 0
    private var repeatCount: Int = 1

    init {
        this.dialogTimerPickInterface = dialogTimerPickInterface
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentDialogTimerpickBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        initSetting()
        clickListener()
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
        focusMin.minValue = 1
        // 최대값 설정
        focusHour.maxValue = 2
        focusMin.maxValue = 5
        // 보여질 값 설정 (string)
        focusHour.displayedValues = arrayOf("0", "1", "2")
        focusMin.displayedValues = arrayOf("10", "20", "30", "40", "50")


        // BreakTime
        val breakMin: NumberPicker = binding.timerBreakNumberPickerMin
        // 순환 막기
        breakMin.wrapSelectorWheel = false
        // editText 설정 해제
        breakMin.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        // 최소값 설정
        breakMin.minValue = 0
        // 최대값 설정
        breakMin.maxValue = 10
        // 보여질 값 설정 (string)
        breakMin.displayedValues = arrayOf("5","10", "15", "20", "25", "30", "35", "40", "45", "50", "55")


        // Repeat Count -> 반복 횟수
        val repeatCount: NumberPicker = binding.timerRepeatNumberPickerTime
        // 순환 막기
        repeatCount.wrapSelectorWheel = false
        // editText 설정 해제
        repeatCount.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        // 최소값 설정
        repeatCount.minValue = 0
        // 최대값 설정
        repeatCount.maxValue = 8
        // 값 설정 (string)
        repeatCount.displayedValues = arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9")

        focusHour.setOnValueChangedListener { focusHour, _ , newVal ->
            this.focusHour = newVal * 60
        }

        focusMin.setOnValueChangedListener { numberPicker, _ , newVal ->
            if (newVal == 0) {
                this.focusMin = 10 // 0이면 10으로 설정
            } else {
                this.focusMin = newVal * 10
            }
        }

        breakMin.setOnValueChangedListener { numberPicker, _ , newVal ->
            this.breakMin = (5 * newVal) + 10
        }

        repeatCount.setOnValueChangedListener { _, _ , newVal ->
            this.repeatCount = newVal
        }
    }

    fun clickListener() {

        // 확인 버튼 클릭 리스너 설정
        binding.dialogTimerpickConfirm.setOnClickListener {
            focusMin += focusHour
            dialogTimerPickInterface?.onTimerSettingConfirm(focusMin, breakMin, repeatCount)
            dismiss()
        }

        binding.dialogTimerpickCancel.setOnClickListener {
            dialogTimerPickInterface?.onTimerSettingCancel()
            dismiss()
        }
    }
}

