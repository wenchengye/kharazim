package com.heqi.kharazim.consume.core.api;

import com.heqi.kharazim.explore.model.ActionDetailInfo;
import com.heqi.kharazim.explore.model.CourseDetailInfo;

/**
 * Created by overspark on 2016/12/28.
 */

public interface Consumer {

  void release();

  void play();

  void pause();

  void resume();

  void stop();

  void forward();

  void backward();

  void skipGuide();

  void seek(int second);

  void jump2Action(int index);

  CourseDetailInfo getCourse();

  void setCourse(CourseDetailInfo course);

  ActionDetailInfo getAction();

  int getActionIndex();

  int getProgress();

  int getDuration();

  int getActionRepeatIndex();

  int getActionRepeatSum();

  boolean canForward();

  boolean canBackward();

  boolean canPlay();

  boolean canJump2Action(int index);

  boolean isGuiding();

  void setConsumerCallback(ConsumerCallback callback);

  public interface ConsumerCallback {

    void onPreparing();

    void onGuideStart();

    void onPlayStart();

    void onPlayOver();

    void onError(String msg);

    void onProgress(int milliseconds, int duration);

    void onActionRepeat(int repeatIndex, int sum);

  }

}
