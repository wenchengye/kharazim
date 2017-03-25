package com.heqi.kharazim.explore.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.heqi.fetcher.BaseFetcher;
import com.heqi.kharazim.KharazimApplication;
import com.heqi.kharazim.R;
import com.heqi.kharazim.activity.NavigationManager;
import com.heqi.kharazim.archives.ArchivesService;
import com.heqi.kharazim.archives.SimpleArchivesObserver;
import com.heqi.kharazim.archives.model.HealthCondition;
import com.heqi.kharazim.archives.model.UserProfile;
import com.heqi.kharazim.explore.http.fetcher.PlanListFetcher;
import com.heqi.kharazim.explore.model.PlanLiteInfo;
import com.heqi.kharazim.explore.view.ExplorePlanLiteView;
import com.heqi.kharazim.ui.adapter.DataAdapter;
import com.heqi.kharazim.ui.fragment.async.NetworkListAsyncloadFragment;
import com.heqi.kharazim.ui.view.AbstractFetchMoreFooterView;
import com.heqi.kharazim.utils.KharazimUtils;

import java.util.concurrent.ExecutionException;

/**
 * Created by overspark on 2016/11/20.
 */

public class PlanListFragment extends NetworkListAsyncloadFragment<PlanLiteInfo> {

  private PlanListArchivesObserver archivesObserver = new PlanListArchivesObserver();
  private ExplorePlanLiteView.ExplorePlanLiteViewListener liteViewListener =
      new ExplorePlanLiteView.ExplorePlanLiteViewListener() {
        @Override
        public void onAddPlan(PlanLiteInfo data) {
          if (data == null) return;

          addPlan(data);
        }
      };

  private void addPlan(PlanLiteInfo plan) {
    if (plan == null) return;

    ArchivesService.ArchivesTaskCallback taskCallback =
        new ArchivesService.ArchivesTaskCallback() {
      @Override
      public void onTaskSuccess(int code, String msg) {
        int textId = R.string.explore_add_plan_failed_toast;
        if (KharazimUtils.isRetCodeOK(code)) {
          textId = R.string.explore_add_plan_success_toast;
        }

        KharazimUtils.showToast(textId);
      }

      @Override
      public void onTaskFailed() {
        KharazimUtils.showToast(R.string.explore_add_plan_failed_toast);
      }
    };

    if (!KharazimApplication.getArchives().addPlan(plan.getId(), taskCallback)) {
      KharazimUtils.showToast(R.string.explore_add_plan_failed_toast);
    }
  }

  @Override
  public void onInflate(Context context, AttributeSet attrs, Bundle savedInstanceState) {
    super.onInflate(context, attrs, savedInstanceState);

    KharazimApplication.getArchives().addObserver(archivesObserver);
  }

  @Override
  protected BaseFetcher<PlanLiteInfo> newFetcher() {
    PlanListFetcher fetcher = new PlanListFetcher();
    fetcher.setUsingToken(true);
    return fetcher;
  }

  @Override
  protected DataAdapter<PlanLiteInfo> newContentAdapter() {
    return new PlanListAdapter(getActivity(), liteViewListener);
  }

  @Override
  protected AbstractFetchMoreFooterView newFetchMoreFooterView(ViewGroup parent) {
    return null;
  }

  @Override
  protected void showLoadingTipsView() {
  }

  @Override
  protected void hideLoadingTipsView() {
  }

  @Override
  protected void showFetchFailedTipsView(int start, ExecutionException e) {

  }

  @Override
  protected void hideFetchFailedTipsView() {
  }

  @Override
  protected void onNoFetchResult() {
  }

  private static class PlanListAdapter extends DataAdapter<PlanLiteInfo> {

    private Context context;
    private ExplorePlanLiteView.ExplorePlanLiteViewListener listener;

    PlanListAdapter(Context context, ExplorePlanLiteView.ExplorePlanLiteViewListener listener) {
      this.context = context;
      this.listener = listener;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
      ExplorePlanLiteView view;
      if (convertView instanceof ExplorePlanLiteView) {
        view = (ExplorePlanLiteView) convertView;
      } else {
        view = ExplorePlanLiteView.newInstance(parent);
      }

      view.setData(getItem(position));
      view.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          NavigationManager.navigateToPlanDetail(context, getItem(position));
        }
      });
      view.setListener(this.listener);
      view.setShowAddIcon(true);
      view.setShowProgress(false);
      return view;
    }
  }

  private class PlanListArchivesObserver extends SimpleArchivesObserver {
    @Override
    public void onAddPlan(String userId, String planId) {

      boolean updated = false;
      for (int i = 0; i < contentAdapter.getCount(); ++i) {
        PlanLiteInfo data = contentAdapter.getItem(i);
        if (data != null && data.getId().equals(planId)) {
          data.setMyplan(true);
          updated = true;
        }
      }

      if (updated) headerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRemovePlan(String userId, String planId) {
      boolean updated = false;
      for (int i = 0; i < contentAdapter.getCount(); ++i) {
        PlanLiteInfo data = contentAdapter.getItem(i);
        if (data != null && data.getId().equals(planId)) {
          data.setMyplan(false);
          updated = true;
        }
      }

      if (updated) headerViewAdapter.notifyDataSetChanged();
    }
  }
}
