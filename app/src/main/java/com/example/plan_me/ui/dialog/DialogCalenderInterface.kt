package com.example.plan_me.ui.dialog

import java.time.LocalDate

interface DialogCalenderInterface {
    fun onClickCalenderConfirm(start : LocalDate?, end:LocalDate?)
}