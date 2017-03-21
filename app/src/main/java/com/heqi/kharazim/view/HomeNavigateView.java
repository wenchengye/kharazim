package com.heqi.kharazim.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.heqi.kharazim.activity.VerticalType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by overspark on 2017/3/21.
 */

public class HomeNavigateView extends LinearLayout {

  private List<HomeNavigateVerticalView> verticalViews = new ArrayList<HomeNavigateVerticalView>();
  private OnVerticalTypeSelectedListener listener;

  public HomeNavigateView(Context context) {
    super(context);
  }

  public HomeNavigateView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public HomeNavigateView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  public void addVerticalType(@NonNull VerticalType type) {

    for (HomeNavigateVerticalView view : verticalViews) {
      if (view != null && view.getVerticalType().ordinal() == type.ordinal()) return;
    }

    HomeNavigateVerticalView verticalView = HomeNavigateVerticalView.newInstance(this);
    verticalView.setVerticalType(type);

    verticalView.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if (v instanceof HomeNavigateVerticalView) {
          selectVerticalType(((HomeNavigateVerticalView)v).getVerticalType());
        }
      }
    });
    verticalViews.add(verticalView);

  }

  public void selectVerticalType(VerticalType type) {
    for (HomeNavigateVerticalView view : verticalViews) {
      if (view != null) {
        view.setSelected(view.getVerticalType().ordinal() == type.ordinal());
      }
    }

    if (listener != null) {
      listener.onVerticalTypeSelected(type);
    }
  }

  public void setListener(OnVerticalTypeSelectedListener listener) {
    this.listener = listener;
  }

  private static class HomeNavigateVerticalView extends ImageView {

    private VerticalType type;

    public HomeNavigateVerticalView(Context context) {
      super(context);
    }

    public HomeNavigateVerticalView(Context context, AttributeSet attrs) {
      super(context, attrs);
    }

    public HomeNavigateVerticalView(Context context, AttributeSet attrs, int defStyleAttr) {
      super(context, attrs, defStyleAttr);
    }

    public static HomeNavigateVerticalView newInstance(ViewGroup viewGroup) {
      return null;
    }

    public void setVerticalType(VerticalType type) {
      this.type = type;
      this.setImageResource(type.getIconRes());
    }

    public VerticalType getVerticalType() {
      return type;
    }
  }

  public interface OnVerticalTypeSelectedListener {
    void onVerticalTypeSelected(VerticalType type);
  }
}
