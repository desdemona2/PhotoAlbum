package com.redheadhammer.photoalbum;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.redheadhammer.photoalbum.databinding.ActivityAddImageBinding;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class AddImageActivity extends AppCompatActivity {

    private ActivityAddImageBinding binding;
    private ActivityResultLauncher<Intent> addImageLauncher;

    private Bitmap selectedImage;
    private Bitmap scaledImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Add New Image");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding = ActivityAddImageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        resultLauncher();

        binding.addImage.setOnClickListener(this::addNewImage);
        binding.submit.setOnClickListener(this::submitData);
    }

    private void addNewImage(View view){
        boolean ifPermitted = (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED);

        if (ifPermitted) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            addImageLauncher.launch(intent);
        }
        else {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 123);
        }
    }

    private void submitData(View view) {
        if (selectedImage != null) {
            String title = binding.title.getText().toString();
            String description = binding.description.getText().toString();

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            scaledImage = makeSmall(selectedImage, 300);
            selectedImage.compress(Bitmap.CompressFormat.PNG, 50, outputStream);

            byte[] image = outputStream.toByteArray();

            Intent intent = getIntent();
            intent.putExtra("TITLE", title);
            intent.putExtra("DESCRIPTION", description);
            intent.putExtra("IMAGE", image);

            Toast.makeText(getApplicationContext(), "" + image.length, Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK, intent);
            finish();

        } else {
            Toast.makeText(getApplicationContext(),
                    "Please select an Image",
                    Toast.LENGTH_SHORT).show();
        }
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

                            } catch (IOException e) {
                                Toast.makeText(getApplicationContext(),
                                        "Failed to select Image",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 123 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            addImageLauncher.launch(intent);
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