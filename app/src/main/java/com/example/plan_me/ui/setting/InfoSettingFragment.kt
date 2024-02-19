package com.example.plan_me.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.example.plan_me.R
import com.example.plan_me.databinding.ActivityInformationBinding

class InfoSettingFragment: Fragment() {
    private lateinit var binding: ActivityInformationBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = ActivityInformationBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.informationBackBtn.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.fadein, R.anim.fadeout)
                .replace(R.id.main_frm, SettingFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.informationOpensourceTv.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.fadein, R.anim.fadeout)
                .replace(R.id.main_frm, InfoOpensourceFragment())
                .commitAllowingStateLoss()
        }

        binding.informationProducerTv.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.fadein, R.anim.fadeout)
                .replace(R.id.main_frm, InfoProducerFragment())
                .commitAllowingStateLoss()
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressedCallback)
    }
    private val onBackPressedCallback = object  : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            parentFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.fadein, R.anim.fadeout2)
                .replace(R.id.main_frm, SettingFragment())
                .commitAllowingStateLoss()
        }
    }
}