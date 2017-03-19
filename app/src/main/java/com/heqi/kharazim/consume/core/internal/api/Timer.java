package com.heqi.kharazim.consume.core.internal.api;

/**
 * Created by overspark on 2017/2/3.
 */

public interface Timer {

  void start();

  void stop();

  boolean isStarted();

  void addRunner(TimerRunner runner);

  void removeRunner(TimerRunner runner);

  public interface TimerRunner {
    void tickSecond();
  }

}
