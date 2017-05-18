package com.heqi.kharazim.consume.core.internal;

import com.heqi.kharazim.config.Const;
import com.heqi.kharazim.consume.core.internal.api.ConsumeRecorder;
import com.heqi.kharazim.consume.model.ConsumeCourseRecord;
import com.heqi.kharazim.explore.model.ActionDetailInfo;
import com.heqi.kharazim.explore.model.CourseDetailInfo;
import com.heqi.kharazim.utils.KharazimUtils;

/**
 * Created by overspark on 2017/4/5.
 */

public class DefaultConsumeRecorder implements ConsumeRecorder {

  private static final int TRUE_VALUE = 1;
  private static final int FLASE_VALUE = 0;

  private ConsumeCourseRecord record;
  private ConsumeCourseRecord.ConsumeActionRecord current;

  private static boolean isBetterRecord(ConsumeCourseRecord.ConsumeActionRecord a,
                                        ConsumeCourseRecord.ConsumeActionRecord b) {
    return Integer.parseInt(b.getCptime()) >= Integer.parseInt(a.getCptime());
  }

  private static ConsumeCourseRecord.ConsumeActionRecord createActionRecord(String id, int index,
                                                                            String acupointName) {
    ConsumeCourseRecord.ConsumeActionRecord ret = new ConsumeCourseRecord.ConsumeActionRecord();
    ret.setActid(id);
    ret.setActnum(String.valueOf(index));
    //ret.setAcupointname(acupointName);
    ret.setCpflg(String.valueOf(FLASE_VALUE));
    ret.setSkipflg(String.valueOf(FLASE_VALUE));
    ret.setStarttime(KharazimUtils.formatTime(System.currentTimeMillis()));
    ret.setCptime(String.valueOf(0));

    return ret;
  }

  @Override
  public ConsumeCourseRecord getRecord() {
    return record;
  }

  @Override
  public void reset(CourseDetailInfo course) {
    record = new ConsumeCourseRecord();
  }

  @Override
  public void recordActionStarted(ActionDetailInfo action, int actionIndex) {
    current = createActionRecord(action.getId(), actionIndex, action.getActname());
    trySwapIntoRecord(current);
  }

  @Override
  public void recordActionProgressed(ActionDetailInfo action, int milliseconds) {
    current.setCptime(String.valueOf((milliseconds / Const.SECOND)));
  }

  @Override
  public void recordActionEnded(ActionDetailInfo action) {
    current.setEndtime(KharazimUtils.formatTime(System.currentTimeMillis()));
    current.setCpflg(String.valueOf(TRUE_VALUE));
    current.setSkipflg(String.valueOf(FLASE_VALUE));

    trySwapIntoRecord(current);
  }

  @Override
  public void recordActionSkipped(ActionDetailInfo action) {
    current.setEndtime(KharazimUtils.formatTime(System.currentTimeMillis()));
    current.setCpflg(String.valueOf(FLASE_VALUE));
    current.setSkipflg(String.valueOf(TRUE_VALUE));

    trySwapIntoRecord(current);
  }

  private void trySwapIntoRecord(ConsumeCourseRecord.ConsumeActionRecord actionRecord) {
    if (this.record == null) return;

    ConsumeCourseRecord.ConsumeActionRecord origin = null;
    int index = 0;
    for (int i = 0; i < this.record.getProgresslist().size(); ++i) {
      if (this.record.getProgresslist().get(i).getActid().equals(actionRecord.getActid())) {
        origin = this.record.getProgresslist().get(i);
        index = i;
        break;
      }
    }

    if (origin == null) {
      this.record.addActionRecord(actionRecord);
    } else if (isBetterRecord(origin, actionRecord)) {
      this.record.getProgresslist().set(index, actionRecord);
    }
  }


}
