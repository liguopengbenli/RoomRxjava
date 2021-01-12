package com.anushka.androidtutz.contactmanager;

import android.app.Application;

import com.anushka.androidtutz.contactmanager.di.ApplicationModule;
import com.anushka.androidtutz.contactmanager.di.ContactsAppDatabaseComponent;
import com.anushka.androidtutz.contactmanager.di.DaggerContactsAppDatabaseComponent;

public class App extends Application {
    private static App app;
    private ContactsAppDatabaseComponent contactsAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        contactsAppComponent = DaggerContactsAppDatabaseComponent.builder().
                                            applicationModule(new ApplicationModule(this))
                                            .build();
    }

    public static App getApp() {
        return app;
    }

    public ContactsAppDatabaseComponent getContactsAppComponent(){
        return contactsAppComponent;
    }
}
