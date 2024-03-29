package com.example.plan_me.ui.all.Daily

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.plan_me.data.local.entity.Schedule_input
import com.example.plan_me.data.remote.dto.category.CategoryList
import com.example.plan_me.data.remote.dto.schedule.ModifyScheduleRes
import com.example.plan_me.data.remote.dto.schedule.ScheduleList
import com.example.plan_me.data.remote.service.schedule.ScheduleService
import com.example.plan_me.data.remote.view.schedule.ModifyScheduleView
import com.example.plan_me.databinding.ItemScheduleListBinding
import com.example.plan_me.ui.add.ScheduleAddActivity
import com.example.plan_me.utils.viewModel.CalendarViewModel
import com.example.plan_me.utils.viewModel.CalendarViewModelFactory

class ScheduleRVAdapter(private val categoryList : List<CategoryList>, private val scheduleMap: MutableList<ScheduleList>?, private val context: Context, private val sendSignalModify: SendSignalModify) :
    RecyclerView.Adapter<ScheduleRVAdapter.ViewHolder>(),
    ModifyScheduleView{

    interface SendSignalModify{
        fun sendSignalModify()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding : ItemScheduleListBinding = ItemScheduleListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(scheduleMap!![position])
    }

    override fun getItemCount(): Int =
        if (scheduleMap.isNullOrEmpty()) 0
        else scheduleMap.size

    inner class ViewHolder(val binding: ItemScheduleListBinding) :RecyclerView.ViewHolder(binding.root) {
        fun bind(schedule: ScheduleList) {
            binding.scheduleListTv.text = schedule.title
            binding.scheduleListBtn.isChecked = schedule.status

            binding.scheduleListBtn.setOnClickListener {
                changeScheduleStatus(schedule)
                schedule.status = !schedule.status
                binding.scheduleListBtn.isChecked = schedule.status
            }
            binding.scheduleListTv.setOnClickListener {
                val intent = Intent(context, ScheduleAddActivity::class.java)
                val categoryIdOneList:CategoryList? = categoryList.find { it.categoryId == schedule.category_id }
                intent.putExtra("schedule", schedule)
                intent.putExtra("schedule_category", categoryIdOneList)
                context.startActivity(intent)
            }
        }
    }

    private fun changeScheduleStatus(schedule : ScheduleList) {
        val access_token = "Bearer " + context.getSharedPreferences("getRes",
            AppCompatActivity.MODE_PRIVATE
        ).getString("getAccessToken", "")
        val scheduleService = ScheduleService()
        scheduleService.setModifyScheduleView(this)
        val schedule_input = Schedule_input(
            !schedule.status,
            schedule.category_id,
            schedule.repeat_period,
            schedule.title,
            schedule.start_time,
            schedule.end_time,
            schedule.alarm,
            schedule.alarm_time,
            schedule.startDate,
            schedule.endDate
        )
        scheduleService.modifyScheduleFun(access_token!!, schedule.id, schedule_input)
    }

    override fun onModifyScheduleSuccess(response: ModifyScheduleRes) {
        Log.d("성공", "")
        notifyDataSetChanged()
        sendSignalModify.sendSignalModify()
    }

    override fun onModifyScheduleFailure(response: ModifyScheduleRes) {
        TODO("Not yet implemented")
    }
}