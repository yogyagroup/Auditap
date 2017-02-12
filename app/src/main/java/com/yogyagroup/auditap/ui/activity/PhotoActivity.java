package com.yogyagroup.auditap.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.soundcloud.android.crop.Crop;
import com.yogyagroup.auditap.R;
import com.yogyagroup.auditap.di.component.ApplicationComponent;
import com.yogyagroup.auditap.di.component.DaggerPhotoComponent;
import com.yogyagroup.auditap.di.component.PhotoComponent;
import com.yogyagroup.auditap.di.module.PhotoModule;
import com.yogyagroup.auditap.model.Photo;
import com.yogyagroup.auditap.presenter.PhotoPresenter;
import com.yogyagroup.auditap.ui.adapter.PhotoListAdapter;
import com.yogyagroup.auditap.ui.view.PhotoView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Random;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jimmy <jimmy.chandra@yogyagroup.com> on 12-02-2017.
 */

public class PhotoActivity extends BaseActivity implements PhotoView {

    private final String TAG = PhotoActivity.class.getSimpleName();

    @Inject
    PhotoPresenter photoPresenter;

    @BindView(R.id.refreshLayoutPhoto)
    SwipeRefreshLayout refreshLayoutPhoto;
    @BindView(R.id.recyclerViewPhoto)
    RecyclerView recyclerViewPhoto;
    @BindView(R.id.progress_bar_photo)
    ProgressBar progressBarPhoto;

    int receiptId;

    @OnClick(R.id.button_photo_add)
    void addPhoto() {
        if (photoPresenter.isUploadAllowed()) {
            Crop.pickImage(PhotoActivity.this);
        } else {
            Toast.makeText(PhotoActivity.this, "Only maximum 3 memo are allowed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        ButterKnife.bind(this);
        photoPresenter.onCreate(savedInstanceState);

        receiptId = getIntent().getIntExtra("receipt_id", 1);
        photoPresenter.onRefresh(receiptId);

        refreshLayoutPhoto.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                photoPresenter.onRefresh(receiptId);
            }
        });

        setTitle(getIntent().getStringExtra("customer_name"));

    }

    @Override
    protected void setupComponent(ApplicationComponent appComponent) {
        PhotoComponent component = DaggerPhotoComponent.builder()
                .photoModule(new PhotoModule(this))
                .applicationComponent(appComponent)
                .build();

        component.inject(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == Crop.REQUEST_PICK) {
            if (data != null) {
                beginCrop(data.getData());
            }
        } else if (requestCode == Crop.REQUEST_CROP) {
            if (data != null) {
                handleCrop(resultCode, data);
            } else {
//                Toast.makeText(PhotoActivity.this, "Empty data", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Cancel crop or press back when selecting image
        }
    }

    public void beginCrop(Uri source) {
        File folder = new File(Environment.getExternalStorageDirectory() + "/memot");
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdir();
        }
        if (success) {
            // Folder created successfully
        } else {
            Toast.makeText(PhotoActivity.this, "Failure creating folder", Toast.LENGTH_SHORT).show();
            // Do something else on failure
        }
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-" + n + ".jpg";
        File file5 = new File(folder, fname);
        if (file5.exists()) {
            file5.delete();
        }

        Uri destination = Uri.fromFile(file5);
        Crop.of(source, destination).asSquare().start(PhotoActivity.this);
    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            final Uri uri = Crop.getOutput(result);
            Log.d(TAG, "handleCrop: data uri: " + uri.getPath());

            Glide.with(this)
                    .load(uri)
                    .asBitmap()
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            photoPresenter.uploadPhoto(uri);
                        }
                    });
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void initializeToolbar() {

    }

    @Override
    public void showRefreshView() {
        refreshLayoutPhoto.post(new Runnable() {
            @Override
            public void run() {
                refreshLayoutPhoto.setRefreshing(true);
            }
        });
    }

    @Override
    public void hideRefreshView() {
        refreshLayoutPhoto.post(new Runnable() {
            @Override
            public void run() {
                refreshLayoutPhoto.setRefreshing(false);
            }
        });
    }

    @Override
    public void showUploadProgress() {
        progressBarPhoto.setVisibility(ProgressBar.VISIBLE);
    }

    @Override
    public void hideUploadProgress() {
        progressBarPhoto.setVisibility(ProgressBar.GONE);
    }

    @Override
    public void initializeRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerViewPhoto.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void initializeAdapter(PhotoListAdapter adapter) {
        recyclerViewPhoto.setAdapter(adapter);
    }

    @Override
    public void showMoreOptions(final Photo photo, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(PhotoActivity.this);
        LayoutInflater inflater = LayoutInflater.from(PhotoActivity.this);

        View dialogView = inflater.inflate(R.layout.dialog_overflow, null);
        builder.setView(dialogView);

        final AlertDialog alertDialog = builder.create();

        TextView deleteView = (TextView)dialogView.findViewById(R.id.view_delete);
        deleteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder deleteDialogBuilder = new AlertDialog.Builder(PhotoActivity.this);
                final AlertDialog deleteDialog = deleteDialogBuilder.create();
                deleteDialog.setTitle("Delete confirmation");
                deleteDialog.setMessage("Are you sure to delete this memo?");
                deleteDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteDialog.dismiss();
                        alertDialog.dismiss();
                    }
                });
                deleteDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        photoPresenter.deletePhoto(photo, position);
                        deleteDialog.dismiss();
                        alertDialog.dismiss();
                    }
                });
                deleteDialog.show();
            }
        });

        TextView cancelView = (TextView)dialogView.findViewById(R.id.view_cancel_delete_photo);
        cancelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }

    @Override
    public void showDeleteProgress() {

    }

    @Override
    public void hideDeleteProgress() {

    }
}
