package com.example.plan_me.data.local.entity

data class Terms(
    val member_id: Int,
    val agreeTermIds: Array<Int>,
    val disagreeTermIds: Array<Int>
)
