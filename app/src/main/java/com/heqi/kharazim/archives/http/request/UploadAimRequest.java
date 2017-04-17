package com.heqi.kharazim.archives.http.request;

import com.android.volley.Response;
import com.heqi.kharazim.archives.model.ArchivesCommonResult;

import java.util.Map;

/**
 * Created by overspark on 2017/4/17.
 */

public class UploadAimRequest extends AbstractKharazimArchivesHttpRequest<ArchivesCommonResult> {

  private static final String UPLOAD_AIM_DIRECTORY = "usr/aimupdate";
  private static final String GET_PARAMS_KEY_USER_AIM = "useraim";

  private Integer userAim;

  public UploadAimRequest(Response.Listener<ArchivesCommonResult> listener,
                          Response.ErrorListener errorListener,
                          String accessToken) {
    super(ArchivesCommonResult.class, listener, errorListener, accessToken);
  }

  @Override
  protected void setGetParams(Map<String, String> params) {
    super.setGetParams(params);

    if (userAim != null) {
      params.put(GET_PARAMS_KEY_USER_AIM, String.valueOf(userAim));
    }
  }

  @Override
  protected String getBaseUrlDirectory() {
    return UPLOAD_AIM_DIRECTORY;
  }

  public void setUserAim(Integer userAim) {
    this.userAim = userAim;
  }
}
