package com.example.plan_me.data.local.entity

import java.time.LocalDate

data class schedule(
    var id: Int,
    var status:Int,
    var category_id : Int,
    var repeat_period : String,
    var title : String,
    var start_time : String,
    var end_time : String,
    var alarm : Int,
    var alarm_time:String,
    var created_at:String,
    var updated_at:String
)
