package com.redheadhammer.photoalbum;


import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {MyImages.class}, version = 1, exportSchema = false)
public abstract class MyImagesDatabase extends RoomDatabase {

    private static MyImagesDatabase instance;

    public abstract MyImagesDoa myImagesDoa();

    public synchronized static MyImagesDatabase getInstance(Application application) {

        if (instance == null) {
            instance = Room.databaseBuilder(application.getApplicationContext(),
                    MyImagesDatabase.class,
                    "images_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return instance;
    }

}
