package com.example.insuranceapp.maps;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.URLUtil;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.insuranceapp.R;
import com.example.insuranceapp.databinding.ActivityMapsDirectionBinding;


public class MapsDirectionActivity extends AppCompatActivity {
    WebView webvw;

    Double latTujuan=0.0, lonTujuan= 0.0, latAsal=0.0, lonAsal=0.0;

    private ActivityMapsDirectionBinding binding;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMapsDirectionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        webvw = (WebView) findViewById(R.id.webView);
        webvw.getSettings().setJavaScriptEnabled(true);

        WebSettings settings = webvw.getSettings();
        settings.setDomStorageEnabled(true);

        Intent intent = getIntent();
        latTujuan = intent.getDoubleExtra("latTujuan",0);
        lonTujuan = intent.getDoubleExtra("lonTujuan",0);
        latAsal = intent.getDoubleExtra("latAsal",0);
        lonAsal = intent.getDoubleExtra("lonAsal",0);

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        webvw.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view,  String url) {
                if(URLUtil.isNetworkUrl(url)){
                    return false;
                }
                if(appInstalledOrNot(url)){
                    Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps?"+"saddr="+latAsal+","+lonAsal+"&daddr="+latTujuan+","+lonTujuan));
                    intent1.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                    startActivity(intent1);
                    return true;


                }
                return false;
            }
        });

        webvw.loadUrl("https://www.google.com/maps?"+"saddr="+latAsal+","+lonAsal+"&daddr="+latTujuan+","+lonTujuan);
        Toast.makeText(this, "Loading Direction...", Toast.LENGTH_SHORT).show();
    }

    boolean appInstalledOrNot(String url){
        PackageManager pm = getPackageManager();
        try{
            pm.getPackageInfo(url, PackageManager.GET_ACTIVITIES);
            return true;
        }catch (PackageManager.NameNotFoundException e){


        }

        return true;

    }

}