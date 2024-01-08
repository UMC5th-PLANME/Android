package com.example.plan_me.utils

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class GlobalApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        // Kakao Sdk 초기화
        KakaoSdk.init(this@GlobalApplication, "55b1939dd22f9a5ef6176930e7a09231")
    }
}