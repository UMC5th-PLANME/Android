package com.example.plan_me.data.remote.service.category

import android.util.Log
import com.example.plan_me.data.local.entity.Category_input
import com.example.plan_me.data.remote.dto.category.AddCategoryRes
import com.example.plan_me.data.remote.dto.category.AllCategoryRes
import com.example.plan_me.data.remote.dto.category.DeleteCategoryRes
import com.example.plan_me.data.remote.dto.category.ModifyCategoryRes
import com.example.plan_me.data.remote.dto.category.ModifyStatusCategoryRes
import com.example.plan_me.data.remote.dto.category.OneCategoryRes
import com.example.plan_me.data.remote.retrofit.categoryRetrofitInterface
import com.example.plan_me.data.remote.view.category.AddCategoryView
import com.example.plan_me.data.remote.view.category.AllCategoryView
import com.example.plan_me.data.remote.view.category.DeleteCategoryView
import com.example.plan_me.data.remote.view.category.ModifyCategoryView
import com.example.plan_me.data.remote.view.category.ModifyStatusCategoryView
import com.example.plan_me.data.remote.view.category.OneCategoryView
import com.example.plan_me.utils.getRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryService  {

    private lateinit var addCategoryView: AddCategoryView
    private lateinit var allCategoryView: AllCategoryView
    private lateinit var oneCategoryView: OneCategoryView
    private lateinit var deleteCategoryView: DeleteCategoryView
    private lateinit var modifyStatusCategoryView: ModifyStatusCategoryView
    private lateinit var modifyCategoryView: ModifyCategoryView
    val CategoryService = getRetrofit().create(categoryRetrofitInterface::class.java)

    fun setAddCategoryView(addCategoryView: AddCategoryView) {
        this.addCategoryView = addCategoryView
    }
    fun setAllCategoryView(allCategoryView: AllCategoryView) {
        this.allCategoryView = allCategoryView
    }

    fun setDeleteCategoryView(deleteCategoryView: DeleteCategoryView) {
        this.deleteCategoryView = deleteCategoryView
    }

    fun setOneCategoryView(allCategoryView: AllCategoryView) {
        this.oneCategoryView = oneCategoryView
    }

    fun setModifyCategoryView(modifyCategoryView: ModifyCategoryView) {
        this.modifyCategoryView = modifyCategoryView
    }

    fun setModifyStatusCategoryView(allCategoryView: AllCategoryView) {
        this.modifyStatusCategoryView = modifyStatusCategoryView
    }

    fun addCategoryFun(accessToken: String, category_input: Category_input) {
        CategoryService.postCategory(accessToken, category_input).enqueue(object : Callback<AddCategoryRes> {
            override fun onResponse(call: Call<AddCategoryRes>, response: Response<AddCategoryRes>) {
                Log.d("add category response", response.body().toString())
                val resp: AddCategoryRes = response.body()!!
                when(resp.code) {
                    "CATEGORY2001" -> addCategoryView.onAddCategorySuccess(resp)
                    else -> addCategoryView.onAddCategoryFailure(resp)
                }
            }
            override fun onFailure(call: Call<AddCategoryRes>, t: Throwable) {
                Log.d("add category failure", t.toString())
            }
        })
    }

    fun getCategoryAllFun(accessToken: String) {
        CategoryService.getCategoryAll(accessToken).enqueue(object :Callback<AllCategoryRes>{
            override fun onResponse(
                call: Call<AllCategoryRes>,
                response: Response<AllCategoryRes>
            ) {
                val resp = response.body()!!
                when(resp.code) {
                    "CATEGORY2005" -> allCategoryView.onAllCategorySuccess(resp)
                    else -> allCategoryView.onAllCategoryFailure(resp)
                }
            }

            override fun onFailure(call: Call<AllCategoryRes>, t: Throwable) {
                Log.d("all category failure", t.toString())
            }
        })
    }

    fun deleteCategoryFun(accessToken: String, categoryId : Int) {
        CategoryService.deleteCategory(categoryId, accessToken).enqueue(object :Callback<DeleteCategoryRes>{
            override fun onResponse(
                call: Call<DeleteCategoryRes>,
                response: Response<DeleteCategoryRes>
            ) {
                Log.d("response delete", response.toString())
                val resp = response.body()!!
                when(resp.code) {
                    "CATEGORY2002" -> deleteCategoryView.onDeleteCategorySuccess(resp)
                    else -> deleteCategoryView.onDeleteCategoryFailure(resp)
                }
            }

            override fun onFailure(call: Call<DeleteCategoryRes>, t: Throwable) {
                Log.d("delete category failure", t.toString())
            }
        })
    }

    fun getOneCategoryFun(accessToken: String, categoryId : Int) {
        CategoryService.getCategoryOne(categoryId, accessToken).enqueue(object :Callback<OneCategoryRes>{
            override fun onResponse(
                call: Call<OneCategoryRes>,
                response: Response<OneCategoryRes>
            ) {
                val resp = response.body()!!
                when(resp.code) {
                    "CATEGORY2005" -> oneCategoryView.onOneCategorySuccess(resp)
                    else -> oneCategoryView.onOneCategoryFailure(resp)
                }
            }

            override fun onFailure(call: Call<OneCategoryRes>, t: Throwable) {
                Log.d("get category failure", t.toString())
            }
        })
    }

    fun modifyCategoryFun(accessToken: String, categoryId : Int, category_input: Category_input) {
        CategoryService.modifyCategory(categoryId, accessToken, category_input).enqueue(object :Callback<ModifyCategoryRes>{
            override fun onResponse(
                call: Call<ModifyCategoryRes>,
                response: Response<ModifyCategoryRes>
            ) {
                val resp = response.body()!!
                when(resp.code) {
                    "CATEGORY2003" -> modifyCategoryView.onModifyCategorySuccess(resp)
                    else -> modifyCategoryView.onModifyCategoryFailure(resp)
                }
            }

            override fun onFailure(call: Call<ModifyCategoryRes>, t: Throwable) {
                Log.d("modify category failure", t.toString())
            }
        })
    }

    fun modifyStatusCategoryFun(accessToken: String, categoryId : Int) {
        CategoryService.modifyStatusCategory(categoryId, accessToken).enqueue(object :Callback<ModifyStatusCategoryRes>{
            override fun onResponse(
                call: Call<ModifyStatusCategoryRes>,
                response: Response<ModifyStatusCategoryRes>
            ) {
                val resp = response.body()!!
                when(resp.code) {
                    "CATEGORY2005" -> modifyStatusCategoryView.onModifyStatusCategorySuccess(resp)
                    else -> modifyStatusCategoryView.onModifyStatusCategoryFailure(resp)
                }
            }

            override fun onFailure(call: Call<ModifyStatusCategoryRes>, t: Throwable) {
                Log.d("modify status category failure", t.toString())
            }
        })
    }
}