package com.heqi.kharazim.archives.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;

import com.heqi.kharazim.KharazimApplication;
import com.heqi.kharazim.R;
import com.heqi.kharazim.archives.Archives;
import com.heqi.kharazim.explore.activity.ExploreActivity;
import com.heqi.kharazim.utils.KharazimUtils;

/**
 * Created by overspark on 2017/3/16.
 */

public class InitActivity extends FragmentActivity {

  private static final long MIN_STAY_DURATION = 2000L;
  private static Handler uiHandler = new Handler(Looper.getMainLooper());

  private Runnable pendingRunnable;
  private boolean paused = false;

  private Runnable goExploreRunnable = new Runnable() {
    @Override
    public void run() {
      //TODO: create listener as Activity's inner class is bad idea,
      //activity maybe not be freed for long time
      pendingRunnable = null;
      ExploreActivity.launchActivity(KharazimApplication.getAppContext());
      finish();
    }
  };

  private Runnable goLoginRunnable = new Runnable() {
    @Override
    public void run() {
      pendingRunnable = null;
    }
  };

  private Archives.ArchivesTaskCallback loginCallback = new Archives.ArchivesTaskCallback() {
    @Override
    public void onTaskSuccess(int code, String msg) {
      if (KharazimUtils.isRetCodeOK(code)) stayAndRun(goExploreRunnable);
      else stayAndRun(goLoginRunnable);
    }

    @Override
    public void onTaskFailed() {
      stayAndRun(goLoginRunnable);
    }
  };

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.archives_init_activity_layout);

    init();
  }

  @Override
  protected void onPause() {
    super.onPause();
    this.paused = true;

    if (this.pendingRunnable != null) {
      uiHandler.removeCallbacks(this.pendingRunnable);
    }
  }

  @Override
  protected void onResume() {
    super.onResume();
    this.paused = false;

    if (this.pendingRunnable != null) {
      stayAndRun(this.pendingRunnable);
    }
  }

  private void init() {
    this.pendingRunnable = null;
    Archives archives = KharazimApplication.getArchives();

    if (archives.getState() == Archives.State.ONLINE) {
      stayAndRun(goExploreRunnable);
    } else if (TextUtils.isEmpty(archives.getLastUserId())) {
      stayAndRun(goLoginRunnable);
    } else if (!archives.relogin(archives.getLastUserId(), loginCallback)) {
      stayAndRun(goLoginRunnable);
    }
  }

  private void stayAndRun(Runnable runnable) {
    if (this.pendingRunnable != null) {
      uiHandler.removeCallbacks(this.pendingRunnable);
    }
    this.pendingRunnable = runnable;
    if (!this.paused) {
      uiHandler.postDelayed(runnable, MIN_STAY_DURATION);
    }
  }

}
