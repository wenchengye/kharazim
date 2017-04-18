package com.heqi.kharazim.third.http;

import com.android.volley.Response;
import com.heqi.kharazim.third.modal.WechatGetAccessTokenResult;
import com.heqi.rpc.GsonVolleyRequest;

import java.util.Map;

/**
 * Created by overspark on 2017/4/18.
 */

public class WechatRefreshAccessTokenRequest extends GsonVolleyRequest<WechatGetAccessTokenResult> {

  private static final String URL = "https://api.weixin.qq.com/sns/oauth2/refresh_token";
  private static final String GET_PARAMS_KEY_APPID = "appid";
  private static final String GET_PARAMS_KEY_REFRESH_TOKEN = "refresh_token";
  private static final String GET_PARAMS_KEY_GRANT_TYPE = "grant_type";
  private static final String GET_PARAMS_VALUE_GRANT_TYPE = "refresh_token";

  private String appid;
  private String refreshToken;

  public WechatRefreshAccessTokenRequest(Response.Listener<WechatGetAccessTokenResult> listener,
                                         Response.ErrorListener errorListener) {
    super(WechatGetAccessTokenResult.class, listener, errorListener);
  }

  @Override
  protected void setGetParams(Map<String, String> params) {
    super.setGetParams(params);

    if (appid != null) {
      params.put(GET_PARAMS_KEY_APPID, appid);
    }

    if (refreshToken != null) {
      params.put(GET_PARAMS_KEY_REFRESH_TOKEN, refreshToken);
    }

    params.put(GET_PARAMS_KEY_GRANT_TYPE, GET_PARAMS_VALUE_GRANT_TYPE);
  }

  @Override
  protected String getBaseUrl() {
    return URL;
  }

  public void setAppid(String appid) {
    this.appid = appid;
  }

  public void setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }
}
