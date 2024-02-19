package com.example.plan_me.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.example.plan_me.R
import com.example.plan_me.databinding.ActivityProcessPoliciesBinding

class TermsPoliciesPersonalFragment: Fragment() {
    private lateinit var binding: ActivityProcessPoliciesBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = ActivityProcessPoliciesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.processPoliciesBackBtn.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.fadein, R.anim.fadeout)
                .replace(R.id.main_frm, TermsSettingFragment())
                .addToBackStack(null)
                .commit()
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressedCallback)
    }
    private val onBackPressedCallback = object  : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            parentFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.fadein, R.anim.fadeout2)
                .replace(R.id.main_frm, TermsSettingFragment())
                .commitAllowingStateLoss()
        }
    }
}