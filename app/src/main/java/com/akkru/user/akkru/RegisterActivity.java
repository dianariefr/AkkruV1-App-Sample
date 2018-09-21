package com.akkru.user.akkru;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";
    private EditText insuranceid , firstname , lastname ,city ,email ;
    ProgressDialog progressDialog;
    private Button btn_signup;
    private ImageButton back_btn;

    @Override

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        insuranceid = (EditText) findViewById(R.id.signup_input_insuranceid);
        firstname = (EditText) findViewById(R.id.signup_input_firstname);
        lastname = (EditText) findViewById(R.id.signup_input_lastname);
        city = (EditText) findViewById(R.id.signup_input_city);
        email = (EditText) findViewById(R.id.signup_input_email);
        back_btn = (ImageButton) findViewById(R.id.btn_back);

        btn_signup = (Button) findViewById(R.id.btn_signup);
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpUser(insuranceid.getText().toString(), firstname.getText().toString(),lastname.getText().toString(),city.getText().toString(),email.getText().toString());

            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(i);
            }
        });


    }

    private void signUpUser(final String insuranceid , final String firstname ,final String lastname , final String city , final String email ){
        String cancel_reg_tag = "signup";
        progressDialog.setMessage("Signing Up . . .");
        showDialog();

    }

    private void showDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    private void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }
}
