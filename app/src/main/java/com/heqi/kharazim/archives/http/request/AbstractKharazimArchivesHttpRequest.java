package com.heqi.kharazim.archives.http.request;

import android.text.TextUtils;

import com.android.volley.Response;
import com.heqi.kharazim.http.AbstractKharazimHttpRequest;

import java.util.Map;

/**
 * Created by overspark on 2017/3/13.
 */

public abstract class AbstractKharazimArchivesHttpRequest<T> extends
    AbstractKharazimHttpRequest<T> {

  private static final String GET_PARAMS_KEY_ACCESS_TOKEN = "accesstoken";

  private String accessToken;

  public AbstractKharazimArchivesHttpRequest(Class<T> clazz,
                                             Response.Listener<T> listener,
                                             Response.ErrorListener errorListener,
                                             String accessToken) {
    super(clazz, listener, errorListener);
    this.accessToken = accessToken;
  }

  @Override
  protected void setGetParams(Map<String, String> params) {
    super.setGetParams(params);
    if (!TextUtils.isEmpty(accessToken)) {
      params.put(GET_PARAMS_KEY_ACCESS_TOKEN, this.accessToken);
    }
  }
}
