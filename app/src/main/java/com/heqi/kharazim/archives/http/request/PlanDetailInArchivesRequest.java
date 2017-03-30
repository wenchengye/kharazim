package com.heqi.kharazim.archives.http.request;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.heqi.kharazim.explore.model.PlanDetailInfo;
import com.heqi.kharazim.utils.KharazimUtils;

import java.util.Map;

/**
 * Created by overspark on 2017/3/18.
 */

public class PlanDetailInArchivesRequest
    extends AbstractKharazimArchivesHttpRequest<PlanDetailInfo> {

  private static final String PLAN_DETAIL_IN_ARCHIVES_DIRECTORY = "plan/details";
  private static final String GET_PARAMS_KEY_PLAN_ID = "id";

  private String planId;

  public PlanDetailInArchivesRequest(Response.Listener<PlanDetailInfo> listener,
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

  @Override
  protected Response<PlanDetailInfo> parseNetworkResponse(NetworkResponse response) {
    Response<PlanDetailInfo> ret = super.parseNetworkResponse(response);
    if (ret != null && ret.result != null) {
      KharazimUtils.redirectKharazimModel(ret.result);
    }
    return ret;
  }
}