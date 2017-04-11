package com.heqi.kharazim.consume.core.internal;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.heqi.kharazim.consume.core.internal.api.Timer;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by overspark on 2017/2/3.
 */

public class DefaultTimer implements Timer {

  private static final int TICK_SECOND_MESSAGE = 1;
  private static final long TICK_SECOND_PERIOD = 100L;

  private Handler handler;
  private Set<TimerRunner> runners = new HashSet<>();
  private volatile boolean started = false;

  public DefaultTimer(Looper looper) {
    handler = new Handler(looper) {
      @Override
      public void handleMessage(Message msg) {
        switch (msg.what) {
          case TICK_SECOND_MESSAGE:
            synchronized (DefaultTimer.this) {
              if (!started) {
                handler.removeMessages(TICK_SECOND_MESSAGE);
                return;
              }
            }

            tickSecond();

            synchronized (DefaultTimer.this) {
              if (started) {
                handler.sendEmptyMessageDelayed(TICK_SECOND_MESSAGE, TICK_SECOND_PERIOD);
              } else {
                handler.removeMessages(TICK_SECOND_MESSAGE);
              }
            }
            break;
          default:
            break;
        }
      }
    };
  }

  @Override
  public synchronized void start() {
    if (started) return;

    started = true;
    handler.removeMessages(TICK_SECOND_MESSAGE);
    handler.sendEmptyMessageDelayed(TICK_SECOND_MESSAGE, TICK_SECOND_PERIOD);
  }

  @Override
  public synchronized void stop() {
    if (!started) return;

    started = false;
    handler.removeMessages(TICK_SECOND_MESSAGE);
  }

  @Override
  public boolean isStarted() {
    return started;
  }

  @Override
  public void addRunner(TimerRunner runner) {

    if (runner == null) return;

    synchronized (runners) {
      if (!runners.contains(runner)) {
        runners.add(runner);
      }
    }
  }

  @Override
  public void removeRunner(TimerRunner runner) {

    if (runner == null) return;

    synchronized (runners) {
      if (runners.contains(runner)) {
        runners.remove(runner);
      }
    }
  }

  private void tickSecond() {
    synchronized (runners) {
      for (TimerRunner runner : runners) {
        runner.tickSecond();
      }
    }
  }
}
