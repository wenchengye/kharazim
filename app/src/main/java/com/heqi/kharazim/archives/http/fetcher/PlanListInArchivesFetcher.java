package com.heqi.kharazim.archives.http.fetcher;

import com.android.volley.toolbox.RequestFuture;
import com.heqi.fetcher.BaseFetcher;
import com.heqi.kharazim.KharazimApplication;
import com.heqi.kharazim.archives.http.request.PlanListInArchivesRequest;
import com.heqi.kharazim.explore.model.PlanListInfo;
import com.heqi.kharazim.explore.model.PlanLiteInfo;
import com.heqi.rpc.RpcHelper;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by overspark on 2017/3/19.
 */

public class PlanListInArchivesFetcher extends BaseFetcher<PlanLiteInfo> {
  @Override
  protected List<PlanLiteInfo> fetchHttpData(int start, int size) throws ExecutionException {
    //TODO: temp code, solve this with server end.
    if (start != 0) return null;

    RequestFuture<PlanListInfo> future = RequestFuture.newFuture();
    PlanListInArchivesRequest request = new PlanListInArchivesRequest(future, future,
        KharazimApplication.getArchives().getCurrentAccessToken());
    PlanListInfo planListInfo =
        RpcHelper.getInstance(KharazimApplication.getAppContext()).executeRequest(request, future);

    return planListInfo != null ? planListInfo.getPage_data() : null;
  }

  @Override
  protected String getCacheKey() {
    return null;
  }
}
