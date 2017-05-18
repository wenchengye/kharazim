package com.heqi.kharazim.archives.http.request;

import com.android.volley.Response;
import com.heqi.kharazim.archives.model.ArchivesCommonResult;

import java.util.Map;

/**
 * Created by overspark on 2017/5/13.
 */

public class DeletePlanRequest extends AbstractKharazimArchivesHttpRequest<ArchivesCommonResult> {

  private static final String DELETE_PLAN_DIRECTORY = "usr/plan/delete";
  private static final String GET_PARAMS_KEY_USER_PLAN_ID = "userplanid";

  private String planId;

  public DeletePlanRequest(Response.Listener<ArchivesCommonResult> listener,
                           Response.ErrorListener errorListener,
                           String accessToken) {
    super(ArchivesCommonResult.class, listener, errorListener, accessToken);
  }

  @Override
  protected void setGetParams(Map<String, String> params) {
    super.setGetParams(params);

    if (planId != null) {
      params.put(GET_PARAMS_KEY_USER_PLAN_ID, planId);
    }
  }

  @Override
  protected String getBaseUrlDirectory() {
    return DELETE_PLAN_DIRECTORY;
  }

  public void setPlanId(String planId) {
    this.planId = planId;
  }
}
