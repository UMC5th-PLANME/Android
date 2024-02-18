package com.example.plan_me.ui.setting

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.plan_me.R
import com.example.plan_me.data.remote.dto.category.AllCategoryRes
import com.example.plan_me.data.remote.dto.category.CategoryList
import com.example.plan_me.data.remote.dto.category.ModifyStatusCategoryRes
import com.example.plan_me.data.remote.service.category.CategoryService
import com.example.plan_me.data.remote.view.category.AllCategoryView
import com.example.plan_me.data.remote.view.category.ModifyStatusCategoryView
import com.example.plan_me.databinding.ActivityManageMestoryBinding

class MestorySettingFragment: Fragment(), AllCategoryView, ModifyStatusCategoryView {
    private lateinit var binding: ActivityManageMestoryBinding
    private lateinit var categoryList : List<CategoryList>
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = ActivityManageMestoryBinding.inflate(layoutInflater)
        getCategoryList()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
        val adapter = MestroySettingRVAdapter(categoryList, requireContext())
        binding.manageMestoryListRv.adapter = adapter
        binding.manageMestoryListRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    private fun getCategoryList() {
        val access_token = "Bearer " + requireActivity().getSharedPreferences("getRes", AppCompatActivity.MODE_PRIVATE)
            .getString("getAccessToken", "")
        val categoryService = CategoryService()
        categoryService.setAllCategoryView(this)
        categoryService.getCategoryAllFun(access_token!!)
    }

    override fun onAllCategorySuccess(response: AllCategoryRes) {
        Log.d("MestorySettingFragment", "Category 불러오기 설공")
        categoryList = response.result.categoryList
        initRV()
    }

    override fun onAllCategoryFailure(response: AllCategoryRes) {
        Log.d("MestorySettingFragment", "Category 불러오기 실패")
    }

    override fun onModifyStatusCategorySuccess(response: ModifyStatusCategoryRes) {
        Log.d("MestorySettingFragment", "Category에서 숨김 상태 변경 성공")

    }

    override fun onModifyStatusCategoryFailure(response: ModifyStatusCategoryRes) {
        Log.d("MestorySettingFragment", "Category에서 숨김 상태 변경 실패")
    }
}