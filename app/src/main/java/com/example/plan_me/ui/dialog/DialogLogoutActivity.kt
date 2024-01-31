package com.example.plan_me.ui.dialog

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import com.example.plan_me.R
import com.example.plan_me.databinding.ActivityDialogLogoutBinding
import com.example.plan_me.ui.login.LoginActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.kakao.sdk.user.UserApiClient

class DialogLogoutActivity(context: Context, val social: String?) : Dialog(context) {
    private lateinit var binding: ActivityDialogLogoutBinding
    private val googleSignInClient: GoogleSignInClient by lazy { getGoogleClient() }
    interface googleLogoutListener {
        fun onGoogleLogout()
    }

    private var logoutListener: googleLogoutListener? = null

    fun setLogoutListener(listener: googleLogoutListener) {
        logoutListener = listener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDialogLogoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.logoutCancelBtn.setOnClickListener {
            dismiss()
        }

        binding.logoutSaveBtn.setOnClickListener {
            logout()
        }
    }

    private fun getGoogleClient(): GoogleSignInClient {
        val googleSignInOption = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .build()

        return GoogleSignIn.getClient(context, googleSignInOption)
    }

    private fun logout() {
        Log.d("type", social.toString())
        if (social == "카카오") {
            UserApiClient.instance.logout { error ->
                if (error != null) {
                    Log.e(TAG_KAKAO, "로그아웃 실패. SDK에서 토큰 삭제됨", error)
                } else {
                    Log.i(TAG_KAKAO, "로그아웃 성공. SDK에서 토큰 삭제됨")
                    Toast.makeText(context, "로그아웃되었습니다.", Toast.LENGTH_SHORT).show()
                    navigateToFirstScreen()

                }
            }
        } else {
            googleSignInClient.signOut().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // 로그아웃이 성공한 경우
                    Log.d("GOOGLE-LOGOUT", "Google 로그아웃 성공")
                    Toast.makeText(context, "로그아웃되었습니다.", Toast.LENGTH_SHORT).show()
                    navigateToFirstScreen()
                } else {
                    // 로그아웃이 실패한 경우
                    Log.e("GOOGLE-LOGOUT", "Google 로그아웃 실패", task.exception)
                    Toast.makeText(context, "로그아웃에 실패하였습니다.", Toast.LENGTH_SHORT).show()
                }
            }
            navigateToFirstScreen()
        }
    }

    private fun navigateToFirstScreen() {
        val intent = Intent(context, LoginActivity::class.java)
        context.startActivity(intent)

        dismiss()
    }

    companion object {
        val TAG_KAKAO = "KAKAO-LOGOUT"
    }
}