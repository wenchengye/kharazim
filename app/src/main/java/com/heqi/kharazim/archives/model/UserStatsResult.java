package com.heqi.kharazim.archives.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by overspark on 2017/3/25.
 */

public class UserStatsResult implements Serializable {

  private static final long serialVersionUID = 167568400868724464L;

  private int ret_code;
  private List<UserStatsInfo> ret_data;

  public int getRet_code() {
    return ret_code;
  }

  public void setRet_code(int ret_code) {
    this.ret_code = ret_code;
  }

  public List<UserStatsInfo> getRet_data() {
    return ret_data;
  }

  public void setRet_data(List<UserStatsInfo> ret_data) {
    this.ret_data = ret_data;
  }
}
