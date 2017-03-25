package com.heqi.kharazim.archives.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import com.heqi.fetcher.BaseFetcher;
import com.heqi.kharazim.KharazimApplication;
import com.heqi.kharazim.R;
import com.heqi.kharazim.activity.NavigationManager;
import com.heqi.kharazim.activity.VerticalType;
import com.heqi.kharazim.archives.ArchivesService;
import com.heqi.kharazim.archives.SimpleArchivesObserver;
import com.heqi.kharazim.archives.http.fetcher.PlanListInArchivesFetcher;
import com.heqi.kharazim.archives.view.ArchivesListAddMoreView;
import com.heqi.kharazim.archives.view.ArchivesListHeaderView;
import com.heqi.kharazim.archives.view.ArchivesListPreviewView;
import com.heqi.kharazim.archives.view.ArchivesUserProgressView;
import com.heqi.kharazim.explore.model.PlanLiteInfo;
import com.heqi.kharazim.explore.view.ExplorePlanLiteView;
import com.heqi.kharazim.ui.adapter.DataAdapter;
import com.heqi.kharazim.ui.adapter.StickyHeaderViewAdapter;
import com.heqi.kharazim.ui.fragment.async.NetworkListAsyncloadFragment;
import com.heqi.kharazim.ui.view.AbstractFetchMoreFooterView;
import com.heqi.kharazim.ui.view.sticky.StickyListHeadersAdapter;

import java.util.concurrent.ExecutionException;

/**
 * Created by overspark on 2017/3/18.
 */

public class ArchivesFragment extends NetworkListAsyncloadFragment<PlanLiteInfo> {

  private ArchivesUserProgressView userProgressView;
  private ArchivesListAddMoreView addMoreView;
  private ArchivesListPreviewView previewView;
  private ArchivesFragmentArchivesObserver archivesObserver =
      new ArchivesFragmentArchivesObserver();

  @Override
  protected void onInflated(View contentView, Bundle savedInstanceState) {
    super.onInflated(contentView, savedInstanceState);

    userProgressView = ArchivesUserProgressView.newInstance((ViewGroup) contentView);
    addMoreView = ArchivesListAddMoreView.newInstance((ViewGroup) contentView);
    previewView = ArchivesListPreviewView.newInstance((ViewGroup) contentView);

    headerViewAdapter.addHeader(userProgressView);
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
  protected ListAdapter newListAdapter() {
    contentAdapter = newContentAdapter();
    headerViewAdapter = new StickyHeaderViewAdapter(null, null, contentAdapter);
    return headerViewAdapter;
  }

  @Override
  protected int getLayoutResId() {
    return R.layout.common_sticky_listview_layout;
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

  private static class ArchivesListAdapter extends DataAdapter<PlanLiteInfo>
      implements StickyListHeadersAdapter {

    private Context context;

    public ArchivesListAdapter(Context context) {
      this.context = context;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
      ArchivesListHeaderView view;
      if (convertView instanceof ArchivesListHeaderView) {
        view = (ArchivesListHeaderView) convertView;
      } else {
        view = ArchivesListHeaderView.newInstance(parent);
      }
      return view;
    }

    @Override
    public long getHeaderId(int position) {
      return 0;
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
