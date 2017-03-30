package com.heqi.kharazim.explore.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.heqi.kharazim.R;
import com.heqi.kharazim.utils.ViewUtils;

/**
 * Created by overspark on 2016/11/23.
 */

public class ExploreDifficultyLevelView extends LinearLayout {

  private static final int MAX_DIFFICULTY_LEVEL = 5;

  private int difficultyLevel;

  public ExploreDifficultyLevelView(Context context) {
    super(context);
  }

  public ExploreDifficultyLevelView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public ExploreDifficultyLevelView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  public static ExploreDifficultyLevelView newInstance(ViewGroup parent) {
    return (ExploreDifficultyLevelView) ViewUtils.newInstance(parent,
        R.layout.explore_difficulty_level_view);
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();

    refreshView();
  }

  public void setDifficultyLevel(int difficultyLevel) {

    difficultyLevel = Math.max(0, Math.min(difficultyLevel, MAX_DIFFICULTY_LEVEL));
    if (this.difficultyLevel == difficultyLevel) return;

    this.difficultyLevel = difficultyLevel;
    refreshView();
  }

  private void refreshView() {
    for (int i = 0; i < this.getChildCount(); ++i) {
      this.getChildAt(i).setBackgroundResource(R.color.explore_difficulty_level_view_item_disable);
    }

    for (int i = 0; i < this.difficultyLevel; ++i) {
      this.getChildAt(i).setBackgroundResource(R.color.kharazim_design_color);
    }
  }
}
