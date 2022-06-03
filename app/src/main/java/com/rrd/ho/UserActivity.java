package com.rrd.ho;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.rrd.ho.databinding.ActivityMainBinding;

public class UserActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private WebView webView;
    private BottomNavigationView navigationView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        webView = findViewById(R.id.web_view);
        navigationView = findViewById(R.id.nav_view);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        // Other webview options
        webView.getSettings().setLoadWithOverviewMode(true);

        //webView.getSettings().setUseWideViewPort(true);

        //Other webview settings

        webSettings.setBuiltInZoomControls(true);
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setAllowFileAccess(true);
        webSettings.setSupportZoom(true);
        webSettings.setAppCachePath(getApplicationContext().getFilesDir().getAbsolutePath() + "/cache");
        webSettings.setDatabasePath(getApplicationContext().getFilesDir().getAbsolutePath() + "/databases");



//        webView.setWebViewClient(new UserActivity.MyWebViewClient());
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.contains("https://historicalobject.skom.id/Auth")) {
                    Intent i = new Intent(UserActivity.this,MainActivity.class);
                    startActivity(i);
                    finish();
                }
                return false;
            }

        });


        webView.loadUrl("http://historicalobject.skom.id/user/home");

        navigationView.setOnItemReselectedListener(new NavigationBarView.OnItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        webView.loadUrl("https://historicalobject.skom.id/user/home");
                        break;
                    case R.id.navigation_kategori:
                        webView.loadUrl("https://historicalobject.skom.id/user/kategori");
                        break;
                    case R.id.navigation_kuis:
                        webView.loadUrl("https://historicalobject.skom.id/user/kuis");
                        break;
                    case R.id.navigation_profile:
                        webView.loadUrl("https://historicalobject.skom.id/user/profile");
                        break;
                    case R.id.navigation_logout:
                        webView.loadUrl("https://historicalobject.skom.id/Auth/logout");

                }


            }
        });


    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            if ("historicalobject.skom.id".equals(request.getUrl().getHost())) {
                // This is my website, so do not override; let my WebView load the page
                return false;
            }
            // Otherwise, the link is not for a page on my site, so launch another Activity that handles URLs
            Intent intent = new Intent(Intent.ACTION_VIEW, request.getUrl());
            startActivity(intent);
            return true;
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            // ignore ssl error
            if (handler != null){
                handler.proceed();
            } else {
                super.onReceivedSslError(view, null, error);
            }

        }
    }

}