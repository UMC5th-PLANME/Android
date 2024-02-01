package com.example.plan_me.data.remote.view.category

import com.example.plan_me.data.remote.dto.category.AddCategoryRes


interface AddCategoryView {
    fun onAddCategorySuccess(response: AddCategoryRes)
    fun onAddCategoryFailure(isSuccess: Boolean, code: String, message: String)
}