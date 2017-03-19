package com.heqi.kharazim.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.heqi.kharazim.archives.activity.InitActivity;
import com.heqi.kharazim.archives.activity.LoginActivity;
import com.heqi.kharazim.config.Intents;
import com.heqi.kharazim.consume.activity.ConsumeActivity;
import com.heqi.kharazim.explore.activity.ExploreActivity;
import com.heqi.kharazim.explore.activity.PlanDetailActivity;
import com.heqi.kharazim.explore.model.PlanLiteInfo;

/**
 * Created by overspark on 2017/3/18.
 */

public class NavigationManager {

  private NavigationManager() {
  }

  public static void navigateToInit(Context context) {
    Intent intent = new Intent(context, InitActivity.class);
    context.startActivity(intent);
  }

  public static void navigateToLogin(Context context) {
    Intent intent = new Intent(context, LoginActivity.class);
    if (!(context instanceof Activity)) {
      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }
    context.startActivity(intent);
  }

  public static void navigateToExplore(Context context) {
    Intent intent = new Intent(context, ExploreActivity.class);
    if (!(context instanceof Activity)) {
      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }
    context.startActivity(intent);
  }

  public static void navigateToPlanDetail(Context context, PlanLiteInfo planLiteInfo) {
    Intent intent = new Intent(context, PlanDetailActivity.class);
    if (!(context instanceof Activity)) {
      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }
    intent.putExtra(Intents.EXTRA_PLAN_LITE_INFO, planLiteInfo);
    context.startActivity(intent);
  }

  public static void navigateToConsume(Context context, String id) {
    Intent intent = new Intent(context, ConsumeActivity.class);
    intent.setAction(Intents.ACTION_CONSUMER_PLAY);
    intent.putExtra(Intents.EXTRA_COURSE_DETAIL_TYPE,
        Intents.EXTRA_VALUE_COURSE_DETAIL_TYPE_DAILY_ID);
    intent.putExtra(Intents.EXTRA_COURSE_DETAIL_DAILY_ID, id);
    context.startActivity(intent);
  }
}
