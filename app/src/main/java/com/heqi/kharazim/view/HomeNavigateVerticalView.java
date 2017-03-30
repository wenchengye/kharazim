package com.heqi.kharazim.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.heqi.kharazim.R;
import com.heqi.kharazim.activity.VerticalType;
import com.heqi.kharazim.utils.ViewUtils;

/**
 * Created by overspark on 2017/3/29.
 */

public class HomeNavigateVerticalView extends ImageView {

  private VerticalType type;
  private boolean selected = false;

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
    return (HomeNavigateVerticalView) ViewUtils.newInstance(viewGroup,
        R.layout.home_navigate_vertical_view);
  }

  public void setVerticalType(VerticalType type) {
    this.type = type;
    this.setImageResource(type.getIconRes());
  }

  public void setVerticalViewSelected(boolean selected) {
    this.selected = selected;
    if (this.selected) {
      this.setImageResource(type.getIconSelectedRes());
    } else {
      this.setImageResource(type.getIconRes());
    }
  }

  public VerticalType getVerticalType() {
    return type;
  }
}
