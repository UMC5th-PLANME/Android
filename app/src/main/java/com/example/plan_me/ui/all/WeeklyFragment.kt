package com.example.plan_me.ui.all

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.plan_me.databinding.FragmentAllBinding
import com.example.plan_me.databinding.FragmentDailyBinding
import com.example.plan_me.databinding.FragmentMonthlyBinding
import com.example.plan_me.databinding.FragmentWeeklyBinding


class WeeklyFragment : Fragment() {
    private lateinit var binding: FragmentWeeklyBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWeeklyBinding.inflate(layoutInflater)
        return binding.root
    }
}