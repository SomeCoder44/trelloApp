package pl.orzechowski.trellomanagmentapp;

import android.app.Application;

import pl.orzechowski.trellomanagmentapp.helpers.AlertDialogManager;
import pl.orzechowski.trellomanagmentapp.helpers.UserPreferences;
import pl.orzechowski.trellomanagmentapp.network.Connection;

public class TMApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Connection.init(this);
        UserPreferences.initInstance(this);
        AlertDialogManager.init(this);
    }
}
