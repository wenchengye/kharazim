package com.heqi.kharazim.action;

import android.content.Context;

import com.heqi.kharazim.explore.activity.ConsumeActivity;

/**
 * Created by overspark on 2016/12/21.
 */

public class JoinPlanAction implements Runnable {

  private String planId;

  /**
   *
   * @param planId the id of the plan to join
   */
  public JoinPlanAction(String planId) {
    this.planId = planId;
  }

  @Override
  public void run() {

  }
}
