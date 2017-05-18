package com.heqi.kharazim.consume.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.VideoView;

import com.android.volley.Response;
import com.heqi.base.utils.SystemUtil;
import com.heqi.kharazim.KharazimApplication;
import com.heqi.kharazim.R;
import com.heqi.kharazim.config.Const;
import com.heqi.kharazim.config.Intents;
import com.heqi.kharazim.consume.DefaultConsumerFactory;
import com.heqi.kharazim.consume.core.api.ConsumerFactory;
import com.heqi.kharazim.consume.core.api.ConsumerObserver;
import com.heqi.kharazim.consume.core.api.ConsumerWrapper;
import com.heqi.kharazim.consume.core.api.Reason;
import com.heqi.kharazim.consume.core.api.State;
import com.heqi.kharazim.consume.fragment.ConsumerFinishFragment;
import com.heqi.kharazim.consume.fragment.ConsumerGiveUpFragment;
import com.heqi.kharazim.consume.fragment.ConsumerInterpretationFragment;
import com.heqi.kharazim.consume.model.ConsumeCourseRecord;
import com.heqi.kharazim.consume.view.ConsumerPauseView;
import com.heqi.kharazim.consume.view.ConsumerView;
import com.heqi.kharazim.explore.http.request.CourseDetailWithDailyIdRequest;
import com.heqi.kharazim.explore.model.ActionDetailInfo;
import com.heqi.kharazim.explore.model.CourseDetailInfo;
import com.heqi.kharazim.explore.model.CourseQueryResult;
import com.heqi.kharazim.explore.model.PlanDetailInfo;
import com.heqi.kharazim.utils.ViewUtils;
import com.heqi.rpc.RpcHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by overspark on 2017/2/11.
 */

public class ConsumeActivity extends FragmentActivity {

  private ConsumerFactory consumerFactory;
  private ConsumerWrapper consumer;
  private Handler uiHandler = new Handler(Looper.getMainLooper());

  private VideoView videoView;
  private FrameLayout viewViewHolder;
  private ConsumerView consumerView;
  private ConsumerPauseView pauseView;
  private ImageView exitBtn;

  private ConsumerInterpretationFragment interpretationFragment;
  private ConsumerGiveUpFragment giveUpFragment;
  private ConsumerFinishFragment finishFragment;

  private int requestCourseTag = 0;
  private String dailyId;
  private String userPlanId;
  private int orientation;
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
          consumer.setMusicVolume(volume);
        }

        @Override
        public void onSetSoundVolume(float volume) {
          consumer.setSoundVolume(volume);
        }
      };
  private ConsumerGiveUpFragment.ConsumeGiveUpFragmentListener giveUpViewListener =
      new ConsumerGiveUpFragment.ConsumeGiveUpFragmentListener() {
        @Override
        public void onConfirmPressed() {
          exit();
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
          start2Exit();
        }

        @Override
        public void onSkipGuidePressed() {
          if (consumer != null) {
            consumer.skipGuide();
          }
        }
      };
  private ConsumerFinishFragment.ConsumeFinishFragmentListener finishViewListener =
      new ConsumerFinishFragment.ConsumeFinishFragmentListener() {
        @Override
        public void onConfirmPressed(int star) {
          uploadStar(star);
          exit();
        }
      };
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

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    videoView = new VideoView(this);

    initConsumer();

    setup(getResources().getConfiguration().orientation);

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

    setup(newConfig.orientation);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();

    release();
  }

  @Override
  protected void onPause() {
    super.onPause();

    if (consumer != null) {
      consumer.pause();
      showPauseView();
    }
  }

  private void setup(int orientation) {
    boolean showInterpretation = this.interpretationFragment != null;
    if (showInterpretation) {
      hideInterpretation();
    }

    boolean showGiveUp = this.giveUpFragment != null;
    if (showGiveUp) {
      hideGiveUpView();
    }

    boolean showFinish = this.finishFragment != null;
    if (showFinish) {
      hideFinishView();
    }

    this.orientation = orientation;

    initViews(this.orientation);
    syncView2Consumer();

    if (showInterpretation) {
      showInterpretation(((View) pauseView).getVisibility() == View.GONE);
    } else if (showGiveUp) {
      showGiveUpView();
    } else if (showFinish) {
      showFinishView();
    }
  }

  private void initViews(int orientation) {
    boolean pauseViewShow = pauseView != null && ((View) pauseView).getVisibility() == View.VISIBLE;

    if (viewViewHolder != null) {
      viewViewHolder.removeView(this.videoView);
    }

    if (orientation == Configuration.ORIENTATION_PORTRAIT) {
      setContentView(R.layout.consumer_activity_portrait_layout);

      WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
      int windowWidth = SystemUtil.getScreentWidth(wm);

      if (windowWidth > 0) {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
            windowWidth,
            windowWidth * Const.VIDEO_HEIGHT / Const.VIDEO_WIDTH);
        videoView.setLayoutParams(layoutParams);
      } else {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT);
        videoView.setLayoutParams(layoutParams);
      }

    } else {
      setContentView(R.layout.consumer_activity_landscape_layout);

      ViewUtils.setViewSize(videoView, ViewGroup.LayoutParams.MATCH_PARENT,
          ViewGroup.LayoutParams.MATCH_PARENT);
    }


    viewViewHolder = (FrameLayout) findViewById(R.id.explore_consumer_video_view_holder);
    viewViewHolder.addView(this.videoView);

    consumerView = (ConsumerView) findViewById(R.id.explore_portrait_consumer_view);
    consumerView.setExploreConsumerViewListener(this.consumerViewListener);

    pauseView = (ConsumerPauseView) findViewById(R.id.consumer_pause_view);
    if (pauseView != null) {
      pauseView.setListener(pauseViewListener);
      if (!pauseViewShow) {
        ((View) pauseView).setVisibility(View.GONE);
      }
    }

    exitBtn = (ImageView) findViewById(R.id.explore_header_left_button);
    if (exitBtn != null) {
      exitBtn.setImageResource(R.drawable.icon_header_navigate_back);
      exitBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          start2Exit();
        }
      });
    }
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

  private void start2Exit() {
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

  private void exit() {
    release();
    finish();
  }

  private void release() {
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

    List<PlanDetailInfo.PlanActionInfo> actionList = new ArrayList<>();
    for (int i = 0; i < consumer.getCourse().getActlist().size(); ++i) {
      actionList.add(ActionDetailInfo.convert2PlanActionInfo(
          consumer.getCourse().getActlist().get(i)));
    }
    interpretationFragment.setData(actionList, consumer.getActionIndex());

    getSupportFragmentManager().beginTransaction()
        .replace(R.id.consumer_fragment_container, interpretationFragment)
        .commit();
  }

  private void hideInterpretation() {
    if (interpretationFragment == null) return;

    getSupportFragmentManager().beginTransaction()
        .remove(interpretationFragment)
        .commit();
    interpretationFragment.setListener(null);
    interpretationFragment = null;
  }

  private void showGiveUpView() {
    giveUpFragment = new ConsumerGiveUpFragment();
    giveUpFragment.setListener(giveUpViewListener);

    Bundle args = new Bundle();
    ConsumeCourseRecord record = consumer.getRecord();
    args.putInt(Intents.EXTRA_CONSUME_TIME_MINUTES, record.getTotalConsumeTimeInMinute());
    args.putInt(Intents.EXTRA_CONSUME_ACTION_COUNT, record.getAcupointCount());
    giveUpFragment.setArguments(args);

    getSupportFragmentManager().beginTransaction()
        .replace(R.id.consumer_fragment_container, giveUpFragment)
        .commit();
  }

  private void hideGiveUpView() {
    if (giveUpFragment == null) return;

    getSupportFragmentManager().beginTransaction()
        .remove(giveUpFragment)
        .commit();
    giveUpFragment.setListener(null);
    giveUpFragment = null;
  }

  private void showFinishView() {
    finishFragment = new ConsumerFinishFragment();
    finishFragment.setListener(finishViewListener);
    finishFragment.setOrientation(this.orientation);

    Bundle args = new Bundle();
    args.putSerializable(Intents.EXTRA_CONSUME_RECORD, consumer.getRecord());
    finishFragment.setArguments(args);

    getSupportFragmentManager().beginTransaction()
        .replace(R.id.consumer_fragment_container, finishFragment)
        .commit();
  }

  private void hideFinishView() {
    if (finishFragment == null) return;

    getSupportFragmentManager().beginTransaction()
        .remove(finishFragment)
        .commit();
    finishFragment.setListener(null);
    finishFragment = null;
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
