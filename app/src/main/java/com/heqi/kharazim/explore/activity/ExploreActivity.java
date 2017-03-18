package com.heqi.kharazim.explore.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.heqi.kharazim.R;
import com.heqi.kharazim.explore.fragment.PlanListFragment;


public class ExploreActivity extends FragmentActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.common_activity_container);

    PlanListFragment planListFragment = new PlanListFragment();
    getSupportFragmentManager().beginTransaction()
        .replace(R.id.fragments_container, planListFragment)
        .commit();
  }
}
