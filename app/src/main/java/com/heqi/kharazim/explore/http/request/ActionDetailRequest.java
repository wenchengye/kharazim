package com.heqi.kharazim.explore.http.request;

import android.text.TextUtils;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.heqi.kharazim.explore.model.ActionQueryResult;
import com.heqi.kharazim.http.AbstractKharazimHttpRequest;
import com.heqi.kharazim.utils.KharazimUtils;

import java.util.Map;

/**
 * Created by overspark on 2016/11/19.
 */

public class ActionDetailRequest extends AbstractKharazimHttpRequest<ActionQueryResult> {

  private static final String ACTION_QUERY_DIRECTORY = "act/details";
  private static final String GET_PARAMS_KEY_ID = "id";

  private String id;

  public ActionDetailRequest(Response.Listener<ActionQueryResult> listener,
                             Response.ErrorListener errorListener) {
    super(ActionQueryResult.class, listener, errorListener);
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @Override
  protected String getBaseUrlDirectory() {
    return ACTION_QUERY_DIRECTORY;
  }

  @Override
  protected void setGetParams(Map<String, String> params) {
    super.setGetParams(params);

    if (!TextUtils.isEmpty(id)) {
      params.put(GET_PARAMS_KEY_ID, id);
    }
  }

  @Override
  protected Response<ActionQueryResult> parseNetworkResponse(NetworkResponse response) {
    Response<ActionQueryResult> ret = super.parseNetworkResponse(response);
    if (ret != null && ret.result != null) {
      KharazimUtils.redirectKharazimModel(ret.result);
    }
    return ret;
  }
}
