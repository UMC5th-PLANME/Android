package com.example.plan_me.ui.dialog;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\b\u0010\f\u001a\u00020\rH\u0002J\u0012\u0010\u000e\u001a\u00020\r2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0014R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0011"}, d2 = {"Lcom/example/plan_me/ui/dialog/DialogTimeRangePickFragment;", "Landroid/app/Dialog;", "context", "Landroid/content/Context;", "dialogTimeRangePickInerface", "Lcom/example/plan_me/ui/dialog/DialogTimeRangePickInerface;", "(Landroid/content/Context;Lcom/example/plan_me/ui/dialog/DialogTimeRangePickInerface;)V", "binding", "Lcom/example/plan_me/databinding/FragmentDialogTimerangepickBinding;", "endTime", "", "startTime", "clickListener", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "app_debug"})
public final class DialogTimeRangePickFragment extends android.app.Dialog {
    private com.example.plan_me.databinding.FragmentDialogTimerangepickBinding binding;
    private com.example.plan_me.ui.dialog.DialogTimeRangePickInerface dialogTimeRangePickInerface;
    @org.jetbrains.annotations.NotNull
    private java.lang.String startTime = "";
    @org.jetbrains.annotations.NotNull
    private java.lang.String endTime = "";
    
    public DialogTimeRangePickFragment(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    com.example.plan_me.ui.dialog.DialogTimeRangePickInerface dialogTimeRangePickInerface) {
        super(null);
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    private final void clickListener() {
    }
}