package com.heqi.kharazim.archives.http;

import com.android.volley.Response;
import com.heqi.base.utils.HttpUtil;
import com.heqi.kharazim.archives.model.ArchivesCommonResult;

import java.util.Map;

/**
 * Created by overspark on 2017/3/13.
 */

public class ResetPasswordInSessionRequest extends
    AbstractKharazimArchivesHttpRequest<ArchivesCommonResult> {

  private static final String RESET_PASSWORD_IN_SESSION_DIRECTORY = "usr/modifypw";
  private static final String GET_PARAMS_KEY_OLD_PASSWORD = "oldpassword";
  private static final String GET_PARAMS_KEY_NEW_PASSWORD = "newpassword";

  private String oldPassword;
  private String newPassword;

  public ResetPasswordInSessionRequest(Response.Listener<ArchivesCommonResult> listener,
                                       Response.ErrorListener errorListener,
                                       String accessToken) {
    super(ArchivesCommonResult.class, listener, errorListener, accessToken);
  }

  @Override
  protected String getBaseUrlDirectory() {
    return RESET_PASSWORD_IN_SESSION_DIRECTORY;
  }

  @Override
  protected void setGetParams(Map<String, String> params) {
    super.setGetParams(params);

    if (this.oldPassword != null) {
      params.put(GET_PARAMS_KEY_OLD_PASSWORD, HttpUtil.MD5(this.oldPassword));
    }

    if (this.newPassword != null) {
      params.put(GET_PARAMS_KEY_NEW_PASSWORD, HttpUtil.MD5(this.newPassword));
    }
  }

  public String getOldPassword() {
    return oldPassword;
  }

  public void setOldPassword(String oldPassword) {
    this.oldPassword = oldPassword;
  }

  public String getNewPassword() {
    return newPassword;
  }

  public void setNewPassword(String newPassword) {
    this.newPassword = newPassword;
  }
}
