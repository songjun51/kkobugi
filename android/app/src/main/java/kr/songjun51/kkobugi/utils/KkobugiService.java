package kr.songjun51.kkobugi.utils;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by KOHA_DESKTOP on 2016. 7. 29..
 */
public class KkobugiService extends Service implements SensorEventListener {
    DataManager manager;
    Sensor accel;
    SensorManager sensorManager;
    long maxDelay = 1000;
    long lastSavedTime = 0;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_TIME_TICK);
        TimeReceiver mr = new TimeReceiver();
        registerReceiver(mr, filter);
        manager = new DataManager();
        manager.initializeManager(getApplicationContext());
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accel, SensorManager.SENSOR_DELAY_FASTEST);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        startService(new Intent(getApplicationContext(), KkobugiService.class));
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (lastSavedTime == 0 || System.currentTimeMillis() - lastSavedTime > maxDelay) {
            lastSavedTime = System.currentTimeMillis();
            manager.saveKkobugiData((sensorEvent.values[sensorEvent.values.length - 1] > 8));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }
}
