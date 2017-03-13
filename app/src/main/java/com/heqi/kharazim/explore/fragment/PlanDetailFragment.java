package com.heqi.kharazim.explore.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.heqi.kharazim.R;
import com.heqi.kharazim.explore.activity.ConsumeActivity;
import com.heqi.kharazim.explore.view.ExplorePlanDetailCourseListView;
import com.heqi.kharazim.explore.view.ExplorePlanLiteView;
import com.heqi.kharazim.ui.fragment.async.NetworkAsyncLoadFragment;
import com.heqi.kharazim.explore.model.PlanDetailInfo;
import com.heqi.kharazim.explore.model.PlanLiteInfo;
import com.heqi.kharazim.explore.http.request.PlanDetailRequest;
import com.heqi.kharazim.config.Intents;

import java.util.concurrent.ExecutionException;

/**
 * Created by overspark on 2016/11/21.
 */

public class PlanDetailFragment extends NetworkAsyncLoadFragment<PlanDetailInfo> {

  // data
  private PlanLiteInfo planLiteInfo;
  private PlanDetailInfo planDetailInfo;

  // view
  private ExplorePlanDetailCourseListView courseListView;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Bundle arg = getArguments();
    if (arg != null) {
      planLiteInfo = (PlanLiteInfo) arg.getSerializable(Intents.EXTRA_PLAN_LITE_INFO);
    }
  }

  @Override
  protected void onInflated(View contentView, Bundle savedInstanceState) {
    LinearLayout planDetailContentLayout =
        (LinearLayout) contentView.findViewById(R.id.explore_plan_detail_content);
    ExplorePlanLiteView planLiteView =
        ExplorePlanLiteView.newInstanceInDetail((ViewGroup) contentView);
    if (planLiteView != null) {

      if (planLiteInfo != null) {
        planLiteView.setData(planLiteInfo);
      }

      if (planDetailContentLayout != null) {
        planDetailContentLayout.addView(planLiteView);
      }

      TextView planDetailJoinBtn = (TextView) planLiteView.findViewById(R.id.plan_detail_join_btn);
      if (planDetailJoinBtn != null && planLiteInfo != null) {
        planDetailJoinBtn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            //new JoinPlanAction(planLiteInfo.getId()).run();

            // TODO: temp code
            ConsumeActivity.launchActivity(getContext(),
                planDetailInfo.getData_info().get(0).getId());
            // temp code end

          }
        });
      }
    }
    courseListView = ExplorePlanDetailCourseListView.newInstance((ViewGroup) contentView);
    if (courseListView != null && planDetailContentLayout != null) {
      planDetailContentLayout.addView(courseListView);
    }
  }

  @Override
  protected int getLayoutResId() {
    return R.layout.explore_plan_detail_fragment;
  }

  @Override
  protected void applyData(PlanDetailInfo data) {
    if (data == null) return;

    this.planDetailInfo = data;
    courseListView.setData(this.planDetailInfo, this.planLiteInfo, false);
  }

  protected Request<PlanDetailInfo> newRequest(Response.Listener<PlanDetailInfo> listener,
                                               Response.ErrorListener errorListener) {

    if (planLiteInfo == null) return null;

    PlanDetailRequest request = new PlanDetailRequest(listener, errorListener);
    request.setId(planLiteInfo.getId());
    return request;
  }

  protected void showLoadingTipsView() {}

  protected void hideLoadingTipsView() {}

  protected void showFetchFailedTipsView(ExecutionException e) {}

  protected void hideFetchFailedTipsView() {}

  protected void onNoFetchResult() {}
}
