package com.heqi.kharazim.archives.http;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.heqi.kharazim.archives.model.UserProfileResult;
import com.heqi.kharazim.config.Const;

/**
 * Created by overspark on 2017/3/13.
 */

public class UserProfileRequest extends AbstractKharazimArchivesHttpRequest<UserProfileResult> {

  private static final String USER_PROFILE_DIRECTORY = "usr/info";

  public UserProfileRequest(Response.Listener<UserProfileResult> listener,
                            Response.ErrorListener errorListener,
                            String accessToken) {
    super(UserProfileResult.class, listener, errorListener, accessToken);
  }

  @Override
  protected String getBaseUrlDirectory() {
    return USER_PROFILE_DIRECTORY;
  }

  @Override
  protected Response<UserProfileResult> parseNetworkResponse(NetworkResponse response) {
    Response<UserProfileResult> ret = super.parseNetworkResponse(response);
    if (ret != null && ret.result != null && ret.result.getData_src() != null) {
      Const.redirectKharazimModel(ret.result.getData_src());
    }
    return ret;
  }
}
