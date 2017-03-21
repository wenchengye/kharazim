package com.heqi.kharazim.archives;

import com.heqi.kharazim.archives.model.HealthCondition;
import com.heqi.kharazim.archives.model.UserProfile;

/**
 * Created by overspark on 2017/3/19.
 */

public class SimpleArchivesObserver implements ArchivesService.ArchivesObserver {
  @Override
  public void onLogin(String userId) {
  }

  @Override
  public void onUserProfileUpdated(String userId, UserProfile userProfile) {
  }

  @Override
  public void onHealthConditionUpdated(String userId, HealthCondition healthCondition) {
  }

  @Override
  public void onAddPlan(String userId, String planId) {
  }

  @Override
  public void onRemovePlan(String userId, String planId) {
  }
}
