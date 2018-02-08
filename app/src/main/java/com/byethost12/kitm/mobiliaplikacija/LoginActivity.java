package com.byethost12.kitm.mobiliaplikacija;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTitle(R.string.login_label);

        Button btnLogin = (Button) findViewById(R.id.login);
        Button btnRegister = (Button) findViewById(R.id.register);
        final EditText etUsername = (EditText) findViewById(R.id.username);
        final EditText etPassword = (EditText) findViewById(R.id.password);
        final CheckBox cbRememberMe = (CheckBox) findViewById(R.id.rememberMe);

        final User user = new User(LoginActivity.this);

        etUsername.setError(null);
        etPassword.setError(null);

        cbRememberMe.setChecked(user.isRemembered());

        if (user.isRemembered()) {
            etUsername.setText(user.getUsernameForLogin(), TextView.BufferType.EDITABLE);
            etPassword.setText(user.getPasswordForLogin(), TextView.BufferType.EDITABLE);
        } else {
            etUsername.setText("", TextView.BufferType.EDITABLE);
            etPassword.setText("", TextView.BufferType.EDITABLE);
        }

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToSearchActivity = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(goToSearchActivity);

            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //cia vykdomas kodas, kai paspaudziamas mygtukas
                boolean cancel = false;

                if (!Validation.isValidCredentials(etUsername.getText().toString())) {
                    cancel = true;
                    etUsername.requestFocus();
                    etUsername.setError(getResources().getString(R.string.login_invalid_username));
                    //Toast.makeText(LoginActivity.this, getResources().getString(R.string.login_invalid_username),
                    //Toast.LENGTH_SHORT).show();
                } else if (!Validation.isValidCredentials(etPassword.getText().toString())) {
                    cancel = true;
                    etPassword.requestFocus();
                    etPassword.setError(getResources().getString(R.string.login_invalid_password));
                    // Toast.makeText(LoginActivity.this, getResources().getString(R.string.login_invalid_password),
                    // Toast.LENGTH_SHORT).show();
                } else { // praėjo validaciją, kreipiamės į duomenų bazę
                    DatabaseSQLite databaseSQLite = new DatabaseSQLite(getApplicationContext());

                    if (databaseSQLite.isValidUser(etUsername.getText().toString(),
                            etPassword.getText().toString())) { // rado vartotoją
                        cancel = false;
                    } else { // nerado vartotojo
                        cancel = true;
                        Toast.makeText(LoginActivity.this, "Tokio vartotojo duomenų bazėje nėra",
                                Toast.LENGTH_SHORT).show();
                    }

                }

                if (!cancel) {
                    Toast.makeText(LoginActivity.this,
                            "Prisijungimo vardas: " + etUsername.getText().toString() + "\n" +
                                    "Slaptažodis: " + etPassword.getText().toString(), Toast.LENGTH_SHORT).show();

                    user.setUsernameForLogin(etUsername.getText().toString());
                    user.setPasswordForLogin(etPassword.getText().toString());
                    if (cbRememberMe.isChecked()) {
                        user.setRememberMeKeyForLogin(true);
                    } else {
                        user.setRememberMeKeyForLogin(false);
                    }

                    Intent goToSearchActivity = new Intent(LoginActivity.this, SearchActivity.class);
                    startActivity(goToSearchActivity);
                }

            }
        });
    }

}
