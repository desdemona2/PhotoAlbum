package com.redheadhammer.photoalbum;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MyImagesViewModel extends AndroidViewModel {

    private final MyImagesRepository imagesRepository;

    public LiveData<List<MyImages>> allImages;

    public MyImagesViewModel(@NonNull Application application) {
        super(application);
        imagesRepository = new MyImagesRepository(application);
    }

    public void insert(MyImages image) {
        imagesRepository.insert(image);
    }

    public void update(MyImages image) {
        imagesRepository.update(image);
    }

    public void delete(MyImages image) {
        imagesRepository.delete(image);
    }

    public LiveData<List<MyImages>> getAllImages() {
        return imagesRepository.getAllImages();
    }
}
