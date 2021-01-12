package com.anushka.androidtutz.contactmanager.di;

import android.app.Application;

import androidx.room.Room;

import com.anushka.androidtutz.contactmanager.db.ContactsAppDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RoomModule {
    @Singleton
    @Provides
    ContactsAppDatabase provideContactsAPPDatabase(Application application){
        return Room.databaseBuilder(application, ContactsAppDatabase.class, "ContactDB")
                .build();
    }
}
