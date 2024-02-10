
package com.example.plan_me.ui.planner

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.plan_me.data.local.entity.Schedule_input
import com.example.plan_me.data.remote.dto.schedule.ModifyScheduleRes
import com.example.plan_me.data.remote.dto.schedule.OneScheduleRes
import com.example.plan_me.databinding.ItemTodoBinding
import com.example.plan_me.data.remote.dto.schedule.ScheduleList
import com.example.plan_me.data.remote.service.category.CategoryService
import com.example.plan_me.data.remote.service.schedule.ScheduleService
import com.example.plan_me.data.remote.view.schedule.ModifyScheduleView
import com.example.plan_me.data.remote.view.schedule.OneScheduleView

class PlannerRVAdapter(private val selectedSchedule : MutableList<ScheduleList>, private val context: Context): RecyclerView.Adapter<PlannerRVAdapter.ViewHolder>(),
    ModifyScheduleView{
    inner class ViewHolder(val binding: ItemTodoBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.todoTv.text = selectedSchedule[position].title
            binding.todoBtn.setOnClickListener {
                changeScheduleStatus(selectedSchedule[position])
                selectedSchedule[position].status = !selectedSchedule[position].status
                binding.todoBtn.isChecked = selectedSchedule[position].status
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): PlannerRVAdapter.ViewHolder {
        val binding: ItemTodoBinding = ItemTodoBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlannerRVAdapter.ViewHolder, position: Int) {
        holder.bind(position)
        if (selectedSchedule[position].status) {
            holder.binding.todoBtn.isChecked = true
        }
    }

    override fun getItemCount(): Int = selectedSchedule.size

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
        Log.d("수정 성공", "!!")
    }

    override fun onModifyScheduleFailure(response: ModifyScheduleRes) {
        TODO("Not yet implemented")
    }
}
