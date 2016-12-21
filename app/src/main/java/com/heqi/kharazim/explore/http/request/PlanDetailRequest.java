package com.heqi.kharazim.explore.http.request;

import android.text.TextUtils;

import com.android.volley.Response;
import com.heqi.kharazim.explore.model.PlanDetailInfo;
import com.heqi.kharazim.http.AbstractKharazimHttpRequest;

import java.util.Map;

/**
 * Created by overspark on 2016/11/19.
 */

public class PlanDetailRequest extends AbstractKharazimHttpRequest<PlanDetailInfo> {

  private static final String PLAN_DETAIL_DIRECTORY = "plan/details";
  private static final String GET_PARAMS_KEY_ID = "id";

  private String id;

  public PlanDetailRequest(Response.Listener<PlanDetailInfo> listener,
                           Response.ErrorListener errorListener) {
    super(PlanDetailInfo.class, listener, errorListener);
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @Override
  protected String getBaseUrlDirectory() {
    return PLAN_DETAIL_DIRECTORY;
  }

  @Override
  protected void setGetParams(Map<String, String> params) {
    super.setGetParams(params);

    if (!TextUtils.isEmpty(id)) {
      params.put(GET_PARAMS_KEY_ID, id);
    }
  }
}
