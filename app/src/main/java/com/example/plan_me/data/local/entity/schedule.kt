package com.example.plan_me.data.local.entity

import java.time.LocalDate

data class schedule(
    var category : Int,
    var isDone : Boolean,
    var content : String,
    var date : LocalDate
)
