package com.example.plan_me

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.plan_me.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Scope
import com.google.android.gms.tasks.Task
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient

class LoginActivity: AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val googleSignInClient: GoogleSignInClient by lazy { getGoogleClient() }
    private val googleAuthLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        try {
            goMainActivity()
            Log.d(TAG, "google 로그인 성공")
        } catch (e: ApiException) {
            Log.e(TAG, "google 로그인 실패", e)
        }
    }

    // 카카오 로그인
    // 카카오 계정으로 로그인 공통 callback 구성
    // 카카오톡으로 로그인할 수 없어 카카오 계정으로 로그인할 경우 사용됨
    val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if(error != null) {
            Log.e(TAG, "카카오계정으로 로그인 실패", error)
        } else if(token != null) {
            Log.i(TAG, "카카오계정으로 로그인 성공 ${token.accessToken}")
            // 사용자 정보 가져오기
            UserApiClient.instance.me { user, meError ->
                if (meError != null) {
                    Log.e(TAG, "사용자 정보 가져오기 실패", meError)
                } else if (user != null) {
                    val userId = user.id.toString()
                    val userName = user.kakaoAccount?.profile?.nickname
                    val userEmail = user.kakaoAccount?.email
                    Log.d("카카오 사용자 정보", "id: $userId & name: $userName & email: $userEmail")
                    goMainActivity()
                }
            }
            goMainActivity()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
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

        if(requestCode == R.string.google_client_id) {
            // Google 로그인 결과 처리
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleGoogleSignInResult(task)
        }
    }

    private fun handleGoogleSignInResult(compltedTask: Task<GoogleSignInAccount>) {
        try{
            val account = compltedTask.getResult(ApiException::class.java)

            // Google 로그인 성공
            getUserInfoFromGoogle(account)
        } catch(e: ApiException) {
            // Google 로그인 실패
            Log.e(TAG, "Google 로그인 실패", e)
        }
    }

    private fun getUserInfoFromGoogle(account: GoogleSignInAccount?) {
        // Google 로그인 성공 시, 사용자 정보 가져오기
        if(account != null) {
            val userId = account.id
            val userName = account.displayName
            Log.d("Google 사용자 정보", "id: $userId & name: $userName")
            //goMainActivity()
        }
    }

    private fun login_kakao() {
        // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this@LoginActivity)) {
            UserApiClient.instance.loginWithKakaoTalk(this@LoginActivity) { token, error ->
                if (error != null) {
                    Log.e(TAG, "카카오톡으로 로그인 실패", error)

                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                    UserApiClient.instance.loginWithKakaoAccount(this@LoginActivity, callback = callback)
                } else if (token != null) {
                    Log.i(TAG, "카카오톡으로 로그인 성공 ${token.accessToken}")
                    goMainActivity()
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(this@LoginActivity, callback = callback)
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

    private fun goMainActivity() {
        val intent = Intent(this@LoginActivity, PlannerActivity::class.java)
        startActivity(intent)
    }
}