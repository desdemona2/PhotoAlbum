package com.redheadhammer.photoalbum;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.redheadhammer.photoalbum.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MyImagesViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        MyImagesAdapter adapter = new MyImagesAdapter();
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));
        binding.recycler.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(MyImagesViewModel.class);

        viewModel.getAllImages().observe(this, new Observer<List<MyImages>>() {
            @Override
            public void onChanged(List<MyImages> myImages) {
                adapter.setImageList(myImages);
                adapter.notifyDataSetChanged();
            }
        });



        binding.floatAdd.setOnClickListener(this::onFab);
    }

    private void onFab(View view) {

    }
}