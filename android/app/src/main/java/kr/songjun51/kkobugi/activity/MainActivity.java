package kr.songjun51.kkobugi.activity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import kr.songjun51.kkobugi.R;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    // 안씀
    int gyroX;
    int gyroY;
    int gyroZ;
    int accX;
    int accY;
    int accZ;
    private SensorManager mSensorManager;
    private Sensor mGyroscope;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //센서 매니저 얻기
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        //자이로스코프 센서(회전)
        mGyroscope = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;

        if (sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            gyroX = Math.round(event.values[0] * 1000);
            gyroY = Math.round(event.values[1] * 1000);
            gyroZ = Math.round(event.values[2] * 1000);
            System.out.println("gyroX =" + gyroX);
            System.out.println("gyroY =" + gyroY);
            System.out.println("gyroZ =" + gyroZ);

        }
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            accX = (int) event.values[0];
            accY = (int) event.values[1];
            accZ = (int) event.values[2];
            System.out.println("accelXValue=" + accX);
            System.out.println("accelYValue=" + accY);
            System.out.println("accelZValue=" + accZ);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    //리스너 등록
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mGyroscope, SensorManager.SENSOR_DELAY_FASTEST);
    }

    //리스너 해제
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }
}
