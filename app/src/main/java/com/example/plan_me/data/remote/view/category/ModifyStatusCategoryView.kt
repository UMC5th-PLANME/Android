package com.example.plan_me.data.remote.view.category

import com.example.plan_me.data.remote.dto.category.AddCategoryRes
import com.example.plan_me.data.remote.dto.category.AllCategoryRes
import com.example.plan_me.data.remote.dto.category.ModifyStatusCategoryRes


interface ModifyStatusCategoryView {
    fun onModifyStatusCategorySuccess(response: ModifyStatusCategoryRes)
    fun onModifyStatusCategoryFailure(response: ModifyStatusCategoryRes)
}