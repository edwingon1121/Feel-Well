package itp341.gonzalez.edwin.feelwellapp;

import android.app.Application;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by edwingon on 11/23/17.
 */

public class transition extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //Allow to suspend execution for 2000 milseconds
        SystemClock.sleep(2000);
    }
}
