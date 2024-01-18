package com.example.plan_me.ui.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.plan_me.databinding.FragmentDialogBtmCalendarBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DialogCalendarBtmFragment:BottomSheetDialogFragment() {
    lateinit var binding : FragmentDialogBtmCalendarBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDialogBtmCalendarBinding.inflate(layoutInflater)

        return binding.root
    }
}