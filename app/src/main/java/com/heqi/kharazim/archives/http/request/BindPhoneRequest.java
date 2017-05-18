package com.heqi.kharazim.archives.http.request;

import com.android.volley.Response;
import com.heqi.kharazim.archives.model.ArchivesCommonResult;
import com.heqi.kharazim.utils.KharazimUtils;

import java.util.Map;

/**
 * Created by overspark on 2017/5/13.
 */

public class BindPhoneRequest extends AbstractKharazimArchivesHttpRequest<ArchivesCommonResult> {

  private static final String BIND_PHONE_DIRECTORY = "usr/upphoneno";
  private static final String GET_PARAMS_KEY_PHONE_NUMBER = "phoneno";
  private static final String GET_PARAMS_KEY_ZONE_NUMBER = "zoneno";
  private static final String GET_PARAMS_KEY_PASSWORD = "loginpw";
  private static final String GET_PARAMS_KEY_VERIFY_CODE = "verifycode";

  private String phoneNumber;
  private String zoneNumber;
  private String password;
  private String verifyCode;

  public BindPhoneRequest(Response.Listener<ArchivesCommonResult> listener,
                          Response.ErrorListener errorListener,
                          String accessToken) {
    super(ArchivesCommonResult.class, listener, errorListener, accessToken);
  }

  @Override
  protected void setGetParams(Map<String, String> params) {
    super.setGetParams(params);

    if (this.phoneNumber != null) {
      params.put(GET_PARAMS_KEY_PHONE_NUMBER, this.phoneNumber);
    }

    if (this.zoneNumber != null) {
      params.put(GET_PARAMS_KEY_ZONE_NUMBER, this.zoneNumber);
    }

    if (this.password != null) {
      params.put(GET_PARAMS_KEY_PASSWORD, KharazimUtils.kharazimEncode(this.password));
    }

    if (this.verifyCode != null) {
      params.put(GET_PARAMS_KEY_VERIFY_CODE, this.verifyCode);
    }
  }

  @Override
  protected String getBaseUrlDirectory() {
    return BIND_PHONE_DIRECTORY;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setZoneNumber(String zoneNumber) {
    this.zoneNumber = zoneNumber;
  }

  public void setVerifyCode(String verifyCode) {
    this.verifyCode = verifyCode;
  }
}
