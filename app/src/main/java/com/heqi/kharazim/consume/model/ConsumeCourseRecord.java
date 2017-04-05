package com.heqi.kharazim.consume.model;

import com.heqi.kharazim.config.Const;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by overspark on 2017/4/1.
 */

public class ConsumeCourseRecord implements Serializable {

  private static final long serialVersionUID = 3761991379942554276L;

  public static final int KHARAZIM_POINT_BASE = 10;

  private String dailyId;
  private String userplanid;
  private List<ConsumeActionRecord> progresslist = new ArrayList<>();

  public String getUserplanid() {
    return userplanid;
  }

  public String getDailyId() {
    return dailyId;
  }

  public List<ConsumeActionRecord> getProgresslist() {
    return progresslist;
  }

  public void setDailyId(String dailyId) {
    this.dailyId = dailyId;
  }

  public void setUserplanid(String userplanid) {
    this.userplanid = userplanid;
  }

  public void addActionRecord(ConsumeActionRecord record) {
    progresslist.add(record);
  }

  public int getTotalConsumeTimeInMinute() {
    int sum = 0;
    for (ConsumeActionRecord record : progresslist) {
      sum += Integer.parseInt(record.getCptime());
    }
    return (int)(sum * Const.SECOND / Const.MINTUE);
  }

  public int getKharazimPoint() {
    return getTotalConsumeTimeInMinute() / KHARAZIM_POINT_BASE;
  }

  public int getAcupointCount() {
    return progresslist.size();
  }


  public static class ConsumeActionRecord implements Serializable {

    private static final long serialVersionUID = 6796086631007019125L;

    private String actid;
    private String actnum;
    private String acupointname;
    private String cpflg;
    private String skipflg;
    private String starttime;
    private String endtime;
    private String cptime;

    public String getActid() {
      return actid;
    }

    public void setActid(String actid) {
      this.actid = actid;
    }

    public String getActnum() {
      return actnum;
    }

    public void setActnum(String actnum) {
      this.actnum = actnum;
    }

    public String getAcupointname() {
      return acupointname;
    }

    public void setAcupointname(String acupointname) {
      this.acupointname = acupointname;
    }

    public String getCpflg() {
      return cpflg;
    }

    public void setCpflg(String cpflg) {
      this.cpflg = cpflg;
    }

    public String getSkipflg() {
      return skipflg;
    }

    public void setSkipflg(String skipflg) {
      this.skipflg = skipflg;
    }

    public String getStarttime() {
      return starttime;
    }

    public void setStarttime(String starttime) {
      this.starttime = starttime;
    }

    public String getEndtime() {
      return endtime;
    }

    public void setEndtime(String endtime) {
      this.endtime = endtime;
    }

    public String getCptime() {
      return cptime;
    }

    public void setCptime(String cptime) {
      this.cptime = cptime;
    }
  }
}
