package com.heqi.kharazim.explore.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.heqi.image.view.AsyncImageView;
import com.heqi.kharazim.R;
import com.heqi.kharazim.explore.model.PlanDetailInfo;
import com.heqi.kharazim.utils.ViewUtils;

/**
 * Created by overspark on 2016/11/27.
 */

public class ExploreActionImageView extends RelativeLayout {

  private AsyncImageView actionImageIv;
  private TextView actionNameTv;

  private PlanDetailInfo.PlanActionInfo data;

  public ExploreActionImageView(Context context) {
    super(context);
  }

  public ExploreActionImageView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public ExploreActionImageView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  public static ExploreActionImageView newInstance(ViewGroup parent) {
    return (ExploreActionImageView) ViewUtils.newInstance(parent,
        R.layout.explore_plan_detail_action_image_view);
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();

    actionImageIv = (AsyncImageView) findViewById(R.id.action_image_view);
    actionNameTv = (TextView) findViewById(R.id.action_name_text_view);
  }

  public void setData(PlanDetailInfo.PlanActionInfo data) {
    if (data == null || data == this.data) return;

    this.data = data;

    if (actionImageIv != null) {
      //TODO: default image
      actionImageIv.loadNetworkImage(data.getActimg(), R.drawable.icon_kharazim_image_logo);
    }

    if (actionNameTv != null) {
      actionNameTv.setText(data.getAcupointname());
    }
  }

  public View getImageView() {
    return actionImageIv;
  }
}
