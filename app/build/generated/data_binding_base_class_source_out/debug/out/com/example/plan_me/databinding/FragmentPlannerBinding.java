// Generated by view binder compiler. Do not edit!
package com.example.plan_me.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.plan_me.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentPlannerBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final TextView plannerCategoryImoticonTv;

  @NonNull
  public final TextView plannerCategoryNameTv;

  @NonNull
  public final TextView plannerDayTv;

  @NonNull
  public final LinearLayout plannerSecondLo;

  @NonNull
  public final ListView plannerTodoLv;

  private FragmentPlannerBinding(@NonNull ConstraintLayout rootView,
      @NonNull TextView plannerCategoryImoticonTv, @NonNull TextView plannerCategoryNameTv,
      @NonNull TextView plannerDayTv, @NonNull LinearLayout plannerSecondLo,
      @NonNull ListView plannerTodoLv) {
    this.rootView = rootView;
    this.plannerCategoryImoticonTv = plannerCategoryImoticonTv;
    this.plannerCategoryNameTv = plannerCategoryNameTv;
    this.plannerDayTv = plannerDayTv;
    this.plannerSecondLo = plannerSecondLo;
    this.plannerTodoLv = plannerTodoLv;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentPlannerBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentPlannerBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_planner, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentPlannerBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.planner_category_imoticon_tv;
      TextView plannerCategoryImoticonTv = ViewBindings.findChildViewById(rootView, id);
      if (plannerCategoryImoticonTv == null) {
        break missingId;
      }

      id = R.id.planner_category_name_tv;
      TextView plannerCategoryNameTv = ViewBindings.findChildViewById(rootView, id);
      if (plannerCategoryNameTv == null) {
        break missingId;
      }

      id = R.id.planner_day_tv;
      TextView plannerDayTv = ViewBindings.findChildViewById(rootView, id);
      if (plannerDayTv == null) {
        break missingId;
      }

      id = R.id.planner_second_lo;
      LinearLayout plannerSecondLo = ViewBindings.findChildViewById(rootView, id);
      if (plannerSecondLo == null) {
        break missingId;
      }

      id = R.id.planner_todo_lv;
      ListView plannerTodoLv = ViewBindings.findChildViewById(rootView, id);
      if (plannerTodoLv == null) {
        break missingId;
      }

      return new FragmentPlannerBinding((ConstraintLayout) rootView, plannerCategoryImoticonTv,
          plannerCategoryNameTv, plannerDayTv, plannerSecondLo, plannerTodoLv);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}