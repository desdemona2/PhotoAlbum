package com.redheadhammer.photoalbum;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyImagesAdapter extends RecyclerView.Adapter<MyImagesAdapter.MyViewHolder> {

    private List<MyImages> allImages = new ArrayList<>();

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.image_card, parent, false
        );
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//        holder.imageView.set
    }

    @Override
    public int getItemCount() {
        return allImages.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title, description;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
        }
    }

    public void setImageList(List<MyImages> myImagesList) {
        this.allImages = myImagesList;
    }
}
