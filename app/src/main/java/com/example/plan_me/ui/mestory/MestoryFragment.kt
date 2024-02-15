package com.example.plan_me.ui.mestory

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.plan_me.data.remote.dto.category.AllCategoryRes
import com.example.plan_me.data.remote.dto.mestory.GetMestoryTimeRes
import com.example.plan_me.data.remote.service.mestory.GetMestoryTimeService
import com.example.plan_me.data.remote.view.category.AllCategoryView
import com.example.plan_me.data.remote.view.mestory.GetMestoryTimeView
import com.example.plan_me.databinding.ActivityMestoryBinding
import com.example.plan_me.ui.CircleTransform
import com.squareup.picasso.Picasso
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MestoryFragment : Fragment(), GetMestoryTimeView {
    private lateinit var binding: ActivityMestoryBinding

    private var userName: String? = ""
    private var userImg: String? = ""

    private var accessToken: String? = ""
    private var memberId: Int? = 0
    private var color: String? = ""

    private val today = LocalDate.now()
    private val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
    private val formattedDate = today.format(formatter)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = ActivityMestoryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getData()

        binding.mestoryProfileDateTv.setText(today.toString())

        binding.mestoryProfileNameTv.text = userName
        if (userImg != "https://k.kakaocdn.net/dn/1G9kp/btsAot8liOn/8CWudi3uy07rvFNUkk3ER0/img_640x640.jpg" && userImg != "null") {
            Picasso.get().load(userImg).transform(CircleTransform())
                .into(binding.mestoryProfileIv)
        }

        mestoryTime()
    }

    private fun getData() {
        // 데이터 읽어오기
        val sharedPreferences: SharedPreferences = requireContext().getSharedPreferences("userInfo", MODE_PRIVATE)
        userName = sharedPreferences.getString("userName", userName)
        userImg = sharedPreferences.getString("userImg", userImg)
    }

    private fun getData2() {
        // 데이터 읽어오기
        val sharedPreferences: SharedPreferences = requireContext().getSharedPreferences("getRes", MODE_PRIVATE)
        memberId = sharedPreferences.getInt("member_id", memberId!!)
        accessToken = sharedPreferences.getString("getAccessToken", accessToken)
    }

    private fun mestoryTime() {
        val mestoryTimeService = GetMestoryTimeService()
        mestoryTimeService.getMestoryTimeView(this@MestoryFragment)

        getData2()
        Log.d("data", "$accessToken, $memberId, $formattedDate")
        mestoryTimeService.getMestoryTime("Bearer " + accessToken, memberId!!, formattedDate)
    }

    override fun onGetMestoryTimeSuccess(response: GetMestoryTimeRes) {
        Log.d("mestory 조회 성공", response.result.toString())
        val mestoryRVAdapter = MestoryRVAdapter(requireContext(), response.result.me_story_result)
        // RecyclerView 어댑터 설정
        binding.mestoryCategoryRv.layoutManager = LinearLayoutManager(requireContext())
        // RecyclerView 레이아웃 매니저 설정
        binding.mestoryCategoryRv.adapter = mestoryRVAdapter
    }

    override fun onGetMestoryTimeFailure(isSuccess: Boolean, code: String, message: String) {
        Log.d("mestory 조회 실패", message)
    }
}
