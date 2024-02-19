package com.example.plan_me.ui.spalsh

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.example.plan_me.R
import com.example.plan_me.data.remote.dto.auth.AutoLoginRes
import com.example.plan_me.data.remote.service.auth.MemberService
import com.example.plan_me.data.remote.view.auth.AutoLoginView
import com.example.plan_me.databinding.ActivitySplashBinding
import com.example.plan_me.ui.login.LoginActivity
import com.example.plan_me.ui.main.MainActivity

class SplashActivity : AppCompatActivity(), AutoLoginView {
    lateinit private var binding:ActivitySplashBinding
    private var getAccessToken: String? = ""
    private var getRefreshToken: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.screen_start, R.anim.screen_none)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = resources.getColor(R.color.dark_gray)

        autoLoginService()

        // 로그인 기록이 없을 경우 -> 로그인 화면으로 이동
        if(getRefreshToken == "") {
            Handler().postDelayed({
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }, 2000)
        }
    }

    private fun saveResponse() {
        val sharedPreferences: SharedPreferences = getSharedPreferences("getRes", MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("getAccessToken", getAccessToken)
        editor.apply()
    }

    private fun getResponse() {
        val sharedPreferences: SharedPreferences = getSharedPreferences("getRes", MODE_PRIVATE)
        getRefreshToken = sharedPreferences.getString("getRefreshToken", getRefreshToken)
    }

    private fun autoLoginService() {
        val getAutoLoginService = MemberService()
        getAutoLoginService.setAutoLoginView(this@SplashActivity)

        getResponse()
        Log.d("토큰", getRefreshToken.toString())
        getAutoLoginService.getAutoLogin("Bearer " + getRefreshToken)
    }

    override fun onGetAutoLoginSuccess(response: AutoLoginRes) {
        getAccessToken = response.result.accessToken

        // 로그인 기록이 있을 경우 -> access token 저장 & main 화면으로 이동
        saveResponse()
        Log.d("자동 로그인", response.result.toString())

        Handler().postDelayed({
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000)
    }

    override fun onGetAutoLoginFailure(isSuccess: Boolean, code: String, message: String) {
        Log.d("자동 로그인 실패", message)
    }
}