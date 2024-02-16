package com.example.plan_me.ui.dialog

import android.app.Dialog
import android.content.Context
import com.example.plan_me.databinding.FragmentDialogTimerpickBinding

class DialogTimerPickFragment(context : Context): Dialog(context) {
    private lateinit var binding: FragmentDialogTimerpickBinding
    fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = // Inflate your dialog layout here

        // ... (기존 코드)

            // 확인 버튼 클릭 리스너 설정
            binding.dialogTimerpickConfirm.setOnClickListener {
                // TimerFragment에 반영하는 코드 호출
                updateTimerFragment()
                // 다이얼로그 닫기
                dismiss()
            }

        return binding.root

}