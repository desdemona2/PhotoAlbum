package com.redheadhammer.photoalbum;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MyImagesDoa {


    @Insert
    void insert(MyImages myImages);

    @Update
    void update(MyImages myImages);

    @Delete
    void delete(MyImages myImages);

    @Query("SELECT * FROM myImages ORDER BY imageId ASC")
    LiveData<List<MyImages>> getAllImages();

}
