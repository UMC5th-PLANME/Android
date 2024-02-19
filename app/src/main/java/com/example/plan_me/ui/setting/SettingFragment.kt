package com.example.plan_me.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import androidx.fragment.app.Fragment
import com.example.plan_me.R
import com.example.plan_me.databinding.ActivitySettingBinding

class SettingFragment: Fragment() {
    private lateinit var binding: ActivitySettingBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = ActivitySettingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickListener()
    }

    private fun clickListener() {
        binding.settingAccountTv.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.fadein, R.anim.fadeout)
                .replace(R.id.main_frm, AccountFragment())
                .commitAllowingStateLoss()
        }

        binding.settingMestoryTv.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.fadein, R.anim.fadeout)
                .replace(R.id.main_frm, MestorySettingFragment())
                .commitAllowingStateLoss()
        }

        binding.settingConsumerTv.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.fadein, R.anim.fadeout)
                .replace(R.id.main_frm, ConsumerSettingFragment())
                .commitAllowingStateLoss()
        }

        binding.settingTermsTv.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.fadein, R.anim.fadeout)
                .replace(R.id.main_frm, TermsSettingFragment())
                .commitAllowingStateLoss()
        }

        binding.settingInformationTv.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.fadein, R.anim.fadeout)
                .replace(R.id.main_frm, InfoSettingFragment())
                .commitAllowingStateLoss()
        }
    }
}