// Generated by view binder compiler. Do not edit!
package com.example.plan_me.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.plan_me.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentDialogTimerangepickBinding implements ViewBinding {
  @NonNull
  private final FrameLayout rootView;

  @NonNull
  public final TimePicker dialogTimepickEnd;

  @NonNull
  public final TimePicker dialogTimepickStart;

  @NonNull
  public final TextView dialogTimerangepickCancel;

  @NonNull
  public final TextView dialogTimerangepickConfirm;

  private FragmentDialogTimerangepickBinding(@NonNull FrameLayout rootView,
      @NonNull TimePicker dialogTimepickEnd, @NonNull TimePicker dialogTimepickStart,
      @NonNull TextView dialogTimerangepickCancel, @NonNull TextView dialogTimerangepickConfirm) {
    this.rootView = rootView;
    this.dialogTimepickEnd = dialogTimepickEnd;
    this.dialogTimepickStart = dialogTimepickStart;
    this.dialogTimerangepickCancel = dialogTimerangepickCancel;
    this.dialogTimerangepickConfirm = dialogTimerangepickConfirm;
  }

  @Override
  @NonNull
  public FrameLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentDialogTimerangepickBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentDialogTimerangepickBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_dialog_timerangepick, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentDialogTimerangepickBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.dialog_timepick_end;
      TimePicker dialogTimepickEnd = ViewBindings.findChildViewById(rootView, id);
      if (dialogTimepickEnd == null) {
        break missingId;
      }

      id = R.id.dialog_timepick_start;
      TimePicker dialogTimepickStart = ViewBindings.findChildViewById(rootView, id);
      if (dialogTimepickStart == null) {
        break missingId;
      }

      id = R.id.dialog_timerangepick_cancel;
      TextView dialogTimerangepickCancel = ViewBindings.findChildViewById(rootView, id);
      if (dialogTimerangepickCancel == null) {
        break missingId;
      }

      id = R.id.dialog_timerangepick_confirm;
      TextView dialogTimerangepickConfirm = ViewBindings.findChildViewById(rootView, id);
      if (dialogTimerangepickConfirm == null) {
        break missingId;
      }

      return new FragmentDialogTimerangepickBinding((FrameLayout) rootView, dialogTimepickEnd,
          dialogTimepickStart, dialogTimerangepickCancel, dialogTimerangepickConfirm);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
