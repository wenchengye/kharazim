package com.heqi.kharazim.explore.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.heqi.kharazim.R;
import com.heqi.kharazim.explore.model.PlanLiteInfo;
import com.heqi.kharazim.utils.ViewUtils;

/**
 * Created by overspark on 2017/3/25.
 */

public class ExplorePlanDetailExplainView extends RelativeLayout {

  private TextView explainTv;

  public ExplorePlanDetailExplainView(Context context) {
    super(context);
  }

  public ExplorePlanDetailExplainView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public ExplorePlanDetailExplainView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  public static ExplorePlanDetailExplainView newInstance(ViewGroup parent) {
    return (ExplorePlanDetailExplainView) ViewUtils.newInstance(parent,
        R.layout.explore_plan_detail_explain_card);
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();

    explainTv = (TextView) findViewById(R.id.explain_content_tv);
  }

  public void setData(PlanLiteInfo.PlanDescriptionInfo data) {

    if (data == null) return;

    if (explainTv != null) {
      explainTv.setText(data.getPlandecl());
    }
  }
}
