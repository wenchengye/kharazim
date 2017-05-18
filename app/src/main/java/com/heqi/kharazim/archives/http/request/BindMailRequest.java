package com.heqi.kharazim.archives.http.request;

import com.android.volley.Response;
import com.heqi.kharazim.archives.model.ArchivesCommonResult;
import com.heqi.kharazim.utils.KharazimUtils;

import java.util.Map;

/**
 * Created by overspark on 2017/5/13.
 */

public class BindMailRequest extends AbstractKharazimArchivesHttpRequest<ArchivesCommonResult> {

  private static final String BIND_MAIL_DIRECTORY = "usr/upemail";
  private static final String GET_PARAMS_KEY_EMAIL = "email";
  private static final String GET_PARAMS_KEY_VERIFY_CODE = "verifycode";
  private static final String GET_PARAMS_KEY_PASSWORD = "loginpw";

  private String email;
  private String verifyCode;
  private String password;

  public BindMailRequest(Response.Listener<ArchivesCommonResult> listener,
                         Response.ErrorListener errorListener,
                         String accessToken) {
    super(ArchivesCommonResult.class, listener, errorListener, accessToken);
  }

  @Override
  protected void setGetParams(Map<String, String> params) {
    super.setGetParams(params);

    if (this.email != null) {
      params.put(GET_PARAMS_KEY_EMAIL, email);
    }

    if (this.verifyCode != null) {
      params.put(GET_PARAMS_KEY_VERIFY_CODE, verifyCode);
    }

    if (this.password != null) {
      params.put(GET_PARAMS_KEY_PASSWORD, KharazimUtils.kharazimEncode(this.password));
    }

  }

  @Override
  protected String getBaseUrlDirectory() {
    return BIND_MAIL_DIRECTORY;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setVerifyCode(String verifyCode) {
    this.verifyCode = verifyCode;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
