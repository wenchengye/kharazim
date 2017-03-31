package com.heqi.kharazim.consume.core.internal;

import android.net.Uri;

import com.heqi.kharazim.consume.core.internal.api.Timeline;
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
  private List<Timeline.TimelineItem> actionTimelineItems = new ArrayList<>();
  private List<Timeline.TimelineItem> guideActionTimelineItems =
      new ArrayList<>();
  private List<Timeline.TimelineItem> guildSoundTimelineItems =
      new ArrayList<>();
  private List<Timeline> soundTimelines = new ArrayList<>();

  public CourseDetailInfo getCourse() {
    return this.course;
  }

  public void setCourse(CourseDetailInfo course) {
    if (course == null || this.course == course) return;

    this.course = course;
    this.actionTimeline = new DefaultTimeline();
    this.actionTimeline.setBaseTime(DEFAULT_BASE_TIME);
    this.actionTimelineItems.clear();
    this.guideActionTimelineItems.clear();
    this.guildSoundTimelineItems.clear();
    this.soundTimelines.clear();
    int offset = DEFAULT_BASE_TIME;
    for (ActionDetailInfo action : this.course.getActlist()) {

      int actionDuration = action.getCptime() / action.getCpcnt();

      Timeline.TimelineItem actionItem = new Timeline.TimelineItem(
          Uri.parse(action.getActvediofile()),
          offset,
          actionDuration,
          action.getCpcnt(),
          0
      );
      actionTimelineItems.add(actionItem);
      actionTimeline.addItem(actionItem);

      ActionDetailInfo.ActionSoundInfo guideSound = null;
      for (ActionDetailInfo.ActionSoundInfo sound : action.getActSoundDtoList()) {
        if (sound != null
            && sound.getPlaytype() == ActionDetailInfo.ActionSoundInfo.PLAY_TYPE_VALUE_GUIDE) {
          guideSound = sound;
          break;
        }
      }

      if (guideSound != null) {
        Timeline.TimelineItem guideActionItem = new Timeline.TimelineItem(
            Uri.parse(action.getActvediofile()),
            offset,
            actionDuration,
            guideSound.getLen() / actionDuration +
                (guideSound.getLen() % actionDuration != 0 ? 1 : 0),
            0
        );
        this.guideActionTimelineItems.add(guideActionItem);

        Timeline.TimelineItem guideSoundItem = new Timeline.TimelineItem(
            Uri.parse(guideSound.getSoundfile()),
            offset,
            guideSound.getLen(),
            1,
            0
        );
        this.guildSoundTimelineItems.add(guideSoundItem);

      } else {
        this.guideActionTimelineItems.add(null);
        this.guildSoundTimelineItems.add(null);
      }


      Timeline timeline = new DefaultTimeline();
      timeline.setBaseTime(offset);
      for (ActionDetailInfo.ActionSoundInfo sound : action.getActSoundDtoList()) {
        if (sound != null
            && sound.getPlaytype() == ActionDetailInfo.ActionSoundInfo.PLAY_TYPE_VALUE_NORMAL) {
          timeline.addItem(new Timeline.TimelineItem(
              Uri.parse(sound.getSoundfile()),
              sound.getStarttime(),
              sound.getLen(),
              1,
              0
          ));
        }
      }
      soundTimelines.add(timeline);

      offset += actionItem.getTotalDuration();
    }
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

  public Timeline.TimelineItem getGuideActionTimelineItem(int index) {
    return index >= 0 && index < guideActionTimelineItems.size() ?
        guideActionTimelineItems.get(index) : null;
  }

  public Timeline.TimelineItem getGuideSoundTimelineItem(int index) {
    return index >= 0 && index < guildSoundTimelineItems.size() ?
        guildSoundTimelineItems.get(index) : null;
  }

  public Timeline getSoundTimeline(int index) {
    return index >= 0 && index < soundTimelines.size() ? soundTimelines.get(index) : null;
  }

  public CourseDetailInfo.CourseMusicInfo getMusic() {
    return this.course != null && !this.course.getMusiclist().isEmpty()
        ? this.course.getMusiclist().get(0) : null;
  }

}
