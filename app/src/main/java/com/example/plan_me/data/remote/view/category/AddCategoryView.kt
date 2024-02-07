package com.example.plan_me.data.remote.view.category

import com.example.plan_me.data.remote.dto.category.AddCategoryRes
import com.example.plan_me.data.remote.dto.category.AllCategoryRes


interface AddCategoryView {
    fun onAddCategorySuccess(response: AddCategoryRes)
    fun onAddCategoryFailure(response: AddCategoryRes)
}