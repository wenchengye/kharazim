package com.heqi.kharazim.explore.consume.internal;

import android.net.Uri;
import android.os.Looper;

import com.heqi.kharazim.explore.consume.api.Consumer;
import com.heqi.kharazim.explore.consume.internal.api.ConsumerFactory;
import com.heqi.kharazim.explore.consume.internal.api.ConsumerInternal;
import com.heqi.kharazim.explore.consume.internal.api.ConsumerInternalState;
import com.heqi.kharazim.explore.consume.internal.api.ConsumerInternalStateRepeat;
import com.heqi.kharazim.explore.consume.internal.api.InternalState;
import com.heqi.kharazim.explore.consume.internal.api.Timeline;
import com.heqi.kharazim.explore.consume.internal.api.Timer;
import com.heqi.kharazim.explore.model.ActionDetailInfo;
import com.heqi.kharazim.explore.model.CourseDetailInfo;

/**
 * Created by overspark on 2016/12/29.
 */

public class DefaultConsumer implements Consumer {

  private static final int MUSIC_REPEAT_FOREVER = 99;

  private ConsumerFactory factory;
  private CourseManager courseManager;
  private Timer timer;
  private ConsumerInternalStateRepeat actionConsumer;
  private ConsumerInternalState soundConsumer;
  private ConsumerInternalStateRepeat musicConsumer;
  private ConsumerCallback callback;

  private int actionIndex;
  private int repeatIndex;
  private int progress;
  private int time;
  private Timeline.TimelineItem currentSound;

  private Timer.TimerRunner progressSync = new Timer.TimerRunner() {
    @Override
    public void tickSecond() {

      updateProgress();

      updateConsumers();
    }
  };

  private ConsumerInternalStateRepeat.ConsumerInternalRepeatCallback actionRepeatCallback =
      new ConsumerInternalStateRepeat.ConsumerInternalRepeatCallback() {
        @Override
        public void onRepeat(int repeatIndex, int repeatSum) {
          if (callback != null) {
            callback.onActionRepeat(repeatIndex, repeatSum);
          }
        }
      };

  private ConsumerInternal.ConsumerInternalCallback actionCallback =
      new ConsumerInternal.ConsumerInternalCallback() {
        @Override
        public void onPrepared() {
          actionConsumer.start();
          if (callback != null) {
            callback.onPlayStart();
          }
        }

        @Override
        public void onError(String msg) {
          stop();
          if (callback != null) {
            callback.onError(msg);
          }
        }

        @Override
        public void onPlayerOver() {
          onActionOver();
        }
      };


  public DefaultConsumer(ConsumerFactory factory) {
    this.factory = factory;
    this.courseManager = new CourseManager();
    this.timer = new DefaultTimer(Looper.myLooper());
    this.actionConsumer =
        new ConsumerInternalStateRepeatWrapper(
            new ConsumerInternalStateWrapper(this.factory.buildActionConsumerInternal()));
    this.soundConsumer =
        new ConsumerInternalStateWrapper(this.factory.buildSoundConsumerInternal());
    this.musicConsumer =
        new ConsumerInternalStateRepeatWrapper(
            new ConsumerInternalStateWrapper(this.factory.buildMusicConsumerInternal()));
    callback = null;
    actionIndex = progress = repeatIndex = time = 0;

    this.timer.addRunner(this.progressSync);
    actionConsumer.setRepeatCallback(actionRepeatCallback);
    actionConsumer.setCallback(actionCallback);
  }

  @Override
  public void release() {
    stop();

    actionConsumer.setCallback(null);
    actionConsumer.release();
    soundConsumer.release();
    musicConsumer.release();
    actionConsumer = null;
    soundConsumer = null;
    musicConsumer = null;
  }

  @Override
  public void play() {
    if (!canPlay()) return;

    this.timer.start();
  }

  @Override
  public void pause() {
    this.timer.stop();

    if (isActionConsumerFunctional()) actionConsumer.pause();
    if (isSoundConsumerFunctional()) soundConsumer.pause();
    if (isMusicConsumerFunctional()) musicConsumer.pause();
  }

  @Override
  public void resume() {
    this.timer.start();
  }

  @Override
  public void stop() {
    this.timer.stop();

    if (isActionConsumerFunctional()) actionConsumer.stop();
    if (isSoundConsumerFunctional()) soundConsumer.stop();
    if (isMusicConsumerFunctional()) musicConsumer.stop();
  }

  @Override
  public void forward() {
    if (!canForward()) return;

    gotoActionIndex(actionIndex + 1);
    this.timer.start();
  }

  @Override
  public void backward() {
    if (!canBackward()) return;

    gotoActionIndex(actionIndex - 1);
    this.timer.start();
  }

  @Override
  public void seek(int second) {

  }

  @Override
  public void jump2Action(int index) {
    if (!canJump2Action(index)) return;

    gotoActionIndex(index);
    this.timer.start();
  }

  @Override
  public CourseDetailInfo getCourse() {
    return courseManager.getCourse();
  }

  @Override
  public void setCourse(CourseDetailInfo course) {
    if (course == null) return;
    courseManager.setCourse(course);

    resetMusic();
    gotoActionIndex(0);

    play();
  }

  @Override
  public ActionDetailInfo getAction() {
    return courseManager.getAction(actionIndex);
  }

  @Override
  public int getActionIndex() {
    return this.actionIndex;
  }

  @Override
  public int getProgress() {
    return progress;
  }

  @Override
  public int getDuration() {
    Timeline.TimelineItem actionTimelineItem = courseManager.getActionTimelineItem(actionIndex);
    return actionTimelineItem != null ? actionTimelineItem.getTotalDuration() : 0;
  }

  @Override
  public int getActionRepeatIndex() {
    return repeatIndex;
  }

  @Override
  public int getActionRepeatSum() {
    Timeline.TimelineItem actionTimelineItem = courseManager.getActionTimelineItem(actionIndex);
    return actionTimelineItem != null ? actionTimelineItem.getRepeat() : 0;
  }

  @Override
  public boolean canForward() {
    int next = actionIndex + 1;
    return next >= 0 && next < courseManager.getActionCount();
  }

  @Override
  public boolean canBackward() {
    int previous = actionIndex - 1;
    return previous >= 0 && previous < courseManager.getActionCount();
  }

  @Override
  public boolean canPlay() {
    return courseManager.getActionTimelineItem(actionIndex) != null;
  }

  @Override
  public boolean canJump2Action(int index) {
    return courseManager.getActionTimelineItem(index) != null;
  }

  @Override
  public void setConsumerCallback(ConsumerCallback callback) {
    this.callback = callback;
  }

  private void gotoActionIndex(int index) {
    actionIndex = index;
    progress = 0;
    repeatIndex = 0;

    Timeline.TimelineItem actionItem = courseManager.getActionTimelineItem(actionIndex);
    if (actionItem != null) {
      actionConsumer.setSource(actionItem.getSource(),
          actionItem.getRepeat(),
          actionItem.getMargin());
      time = courseManager.getActionTimeline().getItemStartTime(actionItem);
    } else {
      actionConsumer.setSource(null);
      time = 0;
    }

    Timeline soundTimeline = courseManager.getSoundTimeline(actionIndex);
    if (soundTimeline != null) {
      currentSound = soundTimeline.getHitOrUpcomingItem(time);
    } else {
      currentSound = null;
    }

    if (currentSound != null) {
      soundConsumer.setSource(currentSound.getSource());
    } else {
      soundConsumer.setSource(null);
    }
  }

  private void resetMusic() {
    if (courseManager.getMusic() != null) {
      musicConsumer.setSource(
          Uri.parse(courseManager.getMusic().getMusicfile()),
          MUSIC_REPEAT_FOREVER, 0);
    } else {
      musicConsumer.setSource(null);
    }
  }

  private boolean isActionConsumerFunctional() {
    return courseManager.getActionTimelineItem(actionIndex) != null;
  }

  private boolean isSoundConsumerFunctional() {
    return currentSound != null;
  }

  private boolean isMusicConsumerFunctional() {
    return courseManager.getMusic() != null;
  }

  private void updateProgress() {

    do {

      if (!isActionConsumerFunctional()) {
        time = progress = 0;
        break;
      }

      switch (actionConsumer.getInternalState()) {
        case IDLE:
        case Preparing:
        case Prepared:
          progress = 0;
          break;
        case Playing:
        case Paused:
          progress = actionConsumer.getProgress();
          break;
        case PlayOver:
          progress = courseManager.getActionTimelineItem(actionIndex).getDuration();
          break;
        default:
          break;
      }
      time = courseManager.getActionTimeline().getItemStartTime(
          courseManager.getActionTimelineItem(actionIndex)) + progress;

    } while (false);

    if (this.callback != null && actionConsumer.getInternalState() == InternalState.Playing) {
      this.callback.onProgress(progress,
          courseManager.getActionTimelineItem(actionIndex).getDuration());
    }

  }

  private void updateConsumers() {

    if (isMusicConsumerFunctional()) {
      switch (musicConsumer.getInternalState()) {
        case IDLE:
          musicConsumer.prepare();
          break;
        case Prepared:
        case Paused:
          musicConsumer.start();
          break;
        default:
          break;
      }
    }

    Timeline.TimelineItem upcomingSound = courseManager.getSoundTimeline(actionIndex) == null
        ? null : courseManager.getSoundTimeline(actionIndex).getHitOrUpcomingItem(time);

    if (upcomingSound != currentSound) {
      currentSound = upcomingSound;
      if (currentSound != null) {
        soundConsumer.setSource(currentSound.getSource());
      } else {
        soundConsumer.setSource(null);
      }
    }

    if (isSoundConsumerFunctional()) {
      switch (soundConsumer.getInternalState()) {
        case IDLE:
          soundConsumer.prepare();
          break;
        case Prepared:
          if (courseManager.getSoundTimeline(actionIndex).isItemHit(currentSound, time)) {
            soundConsumer.start();
          }
          break;
        case Paused:
          soundConsumer.start();
          break;
      }
    }

    if (isActionConsumerFunctional()) {
      switch (actionConsumer.getInternalState()) {
        case IDLE:
          actionConsumer.prepare();
          if (callback != null) {
            callback.onPreparing();
          }
          break;
        case Paused:
          actionConsumer.start();
          break;
      }
    } else {
      onActionOver();
    }
  }

  private void onActionOver() {
    if (canForward()) {
      forward();
    } else {
      stop();
      if (callback != null) {
        callback.onPlayOver();
      }
    }
  }
}
