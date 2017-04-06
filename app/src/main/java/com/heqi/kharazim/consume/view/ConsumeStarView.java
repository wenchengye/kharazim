package com.heqi.kharazim.consume.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.heqi.kharazim.R;
import com.heqi.kharazim.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by overspark on 2017/4/5.
 */

public class ConsumeStarView extends LinearLayout {

  private List<ImageView> starViews = new ArrayList<>();

  private int star = 0;

  public ConsumeStarView(Context context) {
    super(context);
  }

  public ConsumeStarView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public ConsumeStarView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  public static ConsumeStarView newInstance(ViewGroup parent) {
    return (ConsumeStarView) ViewUtils.newInstance(parent, R.layout.consumer_star_view);
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();

    init();
  }

  private void init() {
    for (int i = 0; i < getChildCount(); ++i) {
      View child = getChildAt(i);
      if (child instanceof ImageView) {
        starViews.add((ImageView) child);
      }
    }

    for (int i = 0; i < starViews.size(); ++i) {
      final int indexRef = i;
      starViews.get(i).setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
          setStar(indexRef + 1);
        }
      });
    }

    setStar(0);
  }

  public int getStar() {
    return star;
  }

  public void setStar(int star) {
    if (star < 0) star = 0;
    else if (star > starViews.size()) star = starViews.size();
    this.star = star;

    for (int i = 0; i < starViews.size(); ++i) {
      if (i < star) starViews.get(i).setImageResource(R.drawable.icon_consume_star_light);
      else starViews.get(i).setImageResource(R.drawable.icon_consume_star_gray);
    }
  }
}
