package com.example.plan_me.utils.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.plan_me.R
import com.example.plan_me.data.remote.dto.category.CategoryList

class NaviViewModel:ViewModel() {
    val currentCategory = MutableLiveData<CategoryList>()

    init {
        currentCategory.value = CategoryList(0,"Schedule","\uD83D\uDCC6" ,R.color.light_gray, false, "","" )
    }
    fun sendCategory(categoryList: CategoryList) {
        currentCategory.value = categoryList
    }
}