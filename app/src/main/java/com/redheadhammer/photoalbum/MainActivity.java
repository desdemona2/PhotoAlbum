package com.redheadhammer.photoalbum;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.redheadhammer.photoalbum.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MyImagesViewModel viewModel;
    private ActivityResultLauncher<Intent> addImageLauncher;
    private ActivityResultLauncher<Intent> updateLauncher;
    MyImagesAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        adapter = new MyImagesAdapter();
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));
        binding.recycler.setAdapter(adapter);

        adapterOnClickListener();

        viewModel = new ViewModelProvider(this).get(MyImagesViewModel.class);

        viewModel.getAllImages().observe(this, new Observer<List<MyImages>>() {
            @Override
            public void onChanged(List<MyImages> myImages) {
                adapter.setImageList(myImages);
                adapter.notifyDataSetChanged();
            }
        });

        registerResultActivity();
        setUpdateLauncher();

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;}

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                viewModel.delete(adapter.getImageAtPosition(position));
            }
        }).attachToRecyclerView(binding.recycler);

        binding.floatAdd.setOnClickListener(this::onFab);
    }

    private void onFab(View view) {
        Intent intent = new Intent(MainActivity.this, AddImageActivity.class);
        addImageLauncher.launch(intent);
    }

    private void registerResultActivity() {
        addImageLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        int resultCode = result.getResultCode();
                        Intent data = result.getData();
                        if (resultCode == RESULT_OK && data != null) {
                            byte[] image = data.getByteArrayExtra("IMAGE");
                            String title = data.getStringExtra("TITLE");
                            String description = data.getStringExtra("DESCRIPTION");

                            viewModel.insert(new MyImages(title, description, image));
                        }
                    }
                });
    }

    private void adapterOnClickListener() {
        adapter.setOnItemClickListener(new MyImagesAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                MyImages image = adapter.getImageAtPosition(position);
                String title = image.getImageTitle();
                String description = image.getImageDescription();
                byte[] imageV = image.getImage();
                int imageId = image.getImageId();

                Intent intent = new Intent(MainActivity.this, UpdateImageActivity.class);
                intent.putExtra("TITLE", title);
                intent.putExtra("DESCRIPTION", description);
                intent.putExtra("IMAGE", imageV);
                intent.putExtra("IMAGE_ID", imageId);

                updateLauncher.launch(intent);

            }
        });
    }

    private void setUpdateLauncher() {
        updateLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        int resultCode = result.getResultCode();
                        Intent data = result.getData();

                        if (resultCode == RESULT_OK && data != null) {
                            byte[] image = data.getByteArrayExtra("IMAGE");
                            String title = data.getStringExtra("TITLE");
                            String description = data.getStringExtra("DESCRIPTION");
                            int imageId = data.getIntExtra("IMAGE_ID", -1);

                            MyImages myImage = new MyImages(title, description, image);

                            viewModel.delete(myImage);

                            myImage.setImageId(imageId);

                            viewModel.insert(myImage);
                        }
                    }
                });
    }
}