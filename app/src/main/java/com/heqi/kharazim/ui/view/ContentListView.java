package com.heqi.kharazim.ui.view;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.ListView;

import com.heqi.kharazim.R;
import com.heqi.kharazim.ui.view.util.CompositeScrollListener;
import com.heqi.kharazim.ui.view.util.OnScrollListenerHolder;

public class ContentListView extends ListView implements OnScrollListenerHolder {
  private final CompositeScrollListener compositeScrollListener = new CompositeScrollListener();

  private boolean showShadow = false;
  private View shadowView;

  {
    super.setOnScrollListener(compositeScrollListener);
  }

  public ContentListView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  public ContentListView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public ContentListView(Context context) {
    super(context);
  }

  /**
   * Add an OnScrollListener, will not replace the previous added OnScrollListener.
   * <p>
   * <b>Make sure call this on UI thread</b>
   * </p>
   *
   * @param listener the listener to add
   */
  @Override
  public void setOnScrollListener(final OnScrollListener listener) {
    compositeScrollListener.addOnScrollListener(listener);
  }

  /**
   * Add an OnScrollListener, will not replace the previous added OnScrollListener.
   * <p>
   * <b>Make sure call this on UI thread</b>
   * </p>
   *
   * @param listener the listener to add
   */
  public void addOnScrollListener(final OnScrollListener listener) {
    compositeScrollListener.addOnScrollListener(listener);
  }

  /**
   * Remove a previous added scrollListener, will only remove exact the same object.
   * <p>
   * <b>Make sure call this on UI thread.</b>
   * </p>
   *
   * @param listener the listener to remove
   */
  @Override
  public void removeOnScrollListener(final OnScrollListener listener) {
    compositeScrollListener.removeOnScrollListener(listener);
  }

  /**
   * Need to call before call setOnScrollListener.
   *
   * @param shadowView the shadow view
   */
  public void setTopShadowView(View shadowView) {
    if (shadowView == null) {
      return;
    }
    this.shadowView = shadowView;
    addOnScrollListener(new OnScrollListener() {

      @Override
      public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                           int totalItemCount) {
        View firstChild = view.getChildAt(0);
        if (firstChild != null) {
          if (firstVisibleItem == 0 && firstChild.getTop() == 0) {
            showShadow = false;
            hideTopShadow();
          } else if (!showShadow) {
            showShadow = true;
            showTopShadow();
          }
        }
      }

      @Override
      public void onScrollStateChanged(AbsListView view, int scrollState) {
      }
    });
  }

  private void showTopShadow() {
    if (shadowView == null || shadowView.getVisibility() == View.VISIBLE) {
      return;
    }
    shadowView.setVisibility(View.VISIBLE);
    Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.short_fade_in);
    shadowView.startAnimation(anim);
  }

  private void hideTopShadow() {
    if (shadowView == null || shadowView.getVisibility() == View.GONE) {
      return;
    }
    Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.short_fade_out);
    anim.setAnimationListener(new Animation.AnimationListener() {

      @Override
      public void onAnimationStart(Animation animation) {
      }

      @Override
      public void onAnimationRepeat(Animation animation) {
      }

      @Override
      public void onAnimationEnd(Animation animation) {
        shadowView.setVisibility(View.GONE);
      }
    });
    shadowView.startAnimation(anim);
  }

  @Override
  protected void onAttachedToWindow() {
    super.onAttachedToWindow();
  }

  @Override
  protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
  }

}
