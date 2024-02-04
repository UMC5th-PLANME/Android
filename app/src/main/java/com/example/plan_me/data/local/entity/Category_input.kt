package com.example.plan_me.data.local.entity

import kotlinx.serialization.Serializable

@Serializable
data class Category_input(
    var name : String,
    var emoticon : String,
    var color : Int,
)
