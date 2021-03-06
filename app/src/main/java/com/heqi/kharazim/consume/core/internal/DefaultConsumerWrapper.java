package com.heqi.kharazim.consume.core.internal;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

import com.heqi.kharazim.consume.core.api.Consumer;
import com.heqi.kharazim.consume.core.api.ConsumerFactory;
import com.heqi.kharazim.consume.core.api.ConsumerObserver;
import com.heqi.kharazim.consume.core.api.ConsumerWrapper;
import com.heqi.kharazim.consume.core.api.Reason;
import com.heqi.kharazim.consume.core.api.State;
import com.heqi.kharazim.consume.core.internal.api.ConsumeRecorder;
import com.heqi.kharazim.consume.model.ConsumeCourseRecord;
import com.heqi.kharazim.explore.model.ActionDetailInfo;
import com.heqi.kharazim.explore.model.CourseDetailInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by overspark on 2016/12/28.
 */

public class DefaultConsumerWrapper implements ConsumerWrapper {

  private static HandlerThread consumerThread;

  static {
    consumerThread = new HandlerThread("kharazim_consumer_thread");
    consumerThread.start();
  }

  private final Handler consumerHandler = new Handler(consumerThread.getLooper());
  private final List<ConsumerObserver> observers = new ArrayList<ConsumerObserver>();
  private Context context;
  private volatile State state;
  private volatile State targetState;
  private ConsumerFactory consumerFactory;
  private Consumer consumer;
  private ConsumeRecorder recorder;

  private final Consumer.ConsumerCallback consumerCallback = new ConsumerCallback() {
    @Override
    public void onPreparing() {

      checkOnConsumerThread();

      recorder.recordActionStarted(consumer.getAction(), consumer.getActionIndex());
      onPreparingInternal(Reason.INTERNAL_EVENT);
    }

    @Override
    public void onGuideStart() {
      checkOnConsumerThread();

      setState(State.PLAYING);
      final ActionDetailInfo action = consumer.getAction();
      final int actionIndex = consumer.getActionIndex();
      notifyObserver(new NotifyObserverRunnable() {
        @Override
        public void notify(ConsumerObserver observer) {
          observer.onGuideStart(action, actionIndex);
        }
      });
    }

    @Override
    public void onPlayStart() {

      checkOnConsumerThread();

      setState(State.PLAYING);
      final ActionDetailInfo action = consumer.getAction();
      final int actionIndex = consumer.getActionIndex();
      notifyObserver(new NotifyObserverRunnable() {
        @Override
        public void notify(ConsumerObserver observer) {
          observer.onPlayStart(action, actionIndex);
        }
      });
    }

    @Override
    public void onPlayEnd() {

      checkOnConsumerThread();

      recorder.recordActionEnded(consumer.getAction());
    }

    @Override
    public void onPlayOver() {

      checkOnConsumerThread();

      setState(State.IDLE);
      final ActionDetailInfo action = consumer.getAction();
      final int actionIndex = consumer.getActionIndex();
      notifyObserver(new NotifyObserverRunnable() {
        @Override
        public void notify(ConsumerObserver observer) {
          observer.onPlayOver(action, actionIndex);
        }
      });
    }

    @Override
    public void onError(String msg) {

      checkOnConsumerThread();

      setState(State.IDLE);
      final ActionDetailInfo action = consumer.getAction();
      final int actionIndex = consumer.getActionIndex();
      notifyObserver(new NotifyObserverRunnable() {
        @Override
        public void notify(ConsumerObserver observer) {
          observer.onStop(action, actionIndex, Reason.ERROR);
        }
      });

    }

    @Override
    public void onProgress(final int milliseconds, final int duration) {

      checkOnConsumerThread();

      if (!isGuiding()) {
        recorder.recordActionProgressed(consumer.getAction(), milliseconds);
      }
      notifyObserver(new NotifyObserverRunnable() {
        @Override
        public void notify(ConsumerObserver observer) {
          observer.onProgress(millisecond2Second(milliseconds), millisecond2Second(duration));
        }
      });
    }

    @Override
    public void onActionRepeat(final int repeatIndex, final int sum) {
      checkOnConsumerThread();

      notifyObserver(new NotifyObserverRunnable() {
        @Override
        public void notify(ConsumerObserver observer) {
          observer.onActionRepeat(repeatIndex, sum);
        }
      });
    }
  };

  public DefaultConsumerWrapper(Context context, ConsumerFactory consumerFactory) {
    this.context = context;
    this.consumerFactory = consumerFactory;
    this.recorder = new DefaultConsumeRecorder();
    setState(State.OFF);

    consumerHandler.post(new Runnable() {
      @Override
      public void run() {
        init();
      }
    });
  }

  private static int millisecond2Second(int milliseconds) {
    return milliseconds / 1000;
  }

  private void init() {

    checkOnConsumerThread();

    if (getState() != State.OFF) return;

    if (consumerFactory == null) {
      throw new RuntimeException(
          "ConsumerFactory pass in DefaultConsumerWrapper() method should not be null");
    }

    consumer = consumerFactory.buildConsumer();
    consumer.setConsumerCallback(consumerCallback);
    setState(State.IDLE);

    notifyObserver(new NotifyObserverRunnable() {
      @Override
      public void notify(ConsumerObserver observer) {
        observer.onInit();
      }
    });
  }

  private void checkOnConsumerThread() {
    if (Looper.myLooper() != consumerThread.getLooper()) {
      throw new RuntimeException("This method should run on consumer thread");
    }
  }

  @Override
  public State getState() {
    return this.state;
  }

  private void setState(State state) {
    this.state = state;
  }

  @Override
  public void addConsumerObserver(ConsumerObserver observer) {

    if (observer == null) return;

    synchronized (observers) {
      for (ConsumerObserver item : observers) {
        if (observer == item
            || (observer.getKey() != null && observer.getKey().equals(item.getKey()))) {
          return;
        }
      }
      observers.add(observer);
    }

  }

  @Override
  public void removeConsumerObserver(ConsumerObserver observer) {

    if (observer == null) return;

    synchronized (observers) {
      for (ConsumerObserver item : observers) {
        if (observer == item
            || (observer.getKey() != null && observer.getKey().equals(item.getKey()))) {
          observers.remove(item);
          return;
        }
      }
    }
  }

  @Override
  public ConsumeCourseRecord getRecord() {
    return recorder.getRecord();
  }

  @Override
  public void release() {
    consumerHandler.post(new Runnable() {
      @Override
      public void run() {
        releaseInternal();
      }
    });
  }

  @Override
  public void play() {
    consumerHandler.post(new Runnable() {
      @Override
      public void run() {
        playInternal(Reason.CLIENT_EVENT);
      }
    });
  }

  @Override
  public void pause() {
    consumerHandler.post(new Runnable() {
      @Override
      public void run() {
        pauseInternal(Reason.CLIENT_EVENT);
      }
    });
  }

  @Override
  public void resume() {
    consumerHandler.post(new Runnable() {
      @Override
      public void run() {
        playInternal(Reason.CLIENT_EVENT);
      }
    });
  }

  @Override
  public void stop() {
    consumerHandler.post(new Runnable() {
      @Override
      public void run() {
        stopInternal(Reason.CLIENT_EVENT);
      }
    });

  }

  @Override
  public void forward() {
    consumerHandler.post(new Runnable() {
      @Override
      public void run() {
        forwardInternal();
      }
    });
  }

  @Override
  public void backward() {
    consumerHandler.post(new Runnable() {
      @Override
      public void run() {
        backwardInternal();
      }
    });
  }

  @Override
  public void skipGuide() {
    consumerHandler.post(new Runnable() {
      @Override
      public void run() {
        skipGuideInternal();
      }
    });
  }

  @Override
  public void seek(final int second) {
    consumerHandler.post(new Runnable() {
      @Override
      public void run() {
        seekToInternal(second);
      }
    });
  }

  @Override
  public void jump2Action(final int index) {
    consumerHandler.post(new Runnable() {
      @Override
      public void run() {
        jump2ActionInternal(index);
      }
    });
  }

  @Override
  public CourseDetailInfo getCourse() {
    return getState() != State.OFF ? consumer.getCourse() : null;
  }

  @Override
  public void setCourse(final CourseDetailInfo course) {
    consumerHandler.post(new Runnable() {
      @Override
      public void run() {
        setCourseInternal(course);
      }
    });
  }

  @Override
  public ActionDetailInfo getAction() {
    return getState() != State.OFF ? consumer.getAction() : null;
  }

  @Override
  public int getActionIndex() {
    return getState() != State.OFF ? consumer.getActionIndex() : 0;
  }

  @Override
  public int getProgress() {
    return getState() != State.OFF ? millisecond2Second(consumer.getProgress()) : 0;
  }

  @Override
  public int getDuration() {
    return getState() != State.OFF ? millisecond2Second(consumer.getDuration()) : 0;
  }

  @Override
  public int getActionRepeatIndex() {
    return getState() != State.OFF ? consumer.getActionRepeatIndex() : 0;
  }

  @Override
  public int getActionRepeatSum() {
    return getState() != State.OFF ? consumer.getActionRepeatSum() : 0;
  }

  @Override
  public float getMusicVolume() {
    return getState() != State.OFF ? consumer.getMusicVolume() : 0.f;
  }

  @Override
  public void setMusicVolume(final float volume) {
    consumerHandler.post(new Runnable() {
      @Override
      public void run() {
        setMusicVolumeInternal(volume);
      }
    });
  }

  @Override
  public float getSoundVolume() {
    return getState() != State.OFF ? consumer.getSoundVolume() : 0.f;
  }

  @Override
  public void setSoundVolume(final float volume) {
    consumerHandler.post(new Runnable() {
      @Override
      public void run() {
        setSoundVolumeInternal(volume);
      }
    });
  }

  @Override
  public boolean canForward() {
    return getState() != State.OFF && consumer.canForward();
  }

  @Override
  public boolean canBackward() {
    return getState() != State.OFF && consumer.canForward();
  }

  @Override
  public boolean canPlay() {
    return getState() != State.OFF && consumer.canPlay();
  }

  @Override
  public boolean canJump2Action(int index) {
    return getState() != State.OFF && consumer.canJump2Action(index);
  }

  @Override
  public boolean isGuiding() {
    return getState() != State.OFF && consumer.isGuiding();
  }

  @Override
  final public void setConsumerCallback(ConsumerCallback callback) {
  }

  private void releaseInternal() {

    checkOnConsumerThread();

    if (getState() == State.OFF) return;

    consumer.setConsumerCallback(null);
    consumer.release();

    setState(State.OFF);
    notifyObserver(new NotifyObserverRunnable() {
      @Override
      public void notify(ConsumerObserver observer) {
        observer.onRelease();
      }
    });
  }

  private void playInternal(final Reason reason) {

    checkOnConsumerThread();

    if (getState() != State.PAUSE && getState() != State.IDLE) {
      return;
    }

    if (getState() == State.PAUSE) {

      setState(State.PLAYING);
      final ActionDetailInfo action = consumer.getAction();
      final int actionIndex = consumer.getActionIndex();
      notifyObserver(new NotifyObserverRunnable() {
        @Override
        public void notify(ConsumerObserver observer) {
          observer.onResume(action, actionIndex, reason);
        }
      });

      consumer.resume();
    } else if (getState() == State.IDLE) {
      if (consumer.canPlay()) {
        onPreparingInternal(reason);
        consumer.play();
      }
    }
  }

  private void pauseInternal(final Reason reason) {

    checkOnConsumerThread();

    if (getState() != State.PLAYING && getState() != State.PREPARING) return;

    if (getState() == State.PLAYING) {
      setState(State.PAUSE);
      final ActionDetailInfo action = consumer.getAction();
      final int actionIndex = consumer.getActionIndex();
      notifyObserver(new NotifyObserverRunnable() {
        @Override
        public void notify(ConsumerObserver observer) {
          observer.onPause(action, actionIndex, reason);
        }
      });

      consumer.pause();
    } else if (getState() == State.PREPARING) {
      targetState = State.PAUSE;
    }
  }

  private void stopInternal(final Reason reason) {

    checkOnConsumerThread();

    if (getState() != State.PLAYING && getState() != State.PREPARING && getState() != State.PAUSE) {
      return;
    }

    setState(State.IDLE);
    final ActionDetailInfo action = consumer.getAction();
    final int actionIndex = consumer.getActionIndex();
    notifyObserver(new NotifyObserverRunnable() {
      @Override
      public void notify(ConsumerObserver observer) {
        observer.onStop(action, actionIndex, reason);
      }
    });

    consumer.stop();
  }

  private void forwardInternal() {

    checkOnConsumerThread();

    if (getState() == State.OFF || !consumer.canForward()) return;

    recorder.recordActionSkipped(consumer.getAction());
    consumer.forward();
    onPreparingInternal(Reason.CLIENT_EVENT);
  }

  private void backwardInternal() {

    checkOnConsumerThread();

    if (getState() == State.OFF || !consumer.canBackward()) return;

    recorder.recordActionSkipped(consumer.getAction());
    consumer.backward();
    onPreparingInternal(Reason.CLIENT_EVENT);
  }

  private void skipGuideInternal() {
    checkOnConsumerThread();

    if (getState() != State.PLAYING && getState() != State.PAUSE) return;

    consumer.skipGuide();
  }

  private void seekToInternal(int second) {

    checkOnConsumerThread();

    if (getState() != State.PLAYING || getState() != State.PAUSE) return;

    consumer.seek(second);
  }

  private void jump2ActionInternal(int index) {

    checkOnConsumerThread();

    if (getState() == State.OFF || !consumer.canJump2Action(index)) return;

    consumer.jump2Action(index);
    onPreparingInternal(Reason.CLIENT_EVENT);
  }

  private void setCourseInternal(CourseDetailInfo course) {

    checkOnConsumerThread();

    if (getState() == State.OFF) return;

    consumer.setCourse(course);
    recorder.reset(course);
    onPreparingInternal(Reason.CLIENT_EVENT);
  }

  private void setSoundVolumeInternal(float volume) {

    checkOnConsumerThread();

    if (getState() == State.OFF) return;

    consumer.setSoundVolume(volume);
  }

  private void setMusicVolumeInternal(float volume) {

    checkOnConsumerThread();

    if (getState() == State.OFF) return;

    consumer.setMusicVolume(volume);
  }

  private void onPreparingInternal(final Reason reason) {
    setState(State.PREPARING);
    final ActionDetailInfo action = consumer.getAction();
    final int actionIndex = consumer.getActionIndex();
    notifyObserver(new NotifyObserverRunnable() {
      @Override
      public void notify(ConsumerObserver observer) {
        observer.onPreparing(action, actionIndex, reason);
      }
    });
  }

  private void notifyObserver(NotifyObserverRunnable runnable) {

    if (runnable == null) return;

    synchronized (observers) {
      for (ConsumerObserver item : observers) {
        if (item != null) runnable.notify(item);
      }
    }
  }

  private interface NotifyObserverRunnable {
    void notify(ConsumerObserver observer);
  }
}
