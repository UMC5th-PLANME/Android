
package com.example.plan_me.ui.login

import android.app.Dialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.plan_me.MainActivity
import com.example.plan_me.ui.planner.PlannerFragment
import com.example.plan_me.R
import com.example.plan_me.databinding.ActivityLoginBinding
import com.example.plan_me.ui.dialog.DialogTermsActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Scope
import com.google.android.gms.tasks.Task
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    var nickname : String = ""
    var profile :String = ""
    var social: String = ""
    private val googleSignInClient: GoogleSignInClient by lazy { getGoogleClient() }
    private val googleAuthLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            try {
                Log.d(TAG, "google 로그인 성공")
                openTermsPopup()
            } catch (e: ApiException) {
                Log.e(TAG, "google 로그인 실패", e)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Base_Theme_Plan_Me)
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 카카오 로그인
        binding.loginKakaoBtn.setOnClickListener {
            login_kakao()
        }

        // 구글 로그인
        binding.loginGoogleBtn.setOnClickListener {
            login_google()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == R.string.google_client_id) {
            // Google 로그인 결과 처리
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleGoogleSignInResult(task)
        }
    }

    private fun handleGoogleSignInResult(compltedTask: Task<GoogleSignInAccount>) {
        try {
            val account = compltedTask.getResult(ApiException::class.java)

            // Google 로그인 성공
            getUserInfoFromGoogle(account)
        } catch (e: ApiException) {
            // Google 로그인 실패
            Log.e(TAG, "Google 로그인 실패", e)
        }
    }

    private fun getUserInfoFromGoogle(account: GoogleSignInAccount?) {
        // Google 로그인 성공 시, 사용자 정보 가져오기
        if (account != null) {
            val userId = account.id
            val userName = account.displayName
            Log.d("Google 사용자 정보", "id: $userId & name: $userName")
            //goMainActivity()
        }
    }

    private fun login_kakao() {
        // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        // 이메일 로그인 콜백
        val mCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e(TAG, "로그인 실패 $error")
            } else if (token != null) {
                Log.e(TAG, "로그인 성공 ${token.accessToken}")
            }
            openTermsPopup()
        }

        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
            // 카카오톡 로그인
            UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                // 로그인 실패 부분
                if (error != null) {
                    Log.e(TAG, "로그인 실패 $error")
                    // 사용자가 취소
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled ) {
                        return@loginWithKakaoTalk
                    }
                    // 다른 오류
                    else {
                        UserApiClient.instance.loginWithKakaoAccount(this, callback = mCallback) // 카카오 이메일 로그인
                    }
                }
                // 로그인 성공 부분
                else if (token != null) {
                    Log.e(TAG, "로그인 성공 ${token.accessToken}")
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(this, callback = mCallback) // 카카오 이메일 로그인
        }
        UserApiClient.instance.me { user, _ ->
            if (user != null) {
                nickname = user.kakaoAccount?.profile?.nickname.toString()
                profile = user.kakaoAccount?.profile?.profileImageUrl.toString()
                social = "카카오"
                Log.d("userInfo", nickname + "&&" + profile)
                saveData()
            }
        }
    }

    private fun getGoogleClient(): GoogleSignInClient {
        val googleSignInOption = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestScopes(Scope("https://www.googleapis.com/auth/pubsub"))
            .requestServerAuthCode(getString(R.string.google_client_id)) // server authCode 요청
            .requestEmail() // 이메일도 요청 가능
            .build()

        return GoogleSignIn.getClient(this@LoginActivity, googleSignInOption)
    }

    private fun login_google() {
        googleSignInClient.signOut()
        val signInIntent = googleSignInClient.signInIntent
        googleAuthLauncher.launch(signInIntent)
    }

    private fun openTermsPopup() {
//        DialogTermsActivity(this@LoginActivity, nickname, profile).show()
//        intent.putExtra("nickname", nickname)
//        intent.putExtra("img", profile)
//        startActivity(intent)
        val dialog = DialogTermsActivity(this@LoginActivity, nickname, profile)
        dialog.show()
        dialog.setOnDismissListener {
//            intent.putExtra("nickname", nickname)
//            intent.putExtra("img", profile)
            startActivity(intent)
        }
    }

    private fun saveData() {
        // 데이터 저장
        val sharedPreferences: SharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("userName", nickname)
        editor.putString("userImg", profile)
        editor.putString("social", social)
        editor.apply()
    }
}