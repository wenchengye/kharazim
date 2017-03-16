package com.heqi.kharazim.config;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompatBase;
import android.text.TextUtils;

import com.heqi.base.utils.HttpUtil;
import com.heqi.kharazim.archives.model.UserProfile;
import com.heqi.kharazim.explore.model.ActionDetailInfo;
import com.heqi.kharazim.explore.model.ActionQueryResult;
import com.heqi.kharazim.explore.model.AcupointDetailInfo;
import com.heqi.kharazim.explore.model.AcupointQueryResult;
import com.heqi.kharazim.explore.model.CourseDetailInfo;
import com.heqi.kharazim.explore.model.CourseQueryResult;
import com.heqi.kharazim.explore.model.PlanDetailInfo;
import com.heqi.kharazim.explore.model.PlanListInfo;
import com.heqi.kharazim.explore.model.PlanLiteInfo;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by overspark on 2016/11/19.
 */

public class Const {

  public static class TabId {
    public static final String NONE = "";

    private TabId() {}
  }

  // media

  public static boolean validateSourceUri(Uri uri) {
    return uri != null && !TextUtils.isEmpty(uri.toString());
  }

  // http
  public static final boolean isRetCodeOK(int retCode) {
    return retCode == 0;
  }

  //public static final String KHARAZIM_SERVER = "http://115.28.11.62:8080/";
  public static final String KHARAZIM_SERVER = "https://manager.heal361.com/";

  public static String getKharazimResource(final String resourceUrl) {
    String ret = resourceUrl;

    if (!HttpUtil.isHttpScheme(Uri.parse(resourceUrl).getScheme())) {
      try {
        ret = HttpUtil.encodeUri(KHARAZIM_SERVER + resourceUrl, "UTF-8", true);
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }
    }

    return ret;
  }

  public static void redirectKharazimModel(@NonNull ActionDetailInfo actionDetailInfo) {
    actionDetailInfo.setActimg(getKharazimResource(actionDetailInfo.getActimg()));
    actionDetailInfo.setActvediofile(getKharazimResource(actionDetailInfo.getActvediofile()));

    if (actionDetailInfo.getActSoundDtoList() != null) {
      for (ActionDetailInfo.ActionSoundInfo actionSoundInfo :
          actionDetailInfo.getActSoundDtoList()) {
        redirectKharazimModel(actionSoundInfo);
      }
    }
  }

  public static void redirectKharazimModel(@NonNull ActionDetailInfo.ActionSoundInfo actionSoundInfo) {
    actionSoundInfo.setSoundfile(getKharazimResource(actionSoundInfo.getSoundfile()));
  }

  public static void redirectKharazimModel(@NonNull ActionQueryResult actionQueryResult) {
    redirectKharazimModel(actionQueryResult.getData_info());
  }

  public static void redirectKharazimModel(@NonNull AcupointDetailInfo acupointDetailInfo) {
    acupointDetailInfo.setPositionimg(getKharazimResource(acupointDetailInfo.getPositionimg()));
  }

  public static void redirectKharazimModel(@NonNull AcupointQueryResult acupointQueryResult) {
    redirectKharazimModel(acupointQueryResult.getData_info());
  }

  public static void redirectKharazimModel(@NonNull CourseDetailInfo courseDetailInfo) {
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

  public static void redirectKharazimModel(@NonNull CourseDetailInfo.CourseMusicInfo musicInfo) {
    musicInfo.setMusicfile(getKharazimResource(musicInfo.getMusicfile()));
  }

  public static void redirectKharazimModel(@NonNull CourseQueryResult courseQueryResult) {
    redirectKharazimModel(courseQueryResult.getData_info());
  }

  public static void redirectKharazimModel(@NonNull PlanDetailInfo planDetailInfo) {
    if (planDetailInfo.getData_info() != null) {
      for (PlanDetailInfo.PlanCourseInfo courseInfo : planDetailInfo.getData_info()) {
        redirectKharazimModel(courseInfo);
      }
    }
  }

  public static void redirectKharazimModel(@NonNull PlanDetailInfo.PlanCourseInfo planCourseInfo) {
    if (planCourseInfo.getPlanDailyActDtoList() != null) {
      for (PlanDetailInfo.PlanActionInfo actionInfo : planCourseInfo.getPlanDailyActDtoList()) {
        redirectKharazimModel(actionInfo);
      }
    }
  }

  public static void redirectKharazimModel(@NonNull PlanDetailInfo.PlanActionInfo planActionInfo) {
    planActionInfo.setActimg(getKharazimResource(planActionInfo.getActimg()));
    planActionInfo.setActvediofile(getKharazimResource(planActionInfo.getActvediofile()));
  }

  public static void redirectKharazimModel(@NonNull PlanListInfo planListInfo) {
    for (PlanLiteInfo planLiteInfo : planListInfo.getPage_data()) {
      redirectKharazimModel(planLiteInfo);
    }
  }

  public static void redirectKharazimModel(@NonNull PlanLiteInfo planLiteInfo) {
    planLiteInfo.setPlanimg(getKharazimResource(planLiteInfo.getPlanimg()));
  }

  public static void redirectKharazimModel(@NonNull UserProfile userProfile) {
    userProfile.setHeadimg(getKharazimResource(userProfile.getHeadimg()));
  }


  private Const() {}
}
