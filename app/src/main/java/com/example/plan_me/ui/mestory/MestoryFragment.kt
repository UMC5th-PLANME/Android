package com.example.plan_me.ui.mestory

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.plan_me.data.remote.dto.auth.MemberRes
import com.example.plan_me.data.remote.dto.mestory.GetMestoryTimeRes
import com.example.plan_me.data.remote.dto.schedule.ScheduleList
import com.example.plan_me.data.remote.service.auth.MemberService
import com.example.plan_me.data.remote.service.mestory.GetMestoryTimeService
import com.example.plan_me.data.remote.view.auth.LookUpMemberView
import com.example.plan_me.data.remote.view.mestory.GetMestoryTimeView
import com.example.plan_me.databinding.FragmentMestoryBinding
import com.example.plan_me.ui.CircleTransform
import com.example.plan_me.ui.dialog.DialogDailyCalenderFragment
import com.example.plan_me.ui.dialog.DialogDailyCalenderInterface
import com.example.plan_me.ui.setting.AccountFragment
import com.example.plan_me.utils.viewModel.CalendarViewModel
import com.example.plan_me.utils.viewModel.CalendarViewModelFactory
import com.squareup.picasso.Picasso
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MestoryFragment : Fragment(),
    GetMestoryTimeView,
    LookUpMemberView,
    DialogDailyCalenderInterface{
    private lateinit var binding: FragmentMestoryBinding

    private var userName: String? = ""
    private var userImg: String? = ""

    private var accessToken: String? = ""
    private var memberId: Int? = 0

    private val today = LocalDate.now()
    private val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
    private val formattedDate = today.format(formatter)

    private lateinit var dialogDailyCalenderFragment :DialogDailyCalenderFragment
    private lateinit var calendarViewModel: CalendarViewModel
    private lateinit var selectDate: LocalDate
    private val currentWeek = LocalDate.now()

    var groupedSchedules = mutableMapOf<Int, MutableList<ScheduleList>>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMestoryBinding.inflate(layoutInflater)

        val factory = CalendarViewModelFactory(requireActivity().getSharedPreferences("getRes", Context.MODE_PRIVATE))
        calendarViewModel = ViewModelProvider(requireActivity(), factory).get(CalendarViewModel::class.java)
        selectDate = currentWeek

        initRV()
        initProgressBar()
        clickListener()

        getData2()
        setLookUpService()

        return binding.root
    }

    private fun setLookUpService() {
        val setLookUpService = MemberService()
        setLookUpService.setLookUpMemberView(this@MestoryFragment)
        setLookUpService.getLookUpMember("Bearer " + accessToken)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mestoryTime()
    }

    private fun initRV(){
        groupedSchedules = calendarViewModel.filteringSchedule(selectDate, groupedSchedules)
        val mestoryRVAdapter = MestoryRVAdapter(calendarViewModel.filteringCategoryHidden(groupedSchedules),groupedSchedules , requireContext())
        // RecyclerView 어댑터 설정
        binding.mestoryCategoryRv.layoutManager = LinearLayoutManager(requireContext())
        // RecyclerView 레이아웃 매니저 설정
        binding.mestoryCategoryRv.adapter = mestoryRVAdapter
    }
    private fun initProgressBar() {
        var totalItemCount = 0
        var totalFinishCount =0
        // 각 키(그룹)에 대해 반복
        for (entry in groupedSchedules) {
            // 현재 그룹의 스케줄 목록
            if (calendarViewModel._categoryList.value!!.find { it.categoryId == entry.key }?.meStoryHidden == false) {
                val scheduleList = entry.value
                // 현재 그룹의 스케줄 목록에 있는 항목 수를 총 항목 수에 추가
                totalItemCount += scheduleList.size
                totalFinishCount += scheduleList.count { it.status }
            }
        }
        binding.mestoryTotalPercentLineGray.progress = ((totalFinishCount.toFloat()/ totalItemCount.toFloat()) * 100).toInt()
        binding.mestoryCategoryProgressPercentageTv.text =((totalFinishCount.toFloat()/ totalItemCount.toFloat()) * 100).toInt().toString() + "%"
    }


    private fun clickListener() {
        binding.mestoryCalendarIv.setOnClickListener {
            dialogDailyCalenderFragment = DialogDailyCalenderFragment(requireContext(), this)
            dialogDailyCalenderFragment.show()
        }
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
    }

    override fun onGetMestoryTimeFailure(isSuccess: Boolean, code: String, message: String) {
        Log.d("mestory 조회 실패", message)
    }

    override fun onClickCalender(date: LocalDate?) {
        selectDate = date!!
        binding.mestoryProfileDateTv.text = selectDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))
        initRV()
    }

    private fun updateUI() {
        binding.mestoryProfileNameTv.text = userName
        if (userImg != AccountFragment.DEFAULT_IMG && userImg != "null") {
            if (userImg == "") {
                Picasso.get().load(userImg).transform(CircleTransform())
                    .into(binding.mestoryProfileIv)
            } else {
                Picasso.get().load(userImg).transform(CircleTransform())
                    .into(binding.mestoryProfileIv)
            }
        }
    }

    override fun onGetMemberSuccess(response: MemberRes) {
        Log.d("회원 조회", response.result.toString())
        userName = response.result.nickname
        userImg = response.result.profile_image

        updateUI()
    }

    override fun onGetMemberFailure(isSuccess: Boolean, code: String, message: String) {
        Log.d("회원 조회 실패", message)
    }
}
