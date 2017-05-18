package com.heqi.kharazim.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.heqi.kharazim.archives.activity.AboutUsActivity;
import com.heqi.kharazim.archives.activity.AccountManagementActivity;
import com.heqi.kharazim.archives.activity.EditUserAimActivity;
import com.heqi.kharazim.archives.activity.EditUserProfileActivity;
import com.heqi.kharazim.archives.activity.InitActivity;
import com.heqi.kharazim.archives.activity.LoginActivity;
import com.heqi.kharazim.archives.activity.PageActivity;
import com.heqi.kharazim.config.Intents;
import com.heqi.kharazim.consume.activity.ConsumeActivity;
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
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
    context.startActivity(intent);
  }

  public static void navigateToLogin(Context context) {
    Intent intent = new Intent(context, LoginActivity.class);
    if (!(context instanceof Activity)) {
      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }
    context.startActivity(intent);
  }

  public static void navigateToHome(Context context) {
    navigateToHome(context, null);
  }

  public static void navigateToHome(Context context, VerticalType verticalType) {
    Intent intent = new Intent(context, HomeActivity.class);
    if (!(context instanceof Activity)) {
      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }
    if (verticalType != null) {
      intent.putExtra(Intents.EXTRA_VERTICAL_TYPE, verticalType.name());
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

  public static void navigateToConsume(Context context, String dailyId, String userPlanId) {
    Intent intent = new Intent(context, ConsumeActivity.class);
    intent.setAction(Intents.ACTION_CONSUMER_PLAY);
    intent.putExtra(Intents.EXTRA_COURSE_DETAIL_DAILY_ID, dailyId);
    intent.putExtra(Intents.EXTRA_USER_PLAN_ID, userPlanId);
    context.startActivity(intent);
  }

  public static void navigateToEditUserProfile(Context context) {
    Intent intent = new Intent(context, EditUserProfileActivity.class);
    context.startActivity(intent);
  }

  public static void navigateToEditUserAim(Context context) {
    Intent intent = new Intent(context, EditUserAimActivity.class);
    context.startActivity(intent);
  }

  public static void navigateToPage(Context context, String url, String title) {
    Intent intent = new Intent(context, PageActivity.class);
    if (!(context instanceof Activity)) {
      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }
    intent.putExtra(Intents.EXTRA_PAGE_TITLE, title);
    intent.putExtra(Intents.EXTRA_PAGE_URL, url);
    context.startActivity(intent);
  }

  public static void navigateToAccountManagement(Context context) {
    Intent intent = new Intent(context, AccountManagementActivity.class);
    if (!(context instanceof Activity)) {
      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }
    context.startActivity(intent);
  }

  public static void navigateToAboutUs(Context context) {
    Intent intent = new Intent(context, AboutUsActivity.class);
    if (!(context instanceof Activity)) {
      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }
    context.startActivity(intent);
  }
}
