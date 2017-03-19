package com.heqi.kharazim.ui.adapter;

import android.widget.BaseAdapter;

import java.util.List;

public abstract class DataAdapter<T> extends BaseAdapter {

  protected List<T> data;

  public List<T> getData() {
    return data;
  }

  public void setData(List<T> data) {
    this.data = data;
    notifyDataSetChanged();
  }

  @Override
  public T getItem(int position) {
    return data.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public int getCount() {
    return data == null ? 0 : data.size();
  }


  public void clear() {
    if (data != null) {
      data.clear();
    }
    notifyDataSetChanged();
  }
}
