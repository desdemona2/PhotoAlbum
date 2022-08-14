package com.redheadhammer.photoalbum;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.redheadhammer.photoalbum.databinding.ActivityUpdateImageBinding;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class UpdateImageActivity extends AppCompatActivity {
    private ActivityUpdateImageBinding binding;
    private ActivityResultLauncher<Intent> addImageLauncher;
    private Bitmap selectedImage;
    private Bitmap scaledImage;
    private int imageId;
    private String title, description;
    private byte[] image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityUpdateImageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        resultLauncher();
        updateInit();

        binding.imageView.setOnClickListener(this::updateImage);
        binding.submit.setOnClickListener(this::submitClick);
    }

    private void updateInit() {
        Intent data = getIntent();
        image = data.getByteArrayExtra("IMAGE");
        title = data.getStringExtra("TITLE");
        description = data.getStringExtra("DESCRIPTION");
        imageId = data.getIntExtra("IMAGE_ID", -1);

        binding.imageView.setImageBitmap(BitmapFactory.decodeByteArray(image, 0,
                image.length));
        binding.title.setText(title);
        binding.description.setText(description);
    }

    private void resultLauncher () {
        addImageLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        int resultCode = result.getResultCode();
                        Intent data = result.getData();
                        if (resultCode == RESULT_OK && data != null) {

                            try {
                                selectedImage = MediaStore.Images.Media.getBitmap(
                                        getContentResolver(), data.getData()
                                );
                                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                                scaledImage = makeSmall(selectedImage, 300);
                                selectedImage.compress(Bitmap.CompressFormat.PNG, 50, outputStream);

                                image = outputStream.toByteArray();

                                binding.imageView.setImageBitmap(BitmapFactory.decodeByteArray(image, 0,
                                        image.length));

                            } catch (IOException e) {
                                Toast.makeText(getApplicationContext(),
                                        "Failed to Update Image",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
        );
    }


    private void updateImage(View view) {
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            addImageLauncher.launch(intent);
        } else {
            Toast.makeText(getApplicationContext(),
                    "Grant Permission to read external storage", Toast.LENGTH_SHORT).show();
        }
    }

    private void submitClick(View view) {
        title = binding.title.getText().toString();
        description = binding.description.getText().toString();

        if (image != null) {
            Intent intent = getIntent();
            intent.putExtra("TITLE", title);
            intent.putExtra("DESCRIPTION", description);
            intent.putExtra("IMAGE", image);

            Toast.makeText(getApplicationContext(), "" + image.length, Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK, intent);
            finish();

        }  else {
            Toast.makeText(getApplicationContext(),
                    "Please select an Image",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public Bitmap makeSmall(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float ratio = (float) width / height;

        if (ratio > 1) {
            width = maxSize;
            height = (int) (width/ratio);
        } else {
            height = maxSize;
            width = (int) (height/ratio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }
}