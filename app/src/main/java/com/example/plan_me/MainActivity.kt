package com.example.plan_me

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.plan_me.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashAScreen = installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(R.layout.activity_main)
    }

    private fun initBottomNavigation(){   //맞는 화면으로 넣어주세용~
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, ScheduleAddFragment())
            .commitAllowingStateLoss()

        binding.mainNavi.setOnItemSelectedListener{ item ->
            when (item.itemId) {

                R.id.plannerFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, ScheduleAddFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.mestoryFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, ScheduleAddFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.timerFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, ScheduleAddFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.settingFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, ScheduleAddFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                else -> false
            }
        }
    }
}