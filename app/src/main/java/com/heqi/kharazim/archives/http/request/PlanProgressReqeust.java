package com.heqi.kharazim.archives.http.request;

import com.android.volley.Response;
import com.heqi.kharazim.archives.model.PlanProgressResult;

import java.util.Map;

/**
 * Created by overspark on 2017/4/19.
 */

public class PlanProgressReqeust extends AbstractKharazimArchivesHttpRequest<PlanProgressResult> {

  private static final String PLAN_PROGRESS_DIRECTORY = "usr/plan/myinfo";
  private static final String GET_PARAMS_KEY_USER_PLAN_ID = "userplanid";
  private static final String GET_PARAMS_KEY_PLAN_ID = "planid";

  private String userPlanId;
  private String planId;

  public PlanProgressReqeust(Response.Listener<PlanProgressResult> listener,
                             Response.ErrorListener errorListener, String accessToken) {
    super(PlanProgressResult.class, listener, errorListener, accessToken);
  }

  @Override
  protected void setGetParams(Map<String, String> params) {
    super.setGetParams(params);

    if (userPlanId != null) {
      params.put(GET_PARAMS_KEY_USER_PLAN_ID, userPlanId);
    }

    if (planId != null) {
      params.put(GET_PARAMS_KEY_PLAN_ID, planId);
    }
  }

  @Override
  protected String getBaseUrlDirectory() {
    return PLAN_PROGRESS_DIRECTORY;
  }

  public void setPlanId(String planId) {
    this.planId = planId;
  }

  public void setUserPlanId(String userPlanId) {
    this.userPlanId = userPlanId;
  }
}
