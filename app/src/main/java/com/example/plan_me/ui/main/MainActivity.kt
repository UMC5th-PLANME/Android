package com.example.plan_me.ui.main

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import com.example.plan_me.data.remote.dto.category.AllCategoryRes
import com.example.plan_me.data.remote.dto.category.CategoryList
import com.example.plan_me.data.remote.service.category.CategoryService
import com.example.plan_me.data.remote.view.category.AllCategoryView
import com.example.plan_me.databinding.ActivityMainBinding
import com.example.plan_me.ui.add.ScheduleAddActivity
import com.example.plan_me.ui.all.AllFragment
import com.example.plan_me.ui.dialog.CustomToast
import com.example.plan_me.ui.dialog.DialogAddFragment
import com.example.plan_me.ui.dialog.DialogDeleteCategoryCheckFragment
import com.example.plan_me.ui.dialog.DialogDeleteCategoryFragment
import com.example.plan_me.ui.dialog.DialogModifyCategoryFragment
import com.example.plan_me.ui.dialog.DialogModifyFragment
import com.example.plan_me.ui.mestory.MestoryFragment
import com.example.plan_me.ui.planner.PlannerFragment
import com.example.plan_me.ui.timer.TimerFragment
import com.example.plan_me.ui.setting.SettingFragment
import com.example.plan_me.utils.alarm.AlarmFunctions
import com.example.plan_me.utils.viewModel.CalendarViewModel
import com.example.plan_me.utils.viewModel.CalendarViewModelFactory
import com.example.plan_me.utils.viewModel.NaviFragmentViewModel

class MainActivity :
    AppCompatActivity(),
    DialogAddFragment.SendSignalToMain,
    DialogDeleteCategoryCheckFragment.SendDeleteMessage,
    DialogModifyFragment.SendModifyMessage ,
    MainDrawerRVAdapter.SendClickCategory{

    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerView:View
    private lateinit var drawerAdd: TextView
    private lateinit var drawerDelete: TextView
    private lateinit var drawerModify: TextView

    private lateinit var category_delete : DialogDeleteCategoryFragment
    private lateinit var category_modify : DialogModifyCategoryFragment

    private var isHome : Boolean = true

    lateinit var drawerAdapter : MainDrawerRVAdapter

    private lateinit var naviFragmentViewModel: NaviFragmentViewModel
    private lateinit var calendarViewModel: CalendarViewModel

    private var backPressedTime: Long = 0
    private val BACK_PRESSED_INTERVAL: Long = 2000 // 두 번 누르는 간격 (밀리초)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        naviFragmentViewModel = ViewModelProvider(this).get(NaviFragmentViewModel::class.java)
        val factory = CalendarViewModelFactory(this.getSharedPreferences("getRes", MODE_PRIVATE))
        calendarViewModel = ViewModelProvider(this, factory).get(CalendarViewModel::class.java)

        calendarViewModel._categoryList.observe(this, Observer {
            initActivity()
        })

        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
        initBottomNavigation()
        setContentView(binding.root)

        overridePendingTransition(R.anim.screen_start, R.anim.screen_none)

        drawerView = findViewById(R.id.drawer_layout)
        drawerAdd = findViewById(R.id.drawer_add_tv)
        drawerDelete = findViewById(R.id.drawer_delete_tv)
        drawerModify = findViewById(R.id.drawer_modify_tv)


        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, PlannerFragment())
            .commitAllowingStateLoss()

        clickListener()
    }

    override fun onResume() {
        super.onResume()
    }

    private fun initActivity() {
        if (!calendarViewModel._categoryList.value.isNullOrEmpty()) {
            val layoutManager = LinearLayoutManager(this)
            val drawer = findViewById<RecyclerView>(R.id.drawer_rv)
            drawerAdapter = MainDrawerRVAdapter(calendarViewModel._categoryList.value!!, this)
            drawer.layoutManager = layoutManager
            drawer.adapter = drawerAdapter
            drawerAdapter.notifyDataSetChanged()
        }
    }

    private fun initBottomNavigation(){
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, PlannerFragment())
            .commitAllowingStateLoss()

        binding.mainBtmNavi.setOnItemSelectedListener{ item ->
            when (item.itemId) {

                R.id.planner -> {
                    isHome = true
                    binding.mainAllBtn.setBackgroundResource(R.drawable.planner_btn_all)
                    binding.mainAllBtn.text = "All"
                    binding.mainAllBtn.setTextColor(Color.BLACK)
                    supportFragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.fadein, R.anim.fadeout2)
                        .replace(R.id.main_frm, PlannerFragment())
                        .commitAllowingStateLoss()
                    binding.mainTopLayout.visibility = View.VISIBLE
                    return@setOnItemSelectedListener true
                }

                R.id.mestory -> {
                    supportFragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.fadein, R.anim.fadeout2)
                        .replace(R.id.main_frm, MestoryFragment())
                        .commitAllowingStateLoss()
                    binding.mainTopLayout.visibility = View.GONE
                    return@setOnItemSelectedListener true
                }

                R.id.timer -> {
                    supportFragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.fadein, R.anim.fadeout2)
                        .replace(R.id.main_frm, PlannerFragment())
                        .setCustomAnimations(R.anim.fadein, R.anim.fadeout)
                        .replace(R.id.main_frm, TimerFragment())
                        .commitAllowingStateLoss()
                    binding.mainTopLayout.visibility = View.GONE
                    return@setOnItemSelectedListener true
                }

                R.id.setting -> {
                    supportFragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.fadein, R.anim.fadeout2)
                        .replace(R.id.main_frm, SettingFragment())
                        .commitAllowingStateLoss()
                    binding.mainTopLayout.visibility = View.GONE
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }

    private val onBackPressedCallback = object  : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (binding.mainDrawerLayout.isDrawerOpen(GravityCompat.START)){
                binding.mainDrawerLayout.closeDrawers()
            }else if (!isHome) {
                supportFragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.fadein, R.anim.fadeout)
                    .replace(R.id.main_frm, PlannerFragment())
                    .commitAllowingStateLoss()
                binding.mainAllBtn.setBackgroundResource(R.drawable.planner_btn_all)
                binding.mainAllBtn.text = "All"
                binding.mainAllBtn.setTextColor(Color.BLACK)
                isHome=true
            } else {

                if (System.currentTimeMillis() - backPressedTime >= 1500) {
                    backPressedTime = System.currentTimeMillis()
                    CustomToast.createToast(this@MainActivity, "한 번 더 누르면 종료됩니다.", 300, true)
                } else {
                    finish()
                }
            }
        }
    }

    private fun clickListener() {

        binding.mainBtmAddFab.setOnClickListener {
            if (calendarViewModel._currentCategory.value!!.categoryId == -1) {
                val customToast = CustomToast
                customToast.createToast(this, "카테고리를 선택해주세요", 300, false)
            }else {
                val intent = Intent(this, ScheduleAddActivity::class.java)
                val categoryList = ArrayList(calendarViewModel._categoryList.value)
                intent.putExtra("category", calendarViewModel._currentCategory.value)
                intent.putExtra("categoryList", categoryList)
                startActivity(intent)
                overridePendingTransition(R.anim.screen_none, R.anim.screen_exit)
            }
        }
        binding.mainMenu.setOnClickListener{
            binding.mainDrawerLayout.openDrawer(drawerView!!)
        }
        drawerAdd.setOnClickListener {
            showDialog(DialogAddFragment(this@MainActivity, this))
        }
        drawerDelete.setOnClickListener {
            category_delete = DialogDeleteCategoryFragment(this, calendarViewModel._categoryList.value, this)
            category_delete.show()
        }
        drawerModify.setOnClickListener {
            category_modify = DialogModifyCategoryFragment(this, calendarViewModel._categoryList.value ,this)
            category_modify.show()
        }
        binding.mainAllBtnLayout.setOnClickListener{
            if (isHome) {
                supportFragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.fadein, R.anim.fadeout)
                    .replace(R.id.main_frm, AllFragment())
                    .commitAllowingStateLoss()
                binding.mainAllBtn.setBackgroundResource(R.drawable.planner_btn_planner)
                binding.mainAllBtn.text = "Home"
                binding.mainAllBtn.setTextColor(Color.WHITE)
                isHome=false
            }else {
                supportFragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.fadein, R.anim.fadeout)
                    .replace(R.id.main_frm, PlannerFragment())
                    .commitAllowingStateLoss()
                binding.mainAllBtn.setBackgroundResource(R.drawable.planner_btn_all)
                binding.mainAllBtn.text = "All"
                binding.mainAllBtn.setTextColor(Color.BLACK)
                isHome=true
            }
        }
    }

    private fun showDialog(dialog: Dialog) {
        dialog.show()
    }
    override fun sendSuccessSignal() {  //add
        calendarViewModel.getCategoryList()
    }

    override fun sendDeleteMessage(category: CategoryList) {    //delete
        category_delete.dismiss()
        calendarViewModel.getCategoryList()
        if (calendarViewModel._currentCategory.value == category) {
            calendarViewModel.sendCategory(CategoryList(-1,"Schedule","\uD83D\uDCC6" ,R.color.light_gray, false, "","" ))
        }
        val customToast = CustomToast
        customToast.createToast(this, "카테고리가 삭제되었습니다", 300, false)
    }

    override fun sendModifySuccessSignal(category: CategoryList) {  //modify
        category_modify.dismiss()
        calendarViewModel.getCategoryList()
        calendarViewModel.sendCategory(category)
        val customToast = CustomToast
        customToast.createToast(this, "카테고리가 수정되었습니다", 300, true)
    }

    override fun sendClickCategory(category: CategoryList, position: Int) {
            calendarViewModel.sendCategory(category)
            binding.mainDrawerLayout.closeDrawers()
    }
}