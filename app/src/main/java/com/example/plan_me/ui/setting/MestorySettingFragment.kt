package com.example.plan_me.ui.setting

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.plan_me.R
import com.example.plan_me.data.remote.dto.category.AllCategoryRes
import com.example.plan_me.data.remote.dto.category.CategoryList
import com.example.plan_me.data.remote.dto.category.ModifyStatusCategoryRes
import com.example.plan_me.data.remote.service.category.CategoryService
import com.example.plan_me.data.remote.view.category.AllCategoryView
import com.example.plan_me.data.remote.view.category.ModifyStatusCategoryView
import com.example.plan_me.databinding.ActivityManageMestoryBinding
import com.example.plan_me.utils.viewModel.CalendarViewModel
import com.example.plan_me.utils.viewModel.CalendarViewModelFactory

class MestorySettingFragment: Fragment(),
    SendModifySuccess{
    private lateinit var binding: ActivityManageMestoryBinding
    private lateinit var calendarViewModel: CalendarViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = ActivityManageMestoryBinding.inflate(layoutInflater)
        val factory = CalendarViewModelFactory(requireActivity().getSharedPreferences("getRes", Context.MODE_PRIVATE))
        calendarViewModel = ViewModelProvider(requireActivity(), factory).get(CalendarViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRV()
        binding.manageMestoryBackBtn.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.fadein, R.anim.fadeout)
                .replace(R.id.main_frm, SettingFragment())
                .addToBackStack(null)
                .commit()
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressedCallback)
    }

    private val onBackPressedCallback = object  : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            parentFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.fadein, R.anim.fadeout2)
                .replace(R.id.main_frm, SettingFragment())
                .commitAllowingStateLoss()
        }
    }

    private fun initRV() {
        Log.d("calendarViewModel._categoryList.value",calendarViewModel._categoryList.value.toString())
        if (!calendarViewModel._categoryList.value.isNullOrEmpty()) {
            val adapter = MestroySettingRVAdapter(calendarViewModel._categoryList.value!!, requireContext(), this)
            binding.manageMestoryListRv.adapter = adapter
            binding.manageMestoryListRv.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

    override fun sendModifySuccess() {
        calendarViewModel.getCategoryList()
    }


}