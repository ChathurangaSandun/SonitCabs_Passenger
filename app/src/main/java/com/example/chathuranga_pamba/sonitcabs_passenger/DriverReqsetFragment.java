package com.example.chathuranga_pamba.sonitcabs_passenger;


import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.chathuranga_pamba.sonitcabs_passenger.Parsers.DirectionsJSONParser;
import com.example.chathuranga_pamba.sonitcabs_passenger.Parsers.JobDetailJSONParser;
import com.example.chathuranga_pamba.sonitcabs_passenger.models.JobDetail;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.vision.Frame;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.chathuranga_pamba.sonitcabs_passenger.CommonUtilities.SERVER_URL;



/**
 * A simple {@link Fragment} subclass.
 */
public class DriverReqsetFragment extends Fragment {
    ProgressBar progressBar;
    View v;
    GoogleMap googleMap;

    Timer timer;
    int a;

    AlertDialogManager alert = new AlertDialogManager();

    String reservaitonID;

    JobDetail jb;

    double pickupLat,pickuoLong;

    String estimateTime,estimateDistance;

    Polyline polyline;
    TextView tvTimming,tvName,tvPlateNumber,tvDistance;




    public DriverReqsetFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         v = inflater.inflate(R.layout.fragment_driver_reqset, container, false);
        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setProgress(0);

        reservaitonID=getArguments().getString("RESERVATIONID");
        pickupLat = getArguments().getDouble("CENTERLAT");
        pickuoLong= getArguments().getDouble("CENTERLONG");




        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {


                requestDriverDetails(Integer.parseInt(reservaitonID.trim()));


                switch (a) {
                    case 1:
                        progressBar.setProgress(20);
                        break;
                    case 2:
                        progressBar.setProgress(40);
                        break;
                    case 3:
                        progressBar.setProgress(60);
                        break;
                    case 4:
                        progressBar.setProgress(80);
                        break;
                    case 5:
                        progressBar.setProgress(0);
                        timer.cancel();
                        break;
                }


                System.out.println(a);
                a++;
            }
        }, 1000, 6000);



        tvTimming = (TextView)v.findViewById(R.id.tvTimming);

        tvName = (TextView)v.findViewById(R.id.tvName);
        tvPlateNumber = (TextView)v.findViewById(R.id.tvPlateNumber);







        return v;

    }

    private void requestDriverDetails(int reservationID) {

        String loginURL = SERVER_URL+"GetJobDetail.php";
        Log.e("Chahturanga      URL", loginURL);

        RequestPackage p = new RequestPackage();
        p.setMethod("GET");
        p.setUri(loginURL);
        p.setParam("reservationID", String.valueOf(reservationID));


        Log.e("Chahturanga      URLgo", loginURL);

        GetJobDetailTask task = new GetJobDetailTask();
        task.execute(p);
    }

    private class GetJobDetailTask extends AsyncTask<RequestPackage,String,String> {
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

            if (result.toString().trim().equals("0")) {
                Log.e("result is null", "0");
                if (a == 5) {
                    int b = alert.showAlertDialog(getActivity(), "Sorry", "All cars are busy.", true);
                    if (b == 1) {

                        //TODO place home fragment
                        Intent toHome = new Intent(getActivity(), HomeActivity.class);
                        startActivity(toHome);
                    }


                }


            } else {
                Log.e("Chahturanga      result", result);
                timer.cancel();
                progressBar.setProgress(100);



                List<JobDetail> jobdetail = JobDetailJSONParser.parseFeed(result);

                jb = jobdetail.get(0);

                int driverPhoneNumber = jb.getTelephoneNumber();


                //carLocation = getAddress(jb.getLatitude(), jb.getLongtiitude());
                //driverLat = jb.getLatitude();
                //driverLon = jb.getLongtiitude();


                //tvPlateNumber.setText("Vehicle Detail \nPlate Number : " + jb.getPlateNumber() + "\nNow Loction : " + carLocation + "\n");



                DriverNamesAndCarDetailsFragment driverNamesAndCarDetailsFragment = new DriverNamesAndCarDetailsFragment();
                Bundle bundle = new Bundle();
                bundle.putString("NAME",jb.getFirstName());
                bundle.putString("NNUMBERPLATE",jb.getPlateNumber());
                bundle.putString("TPNUMBER","0"+jb.getTelephoneNumber());
                //bundle.putString("NAME",jb.getFirstName());

                HomeFragment homeFragment = (HomeFragment) getParentFragment();
                 googleMap = homeFragment.getmMapView();
                MarkerOptions marker = new MarkerOptions().position(
                        new LatLng(jb.getLatitude(),jb.getLongtiitude())).title("car posistion");
                googleMap.addMarker(marker);

                driverNamesAndCarDetailsFragment.setArguments(bundle);


                /*FragmentTransaction t = null;
                t = getChildFragmentManager().beginTransaction();
                t.replace(frameLayout.getId(), driverNamesAndCarDetailsFragment);
                t.commit();*/



                showPath();

                tvName.setText(jb.getFirstName());

                tvPlateNumber.setText(jb.getPlateNumber());



            }


        }
    }

    public void showPath(){

        String url = getDistanceOnRoad(jb.getLatitude(),jb.getLongtiitude(),pickupLat,pickuoLong);
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













            }catch(Exception e){
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            System.out.println(result);

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
                lineOptions.color(Color.BLUE);
            }

            // Drawing polyline in the Google Map for the i-th route
            if(polyline != null){
                polyline.remove();
            }
            polyline =googleMap.addPolyline(lineOptions);

            tvTimming.setText("Please wait atleast "+estimateTime +"  and It comes from "+estimateDistance+ "   ");

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


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("WORKAROUND_FOR_BUG_19917_KEY", "WORKAROUND_FOR_BUG_19917_VALUE");
        super.onSaveInstanceState(outState);
    }
}
