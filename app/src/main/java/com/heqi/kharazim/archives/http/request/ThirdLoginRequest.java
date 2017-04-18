package com.heqi.kharazim.archives.http.request;

import com.android.volley.Response;
import com.heqi.kharazim.archives.model.LoginResult;
import com.heqi.kharazim.http.AbstractKharazimHttpRequest;

import java.util.Map;

/**
 * Created by overspark on 2017/4/18.
 */

public class ThirdLoginRequest extends AbstractKharazimHttpRequest<LoginResult> {

  private static final String THIRD_LOGIN_DIRECTORY = "usr/thplogin";
  private static final String GET_PARAMS_KEY_OPEN_ID = "openid";
  private static final String GET_PARAMS_KEY_TYPE = "type";

  private String openId;
  private Integer type;

  public ThirdLoginRequest(Response.Listener<LoginResult> listener,
                           Response.ErrorListener errorListener) {
    super(LoginResult.class, listener, errorListener);
  }

  @Override
  protected void setGetParams(Map<String, String> params) {
    super.setGetParams(params);

    if (openId != null) {
      params.put(GET_PARAMS_KEY_OPEN_ID, openId);
    }

    if (type != null) {
      params.put(GET_PARAMS_KEY_TYPE, String.valueOf(type));
    }
  }

  @Override
  protected String getBaseUrlDirectory() {
    return THIRD_LOGIN_DIRECTORY;
  }

  public void setOpenId(String openId) {
    this.openId = openId;
  }

  public void setType(Integer type) {
    this.type = type;
  }
}
