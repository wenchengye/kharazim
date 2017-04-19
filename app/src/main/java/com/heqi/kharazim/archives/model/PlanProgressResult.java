package com.heqi.kharazim.archives.model;

import java.io.Serializable;

/**
 * Created by overspark on 2017/4/19.
 */

public class PlanProgressResult implements Serializable {

  private static final long serialVersionUID = 6618981263333305596L;

  private int ret_code;
  private PlanProgressInfo ret_data;

  public int getRet_code() {
    return ret_code;
  }

  public void setRet_code(int ret_code) {
    this.ret_code = ret_code;
  }

  public PlanProgressInfo getRet_data() {
    return ret_data;
  }

  public void setRet_data(PlanProgressInfo ret_data) {
    this.ret_data = ret_data;
  }
}
