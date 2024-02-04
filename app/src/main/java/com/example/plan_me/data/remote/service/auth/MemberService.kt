package com.example.plan_me.data.remote.service.auth

import android.util.Log
import com.example.plan_me.data.local.entity.Member
import com.example.plan_me.data.local.entity.Terms
import com.example.plan_me.data.remote.dto.auth.ChangeMemberRes
import com.example.plan_me.data.remote.dto.auth.DeleteMemberRes
import com.example.plan_me.data.remote.dto.auth.MemberRes
import com.example.plan_me.data.remote.dto.auth.SignUpRes
import com.example.plan_me.data.remote.dto.auth.TermsRes
import com.example.plan_me.data.remote.retrofit.AuthRetrofitInterface
import com.example.plan_me.data.remote.view.auth.ChangeProfileView
import com.example.plan_me.data.remote.view.auth.DeleteMemberView
import com.example.plan_me.data.remote.view.auth.LookUpMemberView
import com.example.plan_me.data.remote.view.auth.SignUpView
import com.example.plan_me.data.remote.view.auth.TermsView
import com.example.plan_me.utils.getRetrofit
import kotlinx.serialization.json.Json
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MemberService {
    private lateinit var signUpView: SignUpView
    private lateinit var changeProfileView: ChangeProfileView
    private lateinit var lookUpMemberView: LookUpMemberView
    private lateinit var deleteMemberView: DeleteMemberView
    private lateinit var termsView: TermsView

    fun setSignUpView(signUpView: SignUpView) {
        this.signUpView = signUpView
    }

    fun setChangeProfileView(changeProfileView: ChangeProfileView) {
        this.changeProfileView = changeProfileView
    }

    fun setLookUpMemberView(lookUpMemberView: LookUpMemberView) {
        this.lookUpMemberView = lookUpMemberView
    }

    fun setDeleteMemberView(deleteMemberView: DeleteMemberView) {
        this.deleteMemberView = deleteMemberView
    }

    fun setTermsView(termsView: TermsView) {
        this.termsView = termsView
    }

    fun setSignUp(accessToken: String, member: Member) {
        val signUpService = getRetrofit().create(AuthRetrofitInterface::class.java)
        signUpService.postSignUp(accessToken, member).enqueue(object : Callback<SignUpRes> {
            override fun onResponse(call: Call<SignUpRes>, response: Response<SignUpRes>) {
                if (response.isSuccessful) {
                    val resp: SignUpRes? = response.body()
                    if (resp != null) {
                        when (resp.code) {
                            "MEMBER2005" -> signUpView.onSetSignUpSuccess(resp)
                            else -> signUpView.onSetSignUpFailure(resp.isSuccess, resp.code, resp.message)
                        }
                    } else {
                        Log.e("SIGNUP-SUCCESS", "Response body is null")
                    }
                } else {
                    Log.e("SIGNUP-SUCCESS", "Response not successful: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<SignUpRes>, t: Throwable) {
                Log.d("SIGNUP-FAILURE", t.toString())
            }
        })
    }

    fun setChangeProfile(accessToken: String, member: Member) {
        val changeProfileService = getRetrofit().create(AuthRetrofitInterface::class.java)
        changeProfileService.patchChangeProfile(accessToken, member).enqueue(object : Callback<ChangeMemberRes> {
            override fun onResponse(call: Call<ChangeMemberRes>, response: Response<ChangeMemberRes>) {
                if (response.isSuccessful) {
                    val resp: ChangeMemberRes = response.body()!!
                    if (resp != null) {
                        when(resp.code) {
                            "MEMBER2003" -> changeProfileView.onSetChangeProfileSuccess(resp)
                            else -> changeProfileView.onSetChangeProfileFailure(resp.isSuccess, resp.code, resp.message)
                        }
                    } else {
                        Log.e("UPDATE-PROFILE-SUCCESS", "Response body is null")
                    }
                } else {
                    Log.e("UPDATE-PROFILE-SUCCESS", "Response not successful: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ChangeMemberRes>, t: Throwable) {
                Log.d("UPDATE-PROFILE-FAILURE", t.toString())
            }
        })
    }

    fun getLookUpMember() {
        val lookUpMemberService = getRetrofit().create(AuthRetrofitInterface::class.java)
        lookUpMemberService.getMember().enqueue(object : Callback<MemberRes> {
            override fun onResponse(call: Call<MemberRes>, response: Response<MemberRes>) {
                if (response.isSuccessful) {
                    val resp: MemberRes = response.body()!!
                    if (resp != null) {
                        when(resp.code) {
                            // 코드 미정 (임시 설정)
                            "look-up" -> lookUpMemberView.onGetMemberSuccess(resp)
                            else -> lookUpMemberView.onGetMemberFailure(resp.isSuccess, resp.code, resp.message)
                        }
                    } else {
                        Log.e("LOOK-UP-MEMBER-SUCCESS", "Response body is null")
                    }
                } else {
                    Log.e("LOOK-UP-MEMBER-SUCCESS", "Response not successful: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<MemberRes>, t: Throwable) {
                Log.d("LOOK-UP-MEMBER-FAILURE", t.toString())
            }
        })
    }

    fun delMember(accessToken: String) {
        val deleteMemberService = getRetrofit().create(AuthRetrofitInterface::class.java)
        deleteMemberService.deleteMember(accessToken).enqueue(object : Callback<DeleteMemberRes> {
            override fun onResponse(call: Call<DeleteMemberRes>, response: Response<DeleteMemberRes>) {
                if (response.isSuccessful) {
                    val resp: DeleteMemberRes = response.body()!!
                    if (resp != null) {
                        when(resp.code) {
                            // 코드 미정 (임시 설정)
                            "delete" -> deleteMemberView.onDelMemberSuccess(resp)
                            else -> deleteMemberView.onDelMemberFailure(resp.isSuccess, resp.code, resp.message)
                        }
                    } else {
                        Log.e("DELETE-MEMBER-SUCCESS", "Response body is null")
                    }
                } else {
                    Log.e("DELETE-MEMBER-SUCCESS", "Response not successful: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<DeleteMemberRes>, t: Throwable) {
                Log.d("DELETE-MEMBER-FAILURE", t.toString())
            }
        })
    }

    fun setTerms(authorizationToken: String, terms: Terms) {
        val termsService = getRetrofit().create(AuthRetrofitInterface::class.java)
        Log.d("termsData", "$authorizationToken, ${terms.member_id}, ${terms.agreeTermIds}, ${terms.disagreeTermIds}")
        termsService.postAgreeTerms(authorizationToken, terms).enqueue(object : Callback<TermsRes> {
            override fun onResponse(call: Call<TermsRes>, response: Response<TermsRes>) {
                if (response.isSuccessful) {
                    val resp: TermsRes = response.body()!!
                    if (resp != null) {
                        when(resp.code) {
                            "MEMBER2002" -> termsView.onSetTermsSuccess(resp)
                            else -> termsView.onSetTermsFailure(resp.isSuccess, resp.code, resp.message)
                        }
                    } else {
                        Log.e("AGREE-TERMS-SUCCESS", "Response body is null")
                    }
                } else {
                    Log.e("AGREE-TERMS-SUCCESS", "Response not successful: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<TermsRes>, t: Throwable) {
                Log.d("AGREE-TERMS-FAILURE", t.toString())
            }
        })
    }
}