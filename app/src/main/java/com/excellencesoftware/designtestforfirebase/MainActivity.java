package com.excellencesoftware.designtestforfirebase;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button btnRegister;
    private DatabaseWrapper mDBWrapper = new FirebaseWrapper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        mDBWrapper.connect();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            EditText etEmail = (EditText) findViewById(R.id.etEmail);
            EditText etPassword = (EditText) findViewById(R.id.etPassword);

            @Override
            public void onClick(View v) {
                mDBWrapper.setRegistrationResultListener(new RegistrationResultListener() {
                    @Override
                    public void onComplete(String item) {
                        Toast.makeText(MainActivity.this, item,
                                Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(Throwable error) {
                        Toast.makeText(MainActivity.this, "Error: " +
                            error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

                mDBWrapper.createAccount(etEmail.getText().toString(),
                        etPassword.getText().toString());
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        mDBWrapper.connect();
    }

    protected void onStop() {
        super.onStop();
        mDBWrapper.disconnect();
        mDBWrapper.removeRegistrationResultListener();
    }
}