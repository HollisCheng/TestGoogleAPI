package google.hollischeng.com.googleapi;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Location myLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        Button BtnSave = (Button) findViewById(R.id.BtnSave);
        BtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myLocation = mMap.getMyLocation();
                getCurrentLocation();
            }
        });
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);

        // Add a marker in Sydney and move the camera
//        LatLng hk = new LatLng(22.2479, 114.2034);
        LatLng hk = new LatLng(22.2179, 114.2134);
        mMap.addMarker(new MarkerOptions().position(hk).title("Marker in HK"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hk, 14));
    }

    private void getCurrentLocation() {
// Instantiates a new Polyline object and adds points to define a rectangle
//        PolylineOptions rectOptions = new PolylineOptions()
//                .add(new LatLng(22.2179, 114.2134))
//                .add(new LatLng(22.3, 114.7))  // North of the previous point, but at the same longitude
//                .add(new LatLng(22.2379, 114.2334))  // Same latitude, and 30km to the west
//                .add(new LatLng(22.8, 114.9))// Same longitude, and 16km to the south
//                .add(new LatLng(22.2579, 114.2534)); // Closes the polyline.

// Get back the mutable Polyline
//        Polyline polyline = mMap.addPolyline(rectOptions);
        Polyline line = mMap.addPolyline(new PolylineOptions()
                .add(new LatLng(22.2179, 114.2134),
                        new LatLng(22.3, 114.7),
                        new LatLng(22.2379, 114.2334),
                        new LatLng(22.8, 114.9),
                        new LatLng(22.2579, 114.2534))
                .width(10)
                .color(Color.BLUE)
                .geodesic(true));

        if (myLocation != null) {
            double dLatitude = myLocation.getLatitude();
            double dLongitude = myLocation.getLongitude();
            Log.i("APPLICATION", "Latitude:" + dLatitude);
            Log.i("APPLICATION", "Longitude:" + dLongitude);

        } else {
            Toast.makeText(this, "Unable to fetch the current location", Toast.LENGTH_SHORT).show();
        }

    }
}
