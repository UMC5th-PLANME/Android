package com.example.plan_me.ui.planner

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.plan_me.R
import com.example.plan_me.data.remote.dto.category.CategoryList
import com.example.plan_me.databinding.FragmentPlannerBinding
import com.example.plan_me.ui.login.SendCategoryToPlannerFragment
import com.example.plan_me.ui.main.MainActivity



class PlannerFragment : Fragment(){
    private lateinit var binding: FragmentPlannerBinding/*
    private val plannerRVAdapter = PlannerRVAdapter()*/

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPlannerBinding.inflate(layoutInflater)
        init()
        return binding.root
/*
        binding.plannerTodoRv.layoutManager = LinearLayoutManager(requireContext())
        binding.plannerTodoRv.adapter = plannerRVAdapter*/
    }
    private fun init() {
        if (arguments != null) {
            val newColor = ContextCompat.getColor(requireContext(), arguments?.getInt("color")!!) // Replace with your desired color resource
            val shape = GradientDrawable()
            shape.shape = GradientDrawable.RECTANGLE
            shape.setColor(newColor)
            shape.cornerRadius = resources.getDimension(R.dimen.planner_corner_raidus) // 원하는 radius 값으로 대체

            // 설정한 모양을 레이아웃에 적용
            binding.plannerSecondLo.background = shape
            binding.plannerCategoryNameTv.text = (arguments?.getString("name")!!)
            binding.plannerCategoryImoticonTv.text = (arguments?.getString("emoticon")!!)
        }
    }
}