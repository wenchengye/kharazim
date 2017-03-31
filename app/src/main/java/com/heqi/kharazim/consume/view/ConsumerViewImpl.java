package com.heqi.kharazim.consume.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.heqi.kharazim.R;
import com.heqi.kharazim.consume.core.api.State;
import com.heqi.kharazim.explore.model.ActionDetailInfo;
import com.heqi.kharazim.explore.view.ExploreCircleProgressView;

/**
 * Created by overspark on 2017/3/3.
 */

public class ConsumerViewImpl extends RelativeLayout implements ConsumerView {

  private View consumerNormalLayout;
  private ImageView playBtn;
  private ImageView previousBtn;
  private ImageView nextBtn;
  private ImageView exitBtn;
  private TextView interpretationBtn;
  private ExploreCircleProgressView repeatProgress;
  private ExploreCircleProgressView durationProgress;

  private View consumerGuideLayout;
  private ExploreCircleProgressView guideProgress;
  private View skipGuideBtn;


  private ExploreConsumerViewListener listener;

  private ActionDetailInfo action;
  private State state;

  public ConsumerViewImpl(Context context) {
    super(context);
  }

  public ConsumerViewImpl(Context context, AttributeSet attrs) {
    super(context, attrs);

  }

  public ConsumerViewImpl(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();
    init();
  }

  private void init() {
    consumerNormalLayout = findViewById(R.id.consumer_view_normal_layout);
    playBtn = (ImageView) findViewById(R.id.consumer_play_button);
    previousBtn = (ImageView) findViewById(R.id.consumer_backward_button);
    nextBtn = (ImageView) findViewById(R.id.consumer_forward_button);
    interpretationBtn = (TextView) findViewById(R.id.consumer_interpretation_button);
    repeatProgress = (ExploreCircleProgressView) findViewById(R.id.consumer_repeat_progress);
    durationProgress = (ExploreCircleProgressView) findViewById(R.id.consumer_duration_progress);

    consumerGuideLayout = findViewById(R.id.consumer_view_guide_layout);
    guideProgress = (ExploreCircleProgressView) findViewById(R.id.consumer_guide_progress);
    skipGuideBtn = findViewById(R.id.consumer_skip_guide_btn);

    if (guideProgress != null) {
      guideProgress.setProgressType(ExploreCircleProgressView.ProgressType.PROGRESS_ONLY);
    }

    initGeneralListeners();

    showNormalView();
  }

  private void initGeneralListeners() {
    if (exitBtn != null) {
      exitBtn.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
          if (listener != null) listener.onExitPressed();
        }
      });
    }

    if (interpretationBtn != null) {
      interpretationBtn.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
          if (listener != null) listener.onInterpretationPressed();
        }
      });
    }
  }

  private void initContentListeners() {
    if (playBtn != null) {
      playBtn.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
          if (listener != null) listener.onPlayPressed();
        }
      });
    }

    if (previousBtn != null) {
      previousBtn.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
          if (listener != null) listener.onPreviousPressed();
        }
      });
    }

    if (nextBtn != null) {
      nextBtn.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
          if (listener != null) listener.onNextPressed();
        }
      });
    }

    if (repeatProgress != null) {
      repeatProgress.setUpperMinorText(
          getContext().getString(R.string.consumer_progress_upper_text));
      repeatProgress.setLowerMinorText(
          getContext().getString(R.string.consumer_progress_repeat_lower_text));
    }

    if (durationProgress != null) {
      durationProgress.setUpperMinorText(
          getContext().getString(R.string.consumer_progress_upper_text));
      durationProgress.setLowerMinorText(
          getContext().getString(R.string.consumer_progress_duration_lower_text));
    }

    if (skipGuideBtn != null) {
      skipGuideBtn.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
          if (listener != null) listener.onSkipGuidePressed();
        }
      });
    }
  }

  @Override
  public void initContent(ActionDetailInfo action, int progress, int duration, int repeatIndex,
                          int repeatSum, State state) {
    setAction(action);
    setProgress(progress, duration);
    setRepeat(repeatIndex, repeatSum);
    setState(state);

    initContentListeners();
  }

  @Override
  public void setAction(ActionDetailInfo action) {
    // Do nothing
  }

  @Override
  public void setProgress(int progress, int duration) {
    if (this.durationProgress != null) {
      this.durationProgress.setProgress(progress);
      this.durationProgress.setMax(duration);
      this.durationProgress.setProgressText(String.valueOf(progress));
      this.durationProgress.setMaxText(String.valueOf(duration));
    }

    if (this.guideProgress != null) {
      this.guideProgress.setProgress(progress);
      this.guideProgress.setMax(duration);
      this.guideProgress.setProgressText(String.valueOf(progress));
    }
  }

  @Override
  public void setRepeat(int repeatIndex, int repeatSum) {
    if (this.repeatProgress != null) {
      this.repeatProgress.setProgress(repeatIndex);
      this.repeatProgress.setMax(repeatSum);
      this.repeatProgress.setProgressText(String.valueOf(repeatIndex));
      this.repeatProgress.setMaxText(String.valueOf(repeatSum));
    }
  }

  @Override
  public void setState(State state) {

  }

  @Override
  public void setExploreConsumerViewListener(ExploreConsumerViewListener listener) {
    this.listener = listener;
  }

  @Override
  public void showGuideView() {
    if (consumerGuideLayout != null) {
      consumerGuideLayout.setVisibility(View.VISIBLE);
    }

    if (consumerNormalLayout != null) {
      consumerNormalLayout.setVisibility(View.GONE);
    }
  }

  @Override
  public void showNormalView() {
    if (consumerGuideLayout != null) {
      consumerGuideLayout.setVisibility(View.GONE);
    }

    if (consumerNormalLayout != null) {
      consumerNormalLayout.setVisibility(View.VISIBLE);
    }
  }
}
