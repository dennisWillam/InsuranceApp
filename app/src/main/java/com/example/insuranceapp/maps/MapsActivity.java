package com.example.insuranceapp.maps;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.insuranceapp.R;
import com.example.insuranceapp.databinding.ActivityMapsBinding;

import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;


public class MapsActivity extends AppCompatActivity{

    private ActivityMapsBinding binding;
    private DrawerLayout drawer;
    MapController mapController;
    MapView mapView;
    MyLocationNewOverlay myLocationNewOverlay;
    GpsMyLocationProvider gpsMyLocationProvider;
    EditText latET, lonET;
    Button btn;
    Double latCurrentPosition=0.0, longCurrentPosition=0.0;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        accessPermission();

        latET = (EditText) findViewById(R.id.latitudeET);
        lonET = (EditText) findViewById(R.id.longitudeET);
        btn = (Button) findViewById(R.id.goBtn);


//        Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context));

        mapView = (MapView) findViewById(R.id.mapsMV);





        mapView.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE);
        Configuration.getInstance().setUserAgentValue(getPackageName());

        mapView.setBuiltInZoomControls(true);
        mapView.setMultiTouchControls(true);
        mapController = (MapController) mapView.getController();
        mapController.setZoom(15);


//try
       
//        end
        myLocationNewOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(getApplicationContext()), mapView);
        myLocationNewOverlay.enableMyLocation();
        mapView.getOverlays().add(this.myLocationNewOverlay);
        mapController.setCenter(myLocationNewOverlay.getMyLocation());

//        //-6.2283296341613354, 106.82636121990136
//        GeoPoint pointCenter = new GeoPoint(-6.2283296341613354, 106.82636121990136);
//
//        mapController.setCenter(pointCenter);
//        mapController.animateTo(pointCenter);
        GpsMyLocationProvider mGpsMyLocationProvider = new GpsMyLocationProvider(this);
        mGpsMyLocationProvider.addLocationSource(LocationManager.NETWORK_PROVIDER);

        MyLocationNewOverlay myLocationNewOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(this), mapView);
        myLocationNewOverlay.enableMyLocation();
        myLocationNewOverlay.setDrawAccuracyEnabled(true);
        mapView.getOverlays().add(myLocationNewOverlay);
        myLocationNewOverlay.runOnFirstFix(new Runnable() {
            @Override
            public void run() {
                if(myLocationNewOverlay.getMyLocation()!=null){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mapController.animateTo(myLocationNewOverlay.getMyLocation());
                            mapView.getController().animateTo(myLocationNewOverlay.getMyLocation());
                            latCurrentPosition = myLocationNewOverlay.getMyLocation().getLatitude();
                            longCurrentPosition = myLocationNewOverlay.getMyLocation().getLongitude();

                        }
                    });
                }
                        }



        });






        GeoPoint markerPoint = new GeoPoint(  -6.217813296426207, 106.83203762674869);
        Marker myMarkerPoint = new Marker(mapView);
        myMarkerPoint.setPosition(markerPoint);
        myMarkerPoint.setTitle("Mayapada Hospital Kuningan");
        myMarkerPoint.setIcon(this.getResources().getDrawable(R.drawable.ic_hospittt));
        myMarkerPoint.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        mapView.getOverlays().add(myMarkerPoint);

        GeoPoint markerPoint1 =  new GeoPoint(-6.22471293444516, 106.83296077618668);
        Marker myMarkerPoint1 = new Marker(mapView);
        myMarkerPoint1.setPosition(markerPoint1);
        myMarkerPoint1.setTitle("Prodia Kuningan");
        myMarkerPoint1.setIcon(this.getResources().getDrawable(R.drawable.ic_hospittt));
        myMarkerPoint1.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        mapView.getOverlays().add(myMarkerPoint1);

        MapEventsReceiver mapEventsReceiver = new MapEventsReceiver() {
            @Override
            public boolean singleTapConfirmedHelper(GeoPoint p) {
                Toast.makeText(getApplicationContext(), p.getLatitude()+"-"+p.getLongitude(), Toast.LENGTH_SHORT).show();
                latET.setText(String.valueOf(p.getLatitude()));
                lonET.setText(String.valueOf(p.getLongitude()));
                return false;
            }

            @Override
            public boolean longPressHelper(GeoPoint p) {
                return false;
            }
        };

        MapEventsOverlay mapEventsOverlay = new MapEventsOverlay(getApplicationContext(), mapEventsReceiver);
        mapView.getOverlays().add(mapEventsOverlay);



        binding.goBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapsActivity.this, MapsDirectionActivity.class);
                intent.putExtra("latTujuan", Double.parseDouble(latET.getText().toString()));
                intent.putExtra("lonTujuan", Double.parseDouble(lonET.getText().toString()));
                intent.putExtra("latAsal",latCurrentPosition);
                intent.putExtra("lonAsal", longCurrentPosition);
                startActivity(intent);
            }
        });

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }




    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fremleyout, fragment);
        fragmentTransaction.commit();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    void accessPermission(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.ICE_CREAM_SANDWICH &&
                checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){

            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1001);


        }
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.ICE_CREAM_SANDWICH &&
                checkSelfPermission(Manifest.permission.INTERNET)
                        != PackageManager.PERMISSION_GRANTED){

            requestPermissions(new String[]{Manifest.permission.INTERNET},
                    1002);


        }
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.ICE_CREAM_SANDWICH &&
                checkSelfPermission(Manifest.permission.ACCESS_NETWORK_STATE)
                        != PackageManager.PERMISSION_GRANTED){

            requestPermissions(new String[]{Manifest.permission.ACCESS_NETWORK_STATE},
                    1003);


        }
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.ICE_CREAM_SANDWICH &&
                checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED){

            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1004);


        }
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.ICE_CREAM_SANDWICH &&
                checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED){

            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    1005);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1001:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "Permission Granted Fine Location", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(this, "Permission Denied Fine Location", Toast.LENGTH_SHORT).show();
                }
                break;
            case 1002:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "Permission Granted Access Internet", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(this, "Permission Denied Access Internet", Toast.LENGTH_SHORT).show();
                }
                break;
            case 1003:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "Permission Granted Access Network State", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(this, "Permission Denied Access Network State", Toast.LENGTH_SHORT).show();
                }
                break;
            case 1004:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "Permission Granted Write External Storage", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(this, "Permission Denied Write External Storage", Toast.LENGTH_SHORT).show();
                }
                break;
            case 1005:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "Permission Granted Read External Storage", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(this, "Permission Denied Read External Storage", Toast.LENGTH_SHORT).show();
                }
                break;


        }
    }


}