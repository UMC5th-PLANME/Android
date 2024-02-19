package com.example.plan_me.ui.mestory

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.plan_me.R
import com.example.plan_me.data.local.entity.Category_input
import com.example.plan_me.data.remote.dto.category.AllCategoryRes
import com.example.plan_me.data.remote.dto.category.CategoryList
import com.example.plan_me.data.remote.dto.schedule.ScheduleList
import com.example.plan_me.data.remote.service.category.CategoryService
import com.example.plan_me.data.remote.view.category.AllCategoryView
import com.example.plan_me.databinding.ActivityMainBinding
import com.example.plan_me.databinding.ActivitySharePageBinding
import com.example.plan_me.ui.CircleTransform
import com.example.plan_me.ui.add.ScheduleAddActivity
import com.example.plan_me.ui.all.AllFragment
import com.example.plan_me.ui.dialog.CustomToast
import com.example.plan_me.ui.dialog.DialogAddFragment
import com.example.plan_me.ui.dialog.DialogDeleteCategoryCheckFragment
import com.example.plan_me.ui.dialog.DialogDeleteCategoryFragment
import com.example.plan_me.ui.dialog.DialogModifyCategoryFragment
import com.example.plan_me.ui.dialog.DialogModifyFragment
import com.example.plan_me.ui.main.MainActivity
import com.example.plan_me.ui.mestory.MestoryFragment
import com.example.plan_me.ui.planner.PlannerFragment
import com.example.plan_me.ui.setting.AccountFragment
import com.example.plan_me.ui.timer.TimerFragment
import com.example.plan_me.ui.setting.SettingFragment
import com.example.plan_me.utils.alarm.AlarmFunctions
import com.example.plan_me.utils.viewModel.CalendarViewModel
import com.example.plan_me.utils.viewModel.CalendarViewModelFactory
import com.example.plan_me.utils.viewModel.NaviFragmentViewModel
import com.squareup.picasso.Picasso

class MestoryShareActivity :
    AppCompatActivity(){

    private lateinit var binding: ActivitySharePageBinding
    private lateinit var categoryList : List<CategoryList>
    var categoryPercentMap = mutableMapOf<Int, Int>()
    private var userName: String? = ""
    private var userImg: String? = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySharePageBinding.inflate(layoutInflater)
        init()
        clickListener()
        setContentView(binding.root)

    }
    private fun clickListener() {
        binding.shareBackBtn.setOnClickListener {
            finish()
        }
        binding.shareShareBtn.setOnClickListener {
            saveImageToGallery()
        }
    }
    private fun init() {
        val date = intent.getStringExtra("date")
        categoryList = intent.getSerializableExtra("categoryList") as ArrayList<CategoryList>
        categoryPercentMap = mutableMapOf<Int, Int>()

        val bundle = intent.getBundleExtra("categoryPercent")
        // 번들에서 카테고리별 퍼센트를 추출하여 맵에 추가합니다.
        for (key in bundle!!.keySet()) {
            categoryPercentMap[key.split("_")[1].toInt()] = bundle.getInt(key)
        }
        userName = intent.getStringExtra("name")
        userImg = intent.getStringExtra("img")
        binding.shareProfileDateTv.text = date
        updateUI()
        initRV()
    }

    private fun updateUI() {
        binding.shareProfileTv.text = userName
        if (userImg != AccountFragment.DEFAULT_IMG && userImg != "null") {
            if (userImg == "") {
                Picasso.get().load(userImg).transform(CircleTransform())
                    .into(binding.shareProfileIv)
            } else {
                Picasso.get().load(userImg).transform(CircleTransform())
                    .into(binding.shareProfileIv)
            }
        }
    }

    private fun initRV(){
        val mestoryRVAdapter = MestoryShareRVAdapter(categoryList,categoryPercentMap , this)
        // RecyclerView 어댑터 설정
        binding.shareRv.layoutManager = LinearLayoutManager(this)
        // RecyclerView 레이아웃 매니저 설정
        binding.shareRv.adapter = mestoryRVAdapter
    }

    private fun saveImageToGallery() {
        // 현재 화면의 루트 뷰를 캡처합니다.
        val rootView = window.decorView.rootView
        rootView.isDrawingCacheEnabled = true
        val bitmap = Bitmap.createBitmap(rootView.drawingCache)
        rootView.isDrawingCacheEnabled = false

        // 이미지를 갤러리에 저장합니다.
        MediaStore.Images.Media.insertImage(
            contentResolver,
            bitmap,
            "plan_me_story_${System.currentTimeMillis()}",
            "Image captured by Plan Me"
        )
        val customToast = CustomToast
        customToast.createToast(this, "갤러리에 저장되었습니다", 300, true)
    }
}