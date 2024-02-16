package com.example.plan_me.ui.main

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
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
import com.example.plan_me.ui.setting.SettingFragment
import com.example.plan_me.utils.viewModel.CalendarViewModel
import com.example.plan_me.utils.viewModel.CalendarViewModelFactory
import com.example.plan_me.utils.viewModel.NaviFragmentViewModel
import com.example.plan_me.utils.viewModel.NaviViewModel

class MainActivity :
    AppCompatActivity(),
    AllCategoryView,
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

    private lateinit var categorys : List<CategoryList>

    private lateinit var currentCategory : CategoryList
    private var currentCategoryPosition : Int = -1

    private lateinit var naviViewModel: NaviViewModel
    private lateinit var naviFragmentViewModel: NaviFragmentViewModel
    private lateinit var calendarViewModel: CalendarViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        naviViewModel = ViewModelProvider(this).get(NaviViewModel::class.java)
        naviFragmentViewModel = ViewModelProvider(this).get(NaviFragmentViewModel::class.java)
        val factory = CalendarViewModelFactory(this.getSharedPreferences("getRes", MODE_PRIVATE))
        calendarViewModel = ViewModelProvider(this, factory).get(CalendarViewModel::class.java)

        initBottomNavigation()
        getCategoryList()
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
/*
        AlarmFunctions(this).callAlarm("2024-02-10 21:05:10",  1, "asdf")*/
    }

    override fun onResume() {
        super.onResume()
    }

    private fun initActivity() {
        Log.d("init", categorys.toString())
        val layoutManager = LinearLayoutManager(this)
        val drawer = findViewById<RecyclerView>(R.id.drawer_rv)
        drawerAdapter = MainDrawerRVAdapter(categorys, this)
        drawer.layoutManager = layoutManager
        drawer.adapter = drawerAdapter
        drawerAdapter.notifyDataSetChanged()
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

    override fun onBackPressed() {
        if (binding.mainDrawerLayout.isDrawerOpen(GravityCompat.START)){
            binding.mainDrawerLayout.closeDrawers()
        }
    }
    private fun getCategoryList() {
        val access_token = "Bearer " + getSharedPreferences("getRes", MODE_PRIVATE).getString("getAccessToken", "")
        val setCategoryService = CategoryService()
        setCategoryService.setAllCategoryView(this)
        setCategoryService.getCategoryAllFun(access_token!!)
    }

    private fun clickListener() {
        //다른 화면 클릭시 fab 닫기


        binding.mainBtmAddFab.setOnClickListener {
            val intent = Intent(this, ScheduleAddActivity::class.java)
            val categoryArraList :ArrayList<CategoryList> = ArrayList(categorys)
            intent.putExtra("category", currentCategory)
            intent.putExtra("categoryList", categoryArraList)
            startActivity(intent)
            overridePendingTransition(R.anim.screen_none, R.anim.screen_exit)
        }
        binding.mainMenu.setOnClickListener{
            binding.mainDrawerLayout.openDrawer(drawerView!!)
        }
        drawerAdd.setOnClickListener {
            showDialog(DialogAddFragment(this@MainActivity, this))
        }
        drawerDelete.setOnClickListener {
            category_delete = DialogDeleteCategoryFragment(this, categorys, this)
            category_delete.show()
        }
        drawerModify.setOnClickListener {
            category_modify = DialogModifyCategoryFragment(this, categorys ,this)
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

    override fun onAllCategorySuccess(response: AllCategoryRes) {
        categorys = response.result.categoryList
        if(categorys.isNotEmpty()) {
            if (currentCategoryPosition == -1) {
                currentCategory = categorys[0]
            } else {
                currentCategory = categorys[currentCategoryPosition]
            }
            naviViewModel.sendCategory(currentCategory)
            initActivity()
        }else {
            naviViewModel.sendCategory(CategoryList(0,"Schedule","\uD83D\uDCC6" ,R.color.light_gray, false, "","" ))
            initActivity()
        }
    }

    override fun onAllCategoryFailure(response: AllCategoryRes) {
        TODO("Not yet implemented")
    }

    override fun sendSuccessSignal() {  //add
        getCategoryList()
    }

    override fun sendDeleteMessage(position : Int) {    //delete
        category_delete.dismiss()
        Log.d("position", position.toString())
        Log.d("previousCategoryPosition", currentCategoryPosition.toString())
        if (currentCategoryPosition == position) {
            currentCategoryPosition = -1
            getCategoryList()
        }
        else if (currentCategoryPosition > position) {
            currentCategoryPosition -= 1
            getCategoryList()
        }else {
            getCategoryList()
        }
        val customToast = CustomToast
        customToast.createToast(this, "카테고리가 삭제되었습니다", 300, false)
    }

    override fun sendModifySuccessSignal(position : Int) {  //modify
        category_modify.dismiss()
        currentCategoryPosition = position
        val customToast = CustomToast
        customToast.createToast(this, "카테고리가 수정되었습니다", 300, true)
        getCategoryList()
    }

    override fun sendClickCategory(category: CategoryList, position: Int) {
        if (isHome) {
            currentCategoryPosition =position
            currentCategory = categorys[currentCategoryPosition]
            naviViewModel.sendCategory(currentCategory)
            initActivity()
        }
    }
}