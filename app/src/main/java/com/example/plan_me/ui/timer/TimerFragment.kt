package com.example.plan_me.ui.timer

import android.content.Context
import android.content.SharedPreferences
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.plan_me.R
import com.example.plan_me.data.remote.dto.category.CategoryList
import com.example.plan_me.data.remote.dto.mestory.SaveFocusTimeRes
import com.example.plan_me.data.remote.dto.mestory.TotalFocusTime
import com.example.plan_me.data.remote.dto.timer.GetTimerRes
import com.example.plan_me.data.remote.dto.timer.TimerSettingReq
import com.example.plan_me.data.remote.dto.timer.TimerSettingRes
import com.example.plan_me.data.remote.service.mestory.SaveFocusTimeService
import com.example.plan_me.data.remote.service.timer.TimerService
import com.example.plan_me.data.remote.view.mestory.SaveFocusTimeView
import com.example.plan_me.data.remote.view.timer.GetTimerView
import com.example.plan_me.data.remote.view.timer.TimerView
import com.example.plan_me.databinding.FragmentTimerFocusBinding
import com.example.plan_me.ui.dialog.CustomToast
import com.example.plan_me.ui.dialog.DialogSaveFocusTimeFragment
import com.example.plan_me.ui.dialog.DialogSaveFocusTimeInterface
import com.example.plan_me.ui.dialog.DialogTimerCategoryFragment
import com.example.plan_me.ui.dialog.DialogTimerPickFragment
import com.example.plan_me.ui.dialog.DialogTimerPickInterface
import com.example.plan_me.utils.viewModel.CalendarViewModel
import com.example.plan_me.utils.viewModel.CalendarViewModelFactory
import com.example.plan_me.utils.viewModel.ProgressViewModel
import com.example.plan_me.utils.viewModel.TimerViewModel
import com.example.plan_me.utils.viewModel.TimerViewModelFactory


class TimerFragment : Fragment(),
    DialogTimerPickInterface,
    ResetConfirmedListener,
    DialogTimerCategoryFragment.SendData,
    TimerView,
    GetTimerView,
    DialogSaveFocusTimeInterface,
    SaveFocusTimeView{
    private lateinit var binding: FragmentTimerFocusBinding

    private lateinit var dialogTimerPickFragment : DialogTimerPickFragment
    private lateinit var dialogCautionResetTime: DialogCautionResetTimeFragment

    private lateinit var calendarViewModel: CalendarViewModel
    private lateinit var category_timer : DialogTimerCategoryFragment

    private lateinit var timerViewModel: TimerViewModel

    private lateinit var dialogSaveFocusTimeFragment: DialogSaveFocusTimeFragment

    private var category_id: Int = -1

    lateinit var focus:String
    lateinit var breakTime :String


    private lateinit var progressViewModel: ProgressViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTimerFocusBinding.inflate(layoutInflater, container, false)

        val factory = CalendarViewModelFactory(requireContext().getSharedPreferences("getRes", AppCompatActivity.MODE_PRIVATE))
        calendarViewModel = ViewModelProvider(requireActivity(), factory).get(CalendarViewModel::class.java)
        progressViewModel = ViewModelProvider(this).get(ProgressViewModel::class.java)


        binding.progressViewModel = progressViewModel
        binding.lifecycleOwner = this

        calendarViewModel._categoryList.observe(viewLifecycleOwner, Observer {
            init()
            getTimer()
        })

        progressViewModel._isEnd.observe(viewLifecycleOwner, Observer {
            if (progressViewModel._isEnd.value == true) {
                progressViewModel.clear()
                binding.timerFocusPlayBtn.visibility = View.VISIBLE
                binding.timerFocusPauseBtn.visibility = View.GONE
                dialogSaveFocusTimeFragment = DialogSaveFocusTimeFragment(requireContext(), this)
                dialogSaveFocusTimeFragment.show()
            }
        })

        progressViewModel._time.observe(viewLifecycleOwner, Observer {
            if ( progressViewModel._time.value!! == "00:00:00") {
                binding.timerFocusPlayBtn.isEnabled = false
            }else {
                binding.timerFocusPlayBtn.isEnabled = true
            }
        })

        val getResSharedPreferences = requireContext().getSharedPreferences("getRes", AppCompatActivity.MODE_PRIVATE)
        val categoryIdSharedPreferences = requireContext().getSharedPreferences("category_id", AppCompatActivity.MODE_PRIVATE)
        category_id = calendarViewModel._currentCategory.value!!.categoryId
        val timerFactory = TimerViewModelFactory(getResSharedPreferences, categoryIdSharedPreferences)
        timerViewModel = ViewModelProvider(this, timerFactory).get(TimerViewModel::class.java)

        init()
        clickListener()

        return binding.root
    }

    private fun init() {
        Log.d("current",calendarViewModel._currentCategory.value!!.toString())
        val newColor = ContextCompat.getColor(requireContext(),  calendarViewModel._currentCategory.value!!.color) // Replace with your desired color resource
        val shape = GradientDrawable()
        shape.shape = GradientDrawable.RECTANGLE
        shape.setColor(newColor)
        shape.cornerRadius = resources.getDimension(R.dimen.planner_corner_raidus) // 원하는 radius 값으로 대체

        // 설정한 모양을 레이아웃에 적용
        binding.timerFocusCategoryLo.background = shape
        binding.timerFocusCategoryTv.text = calendarViewModel._currentCategory.value!!.name
        binding.timerFocusStudyEmoticon.text = calendarViewModel._currentCategory.value!!.emoticon
    }

    private fun clickListener() {
        /*if (progressViewModel._hour.value == -1 &&progressViewModel._min.value == -1&&progressViewModel._sec.value == -1) {
            binding.timerFocusPlayBtn.visibility = View.VISIBLE
            binding.timerFocusPauseBtn.visibility = View.GONE
            progressViewModel._time.value = "CLEAR"

        }  //끝나면*/
        // menu button
        binding.timerFocusMenuBtn.setOnClickListener {
            category_timer = DialogTimerCategoryFragment(
                requireContext(),
                calendarViewModel._categoryList.value!!,
                this
            )
            category_timer.show()
        }

        // Setting button
        binding.timerFocusSettingBtn.setOnClickListener {
            Log.d("setting: timer-focus", "Time Setting")

            // Timer-Break 에 시간이 남았다면 -> 초기화 알림 문구

            // TimerSetting
            dialogTimerPickFragment = DialogTimerPickFragment(requireContext(), this)
            dialogTimerPickFragment.show()
        }

        binding.timerFocusPlayBtn.setOnClickListener {
            progressViewModel.startProgress(progressViewModel._hour, progressViewModel._min, progressViewModel._sec)
            binding.timerFocusPlayBtn.visibility = View.GONE
            binding.timerFocusPauseBtn.visibility = View.VISIBLE
        }

        binding.timerFocusPauseBtn.setOnClickListener {
            progressViewModel.stopProgress()
            binding.timerFocusPlayBtn.visibility = View.VISIBLE
            binding.timerFocusPauseBtn.visibility = View.GONE
        }

        // Reset 클릭 시
        binding.timerFocusResetBtn.setOnClickListener {
            // Dialog 타이머 초기화 경고 띄우기
            dialogCautionResetTime = DialogCautionResetTimeFragment(this as Context, this)
            dialogCautionResetTime.show()
        }

    }


    // ResetConfirmedListener 인터페이스 구현
    override fun onResetConfirmed(isConfirmed: Boolean) {

        if (isConfirmed) {

            binding.timerFocusTimeTv.text = "0:00:00"
        }
    }


    private fun saveResponse() {
        val sharedPreferences: SharedPreferences = requireContext().getSharedPreferences("category_id", AppCompatActivity.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putInt("category_id", category_id)
        editor.apply()
    }

    private fun setTimerService(timerSettingReq: TimerSettingReq) {
        val spf = requireContext().getSharedPreferences("getRes", AppCompatActivity.MODE_PRIVATE)
        val spf2 = requireContext().getSharedPreferences("category_id", AppCompatActivity.MODE_PRIVATE)
        val access_token = spf.getString("getAccessToken", "")
        val categoryId = spf2.getInt("category_id", 0)

        val timerService = TimerService()
        timerService.setTimerView(this@TimerFragment)
        timerService.setTimer("Bearer " + access_token, categoryId, timerSettingReq)
    }

    override fun onTimerSettingConfirm(focusMin: Int, breakMin: Int, repeatCount: Int) {  //시간 설정

        progressViewModel._hour.value = focusMin /60
        progressViewModel._min.value = focusMin%60
        progressViewModel._sec.value = 0

        progressViewModel._break_hour.value = breakMin/60
        progressViewModel._break_min.value = breakMin%60
        progressViewModel._break_sec.value = 0

        progressViewModel._repeat.value = repeatCount

        progressViewModel._time.value =  String.format("%02d:%02d:%02d", progressViewModel._hour.value, progressViewModel._min.value, progressViewModel._sec.value)
        Log.d("progressViewModel._time.value!!", progressViewModel._time.value!!)
        val timerSettingReq = TimerSettingReq(progressViewModel._time.value!!, progressViewModel._break_time.value!!, repeatCount)

        setTimerService(timerSettingReq)
    }
    override fun onTimerSettingCancel() {
        dialogTimerPickFragment.dismiss()
        Log.d("TimerSettingCancel", "cancel")
    }

    override fun sendData(category: CategoryList) {
        calendarViewModel.sendCategory(category)

        category_id = category.categoryId
        Log.d("category_id", category_id.toString())
        saveResponse()

        val newColor = ContextCompat.getColor(requireContext(),  calendarViewModel._currentCategory.value!!.color) // Replace with your desired color resource
        val shape = GradientDrawable()
        shape.shape = GradientDrawable.RECTANGLE
        shape.setColor(newColor)
        shape.cornerRadius = resources.getDimension(R.dimen.planner_corner_raidus) // 원하는 radius 값으로 대체

        binding.timerFocusCategoryTv.setText(category.name)
        binding.timerFocusCategoryLo.background = shape
        binding.timerFocusStudyEmoticon.setText(category.emoticon)

        binding.timerFocusSettingBtn.isEnabled = true
        category_timer.dismiss()
        progressViewModel.stopProgress()
        binding.timerFocusPlayBtn.visibility = View.VISIBLE
        binding.timerFocusPauseBtn.visibility = View.GONE
        progressViewModel.clear()
        getTimer()
    }
    private fun getTimer() {
        val access_token = "Bearer " + requireActivity().getSharedPreferences("getRes", AppCompatActivity.MODE_PRIVATE).getString("getAccessToken", "")

        val timerService = TimerService()
        timerService.getTimerView(this)
        timerService.getTimer(access_token, category_id)
    }

    override fun onSetTimerSuccess(response: TimerSettingRes) {
        Log.d("TIMER SAVE", response.result.toString())
    }

    override fun onSetTimerFailure(isSuccess: Boolean, code: String, message: String) {
        Log.d("TIMER-SAVE-FAILURE", message)
    }

    override fun onGetTimerSuccess(response: GetTimerRes) {
        focus = response.result.focusTime
        breakTime = response.result.breakTime
        Log.d("ddddd", focus.substring(0,2).toInt().toString())
        progressViewModel._hour.value = focus.substring(0,2).toInt()
        progressViewModel._min.value = focus.substring(3,5).toInt()
        progressViewModel._sec.value = 0

        progressViewModel._break_hour.value = breakTime.substring(0,2).toInt()
        progressViewModel._break_min.value = breakTime.substring(3,5).toInt()
        progressViewModel._break_sec.value = 0

        progressViewModel._repeat.value = response.result.repeatCnt

        progressViewModel.initTime( progressViewModel._hour,  progressViewModel._min,  progressViewModel._sec)
    }

    override fun onGetTimerFailure(isSuccess: Boolean, code: String, message: String) {
        progressViewModel._hour.value = 0
        progressViewModel._min.value = 0
        progressViewModel._sec.value = 0

        progressViewModel._break_hour.value = 0
        progressViewModel._break_min.value = 0
        progressViewModel._break_sec.value = 0

        progressViewModel._repeat.value = 1

        progressViewModel.initTime( progressViewModel._hour,  progressViewModel._min,  progressViewModel._sec)
    }

    override fun onSaveFocusTimeConfirm() {
        val access_token = "Bearer " + requireActivity().getSharedPreferences("getRes",
        AppCompatActivity.MODE_PRIVATE
    ).getString("getAccessToken", "")
        Log.d("focus", focus)
        val meStoryService = SaveFocusTimeService()
        meStoryService.saveFocusTimeView(this)
        meStoryService.setSaveFocusTime(access_token, category_id, TotalFocusTime(focus) )
    }

    override fun onSaveFocusTimeCancel() {
        TODO("Not yet implemented")
    }

    override fun onSaveFocusTimeSuccess(response: SaveFocusTimeRes) {
        val customToast = CustomToast
        customToast.createToast(requireContext(),"집중 시간이 저장되었습니다", 300, true)
    }

    override fun onSaveFocusTimeFailure(isSuccess: Boolean, code: String, message: String) {
        TODO("Not yet implemented")
    }
}