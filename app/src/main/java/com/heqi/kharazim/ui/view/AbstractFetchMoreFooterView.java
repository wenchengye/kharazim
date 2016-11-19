package com.heqi.kharazim.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public abstract class AbstractFetchMoreFooterView extends FrameLayout {

  private View loadingView;
  private ImageView noMoreView;
  private TextView emptyView;

  public AbstractFetchMoreFooterView(Context context) {
    super(context);
  }

  public AbstractFetchMoreFooterView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public AbstractFetchMoreFooterView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();
  }

  public abstract void showLoading();

  public abstract void showNoMore();

  public abstract void showEmpty();
}
