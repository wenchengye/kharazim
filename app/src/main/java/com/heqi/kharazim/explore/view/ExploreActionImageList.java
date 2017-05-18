package com.heqi.kharazim.explore.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.heqi.kharazim.R;
import com.heqi.kharazim.explore.model.PlanDetailInfo;
import com.heqi.kharazim.ui.adapter.DataAdapter;
import com.heqi.kharazim.utils.ViewUtils;

import java.util.List;

import it.sephiroth.android.library.widget.HListView;

/**
 * Created by overspark on 2016/11/27.
 */

public class ExploreActionImageList extends HListView {

  private ActionImageListAdapter adapter;
  private List<PlanDetailInfo.PlanActionInfo> data;
  private ExploreActionImageListListener listener;
  private ExploreActionImageListListener innerListener = new ExploreActionImageListListener() {
    @Override
    public void onActionClicked(List<PlanDetailInfo.PlanActionInfo> actionList, int index) {
      if (listener != null) listener.onActionClicked(actionList, index);
    }
  };

  public ExploreActionImageList(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public static ExploreActionImageList newInstance(ViewGroup parent) {
    return (ExploreActionImageList) ViewUtils.newInstance(parent,
        R.layout.explore_plan_detail_action_image_list);
  }

  public void setListener(ExploreActionImageListListener listener) {
    this.listener = listener;
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();

    adapter = new ActionImageListAdapter(innerListener);
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

  public interface ExploreActionImageListListener {
    void onActionClicked(List<PlanDetailInfo.PlanActionInfo> actionList, int index);
  }

  private static class ActionImageListAdapter extends DataAdapter<PlanDetailInfo.PlanActionInfo> {

    private ExploreActionImageListListener listener;

    public ActionImageListAdapter(ExploreActionImageListListener listener) {
      this.listener = listener;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
      ExploreActionImageView view;
      if (convertView instanceof ExploreActionImageView) {
        view = (ExploreActionImageView) convertView;
      } else {
        view = ExploreActionImageView.newInstance(parent);
      }

      view.setData(getItem(position));
      if (listener != null) {
        view.setOnClickListener(new OnClickListener() {
          @Override
          public void onClick(View v) {
            if (listener != null) {
              listener.onActionClicked(getData(), position);
            }
          }
        });
      }

      return view;
    }
  }

}
