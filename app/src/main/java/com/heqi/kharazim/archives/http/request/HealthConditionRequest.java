package com.heqi.kharazim.archives.http.request;

import com.android.volley.Response;
import com.heqi.kharazim.archives.model.HealthConditionResult;

/**
 * Created by overspark on 2017/3/13.
 */

public class HealthConditionRequest extends
    AbstractKharazimArchivesHttpRequest<HealthConditionResult> {

  private static final String HEALTH_CONDITION_DIRECTORY = "usr/bodyinfo";


  public HealthConditionRequest(Response.Listener<HealthConditionResult> listener,
                                Response.ErrorListener errorListener,
                                String accessToken) {
    super(HealthConditionResult.class, listener, errorListener, accessToken);
  }

  @Override
  protected String getBaseUrlDirectory() {
    return HEALTH_CONDITION_DIRECTORY;
  }
}
