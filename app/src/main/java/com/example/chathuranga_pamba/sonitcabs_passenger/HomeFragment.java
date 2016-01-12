package com.example.chathuranga_pamba.sonitcabs_passenger;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chathuranga_pamba.sonitcabs_passenger.Parsers.DirectionsJSONParser;
import com.example.chathuranga_pamba.sonitcabs_passenger.Parsers.PlaceJSONParser;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.chathuranga_pamba.sonitcabs_passenger.CommonUtilities.SERVER_URL;

/**
 * A simple {@link Fragment} subclass.
 */


/**
 * A fragment that launches other parts of the demo application.
 */
public class HomeFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener, View.OnTouchListener{
    static LatLng dropLoc = null;
    static String dropAddress = null;
    static boolean okfrag = false;


    MapView mMapView;
    private GoogleMap googleMap;

    private LatLng center;
    private TextView markerText;
    private LinearLayout markerLayout,destinationTextLayout;
    Button setLocationButton,btBook;
    ImageButton btCancel,btdroppoint;

    String estimateTime,estimateDistance;

    boolean isVisible = true;

    Geocoder geocoder;

    TextView tvAddress,tvDropOffplace;
    FrameLayout driverDetailContainer;
    float mLastPosisionY;

    AutoCompleteTextView atvDropOff;

    PlacesTask placesTask;
    ParserTask parserTask;

    String formattedAddress;

    AlertDialogManager alert = new AlertDialogManager();
    Polyline polyline;

    PreReservationFragment preReservationFragment;
    FragmentTransaction transaction;

    int customerID;
    View v;

    Timer timer;

    LatLng pickupLatLng;
    int i=0;

    ConnectionDetector cd;








    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //get data from home activity
         customerID = getArguments().getInt("CUSTOMERID");
        System.out.println("cusid->"+String.valueOf(customerID));

        // inflate and return the layout
         v = inflater.inflate(R.layout.fragment_home, container,false);
        mMapView = (MapView) v.findViewById(R.id.mapp);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume();// needed to get the map to display immediately

        System.out.println("_______________________________________________________");

        //Geocoder geocoder = new Geocoder(getActivity().getApplicationContext());
        double latitude = 6.90229208;
        double longitude = 79.86143364;

        //autocomplete









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
        googleMap.setMyLocationEnabled(true);

        GPSTracker gps = new GPSTracker(getActivity());
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(gps.getLatitude(), gps.getLongitude()), 15));



        // create marker
        MarkerOptions marker = new MarkerOptions().position(
                new LatLng(l1, l2)).title("Hello Maps");

        // Changing marker icon
        //marker.icon(BitmapDescriptorFactory                .defaultMarker(BitmapDescriptorFactory.HUE_ROSE));

        // adding marker
        //googleMap.addMarker(marker);
        //CameraPosition cameraPosition = new CameraPosition.Builder()                .target(new LatLng(6.90229208, 79.86143364)).zoom(18).build();
        //googleMap.animateCamera(CameraUpdateFactory                .newCameraPosition(cameraPosition));


        //markerText = (TextView) v.findViewById(R.id.locationMarkertext);
        tvAddress = (TextView) v.findViewById(R.id.adressText);
        tvDropOffplace = (TextView) v.findViewById(R.id.tvDropOffplace);
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

                if (isVisible) {
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

                pickupLatLng = new LatLng(center.latitude, center.longitude);
                MarkerOptions m = new MarkerOptions().position(new LatLng((center.latitude), (center.longitude))).title("pickup");
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

        final FrameLayout f = (FrameLayout) v.findViewById(R.id.bottemFramLayout);


        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                markerLayout.setVisibility(View.VISIBLE);

                isVisible = true;
                googleMap.clear();
                destinationTextLayout.setVisibility(View.INVISIBLE);
                btBook.setText("ESTIMATE FIRE");
                f.setVisibility(View.INVISIBLE);



            }
        });

        btBook = (Button) v.findViewById(R.id.btBook);
        btBook.setBackgroundColor(Color.GREEN);


        btBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if("ESTIMATE FIRE".equals(btBook.getText().toString())){
                    if(!tvDropOffplace.getText().toString().equals("")) {
                        //show paths
                        System.out.println("fdfdffddfdfffffffffff");


                        if(dropLoc != null){
                            cd = new ConnectionDetector(getActivity());

                            Log.e("Tag", String.valueOf(cd.isConnectingToInternet()));
                            if (!cd.isConnectingToInternet()) {
                                // Internet Connection is not present
                                alert.showAlertDialog(getActivity(),
                                        "Internet Connection Error",
                                        "Please connect to working Internet connection", false);
                                // stop executing code by return
                                return;
                            }else{
                                showPath(pickupLatLng.latitude,pickupLatLng.longitude,dropLoc.latitude,dropLoc.longitude);
                                btBook.setText("BOOK NOW");
                                btBook.setBackgroundColor(Color.BLUE);
                            }



                        }else{
                            alert.showAlertDialog(getActivity(),"Select DropOff","Please select drop off Location",true);

                        }







                                //btBook.setBackgroundColor();




                    }else {
                        alert.showAlertDialog(getActivity(), "Location not filled", "Fill correctly Locations", false);
                    }


                }else if("BOOK NOW".equals(btBook.getText().toString())){
                    System.out.println("dfd");
                    requestData();
                    btBook.setText("CANCEL");
                    btBook.setBackgroundColor(Color.RED);





                }else if("CANCEL".equals(btBook.getText().toString())) {


                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                    alertDialogBuilder.setMessage("Are you sure,You wanted to cancel reservation ");

                    alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            timer.cancel();

                            //todo CANCEL RESERVATION
                            Intent i = new Intent(getActivity(),HomeActivity.class);
                            startActivity(i);

                            btBook.setText("ESTIMATE FIRE");
                            btBook.setBackgroundColor(Color.GREEN);

                        }
                    });

                    alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();



                }
            }
        });



/*
        atvDropOff = (AutoCompleteTextView) v.findViewById(R.id.atv_dropoffplace);
        atvDropOff.setThreshold(1);
        atvDropOff.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                System.out.println("dropoff atv");
                placesTask = new PlacesTask();
                placesTask.execute(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

*/



        btdroppoint = (ImageButton) v.findViewById(R.id.btdroppoint);
        btdroppoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getActivity(), place.class);
                startActivity(i);


            }
        });

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {


                System.out.println(okfrag);
                if (okfrag == true) {
                    timer.cancel();
                    getAddressData();
                }

                if (i == 120) {
                    timer.cancel();
                }

                i++;


            }
        }, 1000, 1000);

















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


    public void setDetails(){


    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        System.out.println("Ontouch");

        switch (event.getAction()){

            case MotionEvent.ACTION_DOWN:
                mLastPosisionY = event.getY();
                return  true;
            case MotionEvent.ACTION_MOVE:
                System.out.println("move");
                float currentPossision = event.getY();
                float delaY = mLastPosisionY-currentPossision;
                float transY = v.getTranslationY();
                System.out.println(delaY);
                transY -= delaY;

                if(transY<0){
                    transY = 0;
                }

                return true;

            default:
                return  v.onTouchEvent(event);


        }

    }


    ////
    private class GetAddressTask extends AsyncTask<RequestPackage,String,String> {
        //AlertDialogManager alert = new AlertDialogManager();




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
                    tvAddress.setText(formattedAddress);




                } catch (JSONException e) {

                    e.printStackTrace();
                }

            }

        }


    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while( ( line = br.readLine()) != null){
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        }catch(Exception e){
            Log.d("Exception while ", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    private class PlacesTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... place) {
            // For storing data from web service
            String data = "";

            // Obtain browser key from https://code.google.com/apis/console
            String key = "key=AIzaSyBp8LncpU00p87iTEMsSTIOJJCTHHIcdl4";

            String input="";

            try {
                input = "input=" + URLEncoder.encode(place[0], "utf-8");
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }

            // place type to be searched
            String types = "types=geocode";

            // Sensor enabled
            String sensor = "sensor=false";

            // Building the parameters to the web service
            String parameters = input+"&"+types+"&"+sensor+"&"+key;


            // Output format
            String output = "json";

            // Building the url to the web service
            String url = "https://maps.googleapis.com/maps/api/place/autocomplete/"+output+"?"+parameters;

            System.out.println(url);

            try{
                // Fetching the data from we service
                data = downloadUrl(url);
            }catch(Exception e){
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            // Creating ParserTask
            parserTask = new ParserTask();

            // Starting Parsing the JSON string returned by Web Service
            parserTask.execute(result);
        }
    }
    /** A class to parse the Google Places in JSON format */
    private class ParserTask extends AsyncTask<String, Integer, List<HashMap<String,String>>>{

        JSONObject jObject;

        @Override
        protected List<HashMap<String, String>> doInBackground(String... jsonData) {

            List<HashMap<String, String>> places = null;

            PlaceJSONParser placeJsonParser = new PlaceJSONParser();

            try{
                jObject = new JSONObject(jsonData[0]);

                // Getting the parsed data as a List construct
                places = placeJsonParser.parse(jObject);

                System.out.println(places.get(0).keySet());

            }catch(Exception e){
                Log.d("Exception",e.toString());
            }
            return places;
        }

        @Override
        protected void onPostExecute(List<HashMap<String, String>> result) {

            String[] from = new String[] { "description"};
            int[] to = new int[] { android.R.id.text1 };

            // Creating a SimpleAdapter for the AutoCompleteTextView
            //TODO some error
            SimpleAdapter adapter = new SimpleAdapter(getActivity(), result, android.R.layout.simple_list_item_1, from, to);

            // Setting the adapter
            atvDropOff.setAdapter(adapter);
        }
    }



    //show polilines
    public void showPath(double pkx,double pky,double dox,double doy){

        String url = getDistanceOnRoad(pkx,pky,dox,doy);
        DownloadTask downloadTask = new DownloadTask();

        downloadTask.execute(url);
    }

    private String getDistanceOnRoad(double latitude, double longitude,
                                     double prelatitute, double prelongitude) {
        String result_in_kms = "";
        String url = "http://maps.google.com/maps/api/directions/json?origin="
                + latitude + "," + longitude + "&destination=" + prelatitute
                + "," + prelongitude + "&sensor=false&units=metric";


        System.out.println(url);
        return url;
    }

    // Fetches data from url passed
    private class DownloadTask extends AsyncTask<String, Void, String>{

        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try{
                // Fetching the data from web service
                data = downloadUrl(url[0]);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }
            return data;
        }

        // Executes in UI thread, after the execution of
        // doInBackground()
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            DrawPath drawPathTasl = new DrawPath();

            // Invokes the thread for parsing the JSON data
            drawPathTasl.execute(result);

        }
    }

    private class DrawPath extends AsyncTask<String, Integer, List<List<HashMap<String,String>>>>
    {

        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            System.out.println(jsonData[0]);
            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;


            try{
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parsers = new DirectionsJSONParser();



                // Starts parsing data
                routes = parsers.parse(jObject);
                parsers.getDistance(jObject);
                estimateDistance = parsers.getDistance();
                System.out.println("---------");
                System.out.println(estimateDistance);
                estimateTime = parsers.getTime();

                 preReservationFragment = new PreReservationFragment();

                //send distance to preReservationFragment
                Bundle bundle = new Bundle();

                estimateDistance = estimateDistance.trim().substring(0,estimateDistance.length()-2);
                System.out.println(estimateDistance.trim());

                if(Double.parseDouble(estimateDistance) != 0){
                    bundle.putDouble("DISTANCE", Double.parseDouble(estimateDistance.trim()));
                }else {
                    bundle.putInt("DISTANCE", 0);
                }


                // set Fragmentclass Arguments
                preReservationFragment.setArguments(bundle);


                transaction = getChildFragmentManager().beginTransaction();
                transaction.replace(R.id.bottemFramLayout, preReservationFragment, "pre");
                transaction.commit();





            }catch(Exception e){
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;
            MarkerOptions markerOptions = new MarkerOptions();

            // Traversing through all the routes
            for(int i=0;i<result.size();i++){
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for(int j=0;j<path.size();j++){
                    HashMap<String,String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(10);
                lineOptions.color(Color.RED);
            }

            // Drawing polyline in the Google Map for the i-th route
            if(polyline != null){
                polyline.remove();
            }
            polyline = googleMap.addPolyline(lineOptions);

        }
    }

    ///////send reservation
    private void requestData() {

        String loginURL = SERVER_URL+"AddReservation.php";
        Log.e("Chahturanga      URL", loginURL);

        RequestPackage p = new RequestPackage();
        p.setMethod("GET");
        p.setUri(loginURL);
        p.setParam("customerID", String.valueOf(customerID));
        //p.setParam("date",date );
        //p.setParam("time", time);
        p.setParam("pkx",String.valueOf(center.latitude) );
        p.setParam("pky",String.valueOf(center.longitude) );
        p.setParam("pkAddress",formattedAddress );
        p.setParam("dox",String.valueOf(dropLoc.latitude));
        p.setParam("doy",String.valueOf(dropLoc.longitude) );
        p.setParam("doAddress",dropAddress );


        Log.e("Chahturanga      URLgo", loginURL);

        AddReservationTask task = new AddReservationTask();
        task.execute(p);
    }

    private class AddReservationTask extends AsyncTask<RequestPackage,String,String> {
        //AlertDialogManager alert = new AlertDialogManager();




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
            super.onPostExecute(result);

            Log.e("Chahturanga      result", result);


            String reservationID = result;

            gotpShowDetailActivity(reservationID);

        }


    }

    private  void gotpShowDetailActivity(String reservationID){

        DriverReqsetFragment driverReqsetFragment = new DriverReqsetFragment();
        Bundle bundle = new Bundle();
        bundle.putString("RESERVATIONID",reservationID);
        bundle.putDouble("CENTERLAT",pickupLatLng.latitude);
        bundle.putDouble("CENTERLONG",pickupLatLng.longitude);
        //todo vehicle detail
        bundle.putInt("VEHICLEID",1);

        driverReqsetFragment.setArguments(bundle);

        FragmentTransaction t = getFragmentManager().beginTransaction();
        t = getChildFragmentManager().beginTransaction();
        t.replace(R.id.bottemFramLayout, driverReqsetFragment);
        t.addToBackStack(null);
        t.commit();


    }

    public GoogleMap getmMapView(){
        return this.googleMap;


    }

    public View getFrameLayout(){
        return v.findViewById(R.id.bottemFramLayout);
    }


    private void getAddressData() {

        String loginURL =SERVER_URL+ "test.php";
        Log.e("Chahturanga      URL", loginURL);

        RequestPackage p = new RequestPackage();
        p.setMethod("GET");
        p.setUri(loginURL);

        TestTask task = new TestTask();
        task.execute(p);
    }

    private class TestTask extends AsyncTask<RequestPackage,String,String> {





        @Override
        protected void onPreExecute() {
            super.onPreExecute();
          ;

        }



        @Override
        protected String doInBackground(RequestPackage... params) {

            String content = HttpManager.getData(params[0]);
            Log.e("Chahturanga    content", content);



            return content;
        }



        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            Log.e("Chahturanga      result", result);

            tvDropOffplace.setText(dropAddress);
            MarkerOptions m = new MarkerOptions().position(dropLoc).title("pickup");
            m.icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_RED));
            googleMap.addMarker(m);








        }


    }










}