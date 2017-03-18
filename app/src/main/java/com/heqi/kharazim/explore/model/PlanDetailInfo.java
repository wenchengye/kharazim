package com.heqi.kharazim.explore.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by overspark on 2016/11/14.
 */

public class PlanDetailInfo implements Serializable {

  private String ret_msg;
  private int ret_code;
  private List<PlanCourseInfo> data_info;

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

  public List<PlanCourseInfo> getData_info() {
    return data_info;
  }

  public void setData_info(List<PlanCourseInfo> data_info) {
    this.data_info = data_info;
  }

  public static class PlanCourseInfo implements Serializable {

    private static final long serialVersionUID = -5250420789650253984L;

    private String id;
    private String title;
    private String cpdatetime;
    private String dailydec;
    private List<PlanActionInfo> planDailyActDtoList;

    public String getId() {
      return id;
    }

    public void setId(String id) {
      this.id = id;
    }

    public String getTitle() {
      return title;
    }

    public void setTitle(String title) {
      this.title = title;
    }

    public String getCpdatetime() {
      return cpdatetime;
    }

    public void setCpdatetime(String cpdatetime) {
      this.cpdatetime = cpdatetime;
    }

    public String getDailydec() {
      return dailydec;
    }

    public void setDailydec(String dailydec) {
      this.dailydec = dailydec;
    }

    public List<PlanActionInfo> getPlanDailyActDtoList() {
      return planDailyActDtoList;
    }

    public void setPlanDailyActDtoList(List<PlanActionInfo> planDailyActDtoList) {
      this.planDailyActDtoList = planDailyActDtoList;
    }
  }

  public static class PlanActionInfo implements Serializable {

    private static final long serialVersionUID = 2967595006698418363L;

    private int num;
    private String actid;
    private String actimg;
    private String acupointid;
    private String acupointname;
    private String actvediofile;

    public int getNum() {
      return num;
    }

    public void setNum(int num) {
      this.num = num;
    }

    public String getActid() {
      return actid;
    }

    public void setActid(String actid) {
      this.actid = actid;
    }

    public String getActimg() {
      return actimg;
    }

    public void setActimg(String actimg) {
      this.actimg = actimg;
    }

    public String getAcupointid() {
      return acupointid;
    }

    public void setAcupointid(String acupointid) {
      this.acupointid = acupointid;
    }

    public String getAcupointname() {
      return acupointname;
    }

    public void setAcupointname(String acupointname) {
      this.acupointname = acupointname;
    }

    public String getActvediofile() {
      return actvediofile;
    }

    public void setActvediofile(String actvediofile) {
      this.actvediofile = actvediofile;
    }
  }
}
