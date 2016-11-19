package com.heqi.kharazim.explore.http;

import android.text.TextUtils;

import com.android.volley.Response;
import com.heqi.kharazim.explore.model.AcupointQueryResult;
import com.heqi.kharazim.http.AbstractKharazimHttpRequest;

import java.util.Map;

/**
 * Created by overspark on 2016/11/19.
 */

public class AcupointDetailRequest extends AbstractKharazimHttpRequest<AcupointQueryResult> {

  private static final String ACUPOINT_DETAIL_DIRECTORY = "acupoint/details";
  private static final String GET_PARAMS_KEY_ID = "id";

  private String id;

  public AcupointDetailRequest(Response.Listener<AcupointQueryResult> listener,
                               Response.ErrorListener errorListener) {
    super(AcupointQueryResult.class, listener, errorListener);
  }

  @Override
  protected String getBaseUrlDirectory() {
    return ACUPOINT_DETAIL_DIRECTORY;
  }

  @Override
  protected void setGetParams(Map<String, String> params) {
    super.setGetParams(params);

    if (!TextUtils.isEmpty(id)) {
      params.put(GET_PARAMS_KEY_ID, id);
    }
  }
}
