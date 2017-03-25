package com.heqi.kharazim.explore.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.heqi.kharazim.KharazimApplication;
import com.heqi.kharazim.R;
import com.heqi.kharazim.explore.model.PlanDetailInfo;
import com.heqi.kharazim.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by overspark on 2017/3/18.
 */

public class ExplorePlanDetailUserProgressView extends RelativeLayout {

  private ExploreCircleProgressView timeProgress;
  private ExploreCircleProgressView acupointProgress;
  private TextView courseProgressText;
  private TextView currentCourseText;

  private PlanDetailInfo.PlanCourseInfo current;
  private List<PlanDetailInfo.PlanCourseInfo> finishedCourses;

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
    return (ExplorePlanDetailUserProgressView)ViewUtils.newInstance(parent,
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

  public void setData(@NonNull PlanDetailInfo planDetailInfo) {
    this.finishedCourses = new ArrayList<PlanDetailInfo.PlanCourseInfo>();

    int index = 0;
    int size = planDetailInfo.getData_info() != null ? planDetailInfo.getData_info().size() : 0;
    for (; index < size; ++index) {
      if (TextUtils.isEmpty(planDetailInfo.getData_info().get(index).getCpdatetime())) {
        break;
      } else {
        this.finishedCourses.add(planDetailInfo.getData_info().get(index));
      }
    }

    this.current = index >= size
        ? (planDetailInfo.getData_info() != null && planDetailInfo.getData_info().size() > 0
        ? planDetailInfo.getData_info().get(0) : null)
        : planDetailInfo.getData_info().get(index);

    //TODO: time progress

    int acupointProgressMax = 0;
    for (int i = 0; i < size; ++i) {
      List<PlanDetailInfo.PlanActionInfo> actionInfoList =
          planDetailInfo.getData_info().get(i).getPlanDailyActDtoList();
      if (actionInfoList != null) {
        acupointProgressMax += actionInfoList.size();
      }
    }
    int acupointProgressValue = 0;
    for (int i = 0; i < finishedCourses.size(); ++i) {
      List<PlanDetailInfo.PlanActionInfo> actionInfoList =
          finishedCourses.get(i).getPlanDailyActDtoList();
      if (actionInfoList != null) {
        acupointProgressValue += actionInfoList.size();
      }
    }
    acupointProgress.setMaxText(String.valueOf(acupointProgressMax));
    acupointProgress.setMax(acupointProgressMax);
    acupointProgress.setProgressText(String.valueOf(acupointProgressValue));
    acupointProgress.setProgress(acupointProgressValue);

    courseProgressText.setText(String.valueOf(finishedCourses.size()));

    currentCourseText.setText(this.current == null ? ""
        : String.format(KharazimApplication.getAppContext().getString(
        R.string.explore_detail_current_course_text_format), this.current.getDailydec()));
  }

  public PlanDetailInfo.PlanCourseInfo getCurrent() {
    return current;
  }
}
