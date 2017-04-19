package com.heqi.kharazim.archives.http.request;

import com.android.volley.Response;
import com.heqi.base.utils.HttpUtil;
import com.heqi.kharazim.archives.model.ArchivesCommonResult;
import com.heqi.kharazim.http.AbstractKharazimHttpRequest;
import com.heqi.kharazim.utils.KharazimUtils;

import java.util.Map;

/**
 * Created by overspark on 2017/3/13.
 */

public class ResetPasswordRequest extends AbstractKharazimHttpRequest<ArchivesCommonResult> {

  private static final String RESET_PASSWORD_DIRECTORY = "usr/forgetpw";
  private static final String GET_PARAMS_KEY_DATA_TYPE = "datatype";
  private static final String GET_PARAMS_KEY_LOGIN_PASSWORD = "loginpw";
  private static final String GET_PARAMS_KEY_PHONE_NUMBER = "phoneno";
  private static final String GET_PARAMS_KEY_ZONE_NUMBER = "zoneno";
  private static final String GET_PARAMS_KEY_EMAIL = "email";
  private static final String GET_PARAMS_KEY_CODE_NUMBER = "codeno";

  private int dataType;
  private String loginPassword;
  private String phoneNumber;
  private String zoneNumber;
  private String email;
  private String codeNumber;

  public ResetPasswordRequest(Response.Listener<ArchivesCommonResult> listener,
                              Response.ErrorListener errorListener) {
    super(ArchivesCommonResult.class, listener, errorListener);
  }

  @Override
  protected String getBaseUrlDirectory() {
    return RESET_PASSWORD_DIRECTORY;
  }

  @Override
  protected void setGetParams(Map<String, String> params) {
    super.setGetParams(params);

    params.put(GET_PARAMS_KEY_DATA_TYPE, String.valueOf(this.dataType));

    if (this.loginPassword != null) {
      params.put(GET_PARAMS_KEY_LOGIN_PASSWORD, KharazimUtils.kharazimEncode(this.loginPassword));
    }

    if (this.phoneNumber != null) {
      params.put(GET_PARAMS_KEY_PHONE_NUMBER, this.phoneNumber);
    }

    if (this.zoneNumber != null) {
      params.put(GET_PARAMS_KEY_ZONE_NUMBER, this.zoneNumber);
    }

    if (this.email != null) {
      params.put(GET_PARAMS_KEY_EMAIL, this.email);
    }

    if (this.codeNumber != null) {
      params.put(GET_PARAMS_KEY_CODE_NUMBER, this.codeNumber);
    }
  }

  public int getDataType() {
    return dataType;
  }

  public void setDataType(int dataType) {
    this.dataType = dataType;
  }

  public String getLoginPassword() {
    return loginPassword;
  }

  public void setLoginPassword(String loginPassword) {
    this.loginPassword = loginPassword;
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

  public String getCodeNumber() {
    return codeNumber;
  }

  public void setCodeNumber(String codeNumber) {
    this.codeNumber = codeNumber;
  }
}
