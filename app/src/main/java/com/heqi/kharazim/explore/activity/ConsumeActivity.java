package com.heqi.kharazim.explore.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.widget.VideoView;

import com.android.volley.Response;
import com.heqi.kharazim.KharazimApplication;
import com.heqi.kharazim.R;
import com.heqi.kharazim.config.Intents;
import com.heqi.kharazim.explore.consume.DefaultConsumerFactory;
import com.heqi.kharazim.explore.consume.api.ConsumerObserver;
import com.heqi.kharazim.explore.consume.api.ConsumerWrapper;
import com.heqi.kharazim.explore.consume.api.Reason;
import com.heqi.kharazim.explore.consume.api.State;
import com.heqi.kharazim.explore.consume.internal.api.ConsumerFactory;
import com.heqi.kharazim.explore.http.request.CourseDetailWithDailyIdRequest;
import com.heqi.kharazim.explore.model.ActionDetailInfo;
import com.heqi.kharazim.explore.model.CourseDetailInfo;
import com.heqi.kharazim.explore.model.CourseQueryResult;
import com.heqi.kharazim.explore.view.ExploreConsumerView;
import com.heqi.rpc.RpcHelper;

/**
 * Created by overspark on 2017/2/11.
 */

public class ConsumeActivity extends FragmentActivity {

  private ConsumerFactory consumerFactory;
  private ConsumerWrapper consumer;
  private Handler uiHandler = new Handler(Looper.getMainLooper());

  private VideoView videoView;
  private ExploreConsumerView consumerView;

  private int requestCourseTag = 0;

  private ConsumerObserver consumerObserver = new ConsumerObserver() {

    private static final String KEY = "com.heqi.kharazim.ConsumerActivity";

    @Override
    public void onInit() {
      uiHandler.post(new Runnable() {
        @Override
        public void run() {
          syncView2Consumer();
        }
      });
    }

    @Override
    public void onRelease() {

    }

    @Override
    public void onPreparing(final ActionDetailInfo action, final int actionIndex, Reason reason) {
      uiHandler.post(new Runnable() {
        @Override
        public void run() {
          consumerView.setAction(action);
          consumerView.setProgress(0, consumer.getDuration());
          consumerView.setRepeat(0, consumer.getActionRepeatSum());
          consumerView.setState(State.PREPARING);
        }
      });
    }

    @Override
    public void onPlayStart(final ActionDetailInfo action, int actionIndex) {
      uiHandler.post(new Runnable() {
        @Override
        public void run() {
          consumerView.setAction(action);
          consumerView.setProgress(0, consumer.getDuration());
          consumerView.setRepeat(0, consumer.getActionRepeatSum());
          consumerView.setState(State.PLAYING);
        }
      });
    }

    @Override
    public void onPlayOver(final ActionDetailInfo action, int actionIndex) {
      uiHandler.post(new Runnable() {
        @Override
        public void run() {
          consumerView.setAction(action);
          consumerView.setProgress(consumer.getDuration(), consumer.getDuration());
          consumerView.setRepeat(consumer.getActionRepeatSum(), consumer.getActionRepeatSum());
          consumerView.setState(State.PLAYING);
        }
      });
    }

    @Override
    public void onPause(final ActionDetailInfo action, int actionIndex, Reason reason) {
      uiHandler.post(new Runnable() {
        @Override
        public void run() {
          consumerView.setAction(action);
          consumerView.setProgress(consumer.getProgress(), consumer.getDuration());
          consumerView.setRepeat(consumer.getActionRepeatIndex(), consumer.getActionRepeatSum());
          consumerView.setState(State.PAUSE);
        }
      });
    }

    @Override
    public void onResume(final ActionDetailInfo action, int actionIndex, Reason reason) {
      uiHandler.post(new Runnable() {
        @Override
        public void run() {
          consumerView.setAction(action);
          consumerView.setProgress(consumer.getProgress(), consumer.getDuration());
          consumerView.setRepeat(consumer.getActionRepeatIndex(), consumer.getActionRepeatSum());
          consumerView.setState(State.PLAYING);
        }
      });
    }

    @Override
    public void onStop(final ActionDetailInfo action, int actionIndex, Reason reason) {
      uiHandler.post(new Runnable() {
        @Override
        public void run() {
          consumerView.setAction(action);
          consumerView.setProgress(0, consumer.getDuration());
          consumerView.setRepeat(0, consumer.getActionRepeatSum());
          consumerView.setState(State.IDLE);
        }
      });
    }

    @Override
    public void onProgress(final int second, final int duration) {
      uiHandler.post(new Runnable() {
        @Override
        public void run() {
          consumerView.setProgress(second, duration);
        }
      });
    }

    @Override
    public void onActionRepeat(final int repeatIndex, final int sum) {
      uiHandler.post(new Runnable() {
        @Override
        public void run() {
          consumerView.setRepeat(repeatIndex, sum);
        }
      });
    }

    @Override
    public void onCourseChanged(CourseDetailInfo course) {

    }

    @Override
    public String getKey() {
      return KEY;
    }
  };

  private ExploreConsumerView.ExploreConsumerViewListener consumerViewListener =
      new ExploreConsumerView.ExploreConsumerViewListener() {
        @Override
        public void onPreviousPressed() {
          if (consumer != null) {
            consumer.backward();
          }
        }

        @Override
        public void onNextPressed() {
          if (consumer != null) {
            consumer.forward();
          }
        }

        @Override
        public void onPlayPressed() {
          if (consumer.getState() == State.PLAYING) {
            consumer.pause();
          } else {
            consumer.play();
          }
        }

        @Override
        public void onInterpretationPressed() {

        }

        @Override
        public void onExitPressed() {
          exit();
        }
      };

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.explore_consumer_activity_portrait_layout);

    initViews();
    initConsumer();
    syncView2Consumer();

    handleIntent(getIntent());
  }

  @Override
  protected void onNewIntent(Intent intent) {
    super.onNewIntent(intent);

    handleIntent(intent);
  }

  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
  }

  private void initViews() {
    videoView = (VideoView) findViewById(R.id.explore_consumer_video_view);
    consumerView = (ExploreConsumerView) findViewById(R.id.explore_portrait_consumer_view);
    consumerView.setExploreConsumerViewListener(this.consumerViewListener);
  }

  private void initConsumer() {
    this.consumerFactory = new DefaultConsumerFactory(this, this.videoView);
    this.consumer = this.consumerFactory.buildConsumerWrapper();
    this.consumer.addConsumerObserver(consumerObserver);

  }

  private void syncView2Consumer() {
    if (this.consumer.getState() != State.OFF) {
      this.consumerView.initContent(this.consumer.getAction(), this.consumer.getProgress(),
          this.consumer.getDuration(), this.consumer.getActionIndex(),
          this.consumer.getActionRepeatSum(), this.consumer.getState());
    }
  }

  private void handleIntent(Intent intent) {

    if (intent == null) return;

    if (Intents.ACTION_CONSUMER_PLAY.equals(intent.getAction())
        && intent.hasExtra(Intents.EXTRA_COURSE_DETAIL_TYPE)) {
      int detailType = intent.getIntExtra(Intents.EXTRA_COURSE_DETAIL_TYPE,
          Intents.EXTRA_VALUE_COURSE_DETAIL_TYPE_DAILY_ID);

      if (detailType == Intents.EXTRA_VALUE_COURSE_DETAIL_TYPE_DAILY_ID
          && intent.hasExtra(Intents.EXTRA_COURSE_DETAIL_DAILY_ID)) {
        String dailyId = intent.getStringExtra(Intents.EXTRA_COURSE_DETAIL_DAILY_ID);
        requestCourseDetailInfo(dailyId);
      } else if (detailType == Intents.EXTRA_VALUE_COURSE_DETAIL_TYPE_INFO
          && intent.hasExtra(Intents.EXTRA_COURSE_DETAIL_INFO)) {
        CourseDetailInfo courseDetailInfo =
            (CourseDetailInfo) intent.getSerializableExtra(Intents.EXTRA_COURSE_DETAIL_INFO);
        startCourse(courseDetailInfo);
      }
    }
  }

  private void requestCourseDetailInfo(String dailyId) {
    final int tagRef = ++requestCourseTag;
    Response.Listener<CourseQueryResult> resultListener =
        new Response.Listener<CourseQueryResult>() {
      @Override
      public void onResponse(CourseQueryResult response) {
        if (!validateRequestTag(tagRef)) return;

        if (response != null) startCourse(response.getData_info());
      }
    };

    CourseDetailWithDailyIdRequest request =
        new CourseDetailWithDailyIdRequest(resultListener, null);
    request.setDailyId(dailyId);
    RpcHelper.getInstance(KharazimApplication.getAppContext()).executeRequestAsync(request);
  }

  private boolean validateRequestTag(int tag) {
    return tag == this.requestCourseTag;
  }

  private void startCourse(CourseDetailInfo info) {
    if (info == null || this.consumer == null) return;

    this.consumer.setCourse(info);
  }

  private void buildConsumerView(int orientation) {
    if (this.consumerView != null) {
      this.consumerView.setExploreConsumerViewListener(null);
    }

    this.consumerView = newConsumerView(orientation);
    if (this.consumerView != null) {
      this.consumerView.setExploreConsumerViewListener(this.consumerViewListener);
    }
  }

  private ExploreConsumerView newConsumerView(int orientation) {
    return null;
  }

  private void exit() {
    if (this.consumerView != null) {
      this.consumerView.setExploreConsumerViewListener(null);
      this.consumerView = null;
    }

    if (this.consumer != null) {
      this.consumer.setConsumerCallback(null);
      this.consumer.release();
      this.consumer = null;
    }
    this.consumerFactory = null;
    finish();
  }

  public static void launchActivity(Context context, String dailyId) {
    Intent intent = new Intent(context, ConsumeActivity.class);
    intent.setAction(Intents.ACTION_CONSUMER_PLAY);
    intent.putExtra(Intents.EXTRA_COURSE_DETAIL_TYPE,
        Intents.EXTRA_VALUE_COURSE_DETAIL_TYPE_DAILY_ID);
    intent.putExtra(Intents.EXTRA_COURSE_DETAIL_DAILY_ID, dailyId);
    context.startActivity(intent);
  }
}
