package com.heqi.kharazim.explore.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.heqi.image.view.AsyncImageView;
import com.heqi.kharazim.R;
import com.heqi.kharazim.explore.model.PlanLiteInfo;
import com.heqi.kharazim.utils.ViewUtils;

/**
 * Created by overspark on 2016/11/20.
 */

public class ExplorePlanLiteView extends RelativeLayout {

  private AsyncImageView planSummaryIv;
  private TextView planTitleTv;
  private FrameLayout planDifficultyPlaceHolder;
  private ExploreDifficultyLevelView planDifficultyView;
  private TextView planAcupointCountTv;
  private TextView planSpanTv;
  private TextView planSpanDailyTv;
  private ImageView planAddBtn;
  private ExploreCircleProgressView progressView;

  private PlanLiteInfo data;
  private ExplorePlanLiteViewListener listener;

  private boolean showAddIcon = true;
  private boolean showProgress = false;

  public ExplorePlanLiteView(Context context) {
    super(context);
  }

  public ExplorePlanLiteView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public ExplorePlanLiteView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  public static ExplorePlanLiteView newInstance(ViewGroup parent) {
    return (ExplorePlanLiteView) ViewUtils.newInstance(parent, R.layout.explore_plan_lite_card);
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();

    planSummaryIv = (AsyncImageView) findViewById(R.id.plan_lite_summary_bg);
    planDifficultyPlaceHolder = (FrameLayout) findViewById(R.id.plan_lite_difficulty_place_holder);
    planTitleTv = (TextView) findViewById(R.id.plan_lite_title_tv);
    planAcupointCountTv = (TextView) findViewById(R.id.plan_lite_acupoint_count_tv);
    planSpanTv = (TextView) findViewById(R.id.plan_lite_span_tv);
    planSpanDailyTv = (TextView) findViewById(R.id.plan_lite_span_daily_tv);
    planAddBtn = (ImageView) findViewById(R.id.plan_lite_add_btn);
    progressView = (ExploreCircleProgressView) findViewById(R.id.plan_lite_progress);

    if (planDifficultyPlaceHolder != null) {
      planDifficultyView = ExploreDifficultyLevelView.newInstance(planDifficultyPlaceHolder);
      planDifficultyPlaceHolder.addView(planDifficultyView);
    }

    if (planAddBtn != null) {
      planAddBtn.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View view) {
          if (listener != null) {
            listener.onAddPlan(data);
          }
        }
      });
      adjustAddIconVisibility();
    }

    if (progressView != null) {
      adjustProgressVisibility();
    }
  }

  public void setData(@NonNull PlanLiteInfo data) {

    if (this.data == data) return;

    this.data = data;
    if (!TextUtils.isEmpty(this.data.getPlanimg()) && planSummaryIv != null) {
      planSummaryIv.loadNetworkImage(this.data.getPlanimg(), R.drawable.icon_plan_card_place_holder);
    }
    if (planDifficultyView != null) {
      planDifficultyView.setDifficultyLevel(this.data.getPlanlev());
    }
    if (!TextUtils.isEmpty(this.data.getPlanname()) && planTitleTv != null) {
      planTitleTv.setText(this.data.getPlanname());
    }
    if (planAcupointCountTv != null) {
      planAcupointCountTv.setText(String.valueOf(this.data.getAcupointcnt()));
    }
    if (planSpanTv != null) {
      planSpanTv.setText(String.valueOf(this.data.getCpdays()));
    }
    if (planSpanDailyTv != null) {
      planSpanDailyTv.setText(this.data.getDaytime());
    }

    if (planAddBtn != null) {
      adjustAddIconVisibility();
    }

    if (this.data.isMyplan() && progressView != null) {
      progressView.setMax(this.data.getCpdays());
      progressView.setMaxText(String.valueOf(this.data.getCpdays()));
      progressView.setProgress(this.data.getMycpdays());
      progressView.setProgressText(String.valueOf(this.data.getMycpdays()));

      adjustProgressVisibility();
    }
  }

  public void setListener(ExplorePlanLiteViewListener listener) {
    this.listener = listener;
  }

  public void setShowAddIcon(boolean showAddIcon) {
    this.showAddIcon = showAddIcon;
    if (planAddBtn != null) {
      adjustAddIconVisibility();
    }
  }

  public void setShowProgress(boolean showProgress) {
    this.showProgress = showProgress;
    if (progressView != null) {
      adjustProgressVisibility();
    }
  }

  private void adjustAddIconVisibility() {
    planAddBtn.setVisibility(this.data == null || this.data.isMyplan() || !this.showAddIcon
        ? View.GONE : View.VISIBLE);
  }

  private void adjustProgressVisibility() {
    progressView.setVisibility(this.data != null && this.data.isMyplan() && this.showProgress
        ? View.VISIBLE : View.GONE);
  }

  public interface ExplorePlanLiteViewListener {
    void onAddPlan(PlanLiteInfo data);
  }
}
