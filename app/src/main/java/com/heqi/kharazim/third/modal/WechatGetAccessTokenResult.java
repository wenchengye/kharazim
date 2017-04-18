package com.heqi.kharazim.third.modal;

import com.heqi.kharazim.http.AbstractKharazimHttpRequest;

import java.io.Serializable;

/**
 * Created by overspark on 2017/4/18.
 */

public class WechatGetAccessTokenResult implements Serializable {

  private static final long serialVersionUID = -8412541895574601298L;

  private String access_token;
  private int expires_in;
  private String refresh_token;
  private String openid;
  private String scope;
  private String unionid;
  private int errcode;
  private String errmsg;

  public String getAccess_token() {
    return access_token;
  }

  public int getExpires_in() {
    return expires_in;
  }

  public String getRefresh_token() {
    return refresh_token;
  }

  public String getOpenid() {
    return openid;
  }

  public String getScope() {
    return scope;
  }

  public String getUnionid() {
    return unionid;
  }

  public int getErrcode() {
    return errcode;
  }

  public String getErrmsg() {
    return errmsg;
  }
}
