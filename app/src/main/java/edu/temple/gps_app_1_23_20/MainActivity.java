package edu.temple.gps_app_1_23_20;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    LocationManager locationManager;
    LocationListener locationListener;

    TextView lat, lon;
    Button requestPermBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lat = findViewById(R.id.textView);
        lon = findViewById(R.id.textView2);
        requestPermBtn = findViewById(R.id.requestPermBtn);

        locationManager = getSystemService(LocationManager.class);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                lat.setText(String.valueOf(location.getLatitude()));
                lon.setText(String.valueOf(location.getLongitude()));
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
        };

        requestPermBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Permission check
                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    // As soon as user requests permission, we want information to be updated, have to use onRequestPermissionsResult() callback below
                    registerLocation();
                } else {
                    // Request permission
                    requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 123);
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        // Android generated permission check for Alt + Enter hint
//        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    Activity#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for Activity#requestPermissions for more details.
//            return;
//        }
    }

    @SuppressLint("MissingPermission") // Lint to ignore warning since we know code will have checked permission
    private void registerLocation() {
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
    }

    // Gets invoked when user allows or denies permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            registerLocation();
        } else {
            // Tell user why they need to turn on feature
            // shouldShowRequestPermissionRationale()
        }
    }
}


