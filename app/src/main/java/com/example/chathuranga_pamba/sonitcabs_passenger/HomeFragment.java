package com.example.chathuranga_pamba.sonitcabs_passenger;


import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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


/**
 * A simple {@link Fragment} subclass.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.vision.barcode.Barcode;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Locale;
import static com.example.chathuranga_pamba.sonitcabs_passenger.CommonUtilities.SERVER_URL;


/**
 * A fragment that launches other parts of the demo application.
 */
public class HomeFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    MapView mMapView;
    private GoogleMap googleMap;

    private LatLng center;
    private TextView markerText;
    private LinearLayout markerLayout,destinationTextLayout;
    Button setLocationButton;
    ImageButton btCancel;

    boolean isVisible = true;

    Geocoder geocoder;

    TextView tvAddress;






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflate and return the layout
        View v = inflater.inflate(R.layout.fragment_home, container,false);
        mMapView = (MapView) v.findViewById(R.id.mapp);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume();// needed to get the map to display immediately

        System.out.println("_______________________________________________________");

        Geocoder geocoder = new Geocoder(getActivity().getApplicationContext());
        double latitude = 6.90229208;
        double longitude = 79.86143364;

        List<Address> addresses = null;
        String addressText="";

        try {
            addresses = geocoder.getFromLocation(latitude, longitude,1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(addresses != null && addresses.size() > 0 ){
            Address address = addresses.get(0);

            addressText = String.format("%s, %s, %s",
                    address.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0) : "",
                    address.getLocality(),
                    address.getCountryName());
        }else{
            System.out.println("not address");
        }






        System.out.println("_______________________________________________________");



        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        googleMap = mMapView.getMap();
        // latitude and longitude
        double l1 = 17.385044;
        double l2 = 78.486671;


        // create marker
        MarkerOptions marker = new MarkerOptions().position(
                new LatLng(l1, l2)).title("Hello Maps");

        // Changing marker icon
        //marker.icon(BitmapDescriptorFactory                .defaultMarker(BitmapDescriptorFactory.HUE_ROSE));

        // adding marker
        //googleMap.addMarker(marker);
        CameraPosition cameraPosition = new CameraPosition.Builder()                .target(new LatLng(6.90229208, 79.86143364)).zoom(18).build();
        googleMap.animateCamera(CameraUpdateFactory                .newCameraPosition(cameraPosition));


        //markerText = (TextView) v.findViewById(R.id.locationMarkertext);
        tvAddress = (TextView) v.findViewById(R.id.adressText);
        markerLayout = (LinearLayout) v.findViewById(R.id.locationMarker);

        destinationTextLayout = (LinearLayout) v.findViewById(R.id.destinationLayout);
        destinationTextLayout.setVisibility(View.INVISIBLE);

        setLocationButton  =(Button) v.findViewById(R.id.locationMarkerButton);
        btCancel =(ImageButton) v.findViewById(R.id.btclose);

        LatLng latLong = new LatLng(6.90229208,79.861433647);
        CameraPosition co = new CameraPosition.Builder()
                .target(latLong).zoom(19f).tilt(7).build();

        googleMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {

            @Override
            public void onCameraChange(CameraPosition arg0) {

                center = googleMap.getCameraPosition().target;

                //markerText.setText(" Set your Location ");

                if (isVisible){
                    googleMap.clear();
                    markerLayout.setVisibility(View.VISIBLE);
                    System.out.println("fdsafdsfdsfsd" + String.valueOf(center.latitude) + " " + String.valueOf(center.longitude));
                    //tvAddress.setText(String.valueOf(center.latitude) + " " + String.valueOf(center.longitude));
                    /*System.out.println("___________________________________________________________________");

                    System.out.println("_______________________________________________________");

                    Geocoder geocoder = new Geocoder(getActivity().getApplicationContext());
                    double latitude = center.latitude;
                    double longitude = center.longitude;

                    List<Address> addresses = null;
                    String addressText="";

                    try {
                        addresses = geocoder.getFromLocation(latitude, longitude,1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if(addresses != null && addresses.size() > 0 ){
                        Address address = addresses.get(0);

                        addressText = String.format("%s, %s, %s",
                                address.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0) : "",
                                address.getLocality(),
                                address.getCountryName());
                    }else{
                        System.out.println("not address");
                    }






                    System.out.println("_______________________________________________________");*/

                   // System.out.println(getAddressFromGPSData(center.latitude, center.longitude));
                    String getAddress = "http://maps.googleapis.com/maps/api/geocode/json?latlng=" + center.latitude + ","
                            + center.longitude + "&sensor=true";


                    RequestPackage p = new RequestPackage();
                    p.setMethod("GET");
                    p.setUri(getAddress);


                    Log.e("Chahturanga      URLgo", getAddress);
                    GetAddressTask task = new GetAddressTask();
                    task.execute(p);




                    System.out.println("___________________________________________________________________");




                }




            }
        });

        setLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isVisible) {
                    markerLayout.setVisibility(View.INVISIBLE);
                    isVisible = false;
                }
                System.out.println(center.latitude);
                MarkerOptions m = new MarkerOptions().position(new LatLng((center.latitude),(center.longitude))).title("pickup");
                m.icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                googleMap.addMarker(m);




                //getAddress

                String getAddress = "http://maps.googleapis.com/maps/api/geocode/json?latlng=" + center.latitude + ","
                        + center.longitude + "&sensor=true";


                RequestPackage p = new RequestPackage();
                p.setMethod("GET");
                p.setUri(getAddress);


                Log.e("Chahturanga      URLgo", getAddress);
                GetAddressTask task = new GetAddressTask();
                task.execute(p);




                System.out.println("___________________________________________________________________");


                destinationTextLayout.setVisibility(View.VISIBLE);



            }
        });

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                markerLayout.setVisibility(View.VISIBLE);

                isVisible = true;
                googleMap.clear();
                destinationTextLayout.setVisibility(View.INVISIBLE);

            }
        });



        // Perform any camera updates here
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }



    ////
    private class GetAddressTask extends AsyncTask<RequestPackage,String,String> {
        //AlertDialogManager alert = new AlertDialogManager();

        String formattedAddress = "";


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //pbLogin.setVisibility(View.VISIBLE);
            //btLogin.setEnabled(false);

        }



        @Override
        protected String doInBackground(RequestPackage... params) {

            String content = HttpManager.getData(params[0]);
            Log.e("Chahturanga    content",content);
            return content;
        }



        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }



        @Override
        protected void onPostExecute(String result) {

            if (result != null) {
                Log.d("GeoCoder", result);
                try {
                    JSONObject parentObject = new JSONObject(result);
                    JSONArray arrayOfAddressResults = parentObject
                            .getJSONArray("results");
                    JSONObject addressItem = arrayOfAddressResults.getJSONObject(0);
                    formattedAddress = addressItem.getString("formatted_address");
                } catch (JSONException e) {

                    e.printStackTrace();
                }

            }

            tvAddress.setText( formattedAddress);


        }


    }





}