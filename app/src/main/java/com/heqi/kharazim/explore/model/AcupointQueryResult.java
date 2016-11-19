package com.heqi.kharazim.explore.model;

import java.io.Serializable;

/**
 * Created by overspark on 2016/11/14.
 */

public class AcupointQueryResult implements Serializable {

  private String ret_msg;
  private int ret_code;
  private AcupointDetailInfo data_info;

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

  public AcupointDetailInfo getData_info() {
    return data_info;
  }

  public void setData_info(AcupointDetailInfo data_info) {
    this.data_info = data_info;
  }
}
