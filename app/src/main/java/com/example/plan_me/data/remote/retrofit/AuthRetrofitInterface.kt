package com.example.plan_me.data.remote.retrofit

import com.example.plan_me.data.local.entity.Member
import com.example.plan_me.data.local.entity.Terms
import com.example.plan_me.data.remote.dto.auth.ChangeMemberRes
import com.example.plan_me.data.remote.dto.auth.DeleteMemberRes
import com.example.plan_me.data.remote.dto.auth.LoginRes
import com.example.plan_me.data.remote.dto.auth.MemberRes
import com.example.plan_me.data.remote.dto.auth.ProfileImageRes
import com.example.plan_me.data.remote.dto.auth.SignUpRes
import com.example.plan_me.data.remote.dto.auth.TermsRes
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface AuthRetrofitInterface {
    // 카카오, 구글로 로그인하기
    @GET("/oauth2/authorization/{provider}")
    fun getSocialLogin(@Path("provider") provider: String): Call<LoginRes>

    // 회원가입
    @PATCH("/api/member")
    fun patchSignUp(@Header("accessToken") accessToken: String, @Body member: Member): Call<SignUpRes>

    // 약관 동의
    @POST("/api/member/terms")
    fun postAgreeTerms(@Header("authorizationToken") authorizationToken: String, @Body terms: Terms): Call<TermsRes>

    // 프로필 이미지 등록
    @Multipart // form-data 사용
    @POST("/api/image")
    fun postImage(@Header("accessToken") accessToken: String, @Part image: MultipartBody.Part): Call<ProfileImageRes>

    // 프로필 변경
    @PATCH("/api/member")
    fun patchChangeProfile(@Header("accessToken") accessToken: String, @Body member: Member): Call<ChangeMemberRes>

    // 회원 조회
    @GET("/api/member")
    fun getMember(): Call<MemberRes>

    // 회원 탈퇴
    @DELETE("/api/member")
    fun deleteMember(@Header("accessToken") accessToken: String): Call<DeleteMemberRes>
}