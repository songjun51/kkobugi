package kr.songjun51.kkobugi.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import kr.songjun51.kkobugi.R;
import kr.songjun51.kkobugi.adapter.CommonListViewAdapter;
import kr.songjun51.kkobugi.adapter.DashboardAdapter;
import kr.songjun51.kkobugi.models.ListData;
import kr.songjun51.kkobugi.models.TodayData;
import kr.songjun51.kkobugi.models.User;
import kr.songjun51.kkobugi.utils.DataManager;
import kr.songjun51.kkobugi.utils.KkobugiService;
import kr.songjun51.kkobugi.utils.NetworkHelper;
import kr.songjun51.kkobugi.utils.NetworkInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewActivity extends AppCompatActivity {


    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    DataManager manager;
    NetworkInterface service;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view);
        startService(new Intent(getApplicationContext(), KkobugiService.class));
        setAppbarLayout();
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        service = NetworkHelper.getNetworkInstance();
        manager = new DataManager();
        manager.initializeManager(this);
    }

    private void setAppbarLayout() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimaryDark));
        getSupportActionBar().setTitle("kkobugi");
        getSupportActionBar().setElevation(0);
    }

    public static class RankingShowFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "pageNumber";
        private static NetworkInterface service;
        private static DataManager dataManager;

        public RankingShowFragment() {
        }

        public static RankingShowFragment newInstance(int sectionNumber) {
            RankingShowFragment fragment = new RankingShowFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            service = NetworkHelper.getNetworkInstance();
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = null;
            dataManager = new DataManager();
            dataManager.initializeManager(container.getContext());
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

        String backgroundColor[] = new String[]{"#40A295", "#DC383D"};

        public void setView(View rootView, int page) {
            switch (page) {
                case 0:
                    int data = dataManager.getKkobugiPercentage();
                    RelativeLayout background = (RelativeLayout) rootView.findViewById(R.id.main_dashboard_background);
                    TextView percentage = (TextView) rootView.findViewById(R.id.main_dashboard_percent);
                    percentage.setText(data + "%");
                    background.setBackground(new ColorDrawable(Color.parseColor(backgroundColor[(data < 50) ? 0 : 1])));
                    final RecyclerView dashGraph = (RecyclerView) rootView.findViewById(R.id.main_dashboard_recyclerview);
                    dashGraph.setHasFixedSize(false);
                    LinearLayoutManager manager = new LinearLayoutManager(getActivity().getApplicationContext());
                    manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                    dashGraph.setLayoutManager(manager);
                    final ArrayList<Pair<Integer, Integer>> arr = new ArrayList<>();
                    Call<List<TodayData>> getDataArray = service.getDataArray(dataManager.getActiveUser().second.getId());
                    getDataArray.enqueue(new Callback<List<TodayData>>() {
                        @Override
                        public void onResponse(Call<List<TodayData>> call, Response<List<TodayData>> response) {
                            Log.e("asdf", response.code() + "");
                            switch (response.code()) {
                                case 200:
                                    for (TodayData d : response.body()) {
                                        arr.add(Pair.create(Integer.parseInt(d.getDate()), Integer.parseInt(d.getPercent())));
                                    }
                                    DashboardAdapter adapter = new DashboardAdapter(getActivity().getApplicationContext(), arr);
                                    dashGraph.setAdapter(adapter);
                            }
                        }

                        @Override
                        public void onFailure(Call<List<TodayData>> call, Throwable t) {
                            Log.e("asdf", t.getMessage());
                        }
                    });

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
                    final ListView rankingView = (ListView) rootView.findViewById(R.id.main_ranking_listview);
                    final ArrayList<ListData> rankingArr = new ArrayList<>();
                    Call<List<User>> getRankingInfo = service.getFriendRank(dataManager.getActiveUser().second.getId());
                    Log.e("asdf", dataManager.getActiveUser().second.getId());
                    getRankingInfo.enqueue(new Callback<List<User>>() {
                        @Override
                        public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                            switch (response.code()) {
                                case 200:
                                    for (User u : response.body()) {
                                        rankingArr.add(new ListData(u.getName(), u.getProfileurl() + ""));
                                        Log.e("asdf", u.getName());
                                    }
                                    break;
                                case 401:
                                    break;
                            }
                        }

                        @Override
                        public void onFailure(Call<List<User>> call, Throwable t) {
                            Toast.makeText(rankingView.getContext(), "서버와의 연동에 문제가 발생했습니다. 잠시 후 다시 시도해주세요", Toast.LENGTH_SHORT).show();
                            Log.e("asdf", t.getMessage());
                        }
                    });
                    CommonListViewAdapter adapter1 = new CommonListViewAdapter(0, getContext(), rankingArr);
                    rankingView.setAdapter(adapter1);
                    break;
                case 2:
                    final ListView friendView = (ListView) rootView.findViewById(R.id.main_friend_listview);
                    final ArrayList<ListData> friendArr = new ArrayList<>();
                    Log.e("asdf_id", dataManager.getActiveUser().second.getId());
                    Call<List<User>> getFriendList = service.getFriendsFriend(dataManager.getActiveUser().second.getId());
                    getFriendList.enqueue(new Callback<List<User>>() {
                        @Override
                        public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                            switch (response.code()) {
                                case 200:
                                    for (int i = 0; i < response.body().size(); i++) {
                                        User u = response.body().get(i);
                                        friendArr.add(new ListData(u.getName(), u.getProfileurl(), i));
                                    }
                                    break;
                                case 401:
                                    break;
                            }
                        }

                        @Override
                        public void onFailure(Call<List<User>> call, Throwable t) {
                            Log.e("asdf", t.getMessage());
                            Toast.makeText(friendView.getContext(), "서버와의 연동에 문제가 발생했습니다. 잠시후 다시 시도해주세요", Toast.LENGTH_SHORT).show();
                        }
                    });
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
