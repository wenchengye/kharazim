package com.heqi.kharazim.archives.model;

/**
 * Created by overspark on 2017/3/13.
 */

public class ReloginResult {
  private int ret_code;
  private String accesstoken;

  public int getRet_code() {
    return ret_code;
  }

  public void setRet_code(int ret_code) {
    this.ret_code = ret_code;
  }

  public String getAccesstoken() {
    return accesstoken;
  }

  public void setAccesstoken(String accesstoken) {
    this.accesstoken = accesstoken;
  }
}
