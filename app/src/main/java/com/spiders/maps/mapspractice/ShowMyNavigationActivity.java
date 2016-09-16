package com.spiders.maps.mapspractice;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.spiders.maps.mapspractice.pojo.Retrofit;
import com.spiders.maps.mapspractice.retrofit.RetrofitService;
import com.spiders.maps.mapspractice.retrofit.ServiceGenerator;
import com.spiders.maps.mapspractice.utils.PolyUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowMyNavigationActivity extends MapActivity implements GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener,LocationListener {

    Location mylocation;
    GoogleApiClient mGoogleApiClient;
    LocationRequest mloLocationRequest;
    List<LatLng> decodedPath;
    LatLng destination;
    double intialLat;
    double intialLong;

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
    }

    @Override
    public void showMap()
    {

    }

    @Override
    public void drawRoute() {


        String url="/maps/api/directions/json?origin=" + mylocation.getLatitude() + "," + mylocation.getLongitude() + "&destination=" + getClickedlatlng().latitude + "," + getClickedlatlng().longitude + "&sensor=false&units=metric&mode=driving&alternatives=false";

        RetrofitService retrofitService= ServiceGenerator.createService(RetrofitService.class);

        Call<Retrofit> response=retrofitService.getDestinationLocationData(url);
        //Gson gson=new Gson();
        response.enqueue(new Callback<Retrofit>() {
           @Override
           public void onResponse(Call<Retrofit> call, Response<Retrofit> response) {
               Retrofit retrofit=response.body();
               //String lat=retrofit.getStatus();

               //Toast.makeText(getApplicationContext(),""+lat,Toast.LENGTH_LONG).show();

               //String polydata=retrofit.getRoutes().get(0).getLegs().get(0).getSteps().get(0).getPolyline().getPoints();

               //String endlatlocation=retrofit.getRoutes().get(0).getLegs().get(0).getSteps().get(0).getEndLocation().getLat().toString();

               //String endlnglocation=retrofit.getRoutes().get(0).getLegs().get(0).getSteps().get(0).getEndLocation().getLng().toString();

               //String startlatlocation=retrofit.getRoutes().get(0).getLegs().get(0).getSteps().get(0).getStartLocation().getLat().toString();

               //String startlnglocation=retrofit.getRoutes().get(0).getLegs().get(0).getSteps().get(0).getStartLocation().getLng().toString();

               //Toast.makeText(getApplicationContext(),""+polydata+", EndLat "+endlatlocation+" End Long "+endlnglocation+" StartLat "+startlatlocation+" start Lng "+startlnglocation,Toast.LENGTH_LONG).show();
             for(int i=0;i<retrofit.getRoutes().size();i++)
             {
                 for(int j=0;j<retrofit.getRoutes().get(i).getLegs().size();j++)
                 {
                     for(int k=0;k<retrofit.getRoutes().get(i).getLegs().get(j).getSteps().size();k++)
                     {

                         String polydata=retrofit.getRoutes().get(i).getLegs().get(j).getSteps().get(k).getPolyline().getPoints();
                         decodedPath = PolyUtil.decode(polydata);
                         getMap().addPolyline(new PolylineOptions().addAll(decodedPath).color(Color.MAGENTA));

                     }
                 }
             }

               destination=decodedPath.get(decodedPath.size()-1);
               getMap().addMarker(new MarkerOptions().position(destination).title("Destination"));

           }

           @Override
           public void onFailure(Call<Retrofit> call, Throwable t) {

           }
       });
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
    public void onConnected(@Nullable Bundle bundle) {
        mloLocationRequest=LocationRequest.create();
        mloLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mloLocationRequest.setInterval(10000);
        if (ActivityCompat.checkSelfPermission(ShowMyNavigationActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ShowMyNavigationActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,mloLocationRequest,this);

        mylocation=LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mylocation!= null) {

            //Toast.makeText(getApplicationContext(),"Intial Toast", Toast.LENGTH_LONG).show();
            //LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            // Toast.makeText(ShowMapActivity.this, "LAT : "+String.valueOf(mLastLocation.getLatitude())+"LONG: "+String.valueOf(mLastLocation.getLatitude()), Toast.LENGTH_SHORT).show();
            Log.e("TAG", "LAT : "+String.valueOf(mylocation.getLatitude())+"  LONG: "+String.valueOf(mylocation.getLongitude()));
            intialLat=mylocation.getLatitude();
            intialLong=mylocation.getLongitude();
            getMap().addMarker(new MarkerOptions().position(new LatLng(mylocation.getLatitude(),mylocation.getLongitude())).title("Source"));
            getMap().animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mylocation.getLatitude(),mylocation.getLongitude()),15.2f));
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

    @Override
    public void onLocationChanged(Location location) {
        if (location!= null)
        {
            //Toast.makeText(getApplicationContext(),"Second Toast", Toast.LENGTH_LONG).show();
            // LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            // Toast.makeText(ShowMapActivity.this, "LAT : "+String.valueOf(mLastLocation.getLatitude())+"LONG: "+String.valueOf(mLastLocation.getLatitude()), Toast.LENGTH_SHORT).show();
            Log.e("TAG", "LAT : "+String.valueOf(location.getLatitude())+"  LONG: "+String.valueOf(location.getLongitude()));
            getMap().addPolyline((new PolylineOptions()).add(new LatLng(intialLat,intialLong),new LatLng(location.getLatitude(),location.getLongitude())).color(Color.BLUE).width(15));
            //Toast.makeText(getApplicationContext(), String.valueOf(location.getLatitude())+":"+String.valueOf(location.getLongitude()), Toast.LENGTH_SHORT).show();
            //gMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(),location.getLongitude())).title("Marker"));
            //gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(),location.getLongitude()),15.2f));
            //gMap.addMarker(new MarkerOptions().position(new LatLng(intialLat,intialLong)).visible(false));
            intialLat=location.getLatitude();
            intialLong=location.getLongitude();
        }
    }
}
