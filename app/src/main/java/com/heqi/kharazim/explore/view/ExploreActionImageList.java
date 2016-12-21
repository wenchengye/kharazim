package com.heqi.kharazim.explore.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.heqi.kharazim.R;
import com.heqi.kharazim.explore.model.PlanDetailInfo;
import com.heqi.kharazim.ui.adapter.DataAdapter;
import com.heqi.kharazim.utils.ViewUtils;
import com.heqi.kharazim.view.HorizontalListView;

import java.util.List;

/**
 * Created by overspark on 2016/11/27.
 */

public class ExploreActionImageList extends HorizontalListView {

  private ActionImageListAdapter adapter;
  private List<PlanDetailInfo.PlanActionInfo> data;

  public ExploreActionImageList(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public static ExploreActionImageList newInstance(ViewGroup parent) {
    return (ExploreActionImageList) ViewUtils.newInstance(parent,
        R.layout.explore_plan_detail_action_image_list);
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();

    adapter = new ActionImageListAdapter();
    this.setAdapter(adapter);
    if (this.data != null) {
      this.adapter.setData(this.data);
      this.adapter.notifyDataSetChanged();
    }
  }

  public void setData(List<PlanDetailInfo.PlanActionInfo> data) {
    if (data == null || data == this.data) return;

    this.data = data;
    if (adapter != null) {
      adapter.setData(this.data);
      adapter.notifyDataSetChanged();
    }
  }

  private static class ActionImageListAdapter extends DataAdapter<PlanDetailInfo.PlanActionInfo> {
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      ExploreActionImageView view;
      if (convertView instanceof ExploreActionImageView) {
        view = (ExploreActionImageView) convertView;
      } else {
        view = ExploreActionImageView.newInstance(parent);
      }

      view.setData(getItem(position));

      return view;
    }
  }

}
