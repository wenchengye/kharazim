package com.heqi.kharazim.ui.view.util;

import android.widget.AbsListView;

/**
 * holder of {@link AbsListView.OnScrollListener}, can add and remove
 */
public interface OnScrollListenerHolder {

  public abstract void addOnScrollListener(AbsListView.OnScrollListener listener);

  public abstract void removeOnScrollListener(AbsListView.OnScrollListener listener);
}
