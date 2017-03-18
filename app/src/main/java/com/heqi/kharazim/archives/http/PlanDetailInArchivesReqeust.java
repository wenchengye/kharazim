package com.heqi.kharazim.archives.http;

import com.android.volley.Response;
import com.heqi.kharazim.explore.model.PlanDetailInfo;

import java.util.Map;

/**
 * Created by overspark on 2017/3/18.
 */

public class PlanDetailInArchivesReqeust
    extends AbstractKharazimArchivesHttpRequest<PlanDetailInfo> {

  private static final String PLAN_DETAIL_IN_ARCHIVES_DIRECTORY = "usr/plan/details";
  private static final String GET_PARAMS_KEY_PLAN_ID = "id";

  private String planId;

  public PlanDetailInArchivesReqeust(Response.Listener<PlanDetailInfo> listener,
                                     Response.ErrorListener errorListener,
                                     String accessToken) {
    super(PlanDetailInfo.class, listener, errorListener, accessToken);
  }

  @Override
  protected String getBaseUrlDirectory() {
    return PLAN_DETAIL_IN_ARCHIVES_DIRECTORY;
  }

  @Override
  protected void setGetParams(Map<String, String> params) {
    super.setGetParams(params);

    if (this.planId != null) {
      params.put(GET_PARAMS_KEY_PLAN_ID, this.planId);
    }
  }

  public String getPlanId() {
    return planId;
  }

  public void setPlanId(String planId) {
    this.planId = planId;
  }
}