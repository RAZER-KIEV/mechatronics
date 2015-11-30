package ua.kiev.netmaster.agro.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import ua.kiev.netmaster.agro.R;
import ua.kiev.netmaster.agro.domain.Zone;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LatLngBounds bounds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
        Log.d(LoginActivity.LOG, "MapsActivity. onMapReady()");
        mMap = googleMap;
        Double minLat = 90.0, minLong= 180.0, maxLat=-90.0, maxLong=-180.0;
        for(Zone zone: ActivityListView.getZoneList()){

           try {
               Double curLat = Double.parseDouble(zone.getLat());
               Double curLng = Double.parseDouble(zone.getLng());
               if (curLat < minLat) minLat = curLat;
               if (curLng < minLong) minLong = curLng;
               if (curLat > maxLat) maxLat = curLat;
               if (curLng > maxLong) maxLong = curLng;
               LatLng curLatLong = new LatLng(Double.parseDouble(zone.getLat()), Double.parseDouble(zone.getLng()));
               mMap.addMarker(new MarkerOptions().position(curLatLong).title(zone.getName()));

           }catch (Exception e){
               e.printStackTrace();
           }
        }

        // Add a marker in Sydney and move the camera
       /* LatLng kiev = new LatLng(50, 30);
        mMap.addMarker(new MarkerOptions().position(kiev).title("Marker near UA min"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(52,32)).title("Marker near UA max"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(kiev, 0.1f));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
        LatLng min = new LatLng(minLat, minLong);
        LatLng max = new LatLng(maxLat, maxLong);
        bounds =new LatLngBounds(min, max);


        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 150);
        Log.d(LoginActivity.LOG, "minLat= " + minLat + ", minLong= " + minLong + ", maxLat= " + maxLat + ", maxLong= " + maxLong);
        try{
            mMap.animateCamera(cameraUpdate);
        }catch (Exception e){
            Log.d(LoginActivity.LOG, "MapsActivity. onMapReady(). catch!");
            e.printStackTrace();
            mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                @Override
                public void onMapLoaded() {
                    mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 150));
                }
            });
        }
        //Log.d(MainActivity.LOG, " min/max zoom" + mMap.getMinZoomLevel() + mMap.getMaxZoomLevel());

    }
}
