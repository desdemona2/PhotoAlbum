package com.redheadhammer.photoalbum;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.redheadhammer.photoalbum.databinding.ActivityAddImageBinding;

public class AddImageActivity extends AppCompatActivity {

    private ActivityAddImageBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Add New Image");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding = ActivityAddImageBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_add_image);

        binding.addImage.setOnClickListener(this::addNewImage);
        binding.submit.setOnClickListener(this::submitData);
    }

    private void addNewImage(View view){}

    private void submitData(View view) {}
}