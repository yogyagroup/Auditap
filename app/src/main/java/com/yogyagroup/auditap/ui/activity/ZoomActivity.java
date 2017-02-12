package com.yogyagroup.auditap.ui.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.yogyagroup.auditap.R;

/**
 * Created by jimmy <jimmy.chandra@yogyagroup.com> on 12-02-2017.
 */

public class ZoomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom);

        String imageLink = getIntent().getStringExtra("image_link");
        WebView webView = (WebView) findViewById(R.id.webview_zoom);
        WebSettings settings = webView.getSettings();
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);

        webView.setBackgroundColor(Color.BLACK);
        webView.loadUrl(imageLink);

        setTitle(getIntent().getStringExtra("customer_name"));

        Toast.makeText(this, "Pinch to zoom the image", Toast.LENGTH_SHORT).show();
    }
}
