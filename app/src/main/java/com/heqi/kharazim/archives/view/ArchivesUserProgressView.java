package com.heqi.kharazim.archives.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.heqi.kharazim.R;
import com.heqi.kharazim.archives.model.UserStatsInfo;
import com.heqi.kharazim.explore.view.ExploreCircleProgressView;
import com.heqi.kharazim.utils.ViewUtils;

/**
 * Created by overspark on 2017/3/19.
 */

public class ArchivesUserProgressView extends LinearLayout {

  private ExploreCircleProgressView courseProgress;
  private ExploreCircleProgressView timeProgress;
  private TextView kharazimPointTv;
  private TextView dayProgressTv;

  public ArchivesUserProgressView(Context context) {
    super(context);
  }

  public ArchivesUserProgressView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public ArchivesUserProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  public static ArchivesUserProgressView newInstance(ViewGroup viewGroup) {
    return (ArchivesUserProgressView) ViewUtils.newInstance(viewGroup,
        R.layout.archives_user_progress_view);
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();

    courseProgress = (ExploreCircleProgressView)
        findViewById(R.id.archives_user_progress_course_progress);
    timeProgress = (ExploreCircleProgressView)
        findViewById(R.id.archives_user_progress_time_progress);
    kharazimPointTv = (TextView) findViewById(
        R.id.archives_user_progress_kharazim_point_progress_tv);
    dayProgressTv = (TextView) findViewById(R.id.archives_user_progress_day_tv);
  }

  public void setData(UserStatsInfo data) {
    timeProgress.setMax(data.getCptimes());
    timeProgress.setProgress(data.getCptimes());
    timeProgress.setMaxText(String.valueOf(data.getCptimes()));
    timeProgress.setProgressText(String.valueOf(data.getCptimes()));

    courseProgress.setMax(data.getCpcnt());
    courseProgress.setProgress(data.getCpcnt());
    courseProgress.setMaxText(String.valueOf(data.getCpcnt()));
    courseProgress.setProgressText(String.valueOf(data.getCpcnt()));

    kharazimPointTv.setText(String.valueOf(data.getVitality()));
    dayProgressTv.setText(String.valueOf(data.getDaycnt()));
  }
}
