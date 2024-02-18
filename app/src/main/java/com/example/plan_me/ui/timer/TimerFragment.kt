package com.example.plan_me.ui.timer

import android.content.Context
import android.content.SharedPreferences
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.os.CountDownTimer
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
import com.example.plan_me.data.local.database.SettingDatabase
import com.example.plan_me.data.local.database.TimeDatabase
import com.example.plan_me.data.local.entity.SettingTime
import com.example.plan_me.data.local.entity.Time
import com.example.plan_me.data.remote.dto.category.CategoryList
import com.example.plan_me.data.remote.dto.timer.TimerSettingReq
import com.example.plan_me.data.remote.dto.timer.TimerSettingRes
import com.example.plan_me.data.remote.service.timer.TimerService
import com.example.plan_me.data.remote.view.timer.TimerView
import com.example.plan_me.databinding.FragmentTimerFocusBinding
import com.example.plan_me.ui.dialog.CustomToast
import com.example.plan_me.ui.dialog.DialogCautionResetTimeFragment
import com.example.plan_me.ui.dialog.DialogDeleteCategoryCheckFragment
import com.example.plan_me.ui.dialog.DialogTimerCategoryFragment
import com.example.plan_me.ui.dialog.DialogTimerPickFragment
import com.example.plan_me.ui.dialog.DialogTimerPickInterface
import com.example.plan_me.utils.viewModel.CalendarViewModel
import com.example.plan_me.utils.viewModel.CalendarViewModelFactory
import com.example.plan_me.utils.viewModel.TimerViewModel
import com.example.plan_me.utils.viewModel.TimerViewModelFactory


class TimerFragment : Fragment(),
    DialogTimerPickInterface,
    ResetConfirmedListener,
    DialogDeleteCategoryCheckFragment.SendDeleteMessage,
    DialogTimerCategoryFragment.SendData,
    TimerView{
    private lateinit var binding: FragmentTimerFocusBinding

    private lateinit var dialogTimerPickFragment : DialogTimerPickFragment
    private lateinit var dialogCautionResetTime: DialogCautionResetTimeFragment

    private lateinit var calendarViewModel: CalendarViewModel
    private lateinit var category_timer : DialogTimerCategoryFragment

    private lateinit var timerViewModel: TimerViewModel

    private var timer: CountDownTimer? = null
    private var remainingTimeInMillis: Long = 0

    private var category_id: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTimerFocusBinding.inflate(layoutInflater, container, false)

        val factory = CalendarViewModelFactory(requireContext().getSharedPreferences("getRes", AppCompatActivity.MODE_PRIVATE))
        calendarViewModel = ViewModelProvider(this, factory).get(CalendarViewModel::class.java)

        val getResSharedPreferences = requireContext().getSharedPreferences("getRes", AppCompatActivity.MODE_PRIVATE)
        val categoryIdSharedPreferences = requireContext().getSharedPreferences("category_id", AppCompatActivity.MODE_PRIVATE)

        val timerFactory = TimerViewModelFactory(getResSharedPreferences, categoryIdSharedPreferences)
        timerViewModel = ViewModelProvider(this, timerFactory).get(TimerViewModel::class.java)

        binding.timerFocusSettingBtn.isEnabled = false

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
        // menu button
        binding.timerFocusMenuBtn.setOnClickListener{
            category_timer = DialogTimerCategoryFragment(requireContext(), calendarViewModel._categoryList.value!!, this)
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

            val settingTimeDB = SettingDatabase.getInstance(requireContext())!!
            var remainingTime = settingTimeDB.SettingTimeDao().getRemainingFocusTime(2)

            remainingTimeInMillis = remainingTime

            if (remainingTime == 0L) return@setOnClickListener

            val time = settingTimeDB.SettingTimeDao().getTime()
            Log.d("TimerFragment", "$time")

            // Timer 생성 or 이어서 실행 -> milliseconds 로 값 전달
            if (remainingTimeInMillis > 0) {
                startTimer(remainingTimeInMillis)
            }

        }

        binding.timerFocusPauseBtn.setOnClickListener {
            pauseTimer()
            saveElapsedTime()
        }

        // Reset 클릭 시
        binding.timerFocusResetBtn.setOnClickListener {
            // Dialog 타이머 초기화 경고 띄우기
            dialogCautionResetTime = DialogCautionResetTimeFragment(this as Context, this)
            dialogCautionResetTime.show()
        }

    }

    // Timer를 시작하거나 이어서 실행하는 함수
    private fun startTimer(millis: Long) {
        timer = object : CountDownTimer(millis, 1000) {     // 1초마다
            override fun onTick(millisUntilFinished: Long) {
                remainingTimeInMillis = millisUntilFinished
                updateTimerText()   // Text 를 업데이트
            }

            override fun onFinish() {
                remainingTimeInMillis = 0
                updateTimerText()
                setTimerRunning(false)
                saveElapsedTime()  // Timer 종료 시 저장
            }
        }
        timer?.start()
        setTimerRunning(true)   // 버튼 변경
    }

    // Timer를 일시정지하는 함수
    private fun pauseTimer() {
        timer?.cancel()
        setTimerRunning(false)
    }

    // Timer의 시간을 텍스트뷰에 업데이트하는 함수
    private fun updateTimerText() {
        val seconds = (remainingTimeInMillis / 1000) % 60
        val minutes = (remainingTimeInMillis / (1000 * 60)) % 60
        val hours = remainingTimeInMillis / (1000 * 60 * 60)

        val formattedTime = String.format("%02d:%02d:%02d", hours, minutes, seconds)
        binding.timerFocusTimeClearTv.text = formattedTime
    }

    // 포커스를 잃거나 pause 버튼이 눌린 경우에 진행된 시간을 저장하는 함수
    private fun saveElapsedTime() {
        val settingTimeDB = SettingDatabase.getInstance(requireContext())!!
        val elapsedTimeInMillis = remainingTimeInMillis
        settingTimeDB.SettingTimeDao().updateRemainingFocusTime(elapsedTimeInMillis, 2)

        val saveTime =  settingTimeDB.SettingTimeDao().getTime()
        Log.d("saveElapsedTime", "TimeTable: $saveTime")    // 저장된 시간 확인
    }
    private fun setTimerRunning(isRunning: Boolean){

        if(isRunning) { // Timer 실행 O
            binding.timerFocusPlayBtn.visibility = View.GONE
            binding.timerFocusPauseBtn.visibility = View.VISIBLE
        }
        else {
            binding.timerFocusPlayBtn.visibility = View.VISIBLE
            binding.timerFocusPauseBtn.visibility = View.GONE
        }
    }

    // ResetConfirmedListener 인터페이스 구현
    override fun onResetConfirmed(isConfirmed: Boolean) {
        val timeDB = TimeDatabase.getInstance(this as Context)!!
        val settingTimeDB = SettingDatabase.getInstance(this as Context)!!

        if (isConfirmed) {
            // "저장 및 재설정"이 확인되었을 때의 동작
            // timeTable set:2 시간 -> 0:00:00 으로 바꾸기
            timeDB.timeDao().updateTime(0,0,1,2)

            // SettingTable set:2 시간 ->
            settingTimeDB.SettingTimeDao().updateTime(0,0,0,0,2)

            binding.timerFocusTimeTv.text = "0:00:00"
        }
    }



    fun setFocusTime(focusTime: Int) {
        val seconds = (focusTime / 1000) % 60
        val minutes = (focusTime / (1000 * 60)) % 60
        val hours = focusTime / (1000 * 60 * 60)

        val formattedTime = String.format("%02d:%02d:%02d", hours, minutes, seconds)
        Log.d("Dialog -> TimerFragment", "FocusTime : $hours, $minutes, $seconds")

        binding.timerFocusTimeTv.visibility = View.INVISIBLE
        binding.timerFocusTimeClearTv.visibility = View.VISIBLE
        binding.timerFocusTimeClearTv.text = formattedTime
    }

    fun setBreakTime(breakTime: Int) {
        val seconds = (breakTime / 1000) % 60
        val minutes = (breakTime / (1000 * 60)) % 60
        val hours = breakTime / (1000 * 60 * 60)

        val formattedTime = String.format("%02d:%02d:%02d", hours, minutes, seconds)
        Log.d("Dialog -> TimerFragment", "BreakTime : $hours, $minutes, $seconds")
        binding.timerFocusTimeClearTv.text = formattedTime
    }

    private fun updateInitRoomDB(focusMin: Int, breakMin: Int, repeatCount: Int) {
        val timeDB = TimeDatabase.getInstance(requireContext())!!
        val settingTimeDB = SettingDatabase.getInstance(requireContext())!!
        val existingTime = timeDB.timeDao().getSavedTime(2)

        if (existingTime != null) {
            // set: 2가 이미 테이블에 존재하면 TimeTable 에 업데이트
            timeDB.timeDao().updateTime(focusMin, breakMin, repeatCount, 2)

            // SettingTimeTable 에 업데이트
            settingTimeDB.SettingTimeDao().updateTime(convertMinutesToMilliseconds(focusMin),
                convertMinutesToMilliseconds(focusMin),
                convertMinutesToMilliseconds(breakMin),
                convertMinutesToMilliseconds(breakMin), 2)
        } else {
            // set: 2가 테이블에 없으면 TimeTable 에 삽입
            timeDB.timeDao().insert(
                Time(
                    focusMin,
                    breakMin,
                    repeatCount
                ).apply {
                    set = 2
                }
            )

            // SettingTimeTable 에 업데이트
            settingTimeDB.SettingTimeDao().insert(
                SettingTime(
                    convertMinutesToMilliseconds(focusMin),
                    convertMinutesToMilliseconds(focusMin),
                    convertMinutesToMilliseconds(breakMin),
                    convertMinutesToMilliseconds(breakMin)
                ).apply {
                    set = 2
                }
            )
        }
    }
    private fun convertMinutesToMilliseconds(minutes: Int): Int {
        return minutes * 60 * 1000
    }

    private fun timeSetting(focusMin: Int, breakMin: Int, repeatCount : Int) {
        if (focusMin == 0) {
            val customToast = CustomToast
            customToast.createToast(requireContext(), "집중 시간을 설정해주세요.",  500, false)
        } else {
            binding.timerFocus.text = "FOCUS"
            val focusTimeInMilliSeconds = focusMin * 60 * 1000
            setFocusTime(focusTimeInMilliSeconds)
        }

        updateInitRoomDB(focusMin, breakMin, repeatCount)
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

    override fun onTimerSettingConfirm(focusMin: Int, breakMin: Int, repeatCount: Int) {
        timeSetting(focusMin, breakMin, repeatCount)
        Log.d("TimerSetting", "focusMin: $focusMin, breakMin: $breakMin, repeatCount: $repeatCount")

        val focus_seconds = (focusMin / 60) % 60
        val focus_minutes = focusMin % 60
        val focus_hours = focusMin / 60

        val break_seconds = (breakMin / 60) % 60
        val break_minutes = breakMin % 60
        val break_hours = breakMin / 60

        val focus_formattedTime = String.format("%02d:%02d:%02d", focus_hours, focus_minutes, focus_seconds)
        val break_formattedTime = String.format("%02d:%02d:%02d", break_hours, break_minutes, break_seconds)

        val timerSettingReq = TimerSettingReq(focus_formattedTime, break_formattedTime, repeatCount)

        setTimerService(timerSettingReq)
    }

    override fun onTimerSettingCancel() {
        dialogTimerPickFragment.dismiss()
        Log.d("TimerSettingCancel", "cancel")
    }

    override fun sendDeleteMessage(category: CategoryList) {
        category_timer.dismiss()
        calendarViewModel.getCategoryList()
        if (calendarViewModel._currentCategory.value == category) {
            calendarViewModel.sendCategory(CategoryList(-1,"Schedule","\uD83D\uDCC6" ,R.color.light_gray, false, "","" ))
        }
        val customToast = CustomToast
        customToast.createToast(requireContext(), calendarViewModel._categoryList.value.toString(), 300, true)
    }

    override fun sendData(category: CategoryList) {
        calendarViewModel.sendCategory(category)

        category_id = category.categoryId
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

        timerViewModel.getTimer()

        // focusML observe
        timerViewModel.getFocus().observe(viewLifecycleOwner, Observer { focusTime ->
            // focusTime이 "00:00:00"이면 breakTime을 사용하여 UI에 반영
            if (focusTime == "00:00:00") {
                timerViewModel.getBreak().value?.let { breakTime ->
                    binding.timerFocusTimeTv.text = breakTime
                    Log.d("focusTime", "Break Time applied: $breakTime")
                }
            } else {
                // focusTime이 "00:00:00"이 아니면 focusTime을 UI에 반영
                binding.timerFocusTimeTv.text = focusTime
                Log.d("focusTime", focusTime)
            }
        })

        // breakML observe
        timerViewModel.getBreak().observe(viewLifecycleOwner, Observer { breakTime ->
            // breakTime을 UI에 반영
            Log.d("breakTime", breakTime)
        })

        // repeatCntML observe
        timerViewModel.getRepeat().observe(viewLifecycleOwner, Observer { repeatCount ->
            // repeatCount를 UI에 반영
            Log.d("repeatCount", repeatCount.toString())
        })
    }

    override fun onSetTimerSuccess(response: TimerSettingRes) {
        Log.d("TIMER SAVE", response.result.toString())
    }

    override fun onSetTimerFailure(isSuccess: Boolean, code: String, message: String) {
        Log.d("TIMER-SAVE-FAILURE", message)
    }
}