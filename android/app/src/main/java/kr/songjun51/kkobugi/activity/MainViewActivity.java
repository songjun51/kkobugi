package kr.songjun51.kkobugi.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Random;

import kr.songjun51.kkobugi.R;
import kr.songjun51.kkobugi.adapter.CommonListViewAdapter;
import kr.songjun51.kkobugi.adapter.DashboardAdapter;
import kr.songjun51.kkobugi.models.ListData;

public class MainViewActivity extends AppCompatActivity {


    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view);

        setAppbarLayout();
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    private void setAppbarLayout() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimaryDark));
        getSupportActionBar().setTitle("kkobugi");
        getSupportActionBar().setElevation(0);
//        getSupportActionBar().setDisplayShowCustomEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        Drawable drawable = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
//        drawable.setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);
//        getSupportActionBar().setHomeAsUpIndicator(drawable);
    }

    public static class RankingShowFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "pageNumber";

        public RankingShowFragment() {
        }

        public static RankingShowFragment newInstance(int sectionNumber) {
            RankingShowFragment fragment = new RankingShowFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = null;
            int page = getArguments().getInt(ARG_SECTION_NUMBER);
            switch (page) {
                case 0:
                    rootView = inflater.inflate(R.layout.main_dashboard, container, false);
                    break;
                case 1:
                    rootView = inflater.inflate(R.layout.main_ranking, container, false);
                    break;
                case 2:
                    rootView = inflater.inflate(R.layout.main_friend, container, false);
                    break;
            }
            setView(rootView, page);
            return rootView;
        }

        public void setView(View rootView, int page) {
            switch (page) {
                case 0:
                    RecyclerView dashGraph = (RecyclerView) rootView.findViewById(R.id.main_dashboard_recyclerview);
                    dashGraph.setHasFixedSize(false);
                    LinearLayoutManager manager = new LinearLayoutManager(getActivity().getApplicationContext());
                    manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                    dashGraph.setLayoutManager(manager);
                    ArrayList<Pair<Integer, Integer>> arr = new ArrayList<>();
                    for (int i = 0; i <= 31; i++) {
                        arr.add(Pair.create(i, new Random().nextInt(100)));
                    }
                    DashboardAdapter adapter = new DashboardAdapter(getActivity().getApplicationContext(), arr);
                    dashGraph.setAdapter(adapter);

                    RelativeLayout video = (RelativeLayout) rootView.findViewById(R.id.main_dashboard_video);
                    video.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Uri uri = Uri.parse("https://www.youtube.com/watch?v=2u97jwzp0Jw");
                            Intent it = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(it);
                        }
                    });
                    break;
                case 1:
                    ListView rankingView = (ListView) rootView.findViewById(R.id.main_ranking_listview);
                    ArrayList<ListData> rankingArr = new ArrayList<>();
                    rankingArr.add(new ListData("구창림", "72%"));
                    rankingArr.add(new ListData("구창림", "72%"));
                    rankingArr.add(new ListData("구창림", "72%"));
                    rankingArr.add(new ListData("구창림", "72%"));
                    rankingArr.add(new ListData("구창림", "72%"));
                    rankingArr.add(new ListData("구창림", "72%"));
                    rankingArr.add(new ListData("구창림", "72%"));
                    rankingArr.add(new ListData("구창림", "72%"));
                    rankingArr.add(new ListData("구창림", "72%"));
                    rankingArr.add(new ListData("구창림", "72%"));
                    CommonListViewAdapter adapter1 = new CommonListViewAdapter(0, getContext(), rankingArr);
                    rankingView.setAdapter(adapter1);
                    break;
                case 2:
                    ListView friendView = (ListView) rootView.findViewById(R.id.main_friend_listview);
                    ArrayList<ListData> friendArr = new ArrayList<>();
                    friendArr.add(new ListData("구창림", "72%", 1));
                    friendArr.add(new ListData("구창림", "72%", 1));
                    friendArr.add(new ListData("구창림", "72%", 1));
                    friendArr.add(new ListData("구창림", "72%", 1));
                    CommonListViewAdapter adapter2 = new CommonListViewAdapter(1, getContext(), friendArr);
                    friendView.setAdapter(adapter2);
                    break;

            }
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a RankingShowFragment (defined as a static inner class below).
            return RankingShowFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "대시보드";
                case 1:
                    return "랭킹";
                case 2:
                    return "친구";
            }
            return null;
        }
    }
}
