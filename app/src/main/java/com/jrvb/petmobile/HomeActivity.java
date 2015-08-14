package com.jrvb.petmobile;

import android.content.IntentSender;
import android.graphics.Color;
import android.location.Geocoder;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.identity.intents.Address;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        SeekBar.OnSeekBarChangeListener {

    private GoogleApiClient mGoogleApiClient;
    GoogleMap _map;
    public static final String TAG = "jonas_tag_maps";
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private LocationRequest mLocationRequest;
    private Location oldLocation;
    private LatLng _llActual;
    Circle myCircle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        SeekBar bar = (SeekBar)findViewById(R.id.seekRadiusKM); // make seekbar object
        bar.setOnSeekBarChangeListener(this); // set seekbar listener.

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(120 * 1000)        // 2min seconds, in milliseconds
                .setFastestInterval(60 * 1000); // 1min second, in milliseconds

        //TextView loggedUser = (TextView)findViewById(R.id.nameLoggedUser);
        //SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        //loggedUser.setText("Email: " + settings.getString("email", "NOTFOUND"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        _map = googleMap;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (location == null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
        else {
            handleNewLocation(location);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
            Log.i(TAG, "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        handleNewLocation(location);
    }

    private void handleNewLocation(Location location) {
        if(oldLocation == null)
            oldLocation = location;
        else if (oldLocation.equals(location))
            return;

        _map.clear();

        double currentLatitude = location.getLatitude();
        double currentLongitude = location.getLongitude();
        LatLng latLng = new LatLng(currentLatitude, currentLongitude);
        _llActual = latLng;

        MarkerOptions options = new MarkerOptions()
            .position(latLng)
            .title("Você esta aqui!");

        _map.addMarker(options);
        _map.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        Geocoder geocoder;
        geocoder = new Geocoder(this, Locale.getDefault());

        List<android.location.Address> addresses = null; // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        try {
            addresses = geocoder.getFromLocation(currentLatitude, currentLongitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        String city = addresses.get(0).getLocality();
        String state = addresses.get(0).getAdminArea();
        String country = addresses.get(0).getCountryName();
        String postalCode = addresses.get(0).getPostalCode();
        String knownName = addresses.get(0).getFeatureName();

        TextView currentAddress = (TextView) findViewById(R.id.currentAddress);
        currentAddress.setText(address + " " + city + " " + state + " " + country);

        addCircle(latLng, 1000);
    }

    private void addCircle(LatLng latLng, double meters)
    {
        if(myCircle!=null){
            myCircle.remove();
        }

        if(meters < 3000 )
            _map.moveCamera(CameraUpdateFactory.zoomTo(140f));
        if(meters > 3000 && meters < 10000 )
            _map.moveCamera(CameraUpdateFactory.zoomTo(11.0f));
        else if(meters > 10000 && meters < 20000 )
            _map.moveCamera(CameraUpdateFactory.zoomTo(10.5f));
        else if(meters > 20000)
            _map.moveCamera(CameraUpdateFactory.zoomTo(10.0f));

        double radiusInMeters = meters;
        int strokeColor = 0x0000FF00; //red outline
        int shadeColor = 0x4400FF00; //opaque red fill

        CircleOptions circleOptions = new CircleOptions().center(latLng).radius(radiusInMeters).fillColor(shadeColor).strokeColor(strokeColor).strokeWidth(8);
        myCircle = _map.addCircle(circleOptions);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        addCircle(_llActual, progress * 1000);

        TextView seekNow = (TextView)findViewById(R.id.seekKMShowing);
        seekNow.setText("Procurar por "+ progress +" km");
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
