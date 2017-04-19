package com.heqi.kharazim.explore.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.heqi.kharazim.KharazimApplication;
import com.heqi.kharazim.R;
import com.heqi.kharazim.archives.ArchivesService;
import com.heqi.kharazim.archives.http.request.PlanDetailInArchivesRequest;
import com.heqi.kharazim.archives.http.request.PlanProgressReqeust;
import com.heqi.kharazim.archives.model.PlanProgressInfo;
import com.heqi.kharazim.archives.model.PlanProgressResult;
import com.heqi.kharazim.config.Intents;
import com.heqi.kharazim.explore.http.request.PlanDetailRequest;
import com.heqi.kharazim.explore.model.PlanDetailInfo;
import com.heqi.kharazim.explore.model.PlanLiteInfo;
import com.heqi.kharazim.explore.view.ExplorePlanDetailCourseListView;
import com.heqi.kharazim.explore.view.ExplorePlanDetailUserProgressView;
import com.heqi.kharazim.explore.view.ExplorePlanLiteView;
import com.heqi.kharazim.ui.fragment.async.NetworkAsyncLoadFragment;
import com.heqi.kharazim.utils.KharazimUtils;
import com.heqi.rpc.RpcHelper;

import java.util.concurrent.ExecutionException;

/**
 * Created by overspark on 2016/11/21.
 */

public class PlanDetailFragment extends NetworkAsyncLoadFragment<PlanDetailInfo> {

  // data
  private PlanLiteInfo planLiteInfo;
  private PlanDetailInfo planDetailInfo;
  private PlanProgressInfo planProgressInfo;
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

    ImageView backBtn = (ImageView) contentView.findViewById(R.id.explore_header_left_button);
    if (backBtn != null) {
      backBtn.setImageResource(R.drawable.icon_header_navigate_back);
      backBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          if (listener != null) {
            listener.onBack();
          }
        }
      });
    }

    LinearLayout planDetailContentLayout =
        (LinearLayout) contentView.findViewById(R.id.explore_plan_detail_content);
    ExplorePlanLiteView planLiteView =
        ExplorePlanLiteView.newInstance((ViewGroup) contentView);
    if (planLiteView != null) {

      planLiteView.setShowAddIcon(false);
      planLiteView.setShowProgress(false);

      if (planLiteInfo != null) {
        planLiteView.setData(planLiteInfo);
      }

      if (planDetailContentLayout != null) {
        planDetailContentLayout.addView(planLiteView);
      }
    }

    TextView planDetailActionBtn = (TextView) contentView.findViewById(R.id.plan_action_btn);
    if (planDetailActionBtn != null) {
      if (!inArchives) {
        if (this.planLiteInfo != null) {
          planDetailActionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              addPlan();
            }
          });
        }
        planDetailActionBtn.setText(KharazimApplication.getAppContext().getString(
            R.string.explore_detail_join_text));

      } else {
        planDetailActionBtn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            consumePlan();
          }
        });
        planDetailActionBtn.setText(KharazimApplication.getAppContext().getString(
            R.string.explore_detail_consume_text
        ));
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

    if (courseListView != null) {
      if (inArchives) courseListView.enablePreview();
      else courseListView.enableExplain();
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

    if (userProgressView != null && this.planProgressInfo != null) {
      userProgressView.setData(this.planProgressInfo, this.planDetailInfo, this.planLiteInfo);
    }
  }

  protected void applyProgress(PlanProgressInfo progress) {
    this.planProgressInfo = progress;

    if (userProgressView != null && this.planDetailInfo != null) {
      userProgressView.setData(this.planProgressInfo, this.planDetailInfo, this.planLiteInfo);
    }
  }

  @Override
  protected void onStartLoading() {
    super.onStartLoading();

    if (inArchives) {
      Response.Listener<PlanProgressResult> successListener =
          new Response.Listener<PlanProgressResult>() {
        @Override
        public void onResponse(PlanProgressResult response) {
          if (response != null && response.getRet_data() != null) {
            applyProgress(response.getRet_data());
          }
        }
      };

      PlanProgressReqeust request = new PlanProgressReqeust(successListener, null,
          KharazimApplication.getArchives().getCurrentAccessToken());
      request.setPlanId(planLiteInfo.getId());
      request.setUserPlanId(planLiteInfo.getUserplanid());
      RpcHelper.getInstance(KharazimApplication.getAppContext()).executeRequestAsync(request);
    }
  }

  protected Request<PlanDetailInfo> newRequest(Response.Listener<PlanDetailInfo> listener,
                                               Response.ErrorListener errorListener) {

    if (planLiteInfo == null) return null;

    Request<PlanDetailInfo> request;
    if (inArchives) {
      request = new PlanDetailInArchivesRequest(listener, errorListener,
          KharazimApplication.getArchives().getCurrentAccessToken());
      ((PlanDetailInArchivesRequest) request).setPlanId(planLiteInfo.getId());
    } else {
      request = new PlanDetailRequest(listener, errorListener);
      ((PlanDetailRequest) request).setId(planLiteInfo.getId());
    }

    return request;
  }

  private void addPlan() {
    KharazimApplication.getArchives().addPlan(this.planLiteInfo.getId(),
        new ArchivesService.ArchivesTaskCallback() {
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
      this.listener.onConsumePlan(userProgressView.getCurrent().getId(),
          planLiteInfo.getUserplanid());
    }
  }

  protected void showLoadingTipsView() {
  }

  protected void hideLoadingTipsView() {
  }

  protected void showFetchFailedTipsView(ExecutionException e) {
  }

  protected void hideFetchFailedTipsView() {
  }

  protected void onNoFetchResult() {
  }

  public interface PlanDetailFragmentListener {

    void onAddPlan(PlanLiteInfo planLiteInfo);

    void onConsumePlan(String courseId, String userPlanId);

    void onBack();
  }
}
