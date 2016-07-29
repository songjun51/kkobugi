package kr.songjun51.kkobugi.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Date;

import kr.songjun51.kkobugi.models.TodayData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by KOHA_DESKTOP on 2016. 7. 29..
 */
public class TimeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_TIME_TICK)) {
            Date date = new Date(System.currentTimeMillis());
            if (date.getHours() == 0 && date.getMinutes() == 0) {
                DataManager manager = new DataManager();
                manager.initializeManager(context);
                Call<TodayData> call = NetworkHelper.getNetworkInstance().addTodayData(date.getMonth() + "" + date.getDate(), manager.getKkobugiPercentage() + "", manager.getActiveUser().second.getId());
                call.enqueue(new Callback<TodayData>() {
                    @Override
                    public void onResponse(Call<TodayData> call, Response<TodayData> response) {
                        switch (response.code()) {
                            case 200:
                                Log.e("asdf", "Success");
                                break;
                            case 401:
                                break;
                        }
                    }

                    @Override
                    public void onFailure(Call<TodayData> call, Throwable t) {
                        Log.e("asdf", t.getMessage());
                    }
                });

            }
        }
    }
}
