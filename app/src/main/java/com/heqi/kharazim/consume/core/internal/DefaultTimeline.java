package com.heqi.kharazim.consume.core.internal;

import com.heqi.kharazim.consume.core.internal.api.Timeline;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * Created by overspark on 2016/12/30.
 */

class DefaultTimeline implements Timeline {

  private TreeSet<TimelineItem> items = new TreeSet<>(new TimelineItemOffsetComparator());
  private int baseTime = 0;

  @Override
  public void addItem(TimelineItem item) {
    items.add(item);
  }

  @Override
  public void addItem(Collection<TimelineItem> items) {
    this.items.addAll(items);
  }

  @Override
  public void setBaseTime(int baseTime) {
    this.baseTime = baseTime;
  }

  @Override
  public int getStartTime() {
    return getFirstItem() != null ? baseTime + getFirstItem().getOffset() : baseTime;
  }

  @Override
  public int getEndTime() {
    return getLastItem() != null
        ? baseTime + getLastItem().getOffset() + getLastItem().getTotalDuration()
        : baseTime;
  }

  @Override
  public int getItemStartTime(TimelineItem item) {
    return item != null ? baseTime + item.getOffset() : baseTime;
  }

  @Override
  public int getItemEndTime(TimelineItem item) {
    return item != null ? baseTime + item.getOffset() + item.getTotalDuration() : baseTime;
  }

  @Override
  public int getDuration() {
    return getEndTime() - getStartTime();
  }

  @Override
  public TimelineItem getHitItem(int time) {
    TimelineItem ret = null;

    if (time >= getStartTime() && time < getEndTime()) {
      Iterator<TimelineItem> iterator = items.iterator();
      while (iterator.hasNext()) {
        TimelineItem item = iterator.next();

        int startTime = getItemStartTime(item);
        int endTime = getItemEndTime(item);
        if (time >= startTime && time < endTime) {
          ret = item;
          break;
        }
      }
    }
    return ret;
  }

  @Override
  public TimelineItem getUpcomingItem(int time) {
    TimelineItem ret = null;
    if (time >= getStartTime() && time < getEndTime()) {
      Iterator<TimelineItem> iterator = items.iterator();
      while (iterator.hasNext()) {
        TimelineItem item = iterator.next();

        int startTime = getItemStartTime(item);
        if (startTime >= time) {
          ret = item;
          break;
        }
      }
    }
    return ret;
  }

  @Override
  public TimelineItem getHitOrUpcomingItem(int time) {
    TimelineItem ret = getHitItem(time);
    return ret == null ? getUpcomingItem(time) : ret;
  }

  @Override
  public boolean isItemHit(TimelineItem item, int time) {
    int startTime = getItemStartTime(item);
    int endTime = getItemEndTime(item);
    return time >= startTime && time < endTime;
  }

  @Override
  public TimelineItem getFirstItem() {
    return items.first();
  }

  @Override
  public TimelineItem getLastItem() {
    return items.last();
  }

  private static class TimelineItemOffsetComparator implements Comparator<TimelineItem> {
    @Override
    public int compare(TimelineItem a, TimelineItem b) {
      return a.getOffset() < b.getOffset() ? -1 : (a.getOffset() == b.getOffset() ? 0 : 1);
    }
  }
}
