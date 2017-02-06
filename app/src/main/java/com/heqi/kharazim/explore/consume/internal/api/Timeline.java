package com.heqi.kharazim.explore.consume.internal.api;

import android.net.Uri;

import java.util.Collection;

/**
 * Created by overspark on 2016/12/30.
 */

public interface Timeline {

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

  TimelineItem getHitOrUpcomingItem(int time);

  boolean isItemHit(TimelineItem item, int time);

  TimelineItem getFirstItem();

  TimelineItem getLastItem();

  public class TimelineItem {

    private Uri source;

    private int offset;

    private int duration;

    private int repeat;

    private int margin;

    public TimelineItem(Uri source, int offset, int duration, int repeat, int margin) {
      this.source = source;
      this.offset = offset;
      this.duration = duration;
      this.repeat = repeat;
      this.margin = margin;
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

    public int getRepeat() {
      return repeat;
    }

    public int getTotalDuration() {
      return duration * repeat + margin;
    }

    public int getMargin() {
      return margin;
    }
  }
}
