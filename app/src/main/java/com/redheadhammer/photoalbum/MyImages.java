package com.redheadhammer.photoalbum;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "myImages")
public class MyImages {

    @PrimaryKey(autoGenerate = true)
    private int imageId;

    private String imageTitle;

    private String imageDescription;

    // Blob(Binary large objects) is used to store media files
    // like images, songs etc.
    private byte[] image;


    public MyImages(String imageTitle, String imageDescription, byte[] image) {
        this.imageTitle = imageTitle;
        this.imageDescription = imageDescription;
        this.image = image;
    }

    public int getImageId() {
        return imageId;
    }

    public String getImageTitle() {
        return imageTitle;
    }

    public String getImageDescription() {
        return imageDescription;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
