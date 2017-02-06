package com.heqi.kharazim.explore.consume.internal;

import android.net.Uri;

import com.heqi.kharazim.config.Const;
import com.heqi.kharazim.explore.consume.internal.api.Timeline;
import com.heqi.kharazim.explore.model.ActionDetailInfo;
import com.heqi.kharazim.explore.model.CourseDetailInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by overspark on 2017/2/3.
 */

public class CourseManager {

  private static final int DEFAULT_BASE_TIME = 0;

  private CourseDetailInfo course;
  private Timeline actionTimeline;
  private List<Timeline.TimelineItem> actionTimelineItems = new ArrayList<Timeline.TimelineItem>();
  private List<Timeline> soundTimelines = new ArrayList<Timeline>();

  public void setCourse(CourseDetailInfo course) {
    if (course == null || this.course == course) return;

    this.course = course;
    this.actionTimeline = new DefaultTimeline();
    this.actionTimeline.setBaseTime(DEFAULT_BASE_TIME);
    this.actionTimelineItems.clear();
    this.soundTimelines.clear();
    int offset = DEFAULT_BASE_TIME;
    for (ActionDetailInfo action : this.course.getActlist()) {
      Timeline.TimelineItem actionItem = new Timeline.TimelineItem(
          Uri.parse(Const.getKharazimResource(action.getActvediofile())),
          offset,
          action.getCptime(),
          action.getCpcnt(),
          0
      );
      actionTimelineItems.add(actionItem);
      actionTimeline.addItem(actionItem);

      Timeline timeline = new DefaultTimeline();
      timeline.setBaseTime(offset);
      for (ActionDetailInfo.ActionSoundInfo sound : action.getActSoundDtoList()) {
        timeline.addItem(new Timeline.TimelineItem(
            Uri.parse(Const.getKharazimResource(sound.getSoundfile())),
            sound.getStarttime(),
            sound.getLen(),
            1,
            0
        ));
      }
      soundTimelines.add(timeline);

      offset += actionItem.getDuration();
    }
  }

  public CourseDetailInfo getCourse() {
    return this.course;
  }

  public int getActionCount() {
    return this.course != null ? this.course.getActlist().size() : 0;
  }

  public ActionDetailInfo getAction(int index) {
    return this.course != null ? this.course.getActlist().get(index) : null;
  }

  public Timeline getActionTimeline() {
    return this.actionTimeline;
  }

  public Timeline.TimelineItem getActionTimelineItem(int index) {
    return index >= 0 && index < actionTimelineItems.size() ? actionTimelineItems.get(index) : null;
  }

  public Timeline getSoundTimeline(int index) {
    return index >= 0 && index < soundTimelines.size() ? soundTimelines.get(index) : null;
  }

  public CourseDetailInfo.CourseMusicInfo getMusic() {
    return this.course != null && !this.course.getMusiclist().isEmpty()
        ? this.course.getMusiclist().get(0) : null;
  }

}
