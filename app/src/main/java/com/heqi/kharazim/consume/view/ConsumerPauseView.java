package com.heqi.kharazim.consume.view;

import com.heqi.kharazim.explore.model.ActionDetailInfo;

/**
 * Created by overspark on 2017/3/31.
 */

public interface ConsumerPauseView {

  void setAction(ActionDetailInfo action);

  void setVolumes(float soundVolume, float musicVolume);

  void setListener(ConsumerPauseViewListener listener);

  interface ConsumerPauseViewListener {

    void onPlayPressed();

    void onInterpretationPressed();

    void onSetMusicVolume(float volume);

    void onSetSoundVolume(float volume);
  }
}
