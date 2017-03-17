package com.heqi.kharazim.explore.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by overspark on 2016/11/13.
 */

public class PlanListInfo implements Serializable {

  private PlanListSummary page_info;
  private String ret_msg;
  private int ret_code;
  private List<PlanLiteInfo> page_data;

  public PlanListSummary getPage_info() {
    return page_info;
  }

  public void setPage_info(PlanListSummary page_info) {
    this.page_info = page_info;
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

  public List<PlanLiteInfo> getPage_data() {
    return page_data;
  }

  public void setPage_data(List<PlanLiteInfo> page_data) {
    this.page_data = page_data;
  }

  public static class PlanListSummary implements Serializable {

    private int pageNo;
    private int pageSize;
    private int count;

    public int getPageNo() {
      return pageNo;
    }

    public void setPageNo(int pageNo) {
      this.pageNo = pageNo;
    }

    public int getPageSize() {
      return pageSize;
    }

    public void setPageSize(int pageSize) {
      this.pageSize = pageSize;
    }

    public int getCount() {
      return count;
    }

    public void setCount(int count) {
      this.count = count;
    }
  }
}
