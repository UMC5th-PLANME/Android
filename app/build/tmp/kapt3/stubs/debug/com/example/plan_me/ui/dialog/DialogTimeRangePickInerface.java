package com.example.plan_me.ui.dialog;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\u0018\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H&\u00a8\u0006\b"}, d2 = {"Lcom/example/plan_me/ui/dialog/DialogTimeRangePickInerface;", "", "onRangeClickCancel", "", "onRangeClickConfirm", "startTime", "", "endTime", "app_debug"})
public abstract interface DialogTimeRangePickInerface {
    
    public abstract void onRangeClickConfirm(@org.jetbrains.annotations.NotNull
    java.lang.String startTime, @org.jetbrains.annotations.NotNull
    java.lang.String endTime);
    
    public abstract void onRangeClickCancel();
}