package com.redheadhammer.photoalbum;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.redheadhammer.photoalbum.databinding.ActivityUpdateImageBinding;

public class UpdateImageActivity extends AppCompatActivity {
    private ActivityUpdateImageBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityUpdateImageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.imageView.setOnClickListener(this::updateImage);
        binding.submit.setOnClickListener(this::submitClick);
    }

    private void updateImage(View view) {}

    private void submitClick(View view) {}
}