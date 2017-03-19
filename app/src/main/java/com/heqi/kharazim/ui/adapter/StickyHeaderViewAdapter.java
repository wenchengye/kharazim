package com.heqi.kharazim.ui.adapter;

import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;

import com.heqi.kharazim.ui.view.sticky.StickyListHeadersAdapter;

/**
 * Created by wenchengye on 13-7-12.
 */
public class StickyHeaderViewAdapter extends HeaderViewAdapter
    implements StickyListHeadersAdapter {

  public StickyHeaderViewAdapter(List<View> headerViews, List<View> footerViews,
      ListAdapter adapter) {
    super(headerViews, footerViews, adapter);
    if (adapter != null && !(adapter instanceof StickyListHeadersAdapter)) {
      throw new IllegalArgumentException(
          "Adapter must implement StickyListHeadersAdapter");
    }
  }

  @Override
  public View getHeaderView(int position, View convertView, ViewGroup parent) {
    int numHeaders = getHeadersCount();
    if (position < numHeaders) {
      return newHeaderViewStickyHeader(position, convertView, parent);
    }

    // Adapter
    final int adjPosition = position - numHeaders;
    if (getWrappedAdapter() != null) {
      int adapterCount = getWrappedAdapter().getCount();
      if (adjPosition < adapterCount) {
        return ((StickyListHeadersAdapter) getWrappedAdapter()).getHeaderView(adjPosition,
            convertView,
            parent);
      }
    }

    return newHeaderViewStickyHeader(position, convertView, parent);
  }

  @Override
  public long getHeaderId(int position) {
    int numHeaders = getHeadersCount();
    if (position < numHeaders) {
      return -1;
    }

    // Adapter
    final int adjPosition = position - numHeaders;
    int adapterCount = 0;
    if (getWrappedAdapter() != null) {
      adapterCount = getWrappedAdapter().getCount();
      if (adjPosition < adapterCount) {
        return ((StickyListHeadersAdapter) getWrappedAdapter()).getHeaderId(adjPosition);
      }
    }

    return -2;
  }

  protected View newHeaderViewStickyHeader(int position, View convertView, ViewGroup parent) {
    ImageView imageView = new ImageView(parent.getContext());
    imageView.setVisibility(View.GONE);// will not draw this view
    return imageView;
  }
}
