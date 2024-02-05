package com.example.plan_me.ui.main

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.core.view.GravityCompat
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
import com.example.plan_me.ui.dialog.DialogAddFragment
import com.example.plan_me.ui.dialog.DialogDeleteCategoryCheckFragment
import com.example.plan_me.ui.dialog.DialogDeleteCategoryFragment
import com.example.plan_me.ui.dialog.DialogModifyCategoryFragment
import com.example.plan_me.ui.dialog.DialogModifyFragment
import com.example.plan_me.ui.login.SendCategoryToPlannerFragment
import com.example.plan_me.ui.mestory.MestoryActivity
import com.example.plan_me.ui.planner.PlannerFragment
import com.example.plan_me.ui.setting.SettingActivity
import com.example.plan_me.ui.timer.TimerFocusActivity

class MainActivity :
    AppCompatActivity(),
    AllCategoryView,
    DialogAddFragment.SendSignalToMain,
    DialogDeleteCategoryCheckFragment.SendDeleteMessage,
    DialogModifyFragment.SendModifyMessage ,
    MainDrawerRVAdapter.SendClickCategory {

    private lateinit var binding: ActivityMainBinding
    private var isFabOpen = false
    private lateinit var drawerView:View
    private lateinit var drawerAdd: TextView
    private lateinit var drawerDelete: TextView
    private lateinit var drawerModify: TextView

    private lateinit var category_delete : DialogDeleteCategoryFragment
    private lateinit var category_modify : DialogModifyCategoryFragment

    private var fab_open: Animation? = null
    private var fab_close: Animation? = null

    private var isHome : Boolean = true

    lateinit var drawerAdapter : MainDrawerRVAdapter

    private lateinit var categorys : List<CategoryList>

    private lateinit var sendCategoryToPlannerFragment: SendCategoryToPlannerFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        getCategoryList()
        setContentView(binding.root)

        overridePendingTransition(R.anim.screen_start, R.anim.screen_none)

        fab_open = AnimationUtils.loadAnimation(this, R.anim.fab_open)
        fab_close = AnimationUtils.loadAnimation(this, R.anim.fab_close)
        drawerView = findViewById(R.id.drawer_layout)
        drawerAdd = findViewById(R.id.drawer_add_tv)
        drawerDelete = findViewById(R.id.drawer_delete_tv)
        drawerModify = findViewById(R.id.drawer_modify_tv)


        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, PlannerFragment())
            .commitAllowingStateLoss()
        clickListener()
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

    override fun onBackPressed() {
        if (binding.mainDrawerLayout.isDrawerOpen(GravityCompat.START)){
            binding.mainDrawerLayout.closeDrawers()
        }
        else {
            super.onBackPressed()
            overridePendingTransition(R.anim.screen_none, R.anim.screen_exit)
        }
    }
    private fun getCategoryList() {  //연결 성공
        val access_token = "Bearer " + getSharedPreferences("getRes", MODE_PRIVATE).getString("getAccessToken", "")
        Log.d("access token", access_token)
        val setCategoryService = CategoryService()
        setCategoryService.setAllCategoryView(this)
        setCategoryService.getCategoryAllFun(access_token!!)
    }
    private fun clickListener() {
        //다른 화면 클릭시 fab 닫기
        binding.root.setOnTouchListener { _, event ->
            if (isFabOpen && event.action == MotionEvent.ACTION_DOWN) {
                toggleFab()
                return@setOnTouchListener true
            }
            return@setOnTouchListener false
        }
        binding.mainFabMenuBtn.setOnClickListener {
            Log.d("fab","fab")
            toggleFab()
        }
        binding.mainFabMestoryBtn.setOnClickListener {
            Log.d("mestory", "mestory")
            switchActivity(MestoryActivity())
            overridePendingTransition(R.anim.screen_none, R.anim.screen_exit)
        }
        binding.mainFabTimerBtn.setOnClickListener {
            switchActivity(TimerFocusActivity())
            overridePendingTransition(R.anim.screen_none, R.anim.screen_exit)
        }
        binding.mainFabSettingBtn.setOnClickListener {
            switchActivity(SettingActivity())
            overridePendingTransition(R.anim.screen_none, R.anim.screen_exit)
        }
        binding.mainFabAddBtn.setOnClickListener {
            switchActivity(ScheduleAddActivity())
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
        binding.mainAllBtn.setOnClickListener{
            if (isHome) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_frm, AllFragment())
                    .commitAllowingStateLoss()
                binding.mainAllBtn.setBackgroundResource(R.drawable.planner_btn_planner)
                binding.mainAllBtn.text = "HOME"
                binding.mainAllBtn.setTextColor(Color.WHITE)
                isHome=false
            }else {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_frm, PlannerFragment())
                    .commitAllowingStateLoss()
                binding.mainAllBtn.setBackgroundResource(R.drawable.planner_btn_all)
                binding.mainAllBtn.text = "ALL"
                binding.mainAllBtn.setTextColor(Color.BLACK)
                isHome=true
            }
        }
    }

    private fun showDialog(dialog: Dialog) {
        dialog.show()
    }

    private fun switchActivity(activity: Activity) {
        val intent = Intent(this, activity::class.java)
        startActivity(intent)
    }

    private fun toggleFab() {
        isFabOpen = if (isFabOpen) {
            binding.mainFabMestoryBtn.startAnimation(fab_close)
            binding.mainFabTimerBtn.startAnimation(fab_close)
            binding.mainFabSettingBtn.startAnimation(fab_close)
            binding.mainFabAddBtn.startAnimation(fab_close)
            false
        } else {
            binding.mainFabMestoryBtn.startAnimation(fab_open)
            binding.mainFabTimerBtn.startAnimation(fab_open)
            binding.mainFabSettingBtn.startAnimation(fab_open)
            binding.mainFabAddBtn.startAnimation(fab_open)

            binding.mainFabMestoryBtn.visibility = View.VISIBLE
            binding.mainFabTimerBtn.visibility = View.VISIBLE
            binding.mainFabSettingBtn.visibility = View.VISIBLE
            binding.mainFabAddBtn.visibility = View.VISIBLE

            binding.mainFabMestoryBtn.setClickable(true)
            binding.mainFabTimerBtn.setClickable(true)
            binding.mainFabSettingBtn.setClickable(true)
            binding.mainFabAddBtn.setClickable(true)
            true
        }
    }

    override fun onAllCategorySuccess(response: AllCategoryRes) {
        Log.d("all category", response.result.toString())
        categorys = response.result.categoryList
        initActivity()
    }

    override fun onAllCategoryFailure(response: AllCategoryRes) {
        TODO("Not yet implemented")
    }

    override fun sendSuccessSignal() {
        getCategoryList()
    }

    override fun sendDeleteMessage() {
        category_delete.dismiss()
        getCategoryList()
    }

    override fun sendModifySuccessSignal() {
        category_modify.dismiss()
        getCategoryList()
    }

    override fun sendClickCategory(category: CategoryList) {
        if (isHome) {
            val bundle = Bundle()
            bundle.putString("name", category.name)
            bundle.putString("emoticon", category.emoticon)
            bundle.putInt("color", category.color)
            val plannerFragment = PlannerFragment()
            plannerFragment.arguments = bundle
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, plannerFragment)
                .commitAllowingStateLoss()
        }
    }
}