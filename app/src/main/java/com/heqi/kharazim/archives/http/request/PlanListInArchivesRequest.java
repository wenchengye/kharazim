package com.heqi.kharazim.archives.http.request;

import com.android.volley.Response;
import com.heqi.kharazim.explore.model.PlanListInfo;

/**
 * Created by overspark on 2017/3/19.
 */

public class PlanListInArchivesRequest extends AbstractKharazimArchivesHttpRequest<PlanListInfo> {

  private static final String PLAN_LIST_IN_ARCHIVES_DIRECTORY = "usr/plan/list";

  public PlanListInArchivesRequest(Response.Listener<PlanListInfo> listener,
                                   Response.ErrorListener errorListener,
                                   String accessToken) {
    super(PlanListInfo.class, listener, errorListener, accessToken);
  }

  @Override
  protected String getBaseUrlDirectory() {
    return PLAN_LIST_IN_ARCHIVES_DIRECTORY;
  }
}
