package com.heqi.kharazim.config;

import android.net.Uri;
import android.support.v4.app.NotificationCompatBase;
import android.text.TextUtils;

import com.heqi.kharazim.explore.model.ActionDetailInfo;
import com.heqi.kharazim.explore.model.ActionQueryResult;
import com.heqi.kharazim.explore.model.AcupointDetailInfo;
import com.heqi.kharazim.explore.model.AcupointQueryResult;
import com.heqi.kharazim.explore.model.CourseDetailInfo;
import com.heqi.kharazim.explore.model.CourseQueryResult;
import com.heqi.kharazim.explore.model.PlanDetailInfo;
import com.heqi.kharazim.explore.model.PlanListInfo;
import com.heqi.kharazim.explore.model.PlanLiteInfo;

/**
 * Created by overspark on 2016/11/19.
 */

public class Const {

  public static class TabId {
    public static final String NONE = "";

    private TabId() {}
  }

  // media

  public static final String HTTP_URI_SCHEME = "http";

  public static boolean validateSourceUri(Uri uri) {
    return uri != null && !TextUtils.isEmpty(uri.toString());
  }

  // http
  //public static final String KHARAZIM_SERVER = "http://115.28.11.62:8080/";
  public static final String KHARAZIM_SERVER = "https://manager.heal361.com/";

  public static String getKharazimResource(final String resourceUrl) {
    return resourceUrl.startsWith("http://") ? resourceUrl : KHARAZIM_SERVER + resourceUrl;
  }

  public static void redirectKharazimModel(ActionDetailInfo actionDetailInfo) {
    actionDetailInfo.setActimg(getKharazimResource(actionDetailInfo.getActimg()));
    actionDetailInfo.setActvediofile(getKharazimResource(actionDetailInfo.getActvediofile()));

    if (actionDetailInfo.getActSoundDtoList() != null) {
      for (ActionDetailInfo.ActionSoundInfo actionSoundInfo :
          actionDetailInfo.getActSoundDtoList()) {
        redirectKharazimModel(actionSoundInfo);
      }
    }
  }

  public static void redirectKharazimModel(ActionDetailInfo.ActionSoundInfo actionSoundInfo) {
    actionSoundInfo.setSoundfile(getKharazimResource(actionSoundInfo.getSoundfile()));
  }

  public static void redirectKharazimModel(ActionQueryResult actionQueryResult) {
    redirectKharazimModel(actionQueryResult.getData_info());
  }

  public static void redirectKharazimModel(AcupointDetailInfo acupointDetailInfo) {
    acupointDetailInfo.setPositionimg(getKharazimResource(acupointDetailInfo.getPositionimg()));
  }

  public static void redirectKharazimModel(AcupointQueryResult acupointQueryResult) {
    redirectKharazimModel(acupointQueryResult.getData_info());
  }

  public static void redirectKharazimModel(CourseDetailInfo courseDetailInfo) {
    courseDetailInfo.setCourseimg(getKharazimResource(courseDetailInfo.getCourseimg()));

    if (courseDetailInfo.getActlist() != null) {
      for (ActionDetailInfo actionDetailInfo : courseDetailInfo.getActlist()) {
        redirectKharazimModel(actionDetailInfo);
      }
    }

    if (courseDetailInfo.getMusiclist() != null) {
      for (CourseDetailInfo.CourseMusicInfo musicInfo : courseDetailInfo.getMusiclist()) {
        redirectKharazimModel(musicInfo);
      }
    }
  }

  public static void redirectKharazimModel(CourseDetailInfo.CourseMusicInfo musicInfo) {
    musicInfo.setMusicfile(getKharazimResource(musicInfo.getMusicfile()));
  }

  public static void redirectKharazimModel(CourseQueryResult courseQueryResult) {
    redirectKharazimModel(courseQueryResult.getData_info());
  }

  public static void redirectKharazimModel(PlanDetailInfo planDetailInfo) {
    if (planDetailInfo.getData_info() != null) {
      for (PlanDetailInfo.PlanCourseInfo courseInfo : planDetailInfo.getData_info()) {
        redirectKharazimModel(courseInfo);
      }
    }
  }

  public static void redirectKharazimModel(PlanDetailInfo.PlanCourseInfo planCourseInfo) {
    if (planCourseInfo.getPlanDailyActDtoList() != null) {
      for (PlanDetailInfo.PlanActionInfo actionInfo : planCourseInfo.getPlanDailyActDtoList()) {
        redirectKharazimModel(actionInfo);
      }
    }
  }

  public static void redirectKharazimModel(PlanDetailInfo.PlanActionInfo planActionInfo) {
    planActionInfo.setActimg(getKharazimResource(planActionInfo.getActimg()));
    planActionInfo.setActvediofile(getKharazimResource(planActionInfo.getActvediofile()));
  }

  public static void redirectKharazimModel(PlanListInfo planListInfo) {
    for (PlanLiteInfo planLiteInfo : planListInfo.getPage_data()) {
      redirectKharazimModel(planLiteInfo);
    }
  }

  public static void redirectKharazimModel(PlanLiteInfo planLiteInfo) {
    planLiteInfo.setPlanimg(getKharazimResource(planLiteInfo.getPlanimg()));
  }


  private Const() {
  }
}
