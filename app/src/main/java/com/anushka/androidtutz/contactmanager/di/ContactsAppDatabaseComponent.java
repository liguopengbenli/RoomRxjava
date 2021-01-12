package com.anushka.androidtutz.contactmanager.di;

import android.app.Application;

import com.anushka.androidtutz.contactmanager.MainActivity;
import com.anushka.androidtutz.contactmanager.db.ContactsAppDatabase;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, RoomModule.class})
public interface ContactsAppDatabaseComponent {
    void inject(MainActivity mainActivity);
}
