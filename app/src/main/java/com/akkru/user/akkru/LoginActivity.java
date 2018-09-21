package com.akkru.user.akkru;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.concurrent.TimeoutException;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    Toolbar toolbar;
    ProgressDialog progressDialog;
    private EditText loginusername , loginpassword;
    private Button btnlogin;
    private Button btnlinkSignUp;
//    private ImageButton btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        toolbar = findViewById(R.id.toolbar);
        loginusername = (EditText) findViewById(R.id.login_input_username);
        loginpassword = (EditText) findViewById(R.id.login_input_password);
        btnlogin = (Button) findViewById(R.id.btn_login);
        btnlinkSignUp = (Button) findViewById(R.id.btnLinkSignUp);
//        btn_back = (ImageButton) findViewById(R.id.btn_back);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser(loginusername.getText().toString(),loginpassword.getText().toString());
            }
        });

        btnlinkSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(i);
            }
        });
//
//        btn_back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent f = new Intent(getApplicationContext(),IntroActivity.class);
//                startActivity(f);
//            }
//        });

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void loginUser(final String username , final String password){
        String cancel_reg_tag = "login";
        progressDialog.setMessage("Login . . .");
        showDialog();

        if (username.equals("admin")  && password.equals("admin") ){

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            String errorMsg = "wrong username or password";
            Toast.makeText(getApplicationContext(),
                    errorMsg, Toast.LENGTH_LONG).show();
            hideDialog();
        }

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
