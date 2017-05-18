package com.heqi.kharazim.archives.http.request;

import com.android.volley.Response;
import com.heqi.kharazim.archives.model.ArchivesCommonResult;
import com.heqi.kharazim.config.Const;

import java.util.Map;

/**
 * Created by overspark on 2017/5/13.
 */

public class BindThirdRequest extends AbstractKharazimArchivesHttpRequest<ArchivesCommonResult> {

  private static final String BIND_THIRD_DIRECTORY = "usr/upthpid";
  private static final String GET_PARAMS_KEY_TYPE = "type";
  private static final String GET_PARAMS_KEY_OPEN_ID = "openid";

  private Const.LoginType loginType;
  private String openid;

  public BindThirdRequest(Response.Listener<ArchivesCommonResult> listener,
                          Response.ErrorListener errorListener,
                          String accessToken) {
    super(ArchivesCommonResult.class, listener, errorListener, accessToken);
  }

  @Override
  protected void setGetParams(Map<String, String> params) {
    super.setGetParams(params);

    if (loginType != null) {
      params.put(GET_PARAMS_KEY_TYPE, String.valueOf(loginType.getValue()));
    }

    if (openid != null) {
      params.put(GET_PARAMS_KEY_OPEN_ID, openid);
    }
  }

  @Override
  protected String getBaseUrlDirectory() {
    return BIND_THIRD_DIRECTORY;
  }

  public void setLoginType(Const.LoginType loginType) {
    this.loginType = loginType;
  }

  public void setOpenid(String openid) {
    this.openid = openid;
  }
}
