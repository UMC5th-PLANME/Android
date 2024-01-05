package com.example.plan_me

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.plan_me.databinding.FragmentTimerFocusBinding

class TimerFocusFragment : Fragment() {

    lateinit var binding : FragmentTimerFocusBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTimerFocusBinding.inflate(inflater, container, false)

        return binding.root
    }
}