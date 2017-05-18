package com.heqi.kharazim.explore.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.heqi.kharazim.KharazimApplication;
import com.heqi.kharazim.R;
import com.heqi.kharazim.archives.model.PlanProgressInfo;
import com.heqi.kharazim.explore.model.PlanDetailInfo;
import com.heqi.kharazim.explore.model.PlanLiteInfo;
import com.heqi.kharazim.utils.ViewUtils;

/**
 * Created by overspark on 2017/3/18.
 */

public class ExplorePlanDetailUserProgressView extends RelativeLayout {

  private ExploreCircleProgressView timeProgress;
  private ExploreCircleProgressView acupointProgress;
  private TextView courseProgressText;
  private TextView currentCourseText;

  private PlanDetailInfo.PlanCourseInfo current;

  public ExplorePlanDetailUserProgressView(Context context) {
    super(context);
  }

  public ExplorePlanDetailUserProgressView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public ExplorePlanDetailUserProgressView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  public static ExplorePlanDetailUserProgressView newInstance(ViewGroup parent) {
    return (ExplorePlanDetailUserProgressView) ViewUtils.newInstance(parent,
        R.layout.explore_plan_detail_user_progress_view);
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();

    timeProgress = (ExploreCircleProgressView) findViewById(R.id.user_progress_time_progress);
    acupointProgress = (ExploreCircleProgressView)
        findViewById(R.id.user_progress_acupoint_progress);
    courseProgressText = (TextView) findViewById(R.id.user_progress_count_tv);
    currentCourseText = (TextView) findViewById(R.id.current_course_title_tv);
  }

  public void setData(@NonNull PlanProgressInfo planProgressInfo,
                      @NonNull PlanDetailInfo planDetailInfo,
                      @NonNull PlanLiteInfo planLiteInfo) {

    int index;
    try {
      index = Integer.valueOf(planProgressInfo.getNextnum());
    } catch (NumberFormatException e) {
      index = 1;
    }

    int size = planDetailInfo.getData_info() != null ? planDetailInfo.getData_info().size() : 0;
    this.current = (index - 1) >= size
        ? (planDetailInfo.getData_info() != null && planDetailInfo.getData_info().size() > 0
        ? planDetailInfo.getData_info().get(0) : null)
        : planDetailInfo.getData_info().get(index - 1);

    timeProgress.setMaxText(String.valueOf(planLiteInfo.getCptime()));
    timeProgress.setMax(planLiteInfo.getCptime());
    timeProgress.setProgressText(String.valueOf(planProgressInfo.getCptimes()));
    timeProgress.setProgress(planProgressInfo.getCptimes());

    acupointProgress.setMaxText(String.valueOf(planLiteInfo.getAcupointcnt()));
    acupointProgress.setMax(planLiteInfo.getAcupointcnt());
    acupointProgress.setProgressText(String.valueOf(planProgressInfo.getActcnt()));
    acupointProgress.setProgress(planProgressInfo.getActcnt());

    courseProgressText.setText(String.valueOf(planProgressInfo.getCpdate()));

    currentCourseText.setText(this.current == null ? ""
        : String.format(KharazimApplication.getAppContext().getString(
        R.string.explore_detail_current_course_text_format), this.current.getDailydec()));
  }

  public PlanDetailInfo.PlanCourseInfo getCurrent() {
    return current;
  }
}
