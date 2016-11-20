package com.heqi.kharazim.explore.model;

import com.heqi.kharazim.http.request.AbstractKharazimHttpRequest;

import java.io.Serializable;

/**
 * Created by overspark on 2016/11/13.
 */

public class PlanLiteInfo implements Serializable {

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
  private int daytime;
  private int planlev;
  private String senddatetime;
  private String version;

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
    return AbstractKharazimHttpRequest.KHARAZIM_SERVER + planimg;
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

  public int getDaytime() {
    return daytime;
  }

  public void setDaytime(int daytime) {
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
}
