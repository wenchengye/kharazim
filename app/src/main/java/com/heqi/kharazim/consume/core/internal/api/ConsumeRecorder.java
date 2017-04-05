package com.heqi.kharazim.consume.core.internal.api;

import com.heqi.kharazim.consume.model.ConsumeCourseRecord;
import com.heqi.kharazim.explore.model.ActionDetailInfo;
import com.heqi.kharazim.explore.model.CourseDetailInfo;

/**
 * Created by overspark on 2017/4/5.
 */

public interface ConsumeRecorder {

  ConsumeCourseRecord getRecord();

  void reset(CourseDetailInfo course);

  void recordActionStarted(ActionDetailInfo action, int index);

  void recordActionProgressed(ActionDetailInfo action, int milliseconds);

  void recordActionEnded(ActionDetailInfo action);

  void recordActionSkipped(ActionDetailInfo action);

}
