package com.example.plan_me.ui.timer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import androidx.fragment.app.Fragment
import com.example.plan_me.databinding.FragmentRepeatCountSettingBinding

class TimerRepeatCountFragment: Fragment() {
    private lateinit var binding: FragmentRepeatCountSettingBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRepeatCountSettingBinding.inflate(layoutInflater)
        initSetting()
        return binding.root
    }

    private fun initSetting() {
        // 바인딩을 사용하여 NumberPicker에 대한 참조 가져오기
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

    fun getRepeatCount(): Int {
        // NumberPicker에서 설정된 값을 반환
        return binding.timerRepeatNumberPickerTime.value

    }
}