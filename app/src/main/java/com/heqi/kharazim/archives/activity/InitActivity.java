package com.heqi.kharazim.archives.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.heqi.kharazim.KharazimApplication;
import com.heqi.kharazim.R;
import com.heqi.kharazim.activity.NavigationManager;
import com.heqi.kharazim.activity.PendingNavigateActivity;
import com.heqi.kharazim.archives.Archives;
import com.heqi.kharazim.utils.KharazimUtils;

/**
 * Created by overspark on 2017/3/16.
 */

public class InitActivity extends PendingNavigateActivity {

  private static final long MIN_STAY_DURATION = 2000L;
  private static Handler uiHandler = new Handler(Looper.getMainLooper());

  //TODO: create listener as Activity's inner class is bad idea,
  //activity maybe not be freed for long time

  private Runnable goExploreRunnable = new Runnable() {
    @Override
    public void run() {
      NavigationManager.navigateToExplore(KharazimApplication.getAppContext());
      finish();
    }
  };

  private Runnable goLoginRunnable = new Runnable() {
    @Override
    public void run() {
      NavigationManager.navigateToLogin(KharazimApplication.getAppContext());
      finish();
    }
  };

  private Archives.ArchivesTaskCallback loginCallback = new Archives.ArchivesTaskCallback() {
    @Override
    public void onTaskSuccess(int code, String msg) {
      if (KharazimUtils.isRetCodeOK(code)) stayAndNavigate(goExploreRunnable);
      else stayAndNavigate(goLoginRunnable);
    }

    @Override
    public void onTaskFailed() {
      stayAndNavigate(goLoginRunnable);
    }
  };

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.archives_init_activity_layout);

    init();
  }

  private void init() {
    Archives archives = KharazimApplication.getArchives();

    if (archives.getState() == Archives.State.ONLINE) {
      stayAndNavigate(goExploreRunnable);
    } else if (TextUtils.isEmpty(archives.getLastUserId())) {
      stayAndNavigate(goLoginRunnable);
    } else if (!archives.relogin(archives.getLastUserId(), loginCallback)) {
      stayAndNavigate(goLoginRunnable);
    }
  }

  private void stayAndNavigate(final Runnable runnable) {
    uiHandler.postDelayed(new Runnable() {
      @Override
      public void run() {
        navigate(runnable);
      }
    }, MIN_STAY_DURATION);
  }

}
