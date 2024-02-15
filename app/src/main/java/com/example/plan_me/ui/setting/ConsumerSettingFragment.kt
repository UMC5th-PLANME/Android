package com.example.plan_me.ui.setting

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.plan_me.R
import com.example.plan_me.databinding.ActivityConsumerCenterBinding
import com.example.plan_me.ui.dialog.DialogBottomConsumerCenterActivity

class ConsumerSettingFragment: Fragment() {
    private lateinit var binding: ActivityConsumerCenterBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = ActivityConsumerCenterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.consumerCenterEmailTv.setOnClickListener {
            showDialog(DialogBottomConsumerCenterActivity(requireContext()))
        }

        binding.consumerCenterBackBtn.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.fadein, R.anim.fadeout)
                .replace(R.id.main_frm, SettingFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    private fun showDialog(dialog: Dialog) {
        dialog.show()
    }
}