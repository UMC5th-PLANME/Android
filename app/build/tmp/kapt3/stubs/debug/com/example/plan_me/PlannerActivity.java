package com.example.plan_me;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0011\u001a\u00020\u0012H\u0002J\b\u0010\u0013\u001a\u00020\u0012H\u0002J\b\u0010\u0014\u001a\u00020\u0012H\u0002J\b\u0010\u0015\u001a\u00020\u0012H\u0002J\u0012\u0010\u0016\u001a\u00020\u00122\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0014J\b\u0010\u0019\u001a\u00020\u0012H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001a"}, d2 = {"Lcom/example/plan_me/PlannerActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "binding", "Lcom/example/plan_me/databinding/ActivityPlannerBinding;", "fab_add", "Lcom/google/android/material/floatingactionbutton/FloatingActionButton;", "fab_close", "Landroid/view/animation/Animation;", "fab_mestory", "fab_open", "fab_planner", "fab_setting", "fab_timer", "ignoreCheckChange", "", "isFabOpen", "addPopup", "", "goMestoryActivity", "goSettingActivity", "goTimerActivity", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "toggleFab", "app_debug"})
public final class PlannerActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.example.plan_me.databinding.ActivityPlannerBinding binding;
    @org.jetbrains.annotations.Nullable
    private com.google.android.material.floatingactionbutton.FloatingActionButton fab_planner;
    @org.jetbrains.annotations.Nullable
    private com.google.android.material.floatingactionbutton.FloatingActionButton fab_mestory;
    @org.jetbrains.annotations.Nullable
    private com.google.android.material.floatingactionbutton.FloatingActionButton fab_timer;
    @org.jetbrains.annotations.Nullable
    private com.google.android.material.floatingactionbutton.FloatingActionButton fab_setting;
    @org.jetbrains.annotations.Nullable
    private com.google.android.material.floatingactionbutton.FloatingActionButton fab_add;
    @org.jetbrains.annotations.Nullable
    private android.view.animation.Animation fab_open;
    @org.jetbrains.annotations.Nullable
    private android.view.animation.Animation fab_close;
    private boolean isFabOpen = false;
    private boolean ignoreCheckChange = false;
    
    public PlannerActivity() {
        super();
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    private final void toggleFab() {
    }
    
    private final void goMestoryActivity() {
    }
    
    private final void goTimerActivity() {
    }
    
    private final void goSettingActivity() {
    }
    
    private final void addPopup() {
    }
}