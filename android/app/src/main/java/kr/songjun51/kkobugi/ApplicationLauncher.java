package kr.songjun51.kkobugi;

import android.app.Application;

import com.facebook.FacebookSdk;

/**
 * Created by KOHA_DESKTOP on 2016. 7. 27..
 */
public class ApplicationLauncher extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(this);
    }

}
