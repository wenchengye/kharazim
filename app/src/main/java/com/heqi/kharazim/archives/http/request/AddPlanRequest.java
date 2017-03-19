package com.heqi.kharazim.archives.http.request;

import com.android.volley.Response;
import com.heqi.kharazim.archives.model.ArchivesCommonResult;

import java.util.Map;

/**
 * Created by overspark on 2017/3/18.
 */

public class AddPlanRequest extends AbstractKharazimArchivesHttpRequest<ArchivesCommonResult> {

  private static final String ADD_PLAN_DIRECTORY = "usr/plan/add";
  private static final String GET_PARAMS_KEY_PLAN_ID = "planid";

  private String planId;

  public AddPlanRequest(Response.Listener<ArchivesCommonResult> listener,
                        Response.ErrorListener errorListener,
                        String accessToken) {
    super(ArchivesCommonResult.class, listener, errorListener, accessToken);
  }

  @Override
  protected void setGetParams(Map<String, String> params) {
    super.setGetParams(params);

    if (this.planId != null) {
      params.put(GET_PARAMS_KEY_PLAN_ID, this.planId);
    }
  }

  @Override
  protected String getBaseUrlDirectory() {
    return ADD_PLAN_DIRECTORY;
  }

  public String getPlanId() {
    return planId;
  }

  public void setPlanId(String planId) {
    this.planId = planId;
  }
}
