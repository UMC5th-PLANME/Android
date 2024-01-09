package com.example.plan_me.ui.dialog

interface DialogTimeRangePickInerface {
    fun onRangeClickConfirm(startTime : String, endTime:String)
    fun onRangeClickCancel()
}