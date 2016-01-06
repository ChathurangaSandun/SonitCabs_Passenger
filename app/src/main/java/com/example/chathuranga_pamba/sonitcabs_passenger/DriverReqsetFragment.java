package com.example.chathuranga_pamba.sonitcabs_passenger;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.chathuranga_pamba.sonitcabs_passenger.Parsers.JobDetailJSONParser;
import com.example.chathuranga_pamba.sonitcabs_passenger.models.JobDetail;

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

    Timer timer;
    int a;

    AlertDialogManager alert = new AlertDialogManager();

    String reservaitonID;

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
        }, 1000, 5000);








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
                JobDetail jb;


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

                driverNamesAndCarDetailsFragment.setArguments(bundle);
                //TODO correct fragemtn error

                FragmentTransaction t = getFragmentManager().beginTransaction();
                t = getChildFragmentManager().beginTransaction();
                //t.add(R.id.bottemFramLayout, driverNamesAndCarDetailsFragment);
                //t.addToBackStack(null);
                //t.commit();


                //showPath();*/


            }


        }
    }


}
