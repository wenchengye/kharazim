package com.heqi.kharazim.ui.fragment.tab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.heqi.base.utils.CollectionUtils;
import com.heqi.fetcher.ReferenceAcceptor;
import com.heqi.kharazim.R;
import com.heqi.kharazim.config.Const;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A fragment that is to be the tab host of several fragments.
 */
public abstract class TabHostFragment extends Fragment implements ReferenceAcceptor {

  public static final String LAST_SELECTED_ITEM_POS = "last_selected_item_pos";

  private View contentView;
  private PagerSlidingTabStrip tabStrip;
  private CommonViewPager viewPager;
  private TabFragmentPagerAdapter pagerAdapter;
  private int currentFragmentIndex;
  private Set cachedReferenceSet = new HashSet();
  private ViewPager.OnPageChangeListener delegateOnPageChangeListener;
  private ViewPager.OnPageChangeListener onPageChangeListener =
      new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
          if (delegateOnPageChangeListener != null) {
            delegateOnPageChangeListener.onPageScrolled(position, positionOffset,
                positionOffsetPixels);
          }
        }

        @Override
        public void onPageSelected(int position) {
          if (currentFragmentIndex != position) {
            currentFragmentIndex = position;
          }
          if (delegateOnPageChangeListener != null) {
            delegateOnPageChangeListener.onPageSelected(position);
          }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
          pagerAdapter.notifyScrollStateChanged(state);
          if (delegateOnPageChangeListener != null) {
            delegateOnPageChangeListener.onPageScrollStateChanged(state);
          }
        }
      };

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    contentView = inflater.inflate(getLayoutResId(), container, false);
    return contentView;
  }

  protected int getLayoutResId() {
    return R.layout.common_tab_layout;
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    tabStrip = (PagerSlidingTabStrip) contentView.findViewById(R.id.tabs);
    viewPager = (CommonViewPager) contentView.findViewById(R.id.common_view_pager);
    pagerAdapter = new TabFragmentPagerAdapter(getActivity(), getChildFragmentManager());
    final List<TabFragmentDelegate> fragmentDelegates = getTabFragmentDelegates();
    viewPager.setAdapter(pagerAdapter);
    if (!CollectionUtils.isEmpty(fragmentDelegates)) {
      pagerAdapter.setFragments(getTabFragmentDelegates());
      pagerAdapter.notifyDataSetChanged();
      currentFragmentIndex = getInitTabIndex();
      if (getArguments() != null && getArguments().containsKey(LAST_SELECTED_ITEM_POS)) {
        viewPager.setCurrentItem(getArguments().getInt(LAST_SELECTED_ITEM_POS), false);
      } else {
        viewPager.setCurrentItem(currentFragmentIndex);
      }
    }
    tabStrip.setViewPager(viewPager);
    tabStrip.setOnPageChangeListener(onPageChangeListener);
  }

  protected void setAllowLoading(boolean allowLoading) {
    pagerAdapter.setAllowLoading(allowLoading);
  }

  public void setScrollEnabled(boolean isEnabled) {
    viewPager.setScrollEnabled(isEnabled);
    tabStrip.setAllTabEnabled(isEnabled);
  }

  protected void setOffScreenPageLimit(int limit) {
    viewPager.setOffscreenPageLimit(limit);
  }

  public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
    this.delegateOnPageChangeListener = listener;
  }

  private int getInitTabIndex() {
    if (getInitTabId() != null && pagerAdapter != null) {
      int position = getTabPosition(getInitTabId());
      if (position >= 0) {
        return position;
      }
    }
    return 0;
  }

  /**
   * Get the tab id at the specified position
   *
   * @param position
   * @return tabId
   */
  protected String getTabId(int position) {
    return pagerAdapter.getTabIdByPosition(position);
  }

  /**
   * Get the tab position by the specified tab id.
   *
   * @param tabId
   * @return position
   */
  protected int getTabPosition(String tabId) {
    return pagerAdapter.getTabPositionById(tabId);
  }

  /**
   * Get the initial selected tab, sub class should override this to change the default position,
   * this method will call in {@link TabHostFragment#onActivityCreated(Bundle)}.
   *
   * @return the default selected tab index
   */
  protected String getInitTabId() {
    return Const.TabId.NONE;
  }

  /**
   * Move the specific tab, it should only be called AFTER the initialization of this fragment,
   * if you just want to set the default selected page,
   * just override {@link TabHostFragment#getInitTabId()}.
   *
   * @param position the position of the fragment
   * @param args     the arguments need to be passed to this fragment
   */
  public void selectTab(int position, Bundle args) {
    // TODO don't allow to call this function before onActivityCreated() or add NPE change on
    // pagerAdapter.
    pagerAdapter.setFragmentArgs(position, args);
    viewPager.setCurrentItem(position, false);
  }

  public void selectTab(String id, Bundle args) {
    int index = pagerAdapter.getTabPositionById(id);
    if (index >= 0) {
      selectTab(pagerAdapter.getTabPositionById(id), args);
    }
  }

  /**
   * Just switch page of the viewpager. Do nothing with the fragments.
   * see more {@link TabFragmentPagerAdapter#setFragmentArgs(int, Bundle)}
   *
   * @param position, index of viewpager
   */
  public void selectTabWithoutNotify(int position) {
    viewPager.setCurrentItem(position, false);
  }

  /**
   * Just switch page of the viewpager.
   *
   * @param id the tabId
   */
  public void selectTabWithoutNotify(String id) {
    selectTabWithoutNotify(pagerAdapter.getTabPositionById(id));
  }

  /**
   * Set the arguments for fragment in selected position
   *
   * @param position the position of the fragment
   * @param args     the arguments need to be passed to this fragment
   */
  public void setTabArgs(int position, Bundle args) {
    pagerAdapter.setFragmentArgs(position, args);
  }

  public void setTabArgs(String tabId, Bundle args) {
    pagerAdapter.setFragmentArgs(pagerAdapter.getTabPositionById(tabId), args);
  }

  public int getCurrentItem() {
    if (viewPager != null) {
      return viewPager.getCurrentItem();
    }
    return getInitTabIndex();
  }

  /**
   * Get the entire view of the fragment.
   *
   * @return the content view
   */
  public View getContentView() {
    return contentView;
  }

  /**
   * Get the tab strip
   *
   * @return the tab strip
   */
  public PagerSlidingTabStrip getTabStrip() {
    return tabStrip;
  }

  /**
   * Get the tabs that need to be shown in ViewPager and PagerSlidingTabStrip.
   *
   * @return the list of {@link TabFragmentDelegate}
   */
  public abstract List<TabFragmentDelegate> getTabFragmentDelegates();

  @Override
  public void onDestroy() {
    cachedReferenceSet.clear();
    super.onDestroy();
  }

  @Override
  public void onReferenceAccepted(Object target) {
    cachedReferenceSet.add(target);
  }

  public Fragment getFragment(int position) {
    return pagerAdapter.getFragment(position);
  }

  public Fragment getCurrentFragment() {
    return getFragment(getCurrentItem());
  }

  public boolean isDefaultFragment(Fragment f) {
    return pagerAdapter.getFragment(getInitTabIndex()) == f;
  }

  public void appendFragment(List<TabFragmentDelegate> delegates) {
    pagerAdapter.appendFragments(delegates);
    tabStrip.notifyDataSetChanged();
  }

  public void setFragments(List<TabFragmentDelegate> delegates) {
    pagerAdapter.setFragments(delegates);
    tabStrip.notifyDataSetChanged();
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    outState.putInt(LAST_SELECTED_ITEM_POS, getCurrentItem());
    super.onSaveInstanceState(outState);
  }

  @Override
  public void onViewStateRestored(Bundle savedInstanceState) {
    if (savedInstanceState != null) {
      int lastItemPos = savedInstanceState.getInt(LAST_SELECTED_ITEM_POS, -1);
      if (lastItemPos != -1) {
        selectTab(lastItemPos, savedInstanceState);
      }
    }
    super.onViewStateRestored(savedInstanceState);
  }
}
