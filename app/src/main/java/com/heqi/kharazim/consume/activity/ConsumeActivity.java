package com.heqi.kharazim.consume.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.VideoView;

import com.android.volley.Response;
import com.heqi.kharazim.KharazimApplication;
import com.heqi.kharazim.R;
import com.heqi.kharazim.config.Intents;
import com.heqi.kharazim.consume.DefaultConsumerFactory;
import com.heqi.kharazim.consume.core.api.ConsumerFactory;
import com.heqi.kharazim.consume.core.api.ConsumerObserver;
import com.heqi.kharazim.consume.core.api.ConsumerWrapper;
import com.heqi.kharazim.consume.core.api.Reason;
import com.heqi.kharazim.consume.core.api.State;
import com.heqi.kharazim.consume.fragment.ConsumerInterpretationFragment;
import com.heqi.kharazim.consume.model.ConsumeCourseRecord;
import com.heqi.kharazim.consume.view.ConsumeFinishView;
import com.heqi.kharazim.consume.view.ConsumeGiveUpView;
import com.heqi.kharazim.consume.view.ConsumerPauseView;
import com.heqi.kharazim.consume.view.ConsumerView;
import com.heqi.kharazim.explore.http.request.CourseDetailWithDailyIdRequest;
import com.heqi.kharazim.explore.model.ActionDetailInfo;
import com.heqi.kharazim.explore.model.CourseDetailInfo;
import com.heqi.kharazim.explore.model.CourseQueryResult;
import com.heqi.rpc.RpcHelper;

/**
 * Created by overspark on 2017/2/11.
 */

public class ConsumeActivity extends FragmentActivity {

  private ConsumerFactory consumerFactory;
  private ConsumerWrapper consumer;
  private Handler uiHandler = new Handler(Looper.getMainLooper());

  private VideoView videoView;
  private ConsumerView consumerView;
  private ConsumerPauseView pauseView;
  private ConsumeGiveUpView giveUpView;
  private ConsumeFinishView finishView;
  private ImageView exitBtn;

  private ConsumerInterpretationFragment interpretationFragment;

  private int requestCourseTag = 0;
  private String dailyId;
  private String userPlanId;

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
    public void onGuideStart(final ActionDetailInfo action, final int actionIndex) {
      uiHandler.post(new Runnable() {
        @Override
        public void run() {
          consumerView.setAction(action);
          consumerView.setProgress(0, consumer.getDuration());
          consumerView.setRepeat(0, consumer.getActionRepeatSum());
          consumerView.setState(State.PLAYING);
          consumerView.showGuideView();
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
          consumerView.showNormalView();
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
          consumerView.setState(State.IDLE);

          uploadUserProgress();
          showFinishView();
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

  private ConsumerView.ExploreConsumerViewListener consumerViewListener =
      new ConsumerView.ExploreConsumerViewListener() {
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
            showPauseView();
          } else {
            consumer.play();
          }
        }

        @Override
        public void onInterpretationPressed() {
          consumer.pause();
          showInterpretation(true);
        }

        @Override
        public void onExitPressed() {
          exit();
        }

        @Override
        public void onSkipGuidePressed() {
          if (consumer != null) {
            consumer.skipGuide();
          }
        }
      };

  private ConsumerPauseView.ConsumerPauseViewListener pauseViewListener =
      new ConsumerPauseView.ConsumerPauseViewListener() {
    @Override
    public void onPlayPressed() {
      consumer.play();
      hidePauseView();
    }

    @Override
    public void onInterpretationPressed() {
      showInterpretation(false);
    }

    @Override
    public void onSetMusicVolume(float volume) {

    }

    @Override
    public void onSetSoundVolume(float volume) {

    }
  };

  private ConsumeGiveUpView.ConsumeGiveUpViewListener giveUpViewListener =
      new ConsumeGiveUpView.ConsumeGiveUpViewListener() {
    @Override
    public void onConfirmPressed() {
      exit();
    }
  };

  private ConsumeFinishView.ConsumeFinishViewListener finishViewListener =
      new ConsumeFinishView.ConsumeFinishViewListener() {
    @Override
    public void onConfirmPressed(int star) {
      uploadStar(star);
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

    consumerView = (ConsumerView) findViewById(R.id.explore_portrait_consumer_view);
    consumerView.setExploreConsumerViewListener(this.consumerViewListener);

    pauseView = (ConsumerPauseView) findViewById(R.id.consumer_pause_view);
    pauseView.setListener(pauseViewListener);
    ((View) pauseView).setVisibility(View.GONE);

    giveUpView = (ConsumeGiveUpView) findViewById(R.id.consumer_give_up_view);
    giveUpView.setListener(giveUpViewListener);
    giveUpView.setVisibility(View.GONE);

    finishView = (ConsumeFinishView) findViewById(R.id.consumer_finish_view);
    finishView.setListener(finishViewListener);
    finishView.setVisibility(View.GONE);

    exitBtn = (ImageView) findViewById(R.id.explore_header_left_button);
    exitBtn.setImageResource(R.drawable.icon_header_navigate_back);
    exitBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        consumer.pause();
        AlertDialog.Builder builder = new AlertDialog.Builder(ConsumeActivity.this);
        builder.setMessage(R.string.consumer_exit_dialog_message)
            .setPositiveButton(R.string.consumer_exit_confirm_text,
                new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {
                showGiveUpView();
              }
            })
            .setNegativeButton(R.string.consumer_exit_cancel_text,
                new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {
                consumer.play();
              }
            })
            .setCancelable(false)
            .create().show();
      }
    });
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

      if (this.consumer.isGuiding()) {
        this.consumerView.showGuideView();
      } else {
        this.consumerView.showNormalView();
      }
    }
  }

  private void handleIntent(Intent intent) {

    if (intent == null) return;

    if (Intents.ACTION_CONSUMER_PLAY.equals(intent.getAction())) {
      this.dailyId = intent.getStringExtra(Intents.EXTRA_COURSE_DETAIL_DAILY_ID);
      this.userPlanId = intent.getStringExtra(Intents.EXTRA_USER_PLAN_ID);

      if (intent.hasExtra(Intents.EXTRA_COURSE_DETAIL_INFO)) {
        CourseDetailInfo courseDetailInfo =
            (CourseDetailInfo) intent.getSerializableExtra(Intents.EXTRA_COURSE_DETAIL_INFO);
        startCourse(courseDetailInfo);
      } else {
        requestCourseDetailInfo(this.dailyId);
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

  private ConsumerView newConsumerView(int orientation) {
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

  private void showPauseView() {
    pauseView.setAction(consumer.getAction());
    pauseView.setVolumes(consumer.getSoundVolume(), consumer.getMusicVolume());
    ((View) pauseView).setVisibility(View.VISIBLE);
  }

  private void hidePauseView() {
    ((View) pauseView).setVisibility(View.GONE);
  }

  private void showInterpretation(final boolean causePause) {
    interpretationFragment = new ConsumerInterpretationFragment();
    interpretationFragment.setListener(
        new ConsumerInterpretationFragment.ConsumerInterpretationFragmentListener() {
      @Override
      public void onExitPressed() {
        hideInterpretation();
        if (causePause) consumer.play();
      }
    });
    interpretationFragment.setData(consumer.getCourse().getActlist(), consumer.getActionIndex());

    getSupportFragmentManager().beginTransaction()
        .replace(R.id.consumer_fragment_container, interpretationFragment)
        .commit();
  }

  private void hideInterpretation() {
    getSupportFragmentManager().beginTransaction()
        .remove(interpretationFragment)
        .commit();
    interpretationFragment.setListener(null);
    interpretationFragment = null;
  }

  private void showGiveUpView() {
    ConsumeCourseRecord record = consumer.getRecord();
    giveUpView.setContent(record.getTotalConsumeTimeInMinute(), record.getAcupointCount());
    giveUpView.setVisibility(View.VISIBLE);
  }

  private void hideGiveUpView() {
    giveUpView.setVisibility(View.GONE);
  }

  private void showFinishView() {
    finishView.setData(consumer.getRecord());
    finishView.setVisibility(View.VISIBLE);
  }

  private void hideFinishView() {
    finishView.setVisibility(View.GONE);
  }

  private void uploadUserProgress() {
    ConsumeCourseRecord record = consumer.getRecord();
    record.setUserplanid(this.userPlanId);
    record.setDailyId(this.dailyId);
    KharazimApplication.getArchives().uploadConsumeProgress(record, null);
  }

  private void uploadStar(int star) {
    KharazimApplication.getArchives().uploadConsumeStar(this.userPlanId, this.dailyId,
        consumer.getCourse().getId(), star, null);
  }
}
