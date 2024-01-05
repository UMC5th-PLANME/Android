package com.example.plan_me;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u00032\u00020\u0004B\u0005\u00a2\u0006\u0002\u0010\u0005J\b\u0010\u0010\u001a\u00020\u0011H\u0002J\u0018\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0015H\u0002J\b\u0010\u0017\u001a\u00020\u0011H\u0016J\u0010\u0010\u0018\u001a\u00020\u00112\u0006\u0010\u0019\u001a\u00020\u0015H\u0016J\b\u0010\u001a\u001a\u00020\u0011H\u0016J\b\u0010\u001b\u001a\u00020\u0011H\u0016J&\u0010\u001c\u001a\u0004\u0018\u00010\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\b\u0010 \u001a\u0004\u0018\u00010!2\b\u0010\"\u001a\u0004\u0018\u00010#H\u0016J\b\u0010$\u001a\u00020\u0011H\u0016J\u0018\u0010%\u001a\u00020\u00112\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0015H\u0016R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006&"}, d2 = {"Lcom/example/plan_me/ScheduleAddFragment;", "Landroidx/fragment/app/Fragment;", "Lcom/example/plan_me/DialogTimePickInerface;", "Lcom/example/plan_me/DialogRepeatInterface;", "Lcom/example/plan_me/DialogTimeRangePickInerface;", "()V", "binding", "Lcom/example/plan_me/databinding/FragmentScheduleAddBinding;", "dialogAlarm", "Lcom/example/plan_me/DialogAlarmFragment;", "dialogRepeat", "Lcom/example/plan_me/DialogRepeatFragment;", "dialogTimePick", "Lcom/example/plan_me/DialogTimePickFragment;", "dialogTimeRangePick", "Lcom/example/plan_me/DialogTimeRangePickFragment;", "clickListener", "", "isVaildRange", "", "startTime", "", "endTime", "onClickCancel", "onClickConfirm", "time", "onClickRepeatCancel", "onClickRepeatItem", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onRangeClickCancel", "onRangeClickConfirm", "app_debug"})
public final class ScheduleAddFragment extends androidx.fragment.app.Fragment implements com.example.plan_me.DialogTimePickInerface, com.example.plan_me.DialogRepeatInterface, com.example.plan_me.DialogTimeRangePickInerface {
    private com.example.plan_me.databinding.FragmentScheduleAddBinding binding;
    private com.example.plan_me.DialogAlarmFragment dialogAlarm;
    private com.example.plan_me.DialogRepeatFragment dialogRepeat;
    private com.example.plan_me.DialogTimePickFragment dialogTimePick;
    private com.example.plan_me.DialogTimeRangePickFragment dialogTimeRangePick;
    
    public ScheduleAddFragment() {
        super();
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public android.view.View onCreateView(@org.jetbrains.annotations.NotNull
    android.view.LayoutInflater inflater, @org.jetbrains.annotations.Nullable
    android.view.ViewGroup container, @org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
        return null;
    }
    
    private final void clickListener() {
    }
    
    @java.lang.Override
    public void onClickConfirm(@org.jetbrains.annotations.NotNull
    java.lang.String time) {
    }
    
    @java.lang.Override
    public void onClickCancel() {
    }
    
    @java.lang.Override
    public void onClickRepeatItem() {
    }
    
    @java.lang.Override
    public void onClickRepeatCancel() {
    }
    
    private final boolean isVaildRange(java.lang.String startTime, java.lang.String endTime) {
        return false;
    }
    
    @java.lang.Override
    public void onRangeClickConfirm(@org.jetbrains.annotations.NotNull
    java.lang.String startTime, @org.jetbrains.annotations.NotNull
    java.lang.String endTime) {
    }
    
    @java.lang.Override
    public void onRangeClickCancel() {
    }
}