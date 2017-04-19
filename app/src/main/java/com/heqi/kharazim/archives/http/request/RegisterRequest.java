package com.heqi.kharazim.archives.http.request;

import com.android.volley.Response;
import com.heqi.base.utils.HttpUtil;
import com.heqi.kharazim.archives.model.RegisterResult;
import com.heqi.kharazim.http.AbstractKharazimHttpRequest;
import com.heqi.kharazim.utils.KharazimUtils;

import java.util.Map;

/**
 * Created by overspark on 2017/3/13.
 */

public class RegisterRequest extends AbstractKharazimHttpRequest<RegisterResult> {

  private static final String REGISTER_DIRECTORY = "usr/register";
  private static final String GET_PARAMS_KEY_NICKNAME = "nickname";
  private static final String GET_PARAMS_KEY_PHONE_NUMBER = "phoneno";
  private static final String GET_PARAMS_KEY_ZONE_NUMBER = "zoneno";
  private static final String GET_PARAMS_KEY_EMAIL = "email";
  private static final String GET_PARAMS_KEY_LOGIN_PASSWORD = "loginpw";

  private static final String GET_PARAMS_VALUE_DEFAULT_ZONE_NUMBER = "86";

  private String nickname;
  private String phoneNumber;
  private String zoneNumber;
  private String email;
  private String password;

  public RegisterRequest(Response.Listener<RegisterResult> listener,
                         Response.ErrorListener errorListener) {
    super(RegisterResult.class, listener, errorListener);
  }

  @Override
  protected String getBaseUrlDirectory() {
    return REGISTER_DIRECTORY;
  }

  @Override
  protected void setGetParams(Map<String, String> params) {
    super.setGetParams(params);
    if (this.nickname != null) {
      params.put(GET_PARAMS_KEY_NICKNAME, this.nickname);
    }

    if (this.phoneNumber != null) {
      params.put(GET_PARAMS_KEY_PHONE_NUMBER, this.phoneNumber);
    }

    if (this.zoneNumber != null) {
      params.put(GET_PARAMS_KEY_ZONE_NUMBER, this.zoneNumber);
    } else if (this.phoneNumber != null){
      params.put(GET_PARAMS_KEY_ZONE_NUMBER, GET_PARAMS_VALUE_DEFAULT_ZONE_NUMBER);
    }

    if (this.email != null) {
      params.put(GET_PARAMS_KEY_EMAIL, this.email);
    }

    if (this.password != null) {
      params.put(GET_PARAMS_KEY_LOGIN_PASSWORD, KharazimUtils.kharazimEncode(this.password));
    }
  }

  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getZoneNumber() {
    return zoneNumber;
  }

  public void setZoneNumber(String zoneNumber) {
    this.zoneNumber = zoneNumber;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
