package com.example.plan_me.data.remote.retrofit

import com.example.plan_me.data.local.entity.EditProfile
import com.example.plan_me.data.local.entity.Member
import com.example.plan_me.data.remote.dto.auth.ChangeMemberRes
import com.example.plan_me.data.remote.dto.auth.DeleteMemberRes
import com.example.plan_me.data.remote.dto.auth.MemberRes
import com.example.plan_me.data.remote.dto.auth.ProfileImageRes
import com.example.plan_me.data.remote.dto.auth.SignUpRes
import com.example.plan_me.data.remote.dto.auth.TermsRes
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface AuthRetrofitInterface {

    // 회원가입
    @POST("/api/member/login")
    fun postSignUp(@Header("accessToken") accessToken: String, @Body member: Member): Call<SignUpRes>

    // 프로필 이미지 등록
    @Multipart // form-data 사용
    @POST("/api/image")
    fun postImage(@Header("accessToken") accessToken: String, @Part image: MultipartBody.Part): Call<ProfileImageRes>

    // 프로필 변경
    @PATCH("/api/member")
    fun patchChangeProfile(@Header("accessToken") accessToken: String, @Body member: EditProfile): Call<ChangeMemberRes>

    // 회원 조회
    @GET("/api/member")
    fun getMember(): Call<MemberRes>

    // 회원 탈퇴
    @DELETE("/api/member")
    fun deleteMember(@Header("accessToken") accessToken: String): Call<DeleteMemberRes>
}