package com.example.noraiz.foodex;


import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements LocationListener {

    private static final String TAG = "TESTing";
    double lat = 0;
    double lon = 0;
    Rider rider;
    int count=0;
    TextView tv_lat, tv_long;
    private LocationManager locManager_;
    DatabaseReference myRef;
    String nodeId="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_lat= (TextView) findViewById(R.id.tv_lat);
        tv_long= (TextView) findViewById(R.id.tv_long);



        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef= database.getReference("Rider");


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //Rider rider= dataSnapshot.getValue(Rider.class);
//                String value = dataSnapshot.getValue(String.class);
                //Log.d(TAG, "Value is: " + rider);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


        locManager_ = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        //locManager_.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10,
        //    Criteria.ACCURACY_FINE, this);
        locManager_.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10,
                Criteria.ACCURACY_FINE, this);


        Intent intent= new Intent(this, OrderLaterService.class);
        intent.setAction("some constant string");
        intent.putExtra("a", 1);
        intent.putExtra("b", 2);
        startService(intent);

        IntentFilter filter = new IntentFilter();
        filter.addAction("action");
        registerReceiver(new OrderReceiverClass(), filter);


    }

    @Override
    public void onLocationChanged(Location location) {
        double altitude = location.getAltitude();
        double longitude = location.getLongitude();
        double latitude = location.getLatitude();
        float speed = location.getSpeed();
        float bearing = location.getBearing();
        float accuracy = location.getAccuracy(); //in meters
        long time = location.getTime();
        Toast.makeText(this, ""+latitude, Toast.LENGTH_LONG).show();
        int riderId=1;
        rider = new Rider( riderId,latitude, longitude);
        myRef.setValue(rider);

    }

    protected void onPause() {
        super.onPause();
        locManager_.removeUpdates(this);
    }

    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locManager_.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10,
                Criteria.ACCURACY_FINE, this);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    private class OrderReceiverClass extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
        // handle the received broadcast message

        }
    }

}

