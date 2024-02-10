package com.example.plan_me.data.remote.view.category

import com.example.plan_me.data.remote.dto.category.AddCategoryRes
import com.example.plan_me.data.remote.dto.category.AllCategoryRes
import com.example.plan_me.data.remote.dto.category.OneCategoryRes


interface OneCategoryView {
    fun onOneCategorySuccess(response: OneCategoryRes)
    fun onOneCategoryFailure(response: OneCategoryRes)
}