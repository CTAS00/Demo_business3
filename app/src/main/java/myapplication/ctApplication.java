package myapplication;

import android.app.Application;

/**
 * Created by CTAS on 2017/8/10.
 */
public class ctApplication extends Application {

    private static ctApplication mApplication = null;
    @Override
    public void onCreate() {
        super.onCreate();
    }
    //获得实例
    public static ctApplication getInstance() {
        return mApplication;
    }

}
