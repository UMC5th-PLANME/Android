package com.example.plan_me.ui.all

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.plan_me.ui.add.ScheduleAddActivity
import com.example.plan_me.databinding.FragmentAllBinding
import com.google.android.material.tabs.TabLayoutMediator


class AllFragment : Fragment() {
    private lateinit var binding: FragmentAllBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAllBinding.inflate(layoutInflater)
        initViewPager()
        return binding.root
    }
    private fun initViewPager() {
        val allViewPager2Adapter = AllViewPager2Adapter(requireActivity())
        allViewPager2Adapter.addFragment(DailyFragment())
        allViewPager2Adapter.addFragment(WeeklyFragment())
        allViewPager2Adapter.addFragment(MonthlyFragment())

        binding.allVp.adapter = allViewPager2Adapter
        binding.allVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        TabLayoutMediator(binding.allTb, binding.allVp) {tab, position ->
            when(position) {
                0->tab.text = "daily"
                1->tab.text = "weekly"
                2->tab.text = "monthly"
            }
        }.attach()
    }
}