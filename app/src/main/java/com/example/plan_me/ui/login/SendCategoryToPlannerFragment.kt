package com.example.plan_me.ui.login

import com.example.plan_me.data.remote.dto.category.CategoryList

interface SendCategoryToPlannerFragment {
    fun sendCategoryToFragment(category : CategoryList)
}