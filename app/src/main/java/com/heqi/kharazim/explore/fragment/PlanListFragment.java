package com.heqi.kharazim.explore.fragment;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.heqi.fetcher.BaseFetcher;
import com.heqi.kharazim.explore.activity.PlanDetailActivity;
import com.heqi.kharazim.explore.http.fetcher.PlanListFetcher;
import com.heqi.kharazim.explore.model.PlanLiteInfo;
import com.heqi.kharazim.explore.view.ExplorePlanLiteView;
import com.heqi.kharazim.ui.adapter.DataAdapter;
import com.heqi.kharazim.ui.fragment.async.NetworkListAsyncloadFragment;
import com.heqi.kharazim.ui.view.AbstractFetchMoreFooterView;

import java.util.concurrent.ExecutionException;

/**
 * Created by overspark on 2016/11/20.
 */

public class PlanListFragment extends NetworkListAsyncloadFragment<PlanLiteInfo> {

  @Override
  protected BaseFetcher<PlanLiteInfo> newFetcher() {
    return new PlanListFetcher();
  }

  @Override
  protected DataAdapter<PlanLiteInfo> newContentAdapter() {
    return new PlanListAdapter(getContext());
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

    PlanListAdapter(Context context) {
      this.context = context;
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
          PlanDetailActivity.launchActivity(
              PlanListAdapter.this.context, getItem(position));
        }
      });
      return view;
    }
  }
}
