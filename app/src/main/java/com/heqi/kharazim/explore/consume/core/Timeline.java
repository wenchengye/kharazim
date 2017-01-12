package com.heqi.kharazim.explore.consume.core;

import android.net.Uri;

import java.util.Collection;

/**
 * Created by overspark on 2016/12/30.
 */

interface Timeline {

  void addItem(TimelineItem item);

  void addItem(Collection<TimelineItem> items);

  void setBaseTime(int baseTime);

  int getStartTime();

  int getEndTime();

  int getDuration();

  int getItemStartTime(TimelineItem item);

  int getItemEndTime(TimelineItem item);

  TimelineItem getHitItem(int time);

  TimelineItem getUpcomingItem(int time);

  TimelineItem getFirstItem();

  TimelineItem getLastItem();

  public class TimelineItem {

    private Uri source;

    private int offset;

    private int duration;

    public TimelineItem(Uri source, int offset, int duration) {
      this.source = source;
      this.offset = offset;
      this.duration = duration;
    }

    public Uri getSource() {
      return source;
    }

    public int getOffset() {
      return offset;
    }

    public int getDuration() {
      return duration;
    }
  }
}
