package com.example.plan_me.entity

import java.time.LocalDate

data class schedule(
    var category : Int,
    var isDone : Boolean,
    var content : String,
    var date : LocalDate
)
