package com.heqi.kharazim.archives.model;

/**
 * Created by overspark on 2017/3/13.
 */

public class HeadImageResult {
  private String ret_msg;
  private int ret_code;
  private String data_src;

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

  public String getData_src() {
    return data_src;
  }

  public void setData_src(String data_src) {
    this.data_src = data_src;
  }
}