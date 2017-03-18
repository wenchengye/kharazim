package com.heqi.kharazim.activity;

import android.support.v4.app.FragmentActivity;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by overspark on 2017/3/17.
 */

public class PendingNavigateActivity extends FragmentActivity {

  private Runnable pendingNavigation = null;
  private final AtomicBoolean paused = new AtomicBoolean(false);

  @Override
  protected void onPause() {
    super.onPause();

    paused.set(true);
  }

  @Override
  protected void onResume() {
    super.onResume();

    paused.set(false);
    triggerPendingNavigation();
  }

  protected void triggerPendingNavigation() {

    Runnable navigation = null;

    if (this.pendingNavigation != null) {
      navigation = this.pendingNavigation;
      this.pendingNavigation = null;
    }

    if (navigation != null) {
      navigation.run();
    }
  }

  protected void clearPendingNavigation() {
    this.pendingNavigation = null;
  }

  protected void navigate(Runnable navigation) {
    if (navigation == null) return;

    if (paused.get()) {
      this.pendingNavigation = navigation;
    } else {
      navigation.run();
    }
  }
}
