package com.heqi.kharazim.archives.model;

/**
 * Created by overspark on 2017/3/13.
 */

public class LoginResult {
  private String relogintoken;
  private String accesstoken;
  private String ret_msg;
  private int ret_code;

  public String getRelogintoken() {
    return relogintoken;
  }

  public void setRelogintoken(String relogintoken) {
    this.relogintoken = relogintoken;
  }

  public String getAccesstoken() {
    return accesstoken;
  }

  public void setAccesstoken(String accesstoken) {
    this.accesstoken = accesstoken;
  }

  public String getRet_msg() {
    return ret_msg;
  }

  public void setRet_msg(String ret_msg) {
    this.ret_msg = ret_msg;
  }

  public int getRet_code() {
    return ret_code;
  }

  public void setRet_code(int ret_code) {
    this.ret_code = ret_code;
  }
}
