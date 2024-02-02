package com.example.plan_me.data.remote.view.category

import com.example.plan_me.data.remote.dto.category.AddCategoryRes
import com.example.plan_me.data.remote.dto.category.AllCategoryRes


interface AllCategoryView {
    fun onAllCategorySuccess(response: AllCategoryRes)
    fun onAllCategoryFailure(response: AllCategoryRes)
}