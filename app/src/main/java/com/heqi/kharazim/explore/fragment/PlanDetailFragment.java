package com.heqi.kharazim.explore.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.heqi.kharazim.KharazimApplication;
import com.heqi.kharazim.R;
import com.heqi.kharazim.archives.Archives;
import com.heqi.kharazim.archives.http.PlanDetailInArchivesReqeust;
import com.heqi.kharazim.config.Intents;
import com.heqi.kharazim.explore.http.request.PlanDetailRequest;
import com.heqi.kharazim.explore.model.PlanDetailInfo;
import com.heqi.kharazim.explore.model.PlanLiteInfo;
import com.heqi.kharazim.explore.view.ExplorePlanDetailCourseListView;
import com.heqi.kharazim.explore.view.ExplorePlanDetailUserProgressView;
import com.heqi.kharazim.explore.view.ExplorePlanLiteView;
import com.heqi.kharazim.ui.fragment.async.NetworkAsyncLoadFragment;
import com.heqi.kharazim.utils.KharazimUtils;

import java.util.concurrent.ExecutionException;

/**
 * Created by overspark on 2016/11/21.
 */

public class PlanDetailFragment extends NetworkAsyncLoadFragment<PlanDetailInfo> {

  public interface PlanDetailFragmentListener {

    void onAddPlan(PlanLiteInfo planLiteInfo);

    void onConsumePlan(String courseId);
  }

  // data
  private PlanLiteInfo planLiteInfo;
  private PlanDetailInfo planDetailInfo;
  private boolean inArchives = false;

  // view
  private ExplorePlanDetailCourseListView courseListView;
  private ExplorePlanDetailUserProgressView userProgressView;

  // status
  private PlanDetailFragmentListener listener;

  public void setListener(PlanDetailFragmentListener listener) {
    this.listener = listener;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Bundle arg = getArguments();
    if (arg != null) {
      planLiteInfo = (PlanLiteInfo) arg.getSerializable(Intents.EXTRA_PLAN_LITE_INFO);
    }
    inArchives = planLiteInfo != null && planLiteInfo.isMyplan();
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

      TextView planDetailActionBtn = (TextView) planLiteView.findViewById(R.id.plan_action_btn);
      if (planDetailActionBtn != null) {
        if (!inArchives && this.planLiteInfo != null) {
          planDetailActionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              addPlan();
            }
          });
        } else if (inArchives) {
          planDetailActionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              consumePlan();
            }
          });
        }
      }
    }
    if (inArchives) {
      userProgressView = ExplorePlanDetailUserProgressView.newInstance((ViewGroup) contentView);
      if (userProgressView != null && planDetailContentLayout != null) {
        planDetailContentLayout.addView(userProgressView);
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

    Request<PlanDetailInfo> request;
    if (inArchives) {
      request = new PlanDetailInArchivesReqeust(listener, errorListener,
          KharazimApplication.getArchives().getCurrentAccessToken());
      ((PlanDetailInArchivesReqeust)request).setPlanId(planLiteInfo.getId());
    } else {
      request = new PlanDetailRequest(listener, errorListener);
      ((PlanDetailRequest)request).setId(planLiteInfo.getId());
    }

    return request;
  }

  private void addPlan() {
    KharazimApplication.getArchives().addPlan(this.planLiteInfo.getId(),
        new Archives.ArchivesTaskCallback() {
      @Override
      public void onTaskSuccess(int code, String msg) {
        if (KharazimUtils.isRetCodeOK(code) && listener != null) {
          listener.onAddPlan(planLiteInfo);
        }
      }

      @Override
      public void onTaskFailed() {

      }
    });
  }

  private void consumePlan() {
    if (this.userProgressView != null && this.userProgressView.getCurrent() != null
        && this.listener != null) {
      this.listener.onConsumePlan(userProgressView.getCurrent().getId());
    }
  }

  protected void showLoadingTipsView() {}

  protected void hideLoadingTipsView() {}

  protected void showFetchFailedTipsView(ExecutionException e) {}

  protected void hideFetchFailedTipsView() {}

  protected void onNoFetchResult() {}
}
