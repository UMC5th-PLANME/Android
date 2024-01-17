package com.example.plan_me.utils

import android.app.Application
import com.example.plan_me.R
import com.google.firebase.FirebaseApp
import com.kakao.sdk.common.KakaoSdk

class GlobalApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this@GlobalApplication)

        // Kakao Sdk 초기화
        KakaoSdk.init(this@GlobalApplication, getString(R.string.kakao_appkey))
    }
}