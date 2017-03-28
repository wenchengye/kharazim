package com.heqi.kharazim.archives.fragment;

import android.os.Bundle;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.heqi.kharazim.R;
import com.heqi.kharazim.archives.model.UserStatsResult;
import com.heqi.kharazim.ui.fragment.async.NetworkAsyncLoadFragment;

import java.util.concurrent.ExecutionException;

/**
 * Created by overspark on 2017/3/28.
 */

public class StatsFragment extends NetworkAsyncLoadFragment<UserStatsResult> {

  @Override
  protected void onInflated(View contentView, Bundle savedInstanceState) {

  }

  @Override
  protected int getLayoutResId() {
    return R.layout.archives_stats_fragment_layout;
  }

  @Override
  protected void applyData(UserStatsResult data) {

  }

  @Override
  protected Request<UserStatsResult> newRequest(Response.Listener<UserStatsResult> listener,
                                                Response.ErrorListener errorListener) {
    return null;
  }

  @Override
  protected void showLoadingTipsView() {

  }

  @Override
  protected void hideLoadingTipsView() {

  }

  @Override
  protected void showFetchFailedTipsView(ExecutionException e) {

  }

  @Override
  protected void hideFetchFailedTipsView() {

  }

  @Override
  protected void onNoFetchResult() {

  }
}
