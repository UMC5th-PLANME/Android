package com.example.plan_me.ui.timer

import TimerFocusFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class TimerVPAdapter (fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity){

    private val focusFragment = TimerFocusFragment()
    private val breakFragment = TimerBreakFragment()
    private val repeatFragment = TimerRepeatCountFragment()

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> TimerFocusFragment()
            1 -> TimerBreakFragment()
            else -> TimerRepeatCountFragment()
        }

    }

    fun getFocusTime(): Int {
        return focusFragment.getFocusTime()     // ** 분
    }

    fun getBreakTime(): Int {
        return breakFragment.getBreakTime()     // ** 분
    }

    fun getRepeatCount(): Int {
        return repeatFragment.getRepeatCount()  // ** 번
    }

}