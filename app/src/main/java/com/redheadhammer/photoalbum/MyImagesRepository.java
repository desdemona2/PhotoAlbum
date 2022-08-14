package com.redheadhammer.photoalbum;


import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyImagesRepository {

    private final MyImagesDoa imagesDoa;
    public LiveData<List<MyImages>> imageList;

    private ExecutorService executor = Executors.newSingleThreadExecutor();

    public MyImagesRepository(Application application) {
        MyImagesDatabase database = MyImagesDatabase.getInstance(application);
        imagesDoa = database.myImagesDoa();
        imageList = imagesDoa.getAllImages();
    }

    public void insert(MyImages image) {
        executor.execute(() -> imagesDoa.insert(image));
    }

    public void update(MyImages image) {
        executor.execute(()->imagesDoa.update(image));
    }

    public void delete(MyImages image) {
        executor.execute(()->imagesDoa.delete(image));
    }

    public LiveData<List<MyImages>> getAllImages() {
        return imageList;
    }
}
