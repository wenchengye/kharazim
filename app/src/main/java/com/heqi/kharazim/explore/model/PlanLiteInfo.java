package com.heqi.kharazim.explore.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by overspark on 2016/11/13.
 */

public class PlanLiteInfo implements Serializable {

  private static final long serialVersionUID = 1347763957635128890L;

  private String id;
  private String planname;
  private String plandec;
  private String planimg;
  private int coursecnt;
  private int actcnt;
  private int acupointcnt;
  private int plantype;
  private String nounit;
  private int cpdays;
  private int cptime;
  private String daytime;
  private int planlev;
  private String senddatetime;
  private String version;
  private boolean myplan;
  private String userplanid;
  private int mycpdays;
  private List<PlanDescriptionInfo> plandeclist;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getPlanname() {
    return planname;
  }

  public void setPlanname(String planname) {
    this.planname = planname;
  }

  public String getPlandec() {
    return plandec;
  }

  public void setPlandec(String plandec) {
    this.plandec = plandec;
  }

  public String getPlanimg() {
    return planimg;
  }

  public void setPlanimg(String planimg) {
    this.planimg = planimg;
  }

  public int getCoursecnt() {
    return coursecnt;
  }

  public void setCoursecnt(int coursecnt) {
    this.coursecnt = coursecnt;
  }

  public int getActcnt() {
    return actcnt;
  }

  public void setActcnt(int actcnt) {
    this.actcnt = actcnt;
  }

  public int getAcupointcnt() {
    return acupointcnt;
  }

  public void setAcupointcnt(int acupointcnt) {
    this.acupointcnt = acupointcnt;
  }

  public int getPlantype() {
    return plantype;
  }

  public void setPlantype(int plantype) {
    this.plantype = plantype;
  }

  public String getNounit() {
    return nounit;
  }

  public void setNounit(String nounit) {
    this.nounit = nounit;
  }

  public int getCpdays() {
    return cpdays;
  }

  public void setCpdays(int cpdays) {
    this.cpdays = cpdays;
  }

  public int getCptime() {
    return cptime;
  }

  public void setCptime(int cptime) {
    this.cptime = cptime;
  }

  public String getDaytime() {
    return daytime;
  }

  public void setDaytime(String daytime) {
    this.daytime = daytime;
  }

  public int getPlanlev() {
    return planlev;
  }

  public void setPlanlev(int planlev) {
    this.planlev = planlev;
  }

  public String getSenddatetime() {
    return senddatetime;
  }

  public void setSenddatetime(String senddatetime) {
    this.senddatetime = senddatetime;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public boolean isMyplan() {
    return myplan;
  }

  public void setMyplan(boolean myplan) {
    this.myplan = myplan;
  }

  public String getUserplanid() {
    return userplanid;
  }

  public void setUserplanid(String userplanid) {
    this.userplanid = userplanid;
  }

  public int getMycpdays() {
    return mycpdays;
  }

  public void setMycpdays(int mycpdays) {
    this.mycpdays = mycpdays;
  }

  public List<PlanDescriptionInfo> getPlandeclist() {
    return plandeclist;
  }

  public void setPlandeclist(List<PlanDescriptionInfo> plandeclist) {
    this.plandeclist = plandeclist;
  }

  public static class PlanDescriptionInfo implements Serializable {

    private static final long serialVersionUID = 3894569751797214523L;

    private String plandecl;

    public String getPlandecl() {
      return plandecl;
    }

    public void setPlandecl(String plandecl) {
      this.plandecl = plandecl;
    }
  }
}
