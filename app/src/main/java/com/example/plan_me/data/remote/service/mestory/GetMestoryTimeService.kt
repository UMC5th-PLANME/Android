package com.example.plan_me.data.remote.service.mestory

import android.util.Log
import com.example.plan_me.data.remote.dto.auth.MemberId
import com.example.plan_me.data.remote.dto.mestory.GetMestoryTimeRes
import com.example.plan_me.data.remote.dto.mestory.MestoryTimeRetrofitInterface
import com.example.plan_me.data.remote.view.mestory.GetMestoryTimeView
import com.example.plan_me.utils.getRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetMestoryTimeService {
    private lateinit var getMestoryTimeView: GetMestoryTimeView

    fun getMestoryTimeView(getMestoryTimeView: GetMestoryTimeView) {
        this.getMestoryTimeView = getMestoryTimeView
    }

    fun getMestoryTime(accessToken: String, memberId: Int, date: String) {
        val GetMestoryTimeService = getRetrofit().create(MestoryTimeRetrofitInterface::class.java)
        GetMestoryTimeService.getMestoryTime(accessToken, memberId, date).enqueue(object: Callback<GetMestoryTimeRes> {
            override fun onResponse(call: Call<GetMestoryTimeRes>, response: Response<GetMestoryTimeRes>) {
                if (response.isSuccessful) {
                    val resp: GetMestoryTimeRes? = response.body()

                    if (resp != null) {
                        when(resp.code) {
                            "MESTORY2001" -> getMestoryTimeView.onGetMestoryTimeSuccess(resp)
                            else -> getMestoryTimeView.onGetMestoryTimeFailure(resp.isSuccess, resp.code, resp.message)
                        }
                    } else {
                        Log.e("GET-FOCUS-TIME-SUCCESS", "Response body is null")
                    }
                } else {
                    Log.e("GET-FOCUS-TIME-SUCCESS", "Response not successful: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<GetMestoryTimeRes>, t: Throwable) {
                Log.d("GET-FOCUS-TIME-FAILURE", t.toString())
            }
        })
    }
}