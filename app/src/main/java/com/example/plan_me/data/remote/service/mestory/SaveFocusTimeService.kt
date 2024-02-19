package com.example.plan_me.data.remote.service.mestory

import android.util.Log
import com.example.plan_me.data.remote.dto.mestory.FocusTimeSetting
import com.example.plan_me.data.remote.dto.mestory.SaveFocusTimeRes
import com.example.plan_me.data.remote.dto.mestory.TotalFocusTime
import com.example.plan_me.data.remote.dto.timer.TimerSettingRes
import com.example.plan_me.data.remote.retrofit.SaveFocusTimeRetrofitInterface
import com.example.plan_me.data.remote.retrofit.TimerRetrofitInterface
import com.example.plan_me.data.remote.view.mestory.SaveFocusTimeView
import com.example.plan_me.utils.getRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SaveFocusTimeService {
    private lateinit var saveFocusTimeView: SaveFocusTimeView

    fun saveFocusTimeView(saveFocusTimeView: SaveFocusTimeView) {
        this.saveFocusTimeView = saveFocusTimeView
    }

    fun setSaveFocusTime(accessToken: String, categoryId: Int, saveFocusTimeReq: TotalFocusTime) {
        val SaveFocusTimeService = getRetrofit().create(SaveFocusTimeRetrofitInterface::class.java)
        SaveFocusTimeService.postFocusTime(accessToken, categoryId, saveFocusTimeReq).enqueue(object :
        Callback<SaveFocusTimeRes> {
            override fun onResponse(
                call: Call<SaveFocusTimeRes>,
                response: Response<SaveFocusTimeRes>
            ) {
                Log.d("SAVE-FOCUS-TIME-SUCCESS", response.toString())
                val resp: SaveFocusTimeRes = response.body()!!
                when(resp.code) {
                    "MESTORY2011", "MESTORY2012" -> saveFocusTimeView.onSaveFocusTimeSuccess(resp)
                    else -> saveFocusTimeView.onSaveFocusTimeFailure(resp.isSuccess, resp.code, resp.message)
                }
            }
            // 네트워크 X
            override fun onFailure(call: Call<SaveFocusTimeRes>, t: Throwable) {
                Log.d("SAVE-FOCUS-TIME-FAILURE", t.toString())
            }
        })
    }

}