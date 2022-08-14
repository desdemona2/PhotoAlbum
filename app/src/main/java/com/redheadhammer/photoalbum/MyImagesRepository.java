package com.redheadhammer.photoalbum;


import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class MyImagesRepository {

    private final MyImagesDoa imagesDoa;
    public LiveData<List<MyImages>> imageList;

    public MyImagesRepository(Application application) {
        MyImagesDatabase database = MyImagesDatabase.getInstance(application);
        imagesDoa = database.myImagesDoa();
    }

    public void insert(MyImages image) {
        imagesDoa.insert(image);
    }

    public void update(MyImages image) {
        imagesDoa.update(image);
    }

    public void delete(MyImages image) {
        imagesDoa.delete(image);
    }

    public LiveData<List<MyImages>> getAllImages() {
        return imageList;
    }
}
