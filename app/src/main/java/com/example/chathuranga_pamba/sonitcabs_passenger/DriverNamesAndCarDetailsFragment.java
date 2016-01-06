package com.example.chathuranga_pamba.sonitcabs_passenger;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class DriverNamesAndCarDetailsFragment extends Fragment {


    public DriverNamesAndCarDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_driver_names_and_car_details, container, false);


        String name = getArguments().getString("NAME");
        String nnumberplate = getArguments().getString("NNUMBERPLATE");
        String tpnumber = getArguments().getString("TPNUMBER");


        TextView tvName = (TextView) v.findViewById(R.id.tvName);
        TextView tvPlateNumber = (TextView) v.findViewById(R.id.tvPlateNumber);

        tvName.setText(name);
        tvPlateNumber.setText(nnumberplate);



        return v;
    }


}
