package com.example.plan_me.data.remote.service.auth

import android.util.Log
import com.example.plan_me.data.remote.dto.auth.ProfileImageRes
import com.example.plan_me.data.remote.retrofit.AuthRetrofitInterface
import com.example.plan_me.data.remote.view.auth.ProfileImageView
import com.example.plan_me.utils.getRetrofit
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ImageService {
    private lateinit var imageView: ProfileImageView

    fun setImageView(imageView: ProfileImageView) {
        this.imageView = imageView
    }

    fun setProfileImg(accessToken: String, image: MultipartBody.Part) {
        val profileImgService = getRetrofit().create(AuthRetrofitInterface::class.java)
        profileImgService.postImage(accessToken, image).enqueue(object : Callback<ProfileImageRes> {
            override fun onResponse(call: Call<ProfileImageRes>, response: Response<ProfileImageRes>) {
                if (response.isSuccessful) {
                    val resp: ProfileImageRes? = response.body()
                    if (resp != null) {
                        when (resp.code) {
                            "IMAGE2001" -> imageView.onSetImgSuccess(resp)
                            else -> imageView.onSetImgFailure(resp.isSuccess, resp.code, resp.message)
                        }
                    } else {
                        Log.e("PROFILE-IMG-SUCCESS", "Response body is null")
                    }
                } else {
                    Log.e("PROFILE-IMG-SUCCESS", "Response not successful: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ProfileImageRes>, t: Throwable) {
                Log.d("PROFILE-IMG-FAILURE", t.toString())
            }
        })
    }
}