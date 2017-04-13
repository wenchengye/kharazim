package com.heqi.kharazim.archives.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.heqi.fetcher.BaseFetcher;
import com.heqi.kharazim.KharazimApplication;
import com.heqi.kharazim.R;
import com.heqi.kharazim.activity.NavigationManager;
import com.heqi.kharazim.activity.VerticalType;
import com.heqi.kharazim.archives.SimpleArchivesObserver;
import com.heqi.kharazim.archives.http.fetcher.PlanListInArchivesFetcher;
import com.heqi.kharazim.archives.http.request.UserStatsRequest;
import com.heqi.kharazim.archives.model.UserStatsResult;
import com.heqi.kharazim.archives.view.ArchivesListAddMoreView;
import com.heqi.kharazim.archives.view.ArchivesListHeaderView;
import com.heqi.kharazim.archives.view.ArchivesListPreviewView;
import com.heqi.kharazim.archives.view.ArchivesUserProgressView;
import com.heqi.kharazim.explore.model.PlanLiteInfo;
import com.heqi.kharazim.explore.view.ExplorePlanLiteView;
import com.heqi.kharazim.ui.adapter.DataAdapter;
import com.heqi.kharazim.ui.fragment.async.NetworkListAsyncloadFragment;
import com.heqi.kharazim.ui.view.AbstractFetchMoreFooterView;
import com.heqi.rpc.RpcHelper;

import java.util.concurrent.ExecutionException;

/**
 * Created by overspark on 2017/3/18.
 */

public class ArchivesFragment extends NetworkListAsyncloadFragment<PlanLiteInfo> {

  private ArchivesUserProgressView userProgressView;
  private ArchivesListHeaderView listHeaderView;
  private ArchivesListAddMoreView addMoreView;
  private ArchivesListPreviewView previewView;
  private ArchivesFragmentArchivesObserver archivesObserver =
      new ArchivesFragmentArchivesObserver();

  @Override
  protected void onInflated(View contentView, Bundle savedInstanceState) {
    super.onInflated(contentView, savedInstanceState);

    userProgressView = ArchivesUserProgressView.newInstance((ViewGroup) contentView);
    listHeaderView = ArchivesListHeaderView.newInstance((ViewGroup) contentView);
    addMoreView = ArchivesListAddMoreView.newInstance((ViewGroup) contentView);
    previewView = ArchivesListPreviewView.newInstance((ViewGroup) contentView);

    headerViewAdapter.addHeader(userProgressView);
    headerViewAdapter.addHeader(listHeaderView);
    headerViewAdapter.addFooter(addMoreView);
    headerViewAdapter.addFooter(previewView);

    addMoreView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        NavigationManager.navigateToHome(getActivity(), VerticalType.Explore);
      }
    });

    KharazimApplication.getArchives().addObserver(archivesObserver);
  }

  @Override
  protected BaseFetcher<PlanLiteInfo> newFetcher() {
    return new PlanListInArchivesFetcher();
  }

  @Override
  protected DataAdapter<PlanLiteInfo> newContentAdapter() {
    return new ArchivesListAdapter(getActivity());
  }

  @Override
  protected int getLayoutResId() {
    return R.layout.common_listview_layout;
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

  @Override
  protected void onStartLoading() {
    super.onStartLoading();

    if (!isInflated) {
      return;
    }

    Response.Listener<UserStatsResult> successListener = new Response.Listener<UserStatsResult>() {
      @Override
      public void onResponse(UserStatsResult response) {
        if (response != null && response.getRet_data() != null
            && !response.getRet_data().isEmpty()) {
          userProgressView.setData(response.getRet_data().get(0));
        }
      }
    };

    UserStatsRequest request = new UserStatsRequest(successListener, null,
        KharazimApplication.getArchives().getCurrentAccessToken());
    RpcHelper.getInstance(KharazimApplication.getAppContext()).executeRequestAsync(request);
  }

  private static class ArchivesListAdapter extends DataAdapter<PlanLiteInfo> {

    private Context context;

    private ArchivesListAdapter(Context context) {
      this.context = context;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
      ExplorePlanLiteView view;
      if (convertView instanceof ExplorePlanLiteView) {
        view = (ExplorePlanLiteView) convertView;
      } else {
        view = ExplorePlanLiteView.newInstance(parent);
      }

      view.setData(getItem(position));
      view.setShowAddIcon(false);
      view.setShowProgress(true);
      view.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          NavigationManager.navigateToPlanDetail(context, getItem(position));
        }
      });
      return view;
    }
  }

  private class ArchivesFragmentArchivesObserver extends SimpleArchivesObserver {
    @Override
    public void onAddPlan(String userId, String planId) {
      requestLoad();
    }

    @Override
    public void onRemovePlan(String userId, String planId) {
      requestLoad();
    }
  }


}
