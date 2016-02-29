package com.nguyennk.newsdemo;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.nguyennk.newsdemo.favourite.FavoriteFragment;
import com.nguyennk.newsdemo.home.HomeFragment;
import com.nguyennk.newsdemo.search.SearchFragment;
import com.nguyennk.newsdemo.section.SectionFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {
    private static final String TAB_HOME = "tab_home";
    private static final String TAB_FAV = "tab_fav";
    private static final String TAB_SEARCH = "tab_search";
    private static final String TAB_SECTION = "tab_section";
    private static final String TAB_ABOUT = "tab_about";

    private static final String TITLES[] = {"Home", "Section", "Search", "Saved", "About"};

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(TITLES[0]);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                getSupportActionBar().setTitle(TITLES[position]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home_black_24dp).setTag(TAB_HOME);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_dashboard_black_24dp).setTag(TAB_SECTION);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_search_black_24dp).setTag(TAB_SEARCH);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_history_black_24dp).setTag(TAB_FAV);
//        tabLayout.getTabAt(4).setIcon(R.mipmap.tabbar_profile).setTag(TAB_ABOUT);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_text_size:
                showArticleTextSizeOptions();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new HomeFragment();
                case 1:
                    return new SectionFragment();
                case 2:
                    return new SearchFragment();
                case 3:
                    return new FavoriteFragment();
            }
            return new Fragment();
        }

        @Override
        public int getCount() {
            return 4;
        }
    }
}
