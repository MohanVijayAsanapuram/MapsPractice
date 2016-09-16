package com.spiders.maps.mapspractice;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.spiders.maps.mapspractice.utils.PolyUtil;

import java.util.List;

public class ShowMapActivity extends AppCompatActivity implements LocationListener,GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener{

    GoogleApiClient mGoogleApiClient;

    private Location mLastLocation;

    LocationRequest locationRequest;

    GoogleMap gMap;

    double intialLat;

    double intialLong;

    private Polyline  polyline;

    public static final String TAG = "TAG";

    private int marker_flag=0;

    private final static String LINE = "rvumEis{y[}DUaBGu@EqESyCMyAGGZGdEEhBAb@DZBXCPGP]Xg@LSBy@E{@SiBi@wAYa@AQGcAY]I]KeBm@_Bw@cBu@ICKB}KiGsEkCeEmBqJcFkFuCsFuCgB_AkAi@cA[qAWuAKeB?uALgB\\eDx@oBb@eAVeAd@cEdAaCp@s@PO@MBuEpA{@R{@NaAHwADuBAqAGE?qCS[@gAO{Fg@qIcAsCg@u@SeBk@aA_@uCsAkBcAsAy@AMGIw@e@_Bq@eA[eCi@QOAK@O@YF}CA_@Ga@c@cAg@eACW@YVgDD]Nq@j@}AR{@rBcHvBwHvAuFJk@B_@AgAGk@UkAkBcH{@qCuAiEa@gAa@w@c@o@mA{Ae@s@[m@_AaCy@uB_@kAq@_Be@}@c@m@{AwAkDuDyC_De@w@{@kB_A}BQo@UsBGy@AaA@cLBkCHsBNoD@c@E]q@eAiBcDwDoGYY_@QWEwE_@i@E}@@{BNaA@s@EyB_@c@?a@F}B\\iCv@uDjAa@Ds@Bs@EyAWo@Sm@a@YSu@c@g@Mi@GqBUi@MUMMMq@}@SWWM]C[DUJONg@hAW\\QHo@BYIOKcG{FqCsBgByAaAa@gA]c@I{@Gi@@cALcEv@_G|@gAJwAAUGUAk@C{Ga@gACu@A[Em@Sg@Y_AmA[u@Oo@qAmGeAeEs@sCgAqDg@{@[_@m@e@y@a@YIKCuAYuAQyAUuAWUaA_@wBiBgJaAoFyCwNy@cFIm@Bg@?a@t@yIVuDx@qKfA}N^aE@yE@qAIeDYaFBW\\eBFkANkANWd@gALc@PwAZiBb@qCFgCDcCGkCKoC`@gExBaVViDH}@kAOwAWe@Cg@BUDBU`@sERcCJ{BzFeB";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create an instance of GoogleAPIClient.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();


        setContentView(R.layout.map);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/



       // GoogleMap map
        SupportMapFragment mapFragment=(SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.basicmap);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                gMap=googleMap;
                gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                gMap.setTrafficEnabled(true);
                gMap.getUiSettings().setZoomControlsEnabled(true);
                //gMap.setOnMarkerClickListener(this);

                // googleMap.addMarker(new MarkerOptions().position(new LatLng(17.4375,78.4483)).title("Marker"));
                //googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(17.4375,78.4483),15.2f));
                Toast.makeText(ShowMapActivity.this, "OnMapReady..", Toast.LENGTH_SHORT).show();
                if (ActivityCompat.checkSelfPermission(ShowMapActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ShowMapActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }


    @Override
    public void onLocationChanged(Location location) {
        if (location!= null)
        {


            Toast.makeText(getApplicationContext(),"Second Toast", Toast.LENGTH_LONG).show();
            // LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            // Toast.makeText(ShowMapActivity.this, "LAT : "+String.valueOf(mLastLocation.getLatitude())+"LONG: "+String.valueOf(mLastLocation.getLatitude()), Toast.LENGTH_SHORT).show();
            Log.e("TAG", "LAT : "+String.valueOf(location.getLatitude())+"  LONG: "+String.valueOf(location.getLongitude()));
            gMap.addPolyline((new PolylineOptions()).add(new LatLng(intialLat,intialLong),new LatLng(location.getLatitude(),location.getLongitude())).color(Color.BLUE).width(15));
            Toast.makeText(getApplicationContext(), String.valueOf(location.getLatitude())+":"+String.valueOf(location.getLongitude()), Toast.LENGTH_SHORT).show();
            //gMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(),location.getLongitude())).title("Marker"));
            //gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(),location.getLongitude()),15.2f));
            //gMap.addMarker(new MarkerOptions().position(new LatLng(intialLat,intialLong)).visible(false));
            intialLat=location.getLatitude();
            intialLong=location.getLongitude();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.e("TAG", "Connected");


        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        if (ActivityCompat.checkSelfPermission(ShowMapActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ShowMapActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationRequest, this);

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation!= null) {

            Toast.makeText(getApplicationContext(),"Intial Toast", Toast.LENGTH_LONG).show();
            //LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            // Toast.makeText(ShowMapActivity.this, "LAT : "+String.valueOf(mLastLocation.getLatitude())+"LONG: "+String.valueOf(mLastLocation.getLatitude()), Toast.LENGTH_SHORT).show();
            Log.e("TAG", "LAT : "+String.valueOf(mLastLocation.getLatitude())+"  LONG: "+String.valueOf(mLastLocation.getLongitude()));
            intialLat=mLastLocation.getLatitude();
            intialLong=mLastLocation.getLongitude();
            gMap.addMarker(new MarkerOptions().position(new LatLng(mLastLocation.getLatitude(),mLastLocation.getLongitude())).title("Marker"));
            gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLastLocation.getLatitude(),mLastLocation.getLongitude()),15.2f));
        }
        else {
            Toast.makeText(getApplicationContext()," LastLocation is null", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
