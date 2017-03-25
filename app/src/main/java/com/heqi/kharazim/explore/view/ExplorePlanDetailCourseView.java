package com.heqi.kharazim.explore.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.heqi.kharazim.KharazimApplication;
import com.heqi.kharazim.R;
import com.heqi.kharazim.explore.model.PlanDetailInfo;
import com.heqi.kharazim.utils.ViewUtils;

/**
 * Created by overspark on 2016/11/25.
 */

public class ExplorePlanDetailCourseView extends RelativeLayout {

  private TextView courseDayIndexTv;
  private TextView courseContentSizeTv;
  private ImageView courseFinishStatusIv;
  private TextView courseFinishDateTv;

  private PlanDetailInfo.PlanCourseInfo info;
  private int dayIndex;

  public ExplorePlanDetailCourseView(Context context) {
    super(context);
  }

  public ExplorePlanDetailCourseView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public ExplorePlanDetailCourseView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  public static ExplorePlanDetailCourseView newInstance(ViewGroup parent) {
    return (ExplorePlanDetailCourseView) ViewUtils.newInstance(parent,
        R.layout.explore_plan_detail_course_card);
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();

    courseDayIndexTv = (TextView) findViewById(R.id.course_day_index_tv);
    courseContentSizeTv = (TextView) findViewById(R.id.course_content_size_tv);
    courseFinishStatusIv = (ImageView) findViewById(R.id.course_finish_status_iv);
    courseFinishDateTv = (TextView) findViewById(R.id.course_finish_date_tv);
  }

  public void setData(PlanDetailInfo.PlanCourseInfo info, int dayIndex, boolean showFinish) {

    this.info = info;
    this.dayIndex = dayIndex;

    if (courseDayIndexTv != null) {
      courseDayIndexTv.setText(String.format(
          getContext().getResources().getString(R.string.course_day_index_text_format),
          String.valueOf(this.dayIndex + 1)));
    }

    if (this.info != null && courseContentSizeTv != null) {
      courseContentSizeTv.setText(this.info.getTitle());
    }

    courseFinishStatusIv.setVisibility(showFinish ? VISIBLE : GONE);
    courseFinishDateTv.setVisibility(showFinish ? VISIBLE : GONE);

    if (showFinish) {
      if (TextUtils.isEmpty(info.getCpdatetime())) {
        courseFinishStatusIv.setImageResource(R.drawable.icon_explore_detail_course_not_finish);
        courseFinishDateTv.setText(KharazimApplication.getAppContext().getString(
            R.string.explore_detail_course_not_finish_time_text));
      } else {
        courseFinishStatusIv.setImageResource(R.drawable.icon_explore_detail_course_finish);
        courseFinishDateTv.setText(info.getCpdatetime());
      }
    }
  }

  public PlanDetailInfo.PlanCourseInfo getInfo() {
    return info;
  }

  public void setInfo(PlanDetailInfo.PlanCourseInfo info) {
    this.info = info;
  }

  public int getDayIndex() {
    return dayIndex;
  }

  public void setDayIndex(int dayIndex) {
    this.dayIndex = dayIndex;
  }
}
