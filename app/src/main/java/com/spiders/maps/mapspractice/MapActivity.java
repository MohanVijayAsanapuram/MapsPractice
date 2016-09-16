package com.spiders.maps.mapspractice;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

public abstract class MapActivity extends AppCompatActivity implements OnMapReadyCallback{

    private GoogleMap gmap;

    private LatLng clickedlatlng;

    protected int getLayoutId()
    {
        return R.layout.map;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        setUpMap();
    }

    public void setUpMap()
    {
        ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.basicmap)).getMapAsync(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMap();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if(gmap!=null)
        {
            return;
        }
        gmap=googleMap;
        gmap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        gmap.setTrafficEnabled(true);
        gmap.getUiSettings().setZoomControlsEnabled(true);
       // gmap.setOnMarkerClickListener(this);
        gmap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Toast.makeText(MapActivity.this, "Onclick.."+latLng.latitude+","+latLng.longitude, Toast.LENGTH_SHORT).show();

                clickedlatlng=latLng;
                drawRoute();
            }
        });
        Toast.makeText(MapActivity.this, "OnMapReady..", Toast.LENGTH_SHORT).show();
        if (ActivityCompat.checkSelfPermission(MapActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        googleMap.setMyLocationEnabled(true);
        showMap();

    }

    public abstract void showMap();

    public abstract void drawRoute();

    protected GoogleMap getMap() {
        return gmap;
    }

    protected LatLng getClickedlatlng()
    {
        return clickedlatlng;
    }


}
