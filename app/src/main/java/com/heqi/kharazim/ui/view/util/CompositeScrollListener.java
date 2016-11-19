package com.heqi.kharazim.ui.view.util;

import android.os.Looper;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CompositeScrollListener implements OnScrollListener {
  private final List<OnScrollListener> scrollListenerList = new
      ArrayList<OnScrollListener>();

  public void addOnScrollListener(OnScrollListener listener) {
    throwIfNotOnMainThread();
    if (listener == null) {
      return;
    }
    for (OnScrollListener scrollListener : scrollListenerList) {
      if (listener == scrollListener) {
        return;
      }
    }
    scrollListenerList.add(listener);
  }

  private void throwIfNotOnMainThread() {
    if (Looper.myLooper() != Looper.getMainLooper()) {
      throw new IllegalStateException("Must be invoked from the main thread.");
    }
  }

  public void removeOnScrollListener(OnScrollListener listener) {
    throwIfNotOnMainThread();
    if (listener == null) {
      return;
    }
    Iterator<OnScrollListener> iterator = scrollListenerList.iterator();
    while (iterator.hasNext()) {
      OnScrollListener scrollListener = iterator.next();
      if (listener == scrollListener) {
        iterator.remove();
        return;
      }
    }
  }

  @Override
  public void onScrollStateChanged(AbsListView view, int scrollState) {
    List<OnScrollListener> listeners = new ArrayList<OnScrollListener>(scrollListenerList);
    for (OnScrollListener listener : listeners) {
      listener.onScrollStateChanged(view, scrollState);
    }
  }

  @Override
  public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                       int totalItemCount) {
    List<OnScrollListener> listeners = new ArrayList<OnScrollListener>(scrollListenerList);
    for (OnScrollListener listener : listeners) {
      listener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
    }
  }

  /**
   * The callback when removing a listener. You should remove the listener passed out by calling
   * removeOnScrollListener.
   *
   * @see com.wandoujia.p4.view.ContentListView
   * @see com.wandoujia.p4.view.ContentGridView
   */
  public interface RemoveOnScrollListenerCallback {
    void removeOnScrollListener(OnScrollListener listener);
  }
}
