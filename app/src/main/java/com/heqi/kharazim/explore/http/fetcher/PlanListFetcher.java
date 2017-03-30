package com.heqi.kharazim.explore.http.fetcher;

import com.android.volley.toolbox.RequestFuture;
import com.heqi.fetcher.BaseFetcher;
import com.heqi.kharazim.KharazimApplication;
import com.heqi.kharazim.config.Const;
import com.heqi.kharazim.explore.http.request.PlanListRequest;
import com.heqi.kharazim.explore.model.PlanListInfo;
import com.heqi.kharazim.explore.model.PlanLiteInfo;
import com.heqi.rpc.RpcHelper;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by overspark on 2016/11/20.
 */

public class PlanListFetcher extends BaseFetcher<PlanLiteInfo> {

  private static final String PLAN_LIST_CACHE_KEY = "plan_list";

  private boolean usingToken = false;

  @Override
  protected List<PlanLiteInfo> fetchHttpData(int start, int size) throws ExecutionException {

    RequestFuture<PlanListInfo> future = RequestFuture.newFuture();
    PlanListRequest request = new PlanListRequest(future, future);
    if (isUsingToken()) {
      request.setAccessToken(KharazimApplication.getArchives().getCurrentAccessToken());
    }

    if (start != 0) {
      request.setPageSize(start);
      request.setPageNumber(Const.KHARAZIM_FETCHER_SECOND_PAGE_NUMBER);
    }

    PlanListInfo planListInfo =
        RpcHelper.getInstance(KharazimApplication.getAppContext()).executeRequest(request, future);

    return planListInfo != null ? planListInfo.getPage_data() : null;
  }

  @Override
  protected String getCacheKey() {
    return null;
  }

  public boolean isUsingToken() {
    return usingToken;
  }

  public void setUsingToken(boolean usingToken) {
    this.usingToken = usingToken;
  }
}
