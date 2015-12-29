package com.example.chathuranga_pamba.sonitcabs_passenger;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import static com.example.chathuranga_pamba.sonitcabs_passenger.CommonUtilities.SERVER_URL;

public class RegisterActivity extends AppCompatActivity {

    EditText etFirstName,etLastName,etUsername,etTelephone,etPassword,etRePassword;
    Button btRegister,btCancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initComponents();


    }


    public void initComponents(){

        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etLastName  = (EditText) findViewById(R.id.etLastName);
        etTelephone = (EditText) findViewById(R.id.etTelephone);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etRePassword = (EditText) findViewById(R.id.etRePassword);


        btRegister = (Button) findViewById(R.id.btRegister);


        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println(etPassword.getText().toString());
                System.out.println(etRePassword.getText().toString());

                if (etPassword.getText().toString().equals(etRePassword.getText().toString())) {
                    requestData();
                } else {
                    AlertDialogManager alert = new AlertDialogManager();

                    alert.showAlertDialog(RegisterActivity.this, "Not Matching", "Passwords are not matching", false);
                }

            }
        });
    }

    private void requestData() {

        String loginURL = SERVER_URL+"RegisterCustomer.php";
        Log.e("Chahturanga      URL", loginURL);

        RequestPackage p = new RequestPackage();
        p.setMethod("GET");
        p.setUri(loginURL);
        p.setParam("firstname", etFirstName.getText().toString());
        p.setParam("lastname", etLastName.getText().toString());
        p.setParam("telephone", etTelephone.getText().toString());
        p.setParam("username", etUsername.getText().toString());
        p.setParam("password", etPassword.getText().toString());

        Log.e("Chahturanga      URLgo", loginURL);

        RegisterTask task = new RegisterTask();
        task.execute(p);
    }



    private class RegisterTask extends AsyncTask<RequestPackage,String,String> {
        AlertDialogManager alert = new AlertDialogManager();




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

            if("1".equals(result.trim().toString())){
                alert.showAlertDialog(RegisterActivity.this,"Register","Successfully Registered",false);

                startActivity(new Intent(getApplication(),LoginActivity.class));
            }else{
                alert.showAlertDialog(RegisterActivity.this,"Register Error",result,false);
            }


            //pbLogin.setVisibility(View.INVISIBLE);
            //btLogin.setEnabled(true);
        }


    }



}
