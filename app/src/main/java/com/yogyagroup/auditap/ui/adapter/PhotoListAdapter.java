package com.yogyagroup.auditap.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.yogyagroup.auditap.BuildConfig;
import com.yogyagroup.auditap.R;
import com.yogyagroup.auditap.model.Photo;
import com.yogyagroup.auditap.model.Receipt;
import com.yogyagroup.auditap.ui.activity.ZoomActivity;
import com.yogyagroup.auditap.ui.view.PhotoView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jimmy <jimmy.chandra@yogyagroup.com> on 12-02-2017.
 */

public class PhotoListAdapter extends RecyclerView.Adapter<PhotoListAdapter.PhotoListViewHolder> {
    private final String TAG = PhotoListAdapter.class.getSimpleName();
    private ArrayList<Photo> photos;
    private Context context;
    private PhotoView photoView;

    public PhotoListAdapter(ArrayList<Photo> photos, Context context) {
        this.photos = photos;
        this.context = context;

        try {
            this.photoView = (PhotoView) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("Context must implement PhotoView");
        }
    }

    @Override
    public PhotoListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PhotoListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo, parent, false));
    }

    @Override
    public void onBindViewHolder(final PhotoListViewHolder holder, final int position) {
        final Photo photo = photos.get(position);
        final Receipt receipt = photo.getReceipt();

        holder.viewMemoTitle.setText(receipt.getLeasing() + "  " + receipt.getDate() + " DP: " + receipt.getDp_po());
        holder.viewMemoNotes.setText(receipt.getBrand() + " " + receipt.getColor());
        holder.imageOverflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                photoView.showMoreOptions(photo, holder.getAdapterPosition());
            }
        });

        final String BASE_URL = BuildConfig.API_URL + "files/";
        if (!photo.getPath().isEmpty() && URLUtil.isValidUrl(BASE_URL + photo.getPath())) {
            Picasso
                    .with(context)
                    .load(BASE_URL + photo.getPath())
                    .placeholder(R.drawable.photo_placeholder)
                    .resize(500,500)
                    .into(holder.imagePhoto);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ZoomActivity.class);
                intent.putExtra("customer_name", receipt.getName());
                intent.putExtra("image_link", BASE_URL + photo.getPath());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    class PhotoListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image_photo)
        ImageView imagePhoto;
        @BindView(R.id.view_memo_title)
        TextView viewMemoTitle;
        @BindView(R.id.view_memo_notes)
        TextView viewMemoNotes;
        @BindView(R.id.image_overflow)
        ImageView imageOverflow;

        public PhotoListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
