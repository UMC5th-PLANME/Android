package com.example.plan_me.data.remote.retrofit

import com.example.plan_me.data.local.entity.EditProfile
import com.example.plan_me.data.local.entity.Member
import com.example.plan_me.data.remote.dto.auth.AutoLoginRes
import com.example.plan_me.data.remote.dto.auth.ChangeMemberRes
import com.example.plan_me.data.remote.dto.auth.DeleteMemberRes
import com.example.plan_me.data.remote.dto.auth.MemberRes
import com.example.plan_me.data.remote.dto.auth.ProfileImageRes
import com.example.plan_me.data.remote.dto.auth.SignUpRes
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface AuthRetrofitInterface {
    // 자동 로그인
    @GET("/api/member/login")
    fun getAutoLogin(@Header("Authorization") Authorization: String): Call<AutoLoginRes>


    // 회원가입
    @POST("/api/member/join")
    fun postSignUp(@Header("accessToken") accessToken: String, @Body member: Member): Call<SignUpRes>

    // 프로필 이미지 등록
    @Multipart // form-data 사용
    @POST("/api/image")
    fun postImage(@Header("Authorization") Authorization: String, @Part image: MultipartBody.Part): Call<ProfileImageRes>

    // 프로필 변경
    @PATCH("/api/member")
    fun patchChangeProfile(@Header("Authorization") Authorization: String, @Body member: EditProfile): Call<ChangeMemberRes>

    // 회원 조회
    @GET("/api/member")
    fun getMember(@Header("Authorization") Authorization: String): Call<MemberRes>

    // 회원 탈퇴
    @DELETE("/api/member")
    fun deleteMember(@Header("Authorization") Authorization: String): Call<DeleteMemberRes>
}