package com.example.chathuranga_pamba.sonitcabs_passenger;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import static com.example.chathuranga_pamba.sonitcabs_passenger.HomeFragment.dropAddress;
import static com.example.chathuranga_pamba.sonitcabs_passenger.HomeFragment.dropLoc;

public class place extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        PlaceAutocompleteFragment fragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        fragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) { // Handle the selected Place
                dropLoc = place.getLatLng();
                dropAddress = place.getName() +", "+place.getAddress().toString();
                System.out.println(dropAddress);



            }

            @Override
            public void onError(Status status) { // Handle the error
            }


        });

        /*try {
            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .build(this);
            startActivityForResult(intent,1);
        } catch (GooglePlayServicesRepairableException e) {
            GooglePlayServicesUtil
                    .getErrorDialog(e.getConnectionStatusCode(), getParent(), 0);
        } catch (GooglePlayServicesNotAvailableException e) {
            // Handle the exception
        }*/


    }
}
