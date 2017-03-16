package com.heqi.kharazim.archives.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;

import com.heqi.kharazim.KharazimApplication;
import com.heqi.kharazim.archives.Archives;
import com.heqi.kharazim.config.Const;
import com.heqi.kharazim.explore.activity.ExploreActivity;

/**
 * Created by overspark on 2017/3/16.
 */

public class InitActivity extends FragmentActivity {

  private static final long MIN_STAY_DURATION = 2000L;

  private Handler uiHandler = new Handler(Looper.getMainLooper());

  private Archives.ArchivesTaskCallback loginCallback = new Archives.ArchivesTaskCallback() {
    @Override
    public void onTaskSuccess(int code, String msg) {
      if (Const.isRetCodeOK(code)) stayAndGoExplore();
      else stayAndGoLogin();
    }

    @Override
    public void onTaskFailed() {
      stayAndGoLogin();
    }
  };

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    init();
  }

  private void init() {
    Archives archives = KharazimApplication.getArchives();

    if (archives.getState() == Archives.State.ONLINE) {
      stayAndGoExplore();
    } else if (TextUtils.isEmpty(archives.getLastUserId())) {
      stayAndGoLogin();
    } else if (!archives.relogin(archives.getLastUserId(), loginCallback)) {
      stayAndGoLogin();
    }
  }

  private void stayAndGoExplore() {
    uiHandler.postDelayed(new Runnable() {
      @Override
      public void run() {
        ExploreActivity.launchActivity(InitActivity.this);
      }
    }, MIN_STAY_DURATION);
  }

  private void stayAndGoLogin() {

  }
}
