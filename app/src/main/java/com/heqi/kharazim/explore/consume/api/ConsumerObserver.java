package com.heqi.kharazim.explore.consume.api;

import com.heqi.kharazim.explore.model.ActionDetailInfo;
import com.heqi.kharazim.explore.model.CourseDetailInfo;

/**
 * Created by overspark on 2016/12/28.
 */

public interface ConsumerObserver {

  void onRelease();

  void onPreparing(ActionDetailInfo action, int actionIndex, Reason reason);

  void onPlayStart(ActionDetailInfo action, int actionIndex);

  void onPlayOver(ActionDetailInfo action, int actionIndex);

  void onPause(ActionDetailInfo action, int actionIndex, Reason reason);

  void onResume(ActionDetailInfo action, int actionIndex, Reason reason);

  void onStop(ActionDetailInfo action, int actionIndex, Reason reason);

  void onProgress(int second, int duration);

  void onCourceChanged(CourseDetailInfo course);

  String getKey();


}
