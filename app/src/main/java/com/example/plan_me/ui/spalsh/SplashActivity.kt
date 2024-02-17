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
import com.example.plan_me.ui.dialog.CustomToast
import com.example.plan_me.ui.login.LoginActivity
import com.example.plan_me.ui.main.MainActivity

class SplashActivity : AppCompatActivity(), AutoLoginView {
    lateinit private var binding:ActivitySplashBinding
    private var customToast = CustomToast
    private var getAccessToken: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.screen_start, R.anim.screen_none)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = resources.getColor(R.color.dark_gray)

       /* autoLoginService()
*/
        if(getAccessToken == "") {
            Handler().postDelayed({
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }, 2000)
        }
    }

    private fun saveResponse() {
        // 받아온 데이터 저장
        val sharedPreferences: SharedPreferences = getSharedPreferences("getRes", MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("getAccessToken", getAccessToken!!)
        editor.apply()
    }

    private fun getResponse() {
        val sharedPreferences: SharedPreferences = getSharedPreferences("getRes", MODE_PRIVATE)
        getAccessToken = sharedPreferences.getString("getRefreshToken", getAccessToken)
    }

    private fun autoLoginService() {
        val getAutoLoginService = MemberService()
        getAutoLoginService.setAutoLoginView(this@SplashActivity)

        getResponse()
        Log.d("토큰", getAccessToken.toString())
        getAutoLoginService.getAutoLogin("Bearer " + getAccessToken)
    }

    override fun onGetAutoLoginSuccess(response: AutoLoginRes) {
        saveResponse()
        customToast.createToast(this@SplashActivity,"로그인되었습니다.", 300, true)
        val intent = Intent(this@SplashActivity, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onGetAutoLoginFailure(isSuccess: Boolean, code: String, message: String) {
        customToast.createToast(this@SplashActivity,"로그인되었습니다.", 300, true)
    }
}