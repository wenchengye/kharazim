package com.heqi.kharazim.archives.http.request;

import com.android.volley.Response;
import com.heqi.kharazim.archives.model.ReloginResult;
import com.heqi.kharazim.http.AbstractKharazimHttpRequest;

import java.util.Map;

/**
 * Created by overspark on 2017/3/16.
 */

public class ReloginRequest extends AbstractKharazimHttpRequest<ReloginResult> {

  private static final String RELOGIN_DIRECTORY = "usr/relogin";
  private static final String GET_PARAMS_KEY_RELOGIN_TOKEN = "relogintoken";

  private String token;

  public ReloginRequest(Response.Listener<ReloginResult> listener,
                        Response.ErrorListener errorListener) {
    super(ReloginResult.class, listener, errorListener);
  }

  @Override
  protected String getBaseUrlDirectory() {
    return RELOGIN_DIRECTORY;
  }

  @Override
  protected void setGetParams(Map<String, String> params) {
    super.setGetParams(params);
    if (this.token != null) {
      params.put(GET_PARAMS_KEY_RELOGIN_TOKEN, token);
    }
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }
}
