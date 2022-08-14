package com.redheadhammer.photoalbum;

import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyImagesAdapter extends RecyclerView.Adapter<MyImagesAdapter.MyViewHolder> {

    private List<MyImages> allImages = new ArrayList<>();
    private OnItemClickListener onItemClickListener;
    public MyImages getImageAtPosition(int position) {
        return allImages.get(position);
    }

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
        MyImages myImage = allImages.get(position);

        holder.title.setText(myImage.getImageTitle());
        holder.description.setText(myImage.getImageDescription());

        // decode array will take byte array, starting index (starting point for image decoding)
        // and ending index to end decoding image.
        holder.imageView.setImageBitmap(BitmapFactory.decodeByteArray(myImage.getImage(), 0,
                myImage.getImage().length));
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

            itemView.setOnClickListener(this::clickView);
        }
        public void clickView(View view) {
            onItemClickListener.OnItemClick(getAdapterPosition());
        }
    }

    public void setImageList(List<MyImages> myImagesList) {
        this.allImages = myImagesList;
    }

    public interface OnItemClickListener {
        void OnItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener clickListener){
        this.onItemClickListener = clickListener;
    }
}
