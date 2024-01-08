// Generated by view binder compiler. Do not edit!
package com.example.plan_me.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.plan_me.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ItemMestoryCategoryOpenBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final TextView mestoryCategoryDetailTv;

  @NonNull
  public final ImageView mestoryCategoryDownBt;

  @NonNull
  public final TextView mestoryCategoryEmoticonTv;

  @NonNull
  public final ProgressBar mestoryCategoryProgressBar;

  @NonNull
  public final TextView mestoryCategoryProgressMinuteTv;

  @NonNull
  public final TextView mestoryCategoryProgressPercentageTv;

  @NonNull
  public final TextView mestoryCategoryTv;

  @NonNull
  public final ImageView mestoryCategoryUpBt;

  @NonNull
  public final CardView mestoryItemOpenedCv;

  private ItemMestoryCategoryOpenBinding(@NonNull RelativeLayout rootView,
      @NonNull TextView mestoryCategoryDetailTv, @NonNull ImageView mestoryCategoryDownBt,
      @NonNull TextView mestoryCategoryEmoticonTv, @NonNull ProgressBar mestoryCategoryProgressBar,
      @NonNull TextView mestoryCategoryProgressMinuteTv,
      @NonNull TextView mestoryCategoryProgressPercentageTv, @NonNull TextView mestoryCategoryTv,
      @NonNull ImageView mestoryCategoryUpBt, @NonNull CardView mestoryItemOpenedCv) {
    this.rootView = rootView;
    this.mestoryCategoryDetailTv = mestoryCategoryDetailTv;
    this.mestoryCategoryDownBt = mestoryCategoryDownBt;
    this.mestoryCategoryEmoticonTv = mestoryCategoryEmoticonTv;
    this.mestoryCategoryProgressBar = mestoryCategoryProgressBar;
    this.mestoryCategoryProgressMinuteTv = mestoryCategoryProgressMinuteTv;
    this.mestoryCategoryProgressPercentageTv = mestoryCategoryProgressPercentageTv;
    this.mestoryCategoryTv = mestoryCategoryTv;
    this.mestoryCategoryUpBt = mestoryCategoryUpBt;
    this.mestoryItemOpenedCv = mestoryItemOpenedCv;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ItemMestoryCategoryOpenBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ItemMestoryCategoryOpenBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.item_mestory_category_open, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ItemMestoryCategoryOpenBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.mestory_category_detail_tv;
      TextView mestoryCategoryDetailTv = ViewBindings.findChildViewById(rootView, id);
      if (mestoryCategoryDetailTv == null) {
        break missingId;
      }

      id = R.id.mestory_category_down_bt;
      ImageView mestoryCategoryDownBt = ViewBindings.findChildViewById(rootView, id);
      if (mestoryCategoryDownBt == null) {
        break missingId;
      }

      id = R.id.mestory_category_emoticon_tv;
      TextView mestoryCategoryEmoticonTv = ViewBindings.findChildViewById(rootView, id);
      if (mestoryCategoryEmoticonTv == null) {
        break missingId;
      }

      id = R.id.mestory_category_progressBar;
      ProgressBar mestoryCategoryProgressBar = ViewBindings.findChildViewById(rootView, id);
      if (mestoryCategoryProgressBar == null) {
        break missingId;
      }

      id = R.id.mestory_category_progress_minute_tv;
      TextView mestoryCategoryProgressMinuteTv = ViewBindings.findChildViewById(rootView, id);
      if (mestoryCategoryProgressMinuteTv == null) {
        break missingId;
      }

      id = R.id.mestory_category_progress_percentage_tv;
      TextView mestoryCategoryProgressPercentageTv = ViewBindings.findChildViewById(rootView, id);
      if (mestoryCategoryProgressPercentageTv == null) {
        break missingId;
      }

      id = R.id.mestory_category_tv;
      TextView mestoryCategoryTv = ViewBindings.findChildViewById(rootView, id);
      if (mestoryCategoryTv == null) {
        break missingId;
      }

      id = R.id.mestory_category_up_bt;
      ImageView mestoryCategoryUpBt = ViewBindings.findChildViewById(rootView, id);
      if (mestoryCategoryUpBt == null) {
        break missingId;
      }

      id = R.id.mestory_item_opened_cv;
      CardView mestoryItemOpenedCv = ViewBindings.findChildViewById(rootView, id);
      if (mestoryItemOpenedCv == null) {
        break missingId;
      }

      return new ItemMestoryCategoryOpenBinding((RelativeLayout) rootView, mestoryCategoryDetailTv,
          mestoryCategoryDownBt, mestoryCategoryEmoticonTv, mestoryCategoryProgressBar,
          mestoryCategoryProgressMinuteTv, mestoryCategoryProgressPercentageTv, mestoryCategoryTv,
          mestoryCategoryUpBt, mestoryItemOpenedCv);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}