package com.heqi.kharazim.consume.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.heqi.image.view.AsyncImageView;
import com.heqi.kharazim.R;
import com.heqi.kharazim.explore.model.ActionDetailInfo;

/**
 * Created by overspark on 2017/3/31.
 */

public class ConsumerPauseViewImpl extends RelativeLayout implements ConsumerPauseView {

  private static final int VOLUME_SEEK_BAR_DEFAULT_MAX = 100;

  private AsyncImageView actionIv;
  private View interpretationBtn;
  private SeekBar soundVolumeSeekBar;
  private SeekBar musicVolumeSeekBar;
  private View playBtn;

  private ConsumerPauseView.ConsumerPauseViewListener listener;

  public ConsumerPauseViewImpl(Context context) {
    super(context);
  }

  public ConsumerPauseViewImpl(Context context, AttributeSet attrs) {
    super(context, attrs);

  }

  public ConsumerPauseViewImpl(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();
    init();
  }

  private void init() {
    actionIv = (AsyncImageView) findViewById(R.id.consumer_pause_view_action_iv);
    interpretationBtn = findViewById(R.id.consumer_pause_view_interpretation_btn);
    soundVolumeSeekBar = (SeekBar) findViewById(R.id.consumer_pause_view_sound_volume_seekbar);
    musicVolumeSeekBar = (SeekBar) findViewById(R.id.consumer_pause_view_music_volume_seekbar);
    playBtn = findViewById(R.id.consumer_pause_view_play_btn);

    initGeneralListeners();
  }

  private void initGeneralListeners() {
    if (interpretationBtn != null) {
      interpretationBtn.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
          if (listener != null) {
            listener.onInterpretationPressed();
          }
        }
      });
    }

    if (playBtn != null) {
      playBtn.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
          if (listener != null) {
            listener.onPlayPressed();
          }
        }
      });
    }

    if (soundVolumeSeekBar != null) {
      soundVolumeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
          if (fromUser && listener != null) {
            listener.onSetSoundVolume(((float)progress) / seekBar.getMax());
          }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {}

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {}
      });
    }

    if (musicVolumeSeekBar != null) {
      musicVolumeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
          if (fromUser && listener != null) {
            listener.onSetMusicVolume(((float)progress) / seekBar.getMax());
          }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {}

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {}
      });
    }

    this.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {}
    });
  }

  @Override
  public void setAction(ActionDetailInfo action) {
    if (actionIv != null && action != null) {
      actionIv.loadNetworkImage(action.getActimg(), R.drawable.icon_kharazim_image_logo);
    }
  }

  @Override
  public void setVolumes(float soundVolume, float musicVolume) {
    if (soundVolumeSeekBar != null) {
      soundVolumeSeekBar.setProgress((int)(soundVolumeSeekBar.getMax() * soundVolume));
    }

    if (musicVolumeSeekBar != null) {
      musicVolumeSeekBar.setProgress((int)(musicVolumeSeekBar.getMax() * musicVolume));
    }
  }

  @Override
  public void setListener(ConsumerPauseViewListener listener) {
    this.listener = listener;
  }
}
