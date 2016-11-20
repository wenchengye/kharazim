package com.heqi.kharazim.explore.http.fetcher;

import com.android.volley.toolbox.RequestFuture;
import com.heqi.base.utils.CollectionUtils;
import com.heqi.fetcher.BaseFetcher;
import com.heqi.kharazim.KharazimApplication;
import com.heqi.kharazim.explore.http.request.PlanListRequest;
import com.heqi.kharazim.explore.model.PlanListInfo;
import com.heqi.kharazim.explore.model.PlanLiteInfo;
import com.heqi.rpc.RpcHelper;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by overspark on 2016/11/20.
 */

public class PlanListFether extends BaseFetcher<PlanLiteInfo> {

  private static final String PLAN_LIST_CACHE_KEY = "plan_list";

  @Override
  protected List<PlanLiteInfo> fetchHttpData(int start, int size) throws ExecutionException {

    //TODO: temp code, solve this with server end.
    if (start != 0) return null;

    RequestFuture<PlanListInfo> future = RequestFuture.newFuture();
    PlanListRequest request = new PlanListRequest(future, future);
    PlanListInfo planListInfo =
        RpcHelper.getInstance(KharazimApplication.getAppContext()).executeRequest(request, future);

    return planListInfo != null ? planListInfo.getPage_data() : null;
  }

  @Override
  protected String getCacheKey() {
    return PLAN_LIST_CACHE_KEY;
  }
}
